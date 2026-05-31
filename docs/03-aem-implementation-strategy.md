# 03 - AEM Implementation Strategy

## AEM Architecture Overview

### High-Level Architecture

```text
Browser
  ↓
Dispatcher / CDN
  ↓
Publish Instance
  ↓
Sling Resource Resolver
  ↓
JCR Content Repository
  ↓
HTL Component Rendering
```

### Authoring Flow

```text
AEM Author UI
  ↓
JCR Repository
  ↓
Page/Component Content
  ↓
Replication
  ↓
Publish Instance
```

---

## AEM Project Structure

### Module Architecture

```text
setia/
├── core/               # Backend Java logic
├── ui.apps/            # Component definitions
├── ui.content/         # Initial content
├── ui.frontend/        # CSS/JS/Assets
├── ui.config/          # OSGi configurations
├── ui.apps.structure/  # Repository structure
├── ui.tests/           # UI tests (Cypress)
├── it.tests/           # Integration tests
├── dispatcher/         # Dispatcher config
├── all/                # Aggregated package
└── pom.xml             # Parent POM
```

### Module Responsibilities

#### core/
**Purpose:** Backend Java logic and services

**Contains:**
- Sling Models (`br.com.setia.core.models.*`)
- OSGi Services
- Servlets
- Filters
- Listeners
- Schedulers

**Use When:**
- Data formatting or transformation needed
- Business logic required
- API integrations
- Computed properties
- Service layer functionality

#### ui.apps/
**Purpose:** AEM component definitions and code

**Contains:**
- HTL templates (`/apps/setia/components/*/*.html`)
- Component dialogs (`_cq_dialog/.content.xml`)
- Component definitions (`.content.xml`)
- ClientLib definitions
- Editable templates
- Policies

**Location:** `/apps/setia/`

#### ui.frontend/
**Purpose:** Frontend asset development

**Contains:**
- SCSS source files (`src/main/webpack/site/`)
- JavaScript files
- Component-specific styles (`src/main/webpack/components/`)
- Static assets (`src/main/webpack/static/`)
- Webpack configuration

**Build Flow:**
```text
ui.frontend (source)
  ↓ npm build
Compiled CSS/JS
  ↓ copy
ui.apps clientlibs
  ↓ Maven package
AEM deployment
```

#### ui.content/
**Purpose:** Content and configuration for source control

**Contains:**
- Initial page structure (`/content`)
- Editable templates (`/conf`)
- Policies
- Experience Fragments structure
- Sample content

**When to Use:**
- Export templates created in AEM UI
- Version control initial site structure
- Deploy baseline configuration

#### ui.config/
**Purpose:** OSGi configurations

**Contains:**
- Runmode-specific configs
- Environment configurations
- Service configurations

**Location:** `/apps/setia/osgiconfig/`

---

## Template Strategy

### Overview
Use **2 editable templates** to cover all 4 pages.

> **Editable templates have three independent parts in AEM:**
> 1. **Template Type** (`/conf/setia/settings/wcm/template-types/`) — the seed/blueprint a template is created from.
> 2. **Template** (`/conf/setia/settings/wcm/templates/`) — split into:
>    - **`structure/`** — the locked layout (Header XF, Footer XF, root container). Editable nodes here flow into every page.
>    - **`initial/`** — the starting content authors see when a page is first created.
>    - **`policies/`** — the **template-level component policies** (allowed components, container settings, design properties). Pages inherit these via `cq:policy` references.
> 3. **Policies** (`/conf/setia/settings/wcm/policies/`) — the actual policy nodes referenced by `cq:policy` from the template's `policies/` tree and from component `_cq_design_dialog` configurations.
>
> The "Allowed Components" lists below are **policy data**, not structural data. They are configured in the Policy editor (Template editor → Policy mode), not in HTL or component code.

### Template 1: Setia Landing Page Template

**Used By:** Home page only

**Rationale:**
- Home page has unique hero layout with 3 overlapping service cards
- Different visual hierarchy from other pages
- Special hero component not reused elsewhere

**Structure (locked, in `structure/`):**
```text
Root Container (Responsive Grid)
├── Header XF Reference          (editable=false, structure-locked)
├── Hero Component (home-hero)   (structure-locked, but content editable)
├── Content Container (parsys)   (editable, allowed components via policy)
├── CTA Section (cta-section)    (structure-locked, content editable)
└── Footer XF Reference          (editable=false, structure-locked)
```

**Allowed Components in the Content Container (policy):**
- section-heading
- rich-text-section
- card-grid
- cta-section

### Template 2: Setia Content Page Template

**Used By:** Company, Services, Solutions pages

**Rationale:**
- All three pages share common layout structure
- Start with page banner instead of hero
- Flexible content sections
- Consistent visual hierarchy

**Structure (locked, in `structure/`):**
```text
Root Container (Responsive Grid)
├── Header XF Reference          (editable=false, structure-locked)
├── Page Banner (page-banner)    (structure-locked, content editable)
├── Content Container (parsys)   (editable, allowed components via policy)
├── CTA Section (cta-section)    (structure-locked, content editable)
└── Footer XF Reference          (editable=false, structure-locked)
```

**Allowed Components in the Content Container (policy):**
- section-heading
- rich-text-section
- card-grid
- image-section
- accordion-section
- download-list
- logo-gallery
- cta-section

### Template Configuration Locations

| Part | JCR Path | Module |
|------|----------|--------|
| Template Type | `/conf/setia/settings/wcm/template-types/` | `ui.content` |
| Templates | `/conf/setia/settings/wcm/templates/{landing-page,page-content}` | `ui.content` |
| Template Structure | `…/templates/{name}/structure/` | `ui.content` |
| Template Initial | `…/templates/{name}/initial/` | `ui.content` |
| Template Policy Mappings | `…/templates/{name}/policies/` | `ui.content` |
| Component Policies | `/conf/setia/settings/wcm/policies/setia/components/...` | `ui.content` |

**AEM UI Path:** Tools → General → Templates  
**Editor Modes (within Template editor):** Structure | Initial Content | Layout | Policy  
**Export To:** `ui.content/src/main/content/jcr_root/conf/setia/`

---

## Template & Policy Configuration

### How the Pieces Connect

```text
Page (/content/setia/us/en/home)
  └── jcr:content
        ├── cq:template = /conf/setia/settings/wcm/templates/landing-page
        └── root/responsivegrid/<component>
              ↓ resolves cq:policy via the template's policies/ subtree
              ↓
Template policies tree (/conf/setia/settings/wcm/templates/landing-page/policies)
  └── jcr:content/root/responsivegrid
        cq:policy = "setia/components/responsivegrid/policy_landing_main"
              ↓
Policy node (/conf/setia/settings/wcm/policies/setia/components/responsivegrid/policy_landing_main)
  ├── components = [setia/components/section-heading, setia/components/card-grid, ...]
  ├── cq:styleGroups = [...]
  └── (other design properties consumed via @Style or design dialog)
```

### Locking Components in the Template Structure

In the `structure/` tree, locking is controlled by `editable` and `structure` properties on each node:

```xml
<!-- ui.content/.../templates/landing-page/structure/jcr:content/root/header.xml -->
<jcr:root xmlns:cq="http://www.day.com/jcr/cq/1.0"
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
          xmlns:nt="http://www.jcp.org/jcr/nt/1.0"
          jcr:primaryType="nt:unstructured"
          sling:resourceType="setia/components/xf-header-reference"
          fragmentVariationPath="/content/experience-fragments/setia/header/master"
          editable="{Boolean}false"/>
```

| Intent | `editable` | `structure` |
|--------|-----------|-------------|
| Fully locked (Header/Footer XF) | `false` | implicit |
| Structurally locked, content editable (Banner, CTA) | `true` | locked via parent `structure` node |
| Free authoring zone (responsivegrid) | `true` | unlocked |

### Defining a Component Policy

Component policies live under `/conf/setia/settings/wcm/policies/setia/components/<component-name>/`. The policy is a node with the same `sling:resourceType` as the component it configures.

**Example — Responsive Grid policy that allows the Content-Page component set:**

```xml
<!-- ui.content/.../policies/setia/components/responsivegrid/policy_content_main/.content.xml -->
<jcr:root xmlns:cq="http://www.day.com/jcr/cq/1.0"
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
          xmlns:nt="http://www.jcp.org/jcr/nt/1.0"
          jcr:primaryType="nt:unstructured"
          jcr:title="Content Page Main Container"
          sling:resourceType="wcm/core/components/policy/policy">
    <components jcr:primaryType="nt:unstructured">
        <items jcr:primaryType="nt:unstructured">
            <section-heading
                jcr:primaryType="nt:unstructured"
                path="setia/components/section-heading"/>
            <rich-text-section
                jcr:primaryType="nt:unstructured"
                path="setia/components/rich-text-section"/>
            <card-grid
                jcr:primaryType="nt:unstructured"
                path="setia/components/card-grid"/>
            <image-section
                jcr:primaryType="nt:unstructured"
                path="setia/components/image-section"/>
            <accordion-section
                jcr:primaryType="nt:unstructured"
                path="setia/components/accordion-section"/>
            <download-list
                jcr:primaryType="nt:unstructured"
                path="setia/components/download-list"/>
            <logo-gallery
                jcr:primaryType="nt:unstructured"
                path="setia/components/logo-gallery"/>
            <cta-section
                jcr:primaryType="nt:unstructured"
                path="setia/components/cta-section"/>
        </items>
    </components>
</jcr:root>
```

### Wiring a Policy into a Template

The template's `policies/` subtree mirrors `structure/` and references the policy node by relative path:

```xml
<!-- ui.content/.../templates/page-content/policies/jcr:content/root/responsivegrid/.content.xml -->
<jcr:root xmlns:cq="http://www.day.com/jcr/cq/1.0"
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
          xmlns:nt="http://www.jcp.org/jcr/nt/1.0"
          jcr:primaryType="nt:unstructured"
          cq:policy="setia/components/responsivegrid/policy_content_main"
          sling:resourceType="wcm/core/components/policies/mappings"/>
```

### Container Components and Nested Policies

For our own container-style components (`card-grid`, `accordion-section`), the component itself must declare it accepts children so policies can target it:

```xml
<!-- ui.apps/.../components/card-grid/.content.xml -->
<jcr:root xmlns:cq="http://www.day.com/jcr/cq/1.0"
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
          jcr:primaryType="cq:Component"
          jcr:title="Card Grid"
          componentGroup="Setia"
          cq:isContainer="{Boolean}true"/>
```

Without `cq:isContainer="true"`, the component is treated as a leaf and no nested-component policy can be authored against it.

### Design Dialogs (`_cq_design_dialog`)

Component policies surface design-time options through a **design dialog** (separate from the author dialog). Use it for things authors should NOT change per-instance — column count, theme, allowed nested components, brand variants.

**File location:** `ui.apps/.../components/<name>/_cq_design_dialog/.content.xml`

**Example — `card-grid` design dialog exposing column-count constraints:**

```xml
<jcr:root xmlns:sling="http://sling.apache.org/jcr/sling/1.0"
          xmlns:cq="http://www.day.com/jcr/cq/1.0"
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
          xmlns:nt="http://www.jcp.org/jcr/nt/1.0"
          jcr:primaryType="nt:unstructured"
          jcr:title="Card Grid Design"
          sling:resourceType="cq/gui/components/authoring/dialog">
    <content
        jcr:primaryType="nt:unstructured"
        sling:resourceType="granite/ui/components/coral/foundation/container">
        <items jcr:primaryType="nt:unstructured">
            <allowedColumns
                jcr:primaryType="nt:unstructured"
                sling:resourceType="granite/ui/components/coral/foundation/form/select"
                fieldLabel="Allowed Column Counts"
                multiple="{Boolean}true"
                name="./allowedColumns">
                <items jcr:primaryType="nt:unstructured">
                    <c2 jcr:primaryType="nt:unstructured" text="2" value="2"/>
                    <c3 jcr:primaryType="nt:unstructured" text="3" value="3"/>
                    <c4 jcr:primaryType="nt:unstructured" text="4" value="4"/>
                </items>
            </allowedColumns>
        </items>
    </content>
</jcr:root>
```

The component HTL reads design properties via `currentStyle`:

```html
<sly data-sly-test.allowed="${currentStyle.allowedColumns}">…</sly>
```

### Component Edit Configuration (`_cq_editConfig`)

Every editable component should ship a `_cq_editConfig.xml` that defines drop-targets, inline editing, and editor toolbar actions. Without it, image components have no drag-and-drop target and rich-text inline editing is disabled.

**File location:** `ui.apps/.../components/<name>/_cq_editConfig.xml`

**Example — `home-hero` edit config with a background-image drop target:**

```xml
<jcr:root xmlns:cq="http://www.day.com/jcr/cq/1.0"
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
          xmlns:nt="http://www.jcp.org/jcr/nt/1.0"
          jcr:primaryType="cq:EditConfig"
          cq:actions="[edit,copymove,delete,insert]"
          cq:dialogMode="floating"
          cq:layout="editbar">
    <cq:dropTargets jcr:primaryType="nt:unstructured">
        <backgroundImage
            jcr:primaryType="cq:DropTargetConfig"
            accept="[image/.*]"
            groups="[media]"
            propertyName="./backgroundImage">
            <parameters
                jcr:primaryType="nt:unstructured"
                sling:resourceType="setia/components/home-hero"/>
        </backgroundImage>
    </cq:dropTargets>
    <cq:listeners
        jcr:primaryType="cq:EditListenersConfig"
        afteredit="REFRESH_PAGE"
        afterinsert="REFRESH_PAGE"
        afterdelete="REFRESH_PAGE"/>
</jcr:root>
```

| Component | Needs `_cq_editConfig`? | Why |
|---|---|---|
| `page` (Setia Page) | Inherited from Core | — |
| `xf-header-reference` / `xf-footer-reference` | No | XF component handles it |
| `home-hero` | **Yes** | Background-image drop target, refresh-on-edit |
| `page-banner` | **Yes** | Background-image drop target |
| `image-section` | **Yes** | Image drop target |
| `card-grid` | **Yes** | `cq:isContainer="true"`, refresh listeners |
| `accordion-section` | **Yes** | `cq:isContainer="true"`, refresh listeners |
| `logo-gallery` | **Yes** | Logo image drop target inside multifield |
| `download-list` | **Yes** | File asset drop target |
| `section-heading` / `rich-text-section` / `cta-section` | Optional | Only if inline-edit desired |

### Style System (Optional, via Policy)

Component policies can expose **Style System** classes so authors can swap visual variants without code changes:

```xml
<cq:styleGroups jcr:primaryType="nt:unstructured">
    <item0
        jcr:primaryType="nt:unstructured"
        cq:styleGroupLabel="Theme">
        <cq:styles jcr:primaryType="nt:unstructured">
            <light
                jcr:primaryType="nt:unstructured"
                cq:styleClasses="theme--light"
                cq:styleLabel="Light"/>
            <dark
                jcr:primaryType="nt:unstructured"
                cq:styleClasses="theme--dark"
                cq:styleLabel="Dark"/>
        </cq:styles>
    </item0>
</cq:styleGroups>
```

Recommended for: `cta-section` (light/dark), `card-grid` (column variants), `page-banner` (overlay opacity).

### Best Practices for Templates & Policies

- ✅ Lock global elements (Header, Footer, CTA) in `structure/`, not in pages
- ✅ One policy per logical container role (e.g., `policy_landing_main`, `policy_content_main`) — don't reuse the same policy across templates with different intents
- ✅ Author-controllable variations belong in **author dialog**; designer/template-locked variations belong in **design dialog → policy**
- ✅ Prefer Style System for theming over `colorpicker` fields in author dialogs
- ✅ Always set `cq:isContainer="true"` on components that accept children, so policies can target them
- ✅ Always ship `_cq_editConfig.xml` for components with media drop-targets or inline editing
- ✅ Export the entire `/conf/setia` subtree to `ui.content` (templates + policies + template-types together)
- ❌ Don't put allowed-components lists in component code — that's policy concern
- ❌ Don't author per-page what should be locked once in the template

---

## Experience Fragment Strategy

### Overview
Create **2 Experience Fragments** for shared elements across all pages.

### XF 1: Header

**Path:** `/content/experience-fragments/setia/header`  
**Variations:** `master`, `us/en`

**Purpose:**
- Shared top navigation
- Logo
- Menu links
- Fixed scroll behavior

**Benefits:**
- Single place for header updates
- Consistent navigation across site
- Easy to maintain

**Implementation:**
```text
header/
  master/
    jcr:content/
      root/
        responsivegrid/
          logo
          navigation
```

### XF 2: Footer

**Path:** `/content/experience-fragments/setia/footer`  
**Variations:** `master`, `us/en`

**Purpose:**
- Shared footer content
- Contact section
- Mascot illustration
- Copyright notice

**Benefits:**
- Centralized footer management
- Consistent branding
- Single point of update

**Implementation:**
```text
footer/
  master/
    jcr:content/
      root/
        responsivegrid/
          contact-section
          copyright-text
          footer-image
```

### Including Experience Fragments in Templates

Use Experience Fragment reference components:
```xml
<xf-header-reference
    sling:resourceType="setia/components/xf-header-reference"
    fragmentVariationPath="/content/experience-fragments/setia/header/master"/>
```

---

## Component Strategy

### Component Architecture Principles

1. **Reusability First** - Design for use across multiple pages
2. **Single Responsibility** - Each component does one thing well
3. **Composition Over Inheritance** - Combine simple components
4. **Authoring Flexibility** - Balance control and ease of use

### Component Inventory

**Total Components:** 13 reusable components

#### Global Components
| Component | Usage | Pages |
|-----------|-------|-------|
| `page` (Setia Page) | Base page component | All |
| `xf-header-reference` | Header XF include | All |
| `xf-footer-reference` | Footer XF include | All |
| `cta-section` | Call-to-action block | All |

#### Layout Components
| Component | Usage | Pages |
|-----------|-------|-------|
| `home-hero` | Home hero with 3 cards | Home only |
| `page-banner` | Inner page banner | Company, Services, Solutions |
| `section-heading` | Title/subtitle pair | All |

#### Content Components
| Component | Usage | Pages |
|-----------|-------|-------|
| `rich-text-section` | Rich text content | All |
| `card-grid` | Card layout grid | Home, Company, Services, Solutions |
| `image-section` | Text + image/diagram | Services, Solutions |
| `accordion-section` | Expandable items | Services |
| `download-list` | Downloadable files | Company, Services |
| `logo-gallery` | Partner logos | Company, Services |

### Component Relationship Model

```text
Component Definition (Code)
  Location: /apps/setia/components/hero/
  Contains: HTL, dialog, .content.xml
    ↓
Component Instance (Content)
  Location: /content/setia/us/en/home/jcr:content/.../hero
  Contains: sling:resourceType, authored properties
    ↓
Sling Resolution
  Finds: Component definition via sling:resourceType
    ↓
HTL Rendering
  Uses: Sling Model (if present) + authored properties
    ↓
HTML Output
```

---

## Development Workflow

### Standard Development Flow (Code → AEM)

**When to Use:** Developing components, HTL, Java, CSS, JS

#### Step 1: Component Development
```bash
# Location: ui.apps/src/main/content/jcr_root/apps/setia/components/
# Create:
# - component.html (HTL template)
# - .content.xml (component definition)
# - _cq_dialog/.content.xml (author dialog)
```

#### Step 2: Backend Logic (if needed)
```bash
# Location: core/src/main/java/br/com/setia/core/models/
# Create Sling Model with annotations:
# - @Model(adaptables = Resource.class,
#          defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
# - @ValueMapValue for properties, @ChildResource for multifield items
```

#### Step 3: Frontend Assets
```bash
# Location: ui.frontend/src/main/webpack/
# Add:
# - components/component-name.scss
# - site/main.scss (import)
# - component JavaScript if needed
```

#### Step 4: Build and Deploy
```bash
# Build all modules and deploy
mvn clean install -PautoInstallSinglePackage

# Or deploy specific module:
cd ui.apps
mvn clean install -PautoInstallPackage
```

#### Step 5: Author in AEM
```bash
# Access: http://localhost:4502/editor.html/content/setia/us/en/home.html
# Actions:
# - Add component to page
# - Configure via dialog
# - Preview changes
```

### Reverse Workflow (AEM → Code)

**When to Use:** Creating templates, policies, Experience Fragments

#### Step 1: Create in AEM UI
```bash
# Tools → General → Templates
# Or: Sites → Experience Fragments
# Create and configure in UI
```

#### Step 2: Export from JCR
```bash
# Use Package Manager or vlt:
# - Create package with /conf or /content paths
# - Download package
# - Extract to ui.content
```

#### Step 3: Add to ui.content
```bash
# Location: ui.content/src/main/content/jcr_root/
# Add exported content
# Update filter.xml if needed
```

#### Step 4: Commit to Git
```bash
git add ui.content/
git commit -m "Add template configuration"
```

---

## Component Creation Order

### Recommended Implementation Sequence

**Phase 1: Foundation (Days 1-2)**
1. `page` (Setia Page) - Base page component at `/apps/setia/components/page`
2. Header Experience Fragment
3. Footer Experience Fragment

**Phase 2: Layout Components (Days 2-3)**
4. `page-banner` - Simple banner component
5. `section-heading` - Title/subtitle component
6. `rich-text-section` - Text content component

**Phase 3: Content Components (Days 3-5)**
7. `card-grid` - Reusable card layout
8. `home-hero` - Home page hero (more complex)
9. `image-section` - Text + image layout

**Phase 4: Interactive Components (Days 5-6)**
10. `accordion-section` - Expandable content
11. `download-list` - File downloads
12. `logo-gallery` - Partner logos

**Phase 5: Specialized Components (Day 6)**
13. `cta-section` - Call-to-action block

**Phase 6: Integration (Days 7-8)**
- Create templates
- Configure policies
- Create pages
- Add components to pages
- Test and refine

---

## Content Synchronization Strategy

### Code-Controlled Elements
**Deploy Direction:** Code → AEM  
**Method:** Maven build with `-PautoInstallSinglePackage`

**Elements:**
- Component definitions
- HTL templates
- Component dialogs
- Sling Models
- CSS/JavaScript
- OSGi configurations

### Author-Controlled Elements
**Deploy Direction:** AEM → Code (for version control)  
**Method:** Package Manager export or VLT

**Elements:**
- Templates
- Policies
- Experience Fragments
- Initial page structure
- Sample content

### Runtime-Only Elements
**Not Version Controlled**

**Elements:**
- Page content (authored in production)
- DAM assets (images, videos, documents)
- User-generated content
- Workflow instances

---

## Deployment Profiles

### Maven Profiles

Always combine a deploy profile with `aem-remote`, which holds the system credentials. Without `aem-remote`, deploys fall back to default `admin/admin` and will fail against any environment with real passwords.

| Profile | Purpose | Command |
|---------|---------|---------|
| `autoInstallSinglePackage` | Deploy all to author | `mvn clean install -PautoInstallSinglePackage,aem-remote` |
| `autoInstallSinglePackagePublish` | Deploy all to publish | `mvn clean install -PautoInstallSinglePackagePublish,aem-remote` |
| `autoInstallBundle` | Deploy core bundle only | `mvn clean install -PautoInstallBundle,aem-remote` |
| `autoInstallPackage` | Deploy single module | `cd ui.apps && mvn clean install -PautoInstallPackage,aem-remote` |
| `aem-remote` | Provides AEM credentials (paired with the above) | — |

### Environment Configuration

The `aem-remote` profile defines `sling.user` / `sling.password` and any non-default host/port. Define it in `~/.m2/settings.xml` so credentials never land in the project repo:

```xml
<!-- ~/.m2/settings.xml -->
<profiles>
  <profile>
    <id>aem-remote</id>
    <properties>
      <aem.host>localhost</aem.host>
      <aem.port>4502</aem.port>
      <aem.publish.host>localhost</aem.publish.host>
      <aem.publish.port>4503</aem.publish.port>
      <sling.user>admin</sling.user>
      <sling.password>${env.AEM_PASSWORD}</sling.password>
    </properties>
  </profile>
</profiles>
```

**One-off override (CI, quick test):**
```bash
mvn install -PautoInstallSinglePackage,aem-remote \
  -Daem.host=myhost \
  -Daem.port=4502 \
  -Dsling.user=myuser \
  -Dsling.password=mypass
```

---

## Testing Strategy

### Unit Tests
**Location:** `core/src/test/java/`  
**Purpose:** Test Sling Models and services  
**Command:** `mvn clean test`

### Integration Tests
**Location:** `it.tests/src/main/java/`  
**Purpose:** Test AEM HTTP API  
**Command:** `mvn clean verify -Plocal`

**Configuration:**
```bash
-Dit.author.url=http://localhost:4502
-Dit.author.user=admin
-Dit.author.password=admin
```

### UI Tests
**Location:** `ui.tests/`  
**Purpose:** Browser-based functional tests  
**Framework:** Cypress

**Commands:**
```bash
# Build Docker image
mvn clean package -Pui-tests-docker-build

# Run tests
mvn verify -Pui-tests-docker-execution \
  -DAEM_AUTHOR_URL=http://localhost:4502
```

---

## Best Practices

### Component Development
- ✅ Keep components focused and single-purpose
- ✅ Use Sling Models for logic, HTL for presentation
- ✅ Make dialogs intuitive for authors
- ✅ Provide sensible defaults in dialogs
- ✅ Test components in isolation before page integration

### HTL Templates
- ✅ Use `data-sly-use` for Sling Models
- ✅ Escape output by default (XSS protection)
- ✅ Use `data-sly-resource` for includes
- ✅ Keep HTL logic simple, move complexity to Java

### Sling Models
- ✅ Adapt from `Resource.class` for components
- ✅ Use `@ValueMapValue` for simple properties
- ✅ Cache expensive operations
- ✅ Provide null-safe getters
- ✅ Export packages in core/pom.xml

### Frontend Assets
- ✅ Organize by component in ui.frontend
- ✅ Use SCSS for maintainable styles
- ✅ Follow BEM or similar naming convention
- ✅ Minimize JavaScript dependencies
- ✅ Optimize images before adding to DAM

### Content Authoring
- ✅ Export templates and XFs to ui.content
- ✅ Keep page content in AEM (not Git)
- ✅ Use Experience Fragments for shared content
- ✅ Document authoring workflows

---

## Common Pitfalls to Avoid

❌ **Don't:** Create components without dialogs (not authorable)  
✅ **Do:** Always provide author dialogs for editable properties

❌ **Don't:** Hardcode content in HTL templates  
✅ **Do:** Make content authorable via dialogs

❌ **Don't:** Put business logic in HTL  
✅ **Do:** Use Sling Models for logic

❌ **Don't:** Forget to deploy after code changes  
✅ **Do:** Run `mvn clean install` after every change

❌ **Don't:** Use inline styles or scripts  
✅ **Do:** Use ClientLibs for all CSS/JS

❌ **Don't:** Create deeply nested component hierarchies  
✅ **Do:** Keep component structure flat and composable

❌ **Don't:** Use vault filter `mode="merge"` on template/policy paths during development
✅ **Do:** Use `replace` mode (default — no `mode=` attribute) on `/conf/setia`. Merge mode silently keeps stale template structures alongside new ones — symptom is locked components rendering as empty placeholders without a `sling:resourceType`. Switch `/content/setia` to `merge` only after launch, when authors take over content editing.

---

## Related Documentation

- [01 - Project Overview](./01-project-overview.md)
- [02 - Website Map](./02-website-map.md)
- [04 - Component Specification](./04-component-specification.md)
- [CLAUDE.md](../CLAUDE.md) - Developer guidance
