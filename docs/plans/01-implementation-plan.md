# Implementation Plan - Setia AEM Website

## Executive Summary

This document outlines the complete implementation plan for the Setia corporate website on Adobe Experience Manager (AEM) Cloud Service.

**Project Scope:**
- 4 pages (Home, Company, Services, Solutions)
- 13 reusable AEM components
- 2 editable templates
- 2 Experience Fragments (Header, Footer)
- Responsive design (mobile, tablet, desktop)
- Webpack-based frontend build system

**Estimated Timeline:** 6-8 weeks  
**Team Size:** 2-3 developers (1 AEM + 1-2 Frontend)

---

## Phase 1: Foundation Setup (Week 1)

### Objectives
- Set up development environment
- Configure project structure
- Establish frontend build pipeline
- Create design system foundation

### Tasks

#### 1.1 Environment Setup
**Duration:** 2 days  
**Owner:** AEM Developer

- [ ] Install AEM SDK locally (2026.5.26309 or later)
- [ ] Verify Maven (3.3.9+) and Java (11+) versions
- [ ] Clone project repository
- [ ] Configure `aem-remote` profile in `~/.m2/settings.xml` (holds `sling.user`/`sling.password`); never commit credentials
- [ ] Run initial build: `mvn clean install -PautoInstallSinglePackage,aem-remote`
- [ ] Verify AEM Author access: http://localhost:4502
- [ ] Configure IDE (IntelliJ/Eclipse/VS Code)

**Success Criteria:**
- AEM running locally
- `mvn ... -PautoInstallSinglePackage,aem-remote` succeeds without password prompts
- Can access AEM Author UI

#### 1.2 Design System Foundation
**Status:** ✅ Largely complete — code under `ui.frontend/src/main/webpack/site/` already implements this phase. Tasks below are marked accordingly; remaining work is integration, not creation.
**Duration:** 2 days
**Owner:** Frontend Developer

- [x] Create SCSS variable file with brand colors per [07 — Design System](../07-design-system.md) — naming matches canonical (`$color-primary-dark-blue`, `$color-primary-green`, `$color-primary-light-blue`)
- [x] Define typography scale (`_typography.scss`, with desktop + mobile sizes via `heading-responsive` mixin)
- [x] Set up spacing system (8px grid, including `$spacing-xxxl`)
- [x] Create mixin library (`respond-to`, `container`, `flex-center`, `transition`, `button-reset`, `visually-hidden`, `section-spacing`, `heading-responsive`)
- [x] Establish breakpoints (576/768/1024/1440)
- [x] Establish z-index scale — matches doc 07 canonical (`base=1`, `dropdown=100`, `sticky=500`, `fixed=1000`, `modal-backdrop=1500`, `modal=2000`, `popover=2500`, `tooltip=3000`)
- [x] Configure webpack build per [06 — Frontend Tech Details](../06-frontend-tech-details.md)
- [x] Test CSS compilation

**Files (already in source):**
- `ui.frontend/src/main/webpack/site/_variables.scss`
- `ui.frontend/src/main/webpack/site/_mixins.scss`
- `ui.frontend/src/main/webpack/site/_reset.scss`
- `ui.frontend/src/main/webpack/site/_typography.scss`
- `ui.frontend/src/main/webpack/site/_base.scss` *(extra, not in original plan)*
- `ui.frontend/src/main/webpack/site/_layout.scss` *(extra, not in original plan)*
- `ui.frontend/src/main/webpack/site/main.scss` (entry point — `@import` order matches doc 06)
- `ui.frontend/src/main/webpack/site/main.ts` (TypeScript entry point — note: doc 06 implies vanilla JS; either update doc 06 to acknowledge TypeScript or migrate to `.js`)

**Success Criteria:**
- ✅ SCSS compiles to CSS
- ✅ Variables and mixins are reusable
- ✅ Build integrates with Maven (verify via `mvn ... -PautoInstallSinglePackage,aem-remote`)
- [ ] Decide and document whether the JS layer is TypeScript or vanilla JS (currently `main.ts`)

#### 1.3 Base Page Component
**Status:** ✅ Already implemented at `/apps/setia/components/page/`. Tasks updated to reflect actual state and the path correction.
**Duration:** 1 day
**Owner:** AEM Developer

> **Path correction:** the plan originally referenced `setia-page` as the folder name. The actual component is at `/apps/setia/components/page/` with `jcr:title="Setia Page"`, and every consumer in the repo (templates under `/conf/setia/...`, content pages, `setia/components/spa` super-type, `SimpleServlet.resourceTypes`) references `setia/components/page`. Renaming would orphan all of them — keep the existing path.

- [x] Create the page component at `/apps/setia/components/page/` with super-type `core/wcm/components/page/v3/page` and `componentGroup=".hidden"`
- [x] Inherit `Page` model from the super-type's `body.html` — no need to bind `com.adobe.cq.wcm.core.components.models.Page` in `setia/components/page` itself (the customheaderlibs/customfooterlibs only need to handle ClientLib includes)
- [x] Set up ClientLib includes:
  - `customheaderlibs.html` calls `setia.base` and `setia.site` (CSS)
  - `customfooterlibs.html` calls `setia.base` and `setia.site` (JS, async)
  - `setia.base` **embeds** `setia.grid` and the Core Components clientlibs (see `clientlibs/clientlib-base/.content.xml`), so callers don't need to invoke `setia.grid` directly — this is the idiomatic AEM pattern; do NOT change it to call `setia.grid` separately
- [x] Create `customheaderlibs.html`
- [x] Create `customfooterlibs.html`
- [ ] Smoke-test page rendering on a fresh deploy (`mvn ... -PautoInstallSinglePackage,aem-remote`)

**Location:** `/apps/setia/components/page/` *(folder name `page`, JCR title `Setia Page`)*

**Success Criteria:**
- ✅ Page component exists and is referenced by all templates and content pages
- ✅ ClientLibs load correctly (`setia.base` → embeds `setia.grid` + CC libs; `setia.site` for site-specific styles)
- ✅ Page properties editable (inherited from `core/wcm/components/page/v3/page`)

---

## Phase 2: Experience Fragments (Week 1-2)

### Objectives
- Create reusable Header and Footer
- Establish global navigation structure
- Set up Experience Fragment references

### Tasks

#### 2.1 Header Experience Fragment
**Status:** ✅ Implemented. Path differs from earlier plan revisions — see callout.
**Duration:** 2 days
**Owner:** AEM + Frontend Developer

> **Path correction:** earlier plan revisions specified `/content/experience-fragments/setia/header`. Source uses the locale-aware `/content/experience-fragments/setia/us/en/site/header/master`, which is the AEMaaCS-recommended pattern when i18n is anticipated. The locale-aware path is correct — keep it. References from templates use this full path.

**AEM Tasks:**
- [x] Create Experience Fragment at `/content/experience-fragments/setia/us/en/site/header` with `master` variation
- [x] Variation uses dedicated XF template `/conf/setia/settings/wcm/templates/xf-web-variation` and renders via `setia/components/xfpage`
- [x] Logo (Core Components Image) populated with `linkURL=/content/setia/us/en` and `alt="Setia"` (SVG asset committed at `master/_jcr_content/root/logo/logo.svg`)
- [x] Navigation (Core Components Navigation) configured with `navigationRoot=/content/setia/us/en`, `skipNavigationRoot=true`, `structureDepth=1`
- [ ] Configure component policies for the XF root container if non-default constraints needed (currently uses `setia/components/container`)

**Frontend Tasks:**
- [x] Style header layout (dark blue background, `position: fixed`, body padding-top offset)
- [x] Implement fixed scroll behavior (z-index `$z-index-fixed`)
- [x] Style navigation menu (active state with green underline)
- [x] Add logo styling (responsive height: 32px mobile, 36px tablet+)
- [x] Implement responsive menu (hamburger toggle dynamically created in JS, animated to X on open)
- [x] Test across breakpoints

**Files (already in source):**
- `ui.frontend/src/main/webpack/site/styles/experiencefragment_header.scss` *(not `components/header.scss` — naming follows the `experiencefragment_<name>` convention used by `xfpage`-rendered fragments)*
- `ui.frontend/src/main/webpack/site/styles/header.js` *(vanilla IIFE; sets `aria-expanded`, closes menu on link click; wired into webpack via `main.ts` wildcard import)*
- `ui.content/.../experience-fragments/setia/us/en/site/header/master/.content.xml`

**Success Criteria:**
- ✅ Header displays on all pages that reference it (currently only `page-content` template — see Phase 2.2 gap below for `landing-page`)
- ✅ Fixed positioning works on scroll
- ✅ Mobile menu functions correctly with `aria-expanded`
- ✅ Navigation links work

#### 2.2 Footer Experience Fragment
**Status:** ⚠️ Content + styling exist but **not wired into any template** — pages currently render without a footer.
**Duration:** 1 day
**Owner:** AEM + Frontend Developer

**AEM Tasks:**
- [x] Create Experience Fragment at `/content/experience-fragments/setia/us/en/site/footer` with `master` variation (uses `xf-web-variation` template, rendered via `setia/components/xfpage`)
- [x] Add "Get in touch" Title (h2)
- [x] Add contact information (`<a href="mailto:connect@setia.com.br">`)
- [x] Add copyright text (`Copyright© Setia Technology 2005-2026. All rights reserved.`)
- [ ] Add mascot image (deferred — design system shows it; not committed yet)
- [ ] **🔴 Wire the footer XF into both template structures** — currently neither `landing-page/structure/.content.xml` nor `page-content/structure/.content.xml` contains an `experiencefragment-footer` node referencing `/content/experience-fragments/setia/us/en/site/footer/master`. The header is wired in `page-content` and in the `page` template-type seed, but the footer is wired nowhere. Pages will render without a footer until this is fixed.
- [ ] **🔴 Wire the header XF into `landing-page/structure`** — the landing-page template's structure is currently an empty editable root container. The header XF reference exists only in `page-content` and the `page` template-type seed.

**Frontend Tasks:**
- [x] Style footer layout (dark blue background, centered content, container max-width)
- [ ] Position mascot character (pending mascot asset)
- [x] Style contact section (Get in touch h2/h3 responsive, contact link with hover)
- [x] Implement responsive layout

**Files (already in source):**
- `ui.frontend/src/main/webpack/site/styles/experiencefragment_footer.scss` *(not `components/footer.scss` — same naming convention as the header)*
- `ui.content/.../experience-fragments/setia/us/en/site/footer/master/.content.xml`

**Success Criteria:**
- [ ] Footer displays on all pages *(blocked: not wired into templates)*
- [ ] Mascot image positioned correctly *(blocked: asset not committed)*
- ✅ Responsive on all devices

#### 2.3 Experience Fragment Reference Component
**Status:** ✅ Implemented — single generic component, not two. The plan's original two-component design was incorrect.
**Duration:** 1 day
**Owner:** AEM Developer

> **Design correction:** the plan originally specified separate `xf-header-reference` and `xf-footer-reference` components. The source uses a single generic `setia/components/experiencefragment` parameterized via `fragmentVariationPath` per instance — this matches Adobe's official Core Components pattern and avoids duplicate component definitions. Other docs that still reference `xf-header-reference` / `xf-footer-reference` (docs 02, 03, 04) should be reconciled to the generic-component pattern in a follow-up sweep.

- [x] Create `setia/components/experiencefragment` component (super-type `core/wcm/components/experiencefragment/v2/experiencefragment`, group `Setia - Content`, `cq:styleElements="[div,section,article,main,aside,header,footer]"`)
- [x] Ship `_cq_editConfig.xml` with `cq:inherit="{Boolean}true"` so the parent edit config (drop targets, listeners) applies
- [x] Set `fragmentVariationPath` on each template instance (header is wired into `page-content` and the `page` template-type seed)
- [ ] Smoke-test: verify edits to the XF master variation propagate to all pages once footer/landing-page wiring is fixed

**Success Criteria:**
- ✅ XF reference component exists and is parameterizable per template instance
- [ ] Changes to XF reflect on all pages *(blocked until footer + landing-page wiring is complete — see 2.2)*

---

## Phase 3: Core Layout Components (Week 2)

### Objectives
- Build foundational layout components
- Establish component patterns
- Create reusable section structures

### Tasks

#### 3.1 Page Banner Component
**Status:** ✅ Mostly implemented. Missing: `_cq_editConfig.xml` for image drop target; design dialog is deferred.
**Duration:** 1 day
**Owner:** AEM + Frontend Developer

**AEM Tasks:**
- [x] Create component structure (`.content.xml` with `componentGroup="Setia"`)
- [x] Create author dialog (`_cq_dialog/.content.xml`) with fields: title, subtitle, backgroundImage
- [ ] Create `_cq_editConfig.xml` with image drop target on `./backgroundImage` and `afteredit=REFRESH_PAGE` *(missing)*
- [x] Theme variants (Dark Blue / Light / Accent) ship via `cq:styleGroups` on the page-banner policy; HTL applies `${currentStyle.cssClasses}`. No instance-level design dialog needed since all variants come from the policy.
- [x] Create HTL template (uses `properties['jcr:title']` for title — inherits from page title; verify this matches the template's locked instance)

**Frontend Tasks:**
- [x] Style banner (dark blue, centered text) — `_page-banner.scss`
- [x] Add background image support
- [x] Implement responsive typography
- [x] Test with/without background image

**Files (already in source):**
- `/apps/setia/components/page-banner/{.content.xml, _cq_dialog/.content.xml, page-banner.html}`
- `ui.frontend/src/main/webpack/components/_page-banner.scss`

**Success Criteria:**
- ✅ Banner displays title and subtitle
- [ ] Background image works via drop-target *(blocked until `_cq_editConfig.xml` ships)*
- ✅ Responsive across devices

#### 3.2 Section Heading Component
**Status:** ✅ Implemented.
**Duration:** 1 day
**Owner:** AEM + Frontend Developer

**AEM Tasks:**
- [x] Create component structure (`componentGroup="Setia"`)
- [x] Create author dialog: title, subtitle, headingLevel, alignment
- [x] Create HTL template with conditional heading levels (defaults alignment to `center`)

**Frontend Tasks:**
- [x] Style heading typography
- [x] Implement alignment options (left, center, right)
- [x] Add spacing (margin-bottom)
- [x] Style subtitle differently from title

**Files (already in source):**
- `/apps/setia/components/section-heading/{.content.xml, _cq_dialog/.content.xml, section-heading.html}`
- `ui.frontend/src/main/webpack/components/_section-heading.scss`

**Success Criteria:**
- ✅ Renders H2, H3, or H4 based on selection
- ✅ Alignment works
- ✅ Consistent spacing

#### 3.3 Rich Text Section Component
**Status:** ✅ Implemented.
**Duration:** 1 day
**Owner:** AEM + Frontend Developer

**AEM Tasks:**
- [x] Create component structure (`componentGroup="Setia"`)
- [x] Create author dialog: title (optional), richtext (RTE) stored as `text`, includeList (checkbox), listItems (multifield)
- [x] Create HTL template (escapes rich text with `@ context='html'`)

**Frontend Tasks:**
- [x] Style rich text content
- [x] Style list items
- [x] Ensure proper spacing
- [x] Style links within content

**Files (already in source):**
- `/apps/setia/components/rich-text-section/{.content.xml, _cq_dialog/.content.xml, rich-text-section.html}`
- `ui.frontend/src/main/webpack/components/_rich-text-section.scss`

**Success Criteria:**
- ✅ Rich text renders with formatting
- ✅ Optional list displays
- ✅ Links are styled

---

## Phase 4: Content Components (Week 3)

### Objectives
- Build primary content display components
- Implement card-based layouts
- Create interactive components

### Tasks

#### 4.1 Card Grid Component
**Status:** ⚠️ Mostly implemented; missing container flag, editConfig, design dialog, and HTL has model/properties mixing.
**Duration:** 2 days
**Owner:** AEM + Frontend Developer

**AEM Tasks:**
- [ ] **🔴 Add `cq:isContainer="{Boolean}true"` to `card-grid/.content.xml`** — currently absent; without it, template policies cannot constrain nested children, so this is a blocker for Phase 6 policy work
- [x] Create author dialog with layout options (columns, gap) — uses `columns` property (e.g. `"3"`)
- [x] Create multifield for cards (`cardItems` node): icon, title, description, linkUrl, linkText, backgroundColor
- [x] Design dialog (`_cq_design_dialog`) exposes `allowedColumns` (multi: 2/3/4) and `defaultColumns`. Theme group (Green / Blue / Neutral) ships via the policy's `cq:styleGroups`. HTL applies `${currentStyle.cssClasses}` to the grid root.
- [ ] Create `_cq_editConfig.xml` with `afteredit/afterinsert/afterdelete=REFRESH_PAGE` *(missing)*
- [x] Create Sling Model `CardGridModel` with `@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)`; nested `Card` model via `@ChildResource`
- [ ] **🟡 Fix HTL mixing**: `card-grid.html` reads `${properties.columns}` for the CSS class but accesses cards via `${model.cards}`. Pick one — either expose `gridClass` as `${model.gridClass}` (already in spec doc 04) or read `properties.columns` consistently. Doc 04's no-mix rule is violated.

**Frontend Tasks:**
- [x] Implement CSS Grid layout — `_card-grid.scss`
- [x] Style cards (green background, white text)
- [x] Add hover effects (lift + shadow)
- [x] Implement responsive grid (auto-fit)
- [x] Style icons
- [x] Test different column configurations

**Files (already in source):**
- `/apps/setia/components/card-grid/{.content.xml, _cq_dialog/.content.xml, card-grid.html}`
- `core/src/main/java/br/com/setia/core/models/CardGridModel.java`
- `ui.frontend/src/main/webpack/components/_card-grid.scss`

**Success Criteria:**
- ✅ Cards display in grid
- ✅ Hover effects work
- ✅ Responsive at all breakpoints
- ✅ Multiple column configurations work
- [ ] Container flag set so policies can constrain nested children *(blocker for Phase 6)*

#### 4.2 Image Section Component
**Status:** ✅ Mostly implemented; missing editConfig and image property is named `imagePath` (not `image`).
**Duration:** 1 day
**Owner:** AEM + Frontend Developer

> **Property name correction:** the HTL reads `properties.imagePath` (not `image`). Doc 04 should be updated to say `imagePath`, or the component renamed — either way the editConfig drop target below must target `./imagePath`.

**AEM Tasks:**
- [x] Create component structure (`componentGroup="Setia"`)
- [x] Create author dialog: title, subtitle, description (RTE), `imagePath` (DAM picker), imageAlt, imagePosition, imageWidth
- [ ] Create `_cq_editConfig.xml` with image drop target on `./imagePath` *(missing — required for drag-drop authoring)*
- [x] Create HTL template with left/right variants (defaults position to `right`)

**Frontend Tasks:**
- [x] Style two-column layout (text + image) — `_image-section.scss`
- [x] Implement left/right positioning
- [x] Make responsive (stack on mobile)
- [x] Style image container
- [x] Handle aspect ratios

**Files (already in source):**
- `/apps/setia/components/image-section/{.content.xml, _cq_dialog/.content.xml, image-section.html}`
- `ui.frontend/src/main/webpack/components/_image-section.scss`

**Success Criteria:**
- ✅ Image and text side-by-side
- ✅ Position option works
- ✅ Responsive layout on mobile
- [ ] DAM image drop target works *(blocked until `_cq_editConfig.xml` ships)*

#### 4.3 CTA Section Component
**Status:** ✅ Mostly implemented; design dialog with Style System variants deferred to Phase 6.4.
**Duration:** 1 day
**Owner:** AEM + Frontend Developer

**AEM Tasks:**
- [x] Create component structure (`componentGroup="Setia"`)
- [x] Create author dialog: title, subtitle, buttonText, buttonLink, includeMascot
- [x] Style System theme variants wired in Phase 6.4 — Light (default) / Dark / Accent (green) — applied via `${currentStyle.cssClasses}` on the section root
- [x] Create HTL template

**Frontend Tasks:**
- [x] Style CTA section (dark blue background) — `_cta-section.scss`
- [x] Style large button
- [x] Position mascot image
- [x] Make responsive
- [x] Add button hover effects

**Files (already in source):**
- `/apps/setia/components/cta-section/{.content.xml, _cq_dialog/.content.xml, cta-section.html}`
- `ui.frontend/src/main/webpack/components/_cta-section.scss`

**Success Criteria:**
- ✅ CTA displays prominently
- ✅ Button is clickable and styled
- ✅ Mascot appears when enabled
- [ ] Style System theme variants switchable *(deferred to 6.4)*

---

## Phase 5: Specialized Components (Week 4)

### Objectives
- Build unique/complex components
- Implement interactive features
- Complete component library

### Tasks

#### 5.1 Home Hero Component
**Status:** ✅ Implemented; HTL has model/properties mixing and editConfig is missing.
**Duration:** 2 days
**Owner:** AEM + Frontend Developer

**AEM Tasks:**
- [x] Create component structure (`componentGroup="Setia"`)
- [x] Create author dialog with tabs: Content, Service Cards (multifield, 3 items), Styling
- [ ] Create `_cq_editConfig.xml` with image drop target on `./backgroundImage` and refresh listeners *(missing)*
- [x] Create Sling Model `HomeHeroModel` with `@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)`; nested `ServiceCard` model via `@ChildResource`
- [ ] **🟡 Fix HTL mixing**: `home-hero.html` has `data-sly-test="${properties.mainTitle}"` and uses `${properties.x}` for the hero header but `${model.serviceCards}` for the multifield. Standardize on `${model.x}` per doc 04 rule.

**Frontend Tasks:**
- [x] Style hero section (full-width, dark blue) — `_home-hero.scss`
- [x] Add tech pattern background
- [x] Style service cards (overlapping bottom edge)
- [x] Implement card layout (3 cards)
- [x] Make fully responsive
- [x] Add animations (fade in)

**Files (already in source):**
- `/apps/setia/components/home-hero/{.content.xml, _cq_dialog/.content.xml, home-hero.html}`
- `core/src/main/java/br/com/setia/core/models/HomeHeroModel.java`
- `ui.frontend/src/main/webpack/components/_home-hero.scss`

**Success Criteria:**
- ✅ Hero displays with background
- ✅ 3 service cards overlap bottom
- ✅ Responsive on all devices
- ✅ Pattern overlay visible
- [ ] DAM image drop target works *(blocked until `_cq_editConfig.xml` ships)*

#### 5.2 Accordion Section Component
**Status:** ⚠️ Implemented except `cq:isContainer` flag and editConfig.
**Duration:** 2 days
**Owner:** AEM + Frontend Developer

**AEM Tasks:**
- [ ] **🔴 Add `cq:isContainer="{Boolean}true"` to `accordion-section/.content.xml`** — currently absent; required for nested-policy targeting
- [x] Create author dialog: title (optional), accordionItems (multifield: itemTitle, itemContent RTE, expandedByDefault)
- [ ] Create `_cq_editConfig.xml` with refresh listeners *(missing)*
- [x] Create HTL template with accordion structure (binds via `${model.x}`; verify `aria-expanded` is set in the markup)

**Frontend Tasks:**
- [x] Style accordion headers — `_accordion-section.scss`
- [x] Style accordion content panels
- [x] Implement expand/collapse JavaScript — `accordion-section.js` exists; verify it uses the class-based pattern from doc 06 (not the inline IIFE the spec discourages)
- [x] Add expand/collapse icons
- [x] Add transitions
- [ ] Update `aria-expanded` on toggle — verify the JS does this (audit during testing)

**Files (already in source):**
- `/apps/setia/components/accordion-section/{.content.xml, _cq_dialog/.content.xml, accordion-section.html}`
- `core/src/main/java/br/com/setia/core/models/AccordionSectionModel.java` *(plan didn't anticipate a model; one exists)*
- `ui.frontend/src/main/webpack/components/_accordion-section.scss`
- `ui.frontend/src/main/webpack/components/accordion-section.js`

**Success Criteria:**
- ✅ Accordion items expand/collapse on click
- ✅ Only one item open at a time
- ✅ Smooth transitions
- [ ] Accessible (keyboard navigation + `aria-expanded` toggling) — verify in Phase 8
- [ ] Container flag set for policy targeting *(blocker for Phase 6)*

#### 5.3 Download List Component
**Status:** ✅ Implemented; missing editConfig.
**Duration:** 1 day
**Owner:** AEM + Frontend Developer

**AEM Tasks:**
- [x] Create component structure (`componentGroup="Setia"`)
- [x] Create author dialog: title (optional), `downloadItems` (multifield: fileTitle, fileDescription, fileAsset, fileType, fileSize)
- [ ] Create `_cq_editConfig.xml` with asset drop target on the multifield's `fileAsset` property *(missing — note: the source uses `fileAsset`, not `assetPath` as the plan originally said)*
- [x] Create Sling Model `DownloadListModel` with `@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)`; nested `DownloadItem` via `@ChildResource`
- [x] Create HTL template

**Frontend Tasks:**
- [x] Style download list items — `_download-list.scss`
- [x] Add file type icons
- [x] Style download button
- [x] Add hover effects
- [x] Make responsive

**Files (already in source):**
- `/apps/setia/components/download-list/{.content.xml, _cq_dialog/.content.xml, download-list.html}`
- `core/src/main/java/br/com/setia/core/models/DownloadListModel.java`
- `ui.frontend/src/main/webpack/components/_download-list.scss`

**Success Criteria:**
- ✅ Files display with metadata
- ✅ Download links work
- ✅ File type icons appear

#### 5.4 Logo Gallery Component
**Status:** ✅ Implemented; missing editConfig.
**Duration:** 1 day
**Owner:** AEM + Frontend Developer

**AEM Tasks:**
- [x] Create component structure (`componentGroup="Setia"`)
- [x] Create author dialog: title (optional), logos (multifield: logoImage, altText, linkUrl, logoWidth)
- [ ] Create `_cq_editConfig.xml` with image drop target on each multifield item's `logoImage` *(missing)*
- [x] Create Sling Model `LogoGalleryModel` *(extra — plan didn't specify one, but it's idiomatic for multifield iteration)*
- [x] Create HTL template

**Frontend Tasks:**
- [x] Style logo grid — `_logo-gallery.scss`
- [x] Implement grayscale/color effect (grayscale default, color on hover)
- [x] Center logos
- [x] Make responsive
- [x] Handle variable logo sizes

**Files (already in source):**
- `/apps/setia/components/logo-gallery/{.content.xml, _cq_dialog/.content.xml, logo-gallery.html}`
- `core/src/main/java/br/com/setia/core/models/LogoGalleryModel.java`
- `ui.frontend/src/main/webpack/components/_logo-gallery.scss`

**Success Criteria:**
- ✅ Logos display in grid
- ✅ Hover effect works (grayscale to color)
- ✅ Links work when provided

---

## Phase 6: Templates and Policies (Week 5)

### Objectives
- Create the template-type, both editable templates, and component policies in AEM
- Configure structure-locked vs. editable nodes correctly
- Wire policies into templates via `cq:policy` references
- Export the entire `/conf/setia` subtree to `ui.content` for version control

> Editable templates have **three independent parts**: structure (locked layout), initial (starting content), and policies (allowed components + design properties). See [03 — Template & Policy Configuration](../03-aem-implementation-strategy.md#template--policy-configuration) for the canonical mechanics. Allowed-components lists are policy data, not code.

> **Refactor in flight (per Adobe best-practice review):** the original templates had `<root editable=true>` with no locked structure, which violates the AEM pattern. The refactor was split into four sub-phases: **A** (structure), **B** (content migration), **C** (policies), **D** (verification). Phase A is complete; B/C/D remain.

### Tasks

#### 6.1 Template Type
**Status:** ✅ Implemented (Phase A).
**Duration:** 0.5 day
**Owner:** AEM Developer

- [x] Create template-type at `/conf/setia/settings/wcm/template-types/page` (folder name `page`)
- [x] Configure with `setia/components/page` resource type
- [x] Seed structure: header XF (locked) → editable container → footer XF (locked) — minimal pattern that derived templates extend
- [ ] Verify the type appears in the Template editor as a base option *(verify on deploy)*

**Success Criteria:**
- ✅ Template-type usable as the seed for both templates below

#### 6.2 Setia Landing Page Template
**Status:** ⚠️ Phase A done (structure locked); Phase B (content migration) and Phase C (policies) outstanding.
**Duration:** 2 days
**Owner:** AEM Developer

**Phase A — Structure (✅ done):**
- [x] Template exists at `/conf/setia/settings/wcm/templates/landing-page`
- [x] Structure: header XF (locked) → home-hero (structure-locked, content editable) → editable container parsys → cta-section (structure-locked, content editable) → footer XF (locked)
- [x] Header XF references `/content/experience-fragments/setia/us/en/site/header/master`
- [x] Footer XF references `/content/experience-fragments/setia/us/en/site/footer/master`
- [x] `landing-page/initial/.content.xml` cleaned up (stray nested home-hero removed)

**Phase C — Policies (outstanding):**
- [ ] In Policy mode, for the editable Content Container, allow:
  - [ ] section-heading
  - [ ] rich-text-section
  - [ ] card-grid
  - [ ] cta-section
- [ ] Component-level policies:
  - [ ] `home-hero` policy (design defaults)
  - [ ] `card-grid` policy (allowed column counts via design dialog) — **blocked until 4.1's `cq:isContainer` and design dialog ship**
  - [ ] `cta-section` policy (theme variants via Style System) — **blocked until 4.3's design dialog ships**
- [ ] Replace the placeholder `cq:policy="setia/components/container/policy_landing_page"` reference in `landing-page/policies/.content.xml` with a real policy node under `/conf/setia/settings/wcm/policies/setia/components/container/policy_landing_page`
- [ ] Enable template

**Phase D — Verification:**
- [x] `ui.content/.../META-INF/vault/filter.xml` covers `/conf/setia` and `/content/setia` in **replace mode** (filter has no `mode=` attribute, which defaults to replace). This is critical — see "Vault Filter Modes" below.
- [ ] Test re-deploy from code: `mvn ... -PautoInstallSinglePackage,aem-remote`

**Path:** `/conf/setia/settings/wcm/templates/landing-page`

**Success Criteria:**
- ✅ Template structure locked correctly (Phase A)
- [ ] Content Container shows only the policy-allowed components in the insert menu (Phase C)
- [ ] Re-deployment from `ui.content` reproduces the template/policy state on a clean instance (Phase D)

#### 6.3 Setia Content Page Template
**Status:** ⚠️ Phase A done; B/C/D outstanding.
**Duration:** 2 days
**Owner:** AEM Developer

**Phase A — Structure (✅ done):**
- [x] Template exists at `/conf/setia/settings/wcm/templates/page-content`
- [x] Structure: header XF (locked) → page-banner (structure-locked, content editable) → editable container parsys → cta-section (structure-locked, content editable) → footer XF (locked)
- [x] Header + Footer XF references wired

**Phase C — Policies (outstanding):**
- [ ] In Policy mode, for the editable Content Container, allow:
  - [ ] section-heading
  - [ ] rich-text-section
  - [ ] card-grid
  - [ ] image-section
  - [ ] accordion-section
  - [ ] download-list
  - [ ] logo-gallery
  - [ ] cta-section
- [ ] Component-level policies:
  - [ ] `page-banner` policy (theme/background defaults)
  - [ ] `card-grid` policy (allowed column counts) — **blocked on 4.1**
  - [ ] `accordion-section` policy (default open behavior) — **blocked on 5.2's `cq:isContainer`**
  - [ ] `cta-section` policy (theme variants) — **blocked on 4.3 design dialog**
- [ ] Replace the placeholder `cq:policy="setia/components/container/policy_1574695586800"` reference with a real policy node
- [ ] Enable template

**Phase D — Verification:**
- [ ] Test deployment

**Path:** `/conf/setia/settings/wcm/templates/page-content`

**Success Criteria:**
- ✅ Template structure locked correctly (Phase A)
- [ ] Content Container insert menu matches the allow-list (Phase C)
- [ ] Container components (`card-grid`, `accordion-section`) accept only their allowed nested children (Phase C, blocked on container flags)

#### 6.4 Cross-Cutting Policies and Verification
**Status:** ⚠️ All outstanding (Phase C/D).
**Duration:** 1 day
**Owner:** AEM Developer

- [ ] Configure responsive grid policies (column count, breakpoints)
- [ ] Configure ClientLib embedding policies (`setia.base`, `setia.site`) — note: `setia.base` already embeds `setia.grid` and Core Components libs, so do not embed them separately in the policy
- [x] Configure Style System groups for `card-grid` (Green/Blue/Neutral), `cta-section` (Light/Dark/Accent), `page-banner` (Dark/Light/Accent) — wired via `cq:styleGroups` on each component's policy node; HTL applies `${currentStyle.cssClasses}`; SCSS theme variants land in each component's `_*.scss`
- [ ] **🔴 Verify `cq:isContainer="true"` is set on `card-grid` and `accordion-section`** (currently MISSING — see 4.1 and 5.2)
- [ ] Smoke-test policy resolution: create a page from each template, confirm the insert menu and design dialog show the expected options
- [ ] Verify a fresh clone + `mvn -PautoInstallSinglePackage,aem-remote` reproduces the full template + policy state from `ui.content`

**Success Criteria:**
- [ ] Allowed-components lists driven entirely by policy (no HTL/code edits required to change them)
- [ ] Style System variants switchable from authoring UI
- [ ] Templates + policies fully reproducible from version control

#### 6.5 Vault Filter Modes (`ui.content/.../filter.xml`)
**Status:** ✅ Configured for bootstrap. **Must be revisited before launch** — see Phase 9.3.
**Owner:** AEM Developer

Vault filter `mode=` controls how a package interacts with existing JCR state on install:

| Mode | Behavior on install |
|------|---------------------|
| `replace` (default, no `mode=` attribute) | Entire matched subtree is **overwritten** — nodes in JCR but not in the package are **deleted** |
| `merge` | Adds nodes from the package; **leaves existing JCR nodes untouched** even if they aren't in the package |
| `update` | Updates existing nodes' properties from the package; **does not delete** nodes that exist only in JCR |

**Current configuration** (`ui.content/src/main/content/META-INF/vault/filter.xml`):

| Path | Mode | Why |
|------|------|-----|
| `/conf/setia` | replace | Templates and policies are source-of-truth code. Without `replace`, template-structure refactors (e.g. locking `home-hero` and `cta-section` into the structure tree) silently fail to apply because vault-merge keeps the old empty `<root editable=true>` alongside the new locked nodes. Symptom: locked components render as empty placeholders. |
| `/content/setia` | replace | Pages are bootstrapped from source. Without `replace`, archetype-default content (Hello World, Epic Journey teasers, "Drag components here" empty parsys, San Jose footer) survives alongside the new content and renders confusingly. |
| `/content/dam/setia/{images,logos,mascots,documents}` | merge | DAM binaries are heavy. Merge mode avoids re-uploading the same `original` rendition every deploy. The `asset.jpg` archetype sample is also explicitly merged. |
| `/content/experience-fragments/setia` | replace | Same reasoning as `/content/setia`. |

**Why this matters:** if the team sees stale content rendering after a deploy (component nodes with no `sling:resourceType`, mascot missing, hero collapsed, etc.), 9 times out of 10 the fix is **not** "redeploy" — it's "verify filter mode". Vault merge silently swallowing structural changes is one of the most frequent confusing failure modes in AEM source-controlled projects.

**Success Criteria:**
- ✅ `/conf/setia` and `/content/setia` deploy in replace mode
- ✅ DAM binary paths stay on merge to avoid binary churn
- [ ] Document the launch-time switch to merge mode for `/content/setia` (Phase 9.3 task)

---

## Phase 7: Page Creation and Content (Week 5-6)

### Objectives
- Create all 4 pages
- Add components and content
- Populate with sample/real content
- Test navigation

> **Phase B — Content Migration (cross-cutting, blocks all of Phase 7):** all 4 content pages currently store components under `<root>/component-X`. The template-structure refactor (Phase 6.A) moved the editable parsys to `<root>/container/component-X`. Until pages are migrated, components will not render inside the locked frame. Migration steps below appear in each task.

### Tasks

#### 7.1 Home Page
**Status:** ⚠️ Page exists; needs content migration to align with new template structure.
**Duration:** 2 days
**Owner:** AEM Developer + Content Author

- [x] Create home page at `/content/setia/us/en` *(the locale root **is** the home page; there is no separate `/home` path — earlier plan revisions said `/content/setia/us/en/home` which doesn't exist)*
- [x] Use "Setia Landing Page Template"
- [x] **Content migration (Phase B) ✅ done:** components moved into `<root>/container/`; home-hero and cta-section reduced to authored-property overrides on the structure-locked instances
- [ ] Configure structure-locked components (Header XF, Home Hero, CTA, Footer XF) — content only, no add/move
- [ ] Verify Content Container has the page-specific components (after migration):
  - [ ] Section Heading ("Delivering innovative...")
  - [ ] Section Heading ("What we do")
  - [ ] Card Grid (6 service cards)
  - [ ] Rich Text Section ("Engagement...")
- [x] Upload images to DAM (`/content/dam/setia/...`) — bootstrapped from `docs/assets/` per [09-boostrap-initial-content.md](./09-boostrap-initial-content.md): hero (`bg-home.jpg`), strategy diagram, mascot, partner logos, PDFs
- [ ] Test in preview mode after migration
- [ ] Test responsive behavior

**Success Criteria:**
- ✅ Page exists and uses Landing Page Template
- [ ] Page renders correctly inside the locked structural frame (blocked until Phase B migration)
- [ ] All components configured
- [ ] Responsive on all devices
- [ ] Navigation works

#### 7.2 Company Page
**Status:** ⚠️ Page exists with full content; needs migration into the new container.
**Duration:** 2 days
**Owner:** AEM Developer + Content Author

- [x] Create page: `/content/setia/us/en/company`
- [x] Use "Setia Content Page Template"
- [x] Page-banner content already authored ("We are Setia" / "Delivering Innovative and Quality Solutions") — needs to move to the structure-locked banner instance
- [x] **Content migration (Phase B) ✅ done:** components moved into `<root>/container/`; page-banner and cta-section reduced to authored-property overrides on the structure-locked instances
- [ ] Verify Content Container has (after migration):
  - [ ] Rich Text Section (Our Story)
  - [ ] Rich Text Section (What makes Setia different)
  - [ ] Download List
  - [ ] Section Heading ("Our services")
  - [ ] Card Grid (Services)
  - [ ] Logo Gallery (Partners)
- [x] Upload assets (documents, logos) to DAM
- [ ] Test after migration

**Success Criteria:**
- ✅ Page exists with content authored
- [ ] Page renders correctly inside the locked structural frame
- [ ] Downloads work
- [ ] Partner logos display

#### 7.3 Services Page
**Status:** ⚠️ Page exists; needs migration.
**Duration:** 2 days
**Owner:** AEM Developer + Content Author

- [x] Create page: `/content/setia/us/en/services`
- [x] Use "Setia Content Page Template"
- [x] **Content migration (Phase B) ✅ done:** components moved into `<root>/container/`; page-banner and cta-section reduced to authored-property overrides on the structure-locked instances
- [ ] Verify Content Container has (after migration):
  - [ ] Rich Text Section
  - [ ] Card Grid
  - [ ] Accordion Section (And how...)
  - [ ] Image Section (Strategy diagram)
  - [ ] Logo Gallery (Partners)
- [x] Upload strategy diagram
- [ ] Test accordion interactions (keyboard, `aria-expanded` toggling)

**Success Criteria:**
- ✅ Page exists with content authored
- [ ] Page renders correctly inside the locked structural frame
- [ ] Accordion works
- [ ] Strategy diagram displays

#### 7.4 Solutions Page
**Status:** ⚠️ Page exists; needs migration.
**Duration:** 2 days
**Owner:** AEM Developer + Content Author

- [x] Create page: `/content/setia/us/en/solutions`
- [x] Use "Setia Content Page Template"
- [x] **Content migration (Phase B) ✅ done:** components moved into `<root>/container/`; page-banner and cta-section reduced to authored-property overrides on the structure-locked instances
- [ ] Verify Content Container has (after migration):
  - [ ] Rich Text Section
  - [ ] Image Section (Platform diagram)
  - [ ] Card Grid (Features)
- [x] Upload platform architecture diagram
- [ ] Verify "Item Tracelaw Tokens" wording in spec is intentional or correct it

**Success Criteria:**
- ✅ Page exists with content authored
- [ ] Page renders correctly inside the locked structural frame
- [ ] Platform diagram displays correctly

---

## Phase 8: Testing and Refinement (Week 6-7)

### Objectives
- Comprehensive testing across browsers and devices
- Fix bugs and issues
- Performance optimization
- Accessibility testing

### Tasks

#### 8.1 Functional Testing
**Duration:** 3 days  
**Owner:** All Developers

- [ ] Test all component author dialogs
- [ ] Test all component design dialogs (Style System variants apply correctly)
- [ ] Test all `_cq_editConfig` drop targets (drag from DAM into hero, banner, image-section, logo-gallery, download-list)
- [ ] Test container components honor policy allow-lists (`card-grid`, `accordion-section` reject disallowed children)
- [ ] Test structure-locked components cannot be moved/deleted on pages
- [ ] Test Sling Models with `defaultInjectionStrategy = OPTIONAL` handle missing properties without exceptions
- [ ] Test component rendering
- [ ] Test Experience Fragment updates propagate to all pages
- [ ] Test page creation workflow (template insert menu shows only enabled templates)
- [ ] Test navigation across pages
- [ ] Test all links
- [ ] Test download files
- [ ] Test accordion interactions (keyboard + `aria-expanded`)
- [ ] Test mobile menu
- [ ] Test form inputs (if any)

**Test Matrix:**
- [ ] Chrome (latest)
- [ ] Firefox (latest)
- [ ] Safari (latest)
- [ ] Edge (latest)
- [ ] Mobile Safari (iOS)
- [ ] Chrome Mobile (Android)

#### 8.2 Responsive Testing
**Duration:** 2 days  
**Owner:** Frontend Developer

- [ ] Test at 320px (small mobile)
- [ ] Test at 375px (iPhone)
- [ ] Test at 768px (tablet portrait)
- [ ] Test at 1024px (tablet landscape / small laptop)
- [ ] Test at 1440px (desktop)
- [ ] Test at 1920px (large desktop)

**Check:**
- [ ] Typography scales appropriately
- [ ] Images scale/crop correctly
- [ ] Grids reflow properly
- [ ] Touch targets are adequate (44px min)
- [ ] No horizontal scrolling

#### 8.3 Accessibility Testing
**Duration:** 2 days  
**Owner:** Frontend Developer

- [ ] Run aXe DevTools on all pages
- [ ] Test keyboard navigation
- [ ] Test with screen reader (NVDA/JAWS/VoiceOver)
- [ ] Verify color contrast (WCAG AA)
- [ ] Check heading hierarchy
- [ ] Verify alt text on images
- [ ] Test focus indicators
- [ ] Verify ARIA attributes

**Success Criteria:**
- No critical accessibility issues
- WCAG 2.1 AA compliance
- Keyboard navigable
- Screen reader compatible

#### 8.4 Performance Testing
**Duration:** 2 days  
**Owner:** Frontend Developer

- [ ] Run Lighthouse on all pages
- [ ] Optimize images (compress, correct formats)
- [ ] Minimize CSS/JS
- [ ] Enable ClientLib minification
- [ ] Test page load times
- [ ] Optimize critical rendering path
- [ ] Lazy load below-fold images
- [ ] Check Core Web Vitals

**Targets:**
- Lighthouse Performance: > 90
- LCP (Largest Contentful Paint): < 2.5s
- FID (First Input Delay): < 100ms
- CLS (Cumulative Layout Shift): < 0.1

#### 8.5 Cross-Browser Testing
**Duration:** 1 day  
**Owner:** Frontend Developer

- [ ] Test in Chrome
- [ ] Test in Firefox
- [ ] Test in Safari
- [ ] Test in Edge
- [ ] Document any browser-specific issues
- [ ] Fix critical cross-browser bugs

#### 8.6 Bug Fixing
**Duration:** 3 days  
**Owner:** All Developers

- [ ] Prioritize bugs (Critical, High, Medium, Low)
- [ ] Fix critical bugs
- [ ] Fix high-priority bugs
- [ ] Document known medium/low bugs
- [ ] Retest after fixes

---

## Phase 9: Documentation and Handoff (Week 7-8)

### Objectives
- Complete documentation
- Author training
- Deployment preparation
- Knowledge transfer

### Tasks

#### 9.1 Documentation Completion
**Duration:** 2 days  
**Owner:** Technical Lead

- [ ] Complete all TODO sections in documentation
- [ ] Update CLAUDE.md with lessons learned
- [ ] Document any deviations from plan
- [ ] Create troubleshooting guide
- [ ] Document known issues
- [ ] Create deployment checklist

#### 9.2 Author Training
**Duration:** 2 days  
**Owner:** AEM Developer

- [ ] Conduct AEM authoring training session
- [ ] Walk through component usage
- [ ] Demonstrate page creation workflow
- [ ] Show DAM asset management
- [ ] Explain publishing workflow
- [ ] Provide hands-on practice time
- [ ] Answer questions

**Materials:**
- Authoring Guidelines (already created)
- Live demo in AEM
- Recorded training video (optional)

#### 9.3 Deployment Preparation
**Duration:** 2 days  
**Owner:** DevOps + AEM Developer

- [ ] Configure production environment
- [ ] Configure `aem-remote` profile entries for staging and production credentials (separate from local)
- [ ] Set up Dispatcher (cache rules, filter rules, statfile invalidation)
- [ ] Implement URL strategy: vanity paths (`/company` → `/content/setia/us/en/company`), Sling mapping in `/etc/map`, or dispatcher rewrites — pick one and document it
- [ ] Configure CDN
- [ ] Set up SSL certificates
- [ ] Configure DNS
- [ ] Prepare deployment scripts
- [ ] Test deployment to staging using `aem-remote` profile
- [ ] Create rollback plan
- [ ] **🟡 Switch `/content/setia` filter mode from `replace` to `merge`** in `ui.content/src/main/content/META-INF/vault/filter.xml`. During bootstrap (Phase 7) the `replace` mode wipes JCR content on every deploy so source is source-of-truth. After launch, authors will edit pages in AEM Author and those edits must NOT be wiped by deploys. Switch to `merge` once initial content is in place and authors are taking over. **`/conf/setia` should stay on `replace`** because templates and policies remain code-controlled. See Phase 6.5 for filter mode rationale.

#### 9.4 Knowledge Transfer
**Duration:** 1 day  
**Owner:** All Developers

- [ ] Code walkthrough session
- [ ] Architecture overview presentation
- [ ] Component explanation
- [ ] Frontend build system explanation
- [ ] Q&A session
- [ ] Handoff documentation

---

## Phase 10: Launch and Post-Launch (Week 8)

### Objectives
- Deploy to production
- Monitor for issues
- Quick fixes if needed
- Gather feedback

### Tasks

#### 10.1 Production Deployment
**Duration:** 1 day  
**Owner:** DevOps + Technical Lead

- [ ] Final code review
- [ ] Create production build (with `aem-remote` profile pointing at the production credentials)
- [ ] Deploy to production AEM (`mvn ... -PautoInstallSinglePackagePublish,aem-remote` or pipeline equivalent)
- [ ] Verify all components
- [ ] Test all pages
- [ ] Verify URL strategy works end-to-end (vanity paths / `/etc/map` / dispatcher rewrites — confirm `/company` → `/content/setia/us/en/company`)
- [ ] Clear dispatcher cache
- [ ] Test DNS routing
- [ ] Monitor logs

#### 10.2 Post-Launch Monitoring
**Duration:** 3 days  
**Owner:** All Developers

- [ ] Monitor application logs
- [ ] Monitor performance metrics
- [ ] Monitor user analytics
- [ ] Check for errors in browser console
- [ ] Monitor page load times
- [ ] Track any user-reported issues

#### 10.3 Quick Fixes
**Duration:** 2 days (as needed)  
**Owner:** Development Team

- [ ] Prioritize any production issues
- [ ] Deploy hotfixes as needed
- [ ] Test fixes in production
- [ ] Document issues and resolutions

#### 10.4 Retrospective
**Duration:** 1 day  
**Owner:** All Team

- [ ] Conduct team retrospective
- [ ] Document what went well
- [ ] Document what could improve
- [ ] Document lessons learned
- [ ] Update documentation based on learnings
- [ ] Plan for future iterations

---

## Resource Allocation

### Team Structure

| Role | Allocation | Key Responsibilities |
|------|------------|---------------------|
| AEM Developer | Full-time (8 weeks) | Components, templates, Sling Models, AEM configuration |
| Frontend Developer 1 | Full-time (6 weeks) | SCSS, JavaScript, responsive design, performance |
| Frontend Developer 2 (optional) | Part-time (3 weeks) | Additional styling, testing, browser compatibility |
| Technical Lead | 25% (8 weeks) | Architecture, code review, technical decisions |
| Content Author | Part-time (2 weeks) | Content population, testing authoring experience |
| QA/Tester | Part-time (2 weeks) | Testing, bug reporting, regression testing |
| DevOps | Part-time (1 week) | Deployment, environment setup, monitoring |

### Timeline Summary

| Phase | Duration | Parallel Work |
|-------|----------|---------------|
| Phase 1: Foundation | Week 1 | AEM setup + Frontend setup (parallel) |
| Phase 2: Experience Fragments | Week 1-2 | Header + Footer (sequential) |
| Phase 3: Layout Components | Week 2 | 3 components (can parallelize) |
| Phase 4: Content Components | Week 3 | 3 components (can parallelize) |
| Phase 5: Specialized Components | Week 4 | 4 components (2 parallel tracks) |
| Phase 6: Templates | Week 5 | 2 templates (sequential) |
| Phase 7: Page Creation | Week 5-6 | 4 pages (can parallelize after templates) |
| Phase 8: Testing | Week 6-7 | Multiple testing tracks (parallel) |
| Phase 9: Documentation | Week 7-8 | Documentation + Training (parallel) |
| Phase 10: Launch | Week 8 | Deployment + Monitoring |

**Total Duration:** 6-8 weeks (depending on team size and parallelization)

---

## Risk Mitigation

See [Technical Risks](./05-technical-risks.md) for detailed risk analysis.

**Top Risks:**
1. Component complexity underestimated → Allocate buffer time
2. Browser compatibility issues → Early cross-browser testing
3. Performance issues → Regular Lighthouse checks
4. Design-to-implementation gaps → Regular design reviews
5. AEM environment issues → Set up environments early

---

## Success Criteria

### Technical Success
- ✅ All 13 components functional, with author dialogs, design dialogs (where applicable), and `_cq_editConfig.xml` (where applicable)
- ✅ Container components (`card-grid`, `accordion-section`) declare `cq:isContainer="true"` and honor policy allow-lists
- ✅ All Sling Models use `@Model(adaptables = Resource.class, defaultInjectionStrategy = OPTIONAL)`
- ✅ Templates reproducible from `ui.content` on a clean instance via `mvn ... -PautoInstallSinglePackage,aem-remote`
- ✅ Allowed-components lists driven entirely by template policies (no HTL/code edits needed to change them)
- ✅ All 4 pages created and published
- ✅ Lighthouse score > 90
- ✅ No critical accessibility issues (WCAG AA)
- ✅ Works in all target browsers
- ✅ Responsive on all device sizes
- ✅ All automated tests passing

### Business Success
- ✅ Content authors can create/edit pages independently
- ✅ Pages match approved designs
- ✅ Site loads in < 3 seconds
- ✅ No critical bugs in production
- ✅ Positive user feedback

### Project Success
- ✅ Delivered within 8-week timeline
- ✅ Within budget
- ✅ Documentation complete
- ✅ Team trained
- ✅ Successful knowledge transfer

---

## Next Steps

1. **Review this plan** with stakeholders
2. **Allocate team members** to phases
3. **Set up development environments**
4. **Begin Phase 1** (Foundation Setup)
5. **Schedule regular check-ins** (daily standups, weekly reviews)
6. **Track progress** against this plan
7. **Adjust as needed** based on learnings

---

## Related Documentation

- [01 — Project Overview](../01-project-overview.md)
- [02 — Website Map](../02-website-map.md)
- [03 — AEM Implementation Strategy](../03-aem-implementation-strategy.md) — template & policy mechanics live here
- [04 — Component Specification](../04-component-specification.md) — author dialog / design dialog / editConfig per component
- [05 — Authoring Guidelines](../05-authoring-guidelines.md)
- [06 — Frontend Tech Details](../06-frontend-tech-details.md) — canonical accordion JS lives here
- [07 — Design System](../07-design-system.md)
- [CLAUDE.md](../../CLAUDE.md) — developer conventions

---

**Document Version:** 1.1
**Last Updated:** 2026-05-31
**Status:** Aligned with docs 01–07 spec revision
