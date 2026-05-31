# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

---

## Project

**Setia Corporate Website** on Adobe Experience Manager as a Cloud Service (AEMaaCS).  
Group ID: `br.com.setia` · Artifact: `setia` · Version: `1.0.0-SNAPSHOT`  
AEM SDK: `2026.5.26309` · Core Components: `2.28.0` · Java 11 · Maven 3.3.9+

Full specs live in `docs/` (01–07) and implementation plans in `docs/plans/` (01–09).

**Stack:** HTL/Sightly templates · OSGi DS annotations (no Felix SCR) · Sling Models · JUnit 5 with `io.wcm.testing.mock.aem` (AemContext)

---

## Build & Deploy Commands

```bash
# Full build (no deploy)
mvn clean install -DskipTests

# Build + deploy to local AEM author (requires aem-remote profile in ~/.m2/settings.xml)
mvn clean install -PautoInstallSinglePackage,aem-remote

# Deploy only ui.apps (components, clientlibs — fastest for component iteration)
mvn clean install -pl ui.apps -PautoInstallPackage,aem-remote

# Deploy only core bundle (Java models)
mvn clean install -pl core -PautoInstallBundle,aem-remote

# Unit tests only
mvn clean test

# Frontend only (compiles SCSS/TS → ClientLibs in ui.apps)
cd ui.frontend && npm run prod

# Frontend watch + live-sync to AEM (requires running AEM instance)
cd ui.frontend && npm run watch
```

**The `aem-remote` profile** must be defined in `~/.m2/settings.xml` with `sling.user` / `sling.password` and `aem.host`/`aem.port`. Never commit credentials.

The HTL maven plugin validates all `.html` files in `ui.apps` during build — HTL compile errors fail the build before packaging.

---

## Module Architecture

```
setia/
├── core/              OSGi bundle: Sling Models, servlets, OSGi services
├── ui.apps/           JCR content: HTL components, clientlibs
├── ui.frontend/       Webpack source: SCSS, TypeScript → compiled into ui.apps/clientlibs
├── ui.content/        Mutable content: editable templates, policies, XFs, pages, DAM stubs
├── ui.config/         OSGi configs per runmode
├── ui.apps.structure/ Repository structure definition
├── dispatcher/        Cache and filter rules
├── all/               Aggregated package (embeds all modules)
└── docs/              Specs (docs/) and implementation plans (docs/plans/)
```

### Key data flows

**Frontend build pipeline:**  
`ui.frontend/src/main/webpack/` → `npm run prod` → `ui.frontend/dist/` → `aem-clientlib-generator` → `ui.apps/src/main/content/jcr_root/apps/setia/clientlibs/`

Never edit generated files in `ui.apps/clientlibs/css/` or `ui.apps/clientlibs/js/` directly — they are overwritten on every npm build.

**ClientLib loading:**  
`customheaderlibs.html` loads CSS from category `setia.base` (which embeds `setia.grid` + Core Component libs).  
`customfooterlibs.html` loads JS from `setia.base` (async). Site styles/JS live in `setia.site` (category defined in `clientlib-site`).  
Clientlibs always declare both `categories` and `dependencies` in their `.content.xml`.

**Sling Model auto-registration:**  
The `org.apache.sling.bnd.models.ModelsScannerPlugin` (parent pom line ~179) auto-generates the `Sling-Model-Packages` OSGi header — no manual bnd configuration needed. All `@Model` classes in `br.com.setia.core.models` are registered automatically.

---

## Component Conventions

All custom Setia components live at `/apps/setia/components/<name>`.

**Component group:** `Setia` for content components; `Setia - Content` for structural/proxy components (XF reference, container proxies).

**Each component ships:**
- `.content.xml` — component definition (`jcr:primaryType="cq:Component"`)
- `_cq_dialog/.content.xml` — author dialog
- `<name>.html` — HTL template (use `data-sly-use` for model binding, never inline Java)
- `_cq_editConfig.xml` — drop targets and refresh listeners (components with DAM images)
- `_cq_design_dialog/.content.xml` — design dialog for policy-level options (card-grid, page-banner, cta-section)
- `_*.scss` in `ui.frontend/src/main/webpack/components/` — component styles

**HTL rules (enforced by htl-maven-plugin — violations fail the build):**
- Dynamic `style` attributes require `@ context='styleString'`
- Use `data-sly-use.fmt="${'pattern {0}' @ format=[var]}"` then `${fmt @ context='styleString'}` to build style strings
- Do not use `data-sly-attribute.style` — the plugin rejects it
- All `<parameters>` nodes inside `cq:dropTargets` need `xmlns:sling` declared on the root element
- Do not mix `${properties.x}` and `${model.x}` in the same HTL template — pick one per component

**Sling Models:**
```java
// Adapt from both Request and Resource so the model works in both component and servlet contexts
@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class MyModel {
    @ValueMapValue private String myProp;           // simple property
    @Inject @Default(values = "fallback") private String optional; // optional with default
    @ChildResource private List<ItemModel> items;   // multifield via child nodes
}
```
- Use `@Inject` + `@Default` for optional fields instead of null-checking
- Multifield items use `@ChildResource` on a `List<InnerModel>` where the inner class is also a `@Model`
- Never use the deprecated `WCMUsePojo`

**Java / OSGi rules:**
- Never hardcode `/content` paths — use `ResourceResolver` to resolve paths
- Never call `session.save()` — use `ResourceResolver.commit()`
- Use OSGi DS annotations (`@Component`, `@Service`, `@Reference`) — never Felix SCR annotations

---

## Template & Policy Architecture

Two editable templates under `/conf/setia/settings/wcm/templates/`:

| Template | Used by | Locked structure |
|----------|---------|-----------------|
| `landing-page` | Home (`/content/setia/us/en`) | Header XF → home-hero → content container → cta-section → Footer XF |
| `page-content` | Company, Services, Solutions | Header XF → page-banner → content container → cta-section → Footer XF |

Both use **`setia/components/experiencefragment`** (a generic XF reference component) for header and footer — the deprecated `xf-header-reference` / `xf-footer-reference` pattern is not used here.

**Policy nodes** live in `ui.content/.../conf/setia/settings/wcm/policies/.content.xml`:
- `policy_content_main` — allows 8 components in the Content Page container
- `policy_landing_main` — allows 4 components in the Landing Page container
- `policy_cardgrid`, `policy_cta`, `policy_pagebanner` — component-level policies with `cq:styleGroups` (Style System themes)

**Filter modes** (`ui.content/META-INF/vault/filter.xml`):
- `/conf/setia`, `/content/setia`, `/content/experience-fragments/setia` → **replace** (no `mode=` attr = replace default)
- `/content/dam/setia` → **merge** (avoid binary churn on DAM assets)

> When template structure refactors don't apply after deploy, the first thing to check is filter mode — `mode="merge"` silently keeps stale JCR nodes.

---

## SCSS Architecture

Entry point: `ui.frontend/src/main/webpack/site/main.scss`  
Import order (matters — variables/mixins must precede component partials):
1. `_variables.scss` — design tokens (colors, spacing, typography, breakpoints, z-index)
2. `_mixins.scss` — `respond-to`, `container`, `section-spacing`, `heading-responsive`, `transition`, etc.
3. `_reset.scss`, `_base.scss`, `_typography.scss`, `_layout.scss`
4. `../components/**/*.scss` — globbed component partials
5. `./styles/*.scss` — XF-specific styles (`experiencefragment_header.scss`, `experiencefragment_footer.scss`, `container_main.scss`)
6. `_utilities.scss`

**Do not `@import` variables/mixins inside component partials** — they are already in scope from the glob order.

Key design tokens: `$color-primary-dark-blue: #10245a`, `$color-primary-green: #7ED321`, `$spacing-unit: 8px` (all spacing is multiples).

---

## Content & DAM Bootstrap

Source assets for the website live in `docs/assets/` (`images/`, `pdf/`, `favicon/`).  
DAM destination paths are documented in `docs/plans/08-dam-asset-strategy.md`.

**Partners are AWS, Neo4j, IBM, ITS** (files: `parceria_aws.png`, `parceria_neo.png`, `parceria_ibm.png`, `parceria_its.png`). There is no SAP asset.

The mascot (footer CTA / "Get in touch") is `docs/assets/images/contact_area.png` → DAM path `/content/dam/setia/logos/brand/contact_area.png`.

Authored page content is version-controlled in `ui.content` (not left only in a running AEM instance). After editing pages in AEM Author, export with Package Manager and commit to `ui.content`.

---

## XF Paths

```
/content/experience-fragments/setia/us/en/site/header/master
/content/experience-fragments/setia/us/en/site/footer/master
```

Both render via `setia/components/xfpage` and use the `xf-web-variation` template.

---

## AEM Author URLs (local)

| Tool | URL |
|------|-----|
| Author | `http://localhost:4502` |
| Sites console | `http://localhost:4502/sites.html/content/setia` |
| Templates | `http://localhost:4502/libs/wcm/core/content/sites/templates.html/conf/setia` |
| DAM | `http://localhost:4502/assets.html/content/dam/setia` |
| Experience Fragments | `http://localhost:4502/aem/experience-fragments.html/content/experience-fragments/setia` |
| Rebuild ClientLibs | `http://localhost:4502/libs/granite/ui/content/dumplibs.rebuild.html` |
