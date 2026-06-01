# Claude Code Prompt Sequence — Setia AEM Website
# Copy each prompt block into Claude Code one at a time.
# Wait for BUILD SUCCESS + visual verification before moving to the next.

---

## HOW TO USE THIS FILE

- One prompt per Claude Code session
- Always wait for `mvn clean install` to pass before the next prompt
- Check the browser after every deploy prompt
- If a prompt fails mid-way, paste the error back into the same session

---

---
# ════════════════════════════════════════════
# PHASE 1 — Maven Archetype + Module Skeleton
# ════════════════════════════════════════════
---

## PHASE 1 — Single Prompt

```
Read CLAUDE.md and docs/plans/01-implementation-plan.md and docs/plans/03-recommended-build-order.md before doing anything else.

## Task: Maven Archetype + Module Skeleton

Generate the complete Maven multi-module project structure for the Setia Corporate Website on AEMaaCS.

### Project coordinates
- Group ID: br.com.setia
- Artifact ID: setia
- Version: 1.0.0-SNAPSHOT
- AEM SDK: 2026.5.26309
- Core Components: 2.28.0
- Java: 11, Maven: 3.3.9+

### Required modules — all 8
core/, ui.apps/, ui.frontend/, ui.content/, ui.config/, ui.apps.structure/, dispatcher/, all/

### Component scaffold
Create empty but valid component folders under ui.apps/.../apps/setia/components/ for:
experiencefragment, page, home-hero, page-banner, section-heading, card-grid,
rich-text-section, cta-section, logo-gallery, download-list, accordion-section, image-section

Each needs: .content.xml (cq:Component, group="Setia") + placeholder <name>.html HTL file.
The `page` component gets group=".hidden" and super-type core/wcm/components/page/v3/page.

### ClientLib structure under ui.apps/.../apps/setia/clientlibs/
- clientlib-base/ → category: setia.base (embeds setia.grid + Core Components clientlibs)
- clientlib-site/ → category: setia.site
- clientlib-dependencies/ → category: setia.dependencies
Each needs a valid .content.xml declaring categories and dependencies.

### Page component (critical)
ui.apps/.../apps/setia/components/page/:
- customheaderlibs.html → loads setia.base + setia.site (CSS)
- customfooterlibs.html → loads setia.base + setia.site (JS, async)
Do NOT call setia.grid directly — setia.base already embeds it.

### ui.frontend scaffold
- package.json with webpack, sass, typescript, aem-clientlib-generator
- webpack.config.js → outputs to ui.apps clientlibs
- src/main/webpack/site/main.scss with import order:
  _variables.scss → _mixins.scss → _reset.scss → _base.scss → _typography.scss
  → _layout.scss → components/** → styles/* → _utilities.scss
- _variables.scss pre-populated with:
  $color-primary-dark-blue: #10245a
  $color-primary-green: #7ED321
  $spacing-unit: 8px
- src/main/webpack/components/ with .gitkeep

### ui.content skeleton JCR paths
- /conf/setia/
- /content/setia/us/en/  ← this IS the home page, no /home child
- /content/experience-fragments/setia/us/en/site/header/
- /content/experience-fragments/setia/us/en/site/footer/
- /content/dam/setia/ folder structure per docs/plans/08-dam-asset-strategy.md

### ui.content/META-INF/vault/filter.xml
- /conf/setia → replace (no mode= attribute)
- /content/setia → replace
- /content/experience-fragments/setia → replace
- /content/dam/setia → merge

### ui.config OSGi configs
Create config.author/, config.publish/, config.dev/ runmode folders with placeholder README.

### Validation — run these in order, fix all errors before reporting done
1. mvn clean install -DskipTests
2. mvn clean install -PautoInstallSinglePackage,aem-remote
3. Confirm bundle is Active: http://localhost:4502/system/console/bundles
4. Confirm /apps/setia exists: http://localhost:4502/crx/de
5. Confirm NO Hello World, Epic Journey, or San Jose content exists anywhere

### Done when
- [ ] mvn clean install -DskipTests → BUILD SUCCESS
- [ ] All 8 modules present and building
- [ ] All 12 component folders scaffolded
- [ ] ClientLib categories declared correctly
- [ ] ui.frontend: npm install runs without errors
- [ ] Bundle active on localhost:4502
- [ ] No archetype default content
```

---

---
# ════════════════════════════════════════════
# PHASE 2A — XF Header
# ════════════════════════════════════════════
---

## PHASE 2 — Prompt A: XF Header

```
Read CLAUDE.md and docs/plans/01-implementation-plan.md §2.1 before starting.

## Task: Header Experience Fragment — Full Implementation

Build the complete Header XF: AEM structure + HTL + Sling Model (if needed) + SCSS + JS.

### Canonical paths (do not deviate)
- XF path: /content/experience-fragments/setia/us/en/site/header/master
- XF template: /conf/setia/settings/wcm/templates/xf-web-variation
- XF page component: setia/components/xfpage
- XF reference component: setia/components/experiencefragment
  super-type: core/wcm/components/experiencefragment/v2/experiencefragment
  group: Setia - Content
  Do NOT create xf-header-reference — it is deprecated.

### Header visual requirements (from docs/images/website_home.png)
- Dark blue background: #10245a
- Fixed position: position fixed, top 0, z-index 1000 (use $z-index-fixed from _variables.scss)
- Left: logo image — DAM path /content/dam/setia/logos/brand/logo_white.png
  Link URL: /content/setia/us/en, alt="Setia"
- Right: horizontal nav links — Company, Services, Solutions
  Links: /content/setia/us/en/company, /content/setia/us/en/services, /content/setia/us/en/solutions
  Text color: white. Active/hover: $color-primary-green (#7ED321) underline
- Mobile (< 768px): hamburger menu, slide-out nav panel

### Files to create/update
AEM structure (ui.content):
- /content/experience-fragments/setia/us/en/site/header/master/.content.xml
- XF container with logo (Core Components Image v3) + Navigation (Core Components Navigation v2)
- Navigation configured: navigationRoot=/content/setia/us/en, structureDepth=1

Component (ui.apps):
- apps/setia/components/experiencefragment/.content.xml
- apps/setia/components/experiencefragment/_cq_editConfig.xml (cq:inherit="{Boolean}true")
- apps/setia/components/xfpage/.content.xml (for XF page rendering)

Frontend (ui.frontend):
- src/main/webpack/components/_header.scss
- src/main/webpack/site/styles/experiencefragment_header.scss
- src/main/webpack/components/header.ts (hamburger toggle, close on link click)

### HTL rules reminder
- Use data-sly-use for model binding
- Dynamic style values: use @ context='styleString'
- Do not mix ${properties.x} and ${model.x} in the same template

### Validation
1. cd ui.frontend && npm run prod
2. mvn clean install -PautoInstallSinglePackage,aem-remote
3. Open http://localhost:4502/content/setia/us/en.html
4. Confirm: header visible, logo loads, nav links correct, fixed on scroll
5. Confirm: mobile hamburger works at 375px viewport
6. Confirm XF published and rendering in preview mode

### Done when
- [ ] Header renders on home page with logo + nav
- [ ] Fixed scroll behavior works
- [ ] Mobile hamburger menu functions
- [ ] Logo links to /content/setia/us/en
- [ ] Nav links resolve correctly
- [ ] SCSS compiles without errors
- [ ] No console errors
```

---

## PHASE 2 — Prompt B: XF Footer

```
Read CLAUDE.md and docs/plans/01-implementation-plan.md §2.2 before starting.
The Header XF from Phase 2A is already deployed and working.

## Task: Footer Experience Fragment — Full Implementation

### Canonical paths
- XF path: /content/experience-fragments/setia/us/en/site/footer/master
- XF template: /conf/setia/settings/wcm/templates/xf-web-variation
- XF page component: setia/components/xfpage (already exists from Phase 2A)
- XF reference component: setia/components/experiencefragment (already exists)

### Footer visual requirements (from docs/images/website_home.png)
- Dark blue background: #10245a
- Layout: two-column on desktop — left: text content, right: mascot image
- "Get in touch" heading (h2, white)
- Contact email: connect@setia.com.br (white, clickable mailto:)
- Copyright: "© Setia. All rights reserved." (small, muted white)
- Mascot image: /content/dam/setia/logos/brand/contact_area.png (right side, bottom-aligned)
- Mobile: single column, mascot below text

### Files to create/update
AEM structure (ui.content):
- /content/experience-fragments/setia/us/en/site/footer/master/.content.xml
- XF container with: Text component (heading + email + copyright) + Image component (mascot)

Frontend (ui.frontend):
- src/main/webpack/components/_footer.scss
- src/main/webpack/site/styles/experiencefragment_footer.scss

### Template wiring — CRITICAL
After footer XF is created, wire BOTH XFs into BOTH templates:
1. /conf/setia/settings/wcm/templates/landing-page/structure/.content.xml
   - Header: setia/components/experiencefragment with fragmentVariationPath=/content/experience-fragments/setia/us/en/site/header/master
   - Footer: setia/components/experiencefragment with fragmentVariationPath=/content/experience-fragments/setia/us/en/site/footer/master
2. /conf/setia/settings/wcm/templates/page-content/structure/.content.xml — same wiring
Both XF references must be locked in the template structure (authors cannot delete them).

### Validation
1. cd ui.frontend && npm run prod
2. mvn clean install -PautoInstallSinglePackage,aem-remote
3. Open http://localhost:4502/content/setia/us/en.html
4. Confirm: footer renders — "Get in touch", email, mascot image visible
5. Create a test page using page-content template; confirm header + footer render on it
6. Confirm both XFs published

### Done when
- [ ] Footer renders on all pages with mascot + contact info
- [ ] Header AND Footer appear on both template types
- [ ] Template structure locks both XFs (not deletable by authors)
- [ ] Mascot image loads from DAM (upload contact_area.png from docs/assets/images/ first)
- [ ] Responsive layout correct on mobile
- [ ] No console errors
```

---

---
# ════════════════════════════════════════════
# PHASE 3 — Shared Components (one per prompt)
# ════════════════════════════════════════════
---

## PHASE 3 — Prompt 1: card-grid

```
Read CLAUDE.md and docs/plans/01-implementation-plan.md and docs/04-component-specification.md before starting.
Phase 2 (Header + Footer XFs) is complete and deployed.

## Task: card-grid Component — Full Implementation

This is the most reused component (Home, Company, Services pages). Build it completely.

### Component location
/apps/setia/components/card-grid/
group: Setia

### Visual requirements (from docs/images/website_home.png — "What we do" section)
- 2-row × 3-column grid on desktop, 1-column on mobile
- Each card: icon area (top) + title + short description
- Green theme variant: card background #7ED321, white text
- Default theme: white card, dark text, subtle border/shadow
- Cards are authored via multifield in the dialog
- Section has optional title + subtitle above the grid

### Sling Model: CardGridModel
Location: core/src/main/java/br/com/setia/core/models/CardGridModel.java
- adaptables: {SlingHttpServletRequest.class, Resource.class}
- defaultInjectionStrategy: OPTIONAL
- Fields:
  - @ValueMapValue String title
  - @ValueMapValue String subtitle
  - @ValueMapValue String theme (values: "default" | "green")
  - @ChildResource List<CardItem> items
- Inner static class CardItem (@Model, adaptables=Resource.class, OPTIONAL):
  - @ValueMapValue String cardTitle
  - @ValueMapValue String cardDescription
  - @ValueMapValue String iconPath (DAM path to icon image)

### Dialog fields (_cq_dialog/.content.xml)
- title (textfield)
- subtitle (textfield)
- theme (select: Default / Green)
- items (multifield of composite):
  - cardTitle (textfield, required)
  - cardDescription (textarea)
  - iconPath (pathfield, rootPath=/content/dam/setia)

### Design dialog (_cq_design_dialog/.content.xml)
- cq:styleGroups for Style System: "Default Theme" / "Green Theme"

### HTL (card-grid.html)
- data-sly-use.model for CardGridModel
- Render optional title/subtitle above grid
- data-sly-list over model.items
- Apply theme CSS class based on model.theme
- Placeholder text in edit mode when items list is empty

### SCSS (ui.frontend/src/main/webpack/components/_card-grid.scss)
- .card-grid container: CSS grid, 3 columns desktop, 1 mobile
- .card-grid__card: padding, border-radius, transition on hover
- .card-grid--green modifier: green background cards, white text
- Use $spacing-unit, $color-primary-green, respond-to mixin from _variables/_mixins

### Unit test
core/src/test/java/.../models/CardGridModelTest.java
- Use AemContext (io.wcm.testing.mock.aem), JUnit 5
- Test: items loaded from child nodes, theme defaults to "default"

### Policy
ui.content: add card-grid to allowed components in policy_content_main and policy_landing_main.

### Validation
1. mvn clean install -PautoInstallSinglePackage,aem-remote
2. mvn clean test (unit tests pass)
3. Add card-grid to a test page, populate 6 cards, verify green theme renders
4. Verify responsive: 3-col desktop, 1-col mobile

### Done when
- [ ] Component adds to page via drag-and-drop
- [ ] Dialog saves title, subtitle, theme, and items
- [ ] Green theme renders correctly
- [ ] 6-card grid matches screenshot layout
- [ ] Responsive layout correct
- [ ] Unit tests pass
- [ ] BUILD SUCCESS
```

---

## PHASE 3 — Prompt 2: cta-section

```
Read CLAUDE.md and docs/plans/01-implementation-plan.md before starting.

## Task: cta-section Component — Full Implementation

This component appears on every page. It is structure-locked in both templates
(authors can configure content but cannot delete it).

### Component location
/apps/setia/components/cta-section/
group: Setia

### Visual requirements (from docs/images/website_home.png — bottom section)
- Full-width dark blue section (#10245a)
- Left side: large heading "Ready to tackle your next challenge?" + subtitle
- Right side: mascot image (contact_area.png)
- CTA button below subtitle: "Get in touch" → mailto:connect@setia.com.br
- Button style: green (#7ED321), white text, rounded
- Mobile: single column, mascot below text

### Sling Model: CtaSectionModel
- @ValueMapValue String title
- @ValueMapValue String subtitle
- @ValueMapValue String buttonText
- @ValueMapValue String buttonLink
- @ValueMapValue String mascotPath (DAM path)

### Dialog fields
- title (textfield, required)
- subtitle (textfield)
- buttonText (textfield, default "Get in touch")
- buttonLink (textfield, default "mailto:connect@setia.com.br")
- mascotPath (pathfield, rootPath=/content/dam/setia)

### HTL
- Render heading, subtitle, button, and mascot image
- Button: <a> tag with buttonLink, style as .cta-section__button
- Mascot: <img> with data-sly-attribute.src and alt="Setia mascot"
- Edit-mode placeholder when title is empty

### SCSS (_cta-section.scss)
- Full-width, dark blue background
- Two-column flex layout (text left, image right)
- .cta-section__button: green pill button, hover darken
- Mobile: flex-direction column

### Validation
1. mvn clean install -PautoInstallSinglePackage,aem-remote
2. Add to test page, configure content, verify visual matches screenshot
3. Confirm button links correctly
4. Confirm mascot image loads

### Done when
- [ ] Component renders with title, subtitle, button, mascot
- [ ] Button href works (mailto: or URL)
- [ ] Green button style matches design
- [ ] Mascot image loads from DAM path
- [ ] Responsive: single column on mobile
- [ ] BUILD SUCCESS
```

---

## PHASE 3 — Prompt 3: page-banner

```
Read CLAUDE.md before starting.

## Task: page-banner Component — Full Implementation

Used on Company, Services, and Solutions pages as the top hero area.
Structure-locked in the page-content template.

### Component location
/apps/setia/components/page-banner/
group: Setia

### Visual requirements (from docs/images/website_company.png)
- Full-width banner, ~300px tall
- Background image (DAM) with dark overlay
- Large white title (h1) centered
- Smaller white subtitle below
- Default background: /content/dam/setia/images/banners/bg-content.jpg

### Sling Model: PageBannerModel
- @ValueMapValue String title
- @ValueMapValue String subtitle
- @ValueMapValue String backgroundImagePath

### Dialog fields
- title (textfield, required)
- subtitle (textfield)
- backgroundImagePath (pathfield, rootPath=/content/dam/setia)

### Design dialog
- cq:styleGroups: height options (Standard / Tall)

### _cq_editConfig.xml
- Drop target for background image from DAM

### HTL
- Inline background-image style using @ context='styleString'
- Overlay div with title + subtitle
- Placeholder in edit mode when title is empty

### SCSS (_page-banner.scss)
- Position relative, overflow hidden
- Dark overlay (::before pseudo-element, rgba(0,0,0,0.5))
- Centered white text
- Standard: 300px, Tall modifier: 450px
- Mobile: 200px height, smaller text

### Validation
1. mvn clean install -PautoInstallSinglePackage,aem-remote
2. Add to test page, upload bg-content.jpg to DAM first
3. Verify: background image + overlay + white text
4. Test both height variants

### Done when
- [ ] Banner renders with background image and overlay
- [ ] Title and subtitle display white over the image
- [ ] DAM image reference works
- [ ] Responsive height correct on mobile
- [ ] BUILD SUCCESS
```

---

## PHASE 3 — Prompt 4: section-heading

```
Read CLAUDE.md before starting.

## Task: section-heading Component — Full Implementation

Simple reusable heading + subtitle block used to introduce sections on every page.

### Component location
/apps/setia/components/section-heading/
group: Setia

### Visual requirements
- Optional large heading (configurable level h2/h3)
- Optional subtitle below in a lighter weight/size
- Alignment: left (default), center, right — configurable
- Generous top/bottom padding (use $spacing-unit multiples)

### Sling Model: SectionHeadingModel
- @ValueMapValue String title
- @ValueMapValue String subtitle
- @ValueMapValue @Default(values="h2") String headingLevel
- @ValueMapValue @Default(values="left") String alignment

### Dialog fields
- title (textfield)
- subtitle (textfield)
- headingLevel (select: H2 / H3 / H4, default H2)
- alignment (select: Left / Center / Right, default Left)

### HTL
- data-sly-element on the heading tag using model.headingLevel
- Alignment CSS class applied to wrapper
- Placeholder in edit mode when both title and subtitle are empty

### SCSS (_section-heading.scss)
- .section-heading--center, --left, --right alignment modifiers
- Title: large bold, color $color-primary-dark-blue
- Subtitle: medium weight, muted color
- Responsive type sizes using heading-responsive mixin

### Validation
1. mvn clean install -PautoInstallSinglePackage,aem-remote
2. Test all heading levels and alignments on a test page
3. Verify responsive type scaling

### Done when
- [ ] All 3 heading levels render correctly
- [ ] All 3 alignment options work
- [ ] Responsive type scale applies
- [ ] BUILD SUCCESS
```

---

## PHASE 3 — Prompt 5: rich-text-section

```
Read CLAUDE.md before starting.

## Task: rich-text-section Component — Full Implementation

Flexible rich-text component used for narrative sections (Our Story, differentiators, etc.)
Some instances also include a structured list of items.

### Component location
/apps/setia/components/rich-text-section/
group: Setia

### Visual requirements (from docs/images/website_company.png — "Our Story" section)
- Optional title (h2 or h3)
- Rich text body (RTE — bold, italic, links, bullets)
- Optional toggle: show structured list of items below the RTE
- List item: bold label + description text
- Optional certifications/logos row at bottom (for "Business Experience" subsection)

### Sling Model: RichTextSectionModel
- @ValueMapValue String title
- @ValueMapValue String richText (raw HTML from RTE)
- @ValueMapValue @Default(booleanValues=false) Boolean showList
- @ChildResource List<ListItemModel> listItems
- @ValueMapValue @Default(booleanValues=false) Boolean showLogos
- @ValueMapValue String[] logoPaths

Inner ListItemModel:
- @ValueMapValue String label
- @ValueMapValue String description

### Dialog fields
- title (textfield)
- richText (RTE: bold, italic, link, bulletList, orderedList)
- showList (checkbox "Show list items below")
- listItems (multifield — label textfield + description textarea, shown when showList=true)
- showLogos (checkbox "Show certification logos row")
- logoPaths (multifield pathfield, shown when showLogos=true)

### HTL
- data-sly-unwrap for rich text: ${model.richText @ context='html'}
- data-sly-test on list and logos sections
- Logos: render as <img> row

### SCSS (_rich-text-section.scss)
- RTE styles: headings, links (green), bullet indent
- .rich-text-section__list: structured item list with label bold
- .rich-text-section__logos: flex row, gap, img max-height 60px

### Validation
1. mvn clean install -PautoInstallSinglePackage,aem-remote
2. Author rich text with bold/links, enable list, add 3 items, enable logos
3. Verify all variants render correctly

### Done when
- [ ] RTE renders formatted HTML correctly
- [ ] List items show/hide correctly
- [ ] Logos row renders images from DAM
- [ ] BUILD SUCCESS
```

---

## PHASE 3 — Prompt 6: logo-gallery

```
Read CLAUDE.md before starting.

## Task: logo-gallery Component — Full Implementation

Displays partner logos in a horizontal row. Used on Company and Services pages.
Partners: AWS, Neo4j, IBM, ITS. No SAP.

### Component location
/apps/setia/components/logo-gallery/
group: Setia

### Visual requirements (from docs/images/website_company.png — "Partners" section)
- Optional section title above ("Partners")
- Horizontal row of partner logos, evenly spaced
- Logos: grayscale by default, color on hover
- Max logo height: ~80px, auto width
- Mobile: wrap to 2-column grid

### Sling Model: LogoGalleryModel
- @ValueMapValue String title
- @ChildResource List<LogoItem> logos

Inner LogoItem:
- @ValueMapValue String imagePath
- @ValueMapValue String altText
- @ValueMapValue String linkUrl (optional, for clickable logos)

### Dialog fields
- title (textfield, optional)
- logos (multifield composite):
  - imagePath (pathfield, rootPath=/content/dam/setia/logos)
  - altText (textfield, required)
  - linkUrl (textfield, optional)

### HTL
- Render title if present
- Loop logos: each logo is an <img> (wrapped in <a> if linkUrl set)
- Alt text from model.altText

### SCSS (_logo-gallery.scss)
- Flex row, wrap, justify-content: center, gap
- img: grayscale(1) filter, transition, hover: grayscale(0)
- Mobile: 2-column CSS grid

### DAM note
Partner logos for first use: parceria_aws.png, parceria_neo.png, parceria_ibm.png, parceria_its.png
Upload from docs/assets/images/partners/ to /content/dam/setia/logos/partners/

### Validation
1. mvn clean install -PautoInstallSinglePackage,aem-remote
2. Upload partner logos to DAM
3. Add logo-gallery to test page, add 4 logos
4. Verify grayscale → color on hover, responsive wrap

### Done when
- [ ] Logo row renders correctly
- [ ] Grayscale/hover effect works
- [ ] Responsive 2-column on mobile
- [ ] BUILD SUCCESS
```

---

## PHASE 3 — Prompt 7: download-list

```
Read CLAUDE.md before starting.

## Task: download-list Component — Full Implementation

Used on the Company page to let users download PDF presentations.

### Component location
/apps/setia/components/download-list/
group: Setia

### Visual requirements (from docs/images/website_company.png)
- Optional section title
- List of downloadable files, each row:
  - File icon (PDF icon)
  - File label (e.g. "Company Presentation")
  - Download button / link (opens/downloads the DAM asset)
- Hover: row highlight

### Sling Model: DownloadListModel
- @ValueMapValue String title
- @ChildResource List<DownloadItem> items

Inner DownloadItem:
- @ValueMapValue String label
- @ValueMapValue String filePath (DAM path to PDF)
- @ValueMapValue String fileDescription (optional subtitle)

### Dialog fields
- title (textfield)
- items (multifield):
  - label (textfield, required)
  - filePath (pathfield, rootPath=/content/dam/setia/documents)
  - fileDescription (textfield)

### HTL
- Render each item as <a href="${item.filePath}" download target="_blank">
- PDF icon (inline SVG or CSS icon)
- Label + optional description

### SCSS (_download-list.scss)
- .download-list__item: flex row, border-bottom, padding
- Hover: background lighten, green left border accent
- .download-list__icon: PDF icon, $color-primary-green
- .download-list__label: font-weight bold

### DAM note
PDFs: setia-institucional.pdf, metodo-trabalho-setia.pdf
Upload from docs/assets/pdf/ to /content/dam/setia/documents/presentations/

### Validation
1. mvn clean install -PautoInstallSinglePackage,aem-remote
2. Upload PDFs to DAM
3. Add download-list to test page with 2 items
4. Click download links — verify files open/download

### Done when
- [ ] Download links work (open PDF in browser or trigger download)
- [ ] Row hover effect visible
- [ ] PDF icon renders
- [ ] BUILD SUCCESS
```

---

## PHASE 3 — Prompt 8: accordion-section

```
Read CLAUDE.md and docs/06-frontend-tech-details.md (canonical accordion JS is defined there) before starting.

## Task: accordion-section Component — Full Implementation

Used on the Services page ("and how..." section) with expand/collapse functionality.

### Component location
/apps/setia/components/accordion-section/
group: Setia
cq:isContainer="{Boolean}true" — accordion items are child nodes

### Visual requirements (from docs/images/website_services.png)
- Optional section title above
- List of accordion items, each:
  - Header row: bold label + chevron icon (▼/▲)
  - Body: rich text, collapsed by default
  - Click header: expand body, chevron rotates
  - Only one item open at a time (or allow multiple — use policy)
- Smooth CSS transition on open/close

### Sling Model: AccordionSectionModel
- @ValueMapValue String title
- @ChildResource List<AccordionItem> items

Inner AccordionItem:
- @ValueMapValue String itemTitle
- @ValueMapValue String itemContent (rich text HTML)

### Dialog fields
- title (textfield)
- items (multifield):
  - itemTitle (textfield, required)
  - itemContent (RTE)

### HTL
- data-sly-list over model.items
- Each item: wrapper div.accordion-item, header button.accordion-item__trigger, body div.accordion-item__panel
- aria-expanded on button, aria-hidden on panel — for accessibility
- data-accordion-item attribute for JS targeting

### TypeScript / JS (ui.frontend)
- src/main/webpack/components/accordion.ts
- On DOMContentLoaded: query all [data-accordion-item]
- Toggle aria-expanded + CSS class .is-open on click
- Smooth max-height transition (JS sets max-height to scrollHeight)
- Follow the canonical accordion pattern from docs/06-frontend-tech-details.md

### SCSS (_accordion-section.scss)
- .accordion-item__panel: max-height 0, overflow hidden, transition max-height 0.3s ease
- .accordion-item.is-open .accordion-item__panel: max-height set by JS
- .accordion-item__trigger: full-width button, flex space-between, chevron icon
- Chevron: rotate(180deg) on .is-open

### Validation
1. mvn clean install -PautoInstallSinglePackage,aem-remote
2. Add to test page with 4 items
3. Click each item — verify expand/collapse animation
4. Verify keyboard navigation (Enter/Space on button)
5. Verify aria-expanded toggles correctly

### Done when
- [ ] Accordion expands/collapses with animation
- [ ] Only one item open at a time (or per policy setting)
- [ ] Keyboard accessible (Enter/Space trigger)
- [ ] aria-expanded toggles correctly
- [ ] BUILD SUCCESS
```

---

## PHASE 3 — Prompt 9: image-section

```
Read CLAUDE.md before starting.

## Task: image-section Component — Full Implementation

Used on Services (components.png + strategy.jpg) and Solutions (digital-assets.jpg) pages.
A flexible image + caption/title layout component.

### Component location
/apps/setia/components/image-section/
group: Setia

### Visual requirements
- Optional title (h2/h3) above image
- Optional subtitle / intro text
- Full-width or contained image (configurable)
- Optional caption below image
- Image from DAM

### _cq_editConfig.xml
- Drop target for DAM image

### Sling Model: ImageSectionModel
- @ValueMapValue String title
- @ValueMapValue String subtitle
- @ValueMapValue String imagePath
- @ValueMapValue String imageAlt
- @ValueMapValue String caption
- @ValueMapValue @Default(booleanValues=true) Boolean fullWidth

### Dialog fields
- title (textfield)
- subtitle (textfield)
- imagePath (pathfield, rootPath=/content/dam/setia)
- imageAlt (textfield, required)
- caption (textfield)
- fullWidth (checkbox, default checked)

### HTL
- Render title/subtitle if present
- <img> with src from model.imagePath and alt from model.imageAlt
- Caption in <figcaption> if present
- Full-width modifier CSS class

### SCSS (_image-section.scss)
- .image-section--full-width img: width 100%, height auto
- .image-section__caption: small text, muted color, centered
- Section padding using section-spacing mixin

### Validation
1. mvn clean install -PautoInstallSinglePackage,aem-remote
2. Upload strategy.jpg and digital-assets.jpg from docs/assets/images/
3. Add image-section to test page with each image
4. Verify full-width and contained variants

### Done when
- [ ] Images render from DAM correctly
- [ ] Full-width layout works
- [ ] Title/subtitle render above image
- [ ] Caption renders below
- [ ] BUILD SUCCESS
```

---

## PHASE 3 — Prompt 10: home-hero

```
Read CLAUDE.md and docs/plans/01-implementation-plan.md §home-hero before starting.

## Task: home-hero Component — Full Implementation

The hero banner on the Home page. Structure-locked in the landing-page template.
Contains a title, 3 service highlight cards (hard-coded structure, content-editable).

### Component location
/apps/setia/components/home-hero/
group: Setia

### Visual requirements (from docs/images/website_home.png)
- Full-viewport-height hero (100vh)
- Background image: /content/dam/setia/images/hero-backgrounds/bg-home.jpg
- Dark overlay (rgba 0,0,0,0.5)
- Center: large white headline "Your global software outsourcing partner"
- Below headline: row of 3 service cards (dark blue, semi-transparent)
  Card 1: "Blockchain, Smart Contracts and NFTs"
  Card 2: "Cloud-Native Software Development"
  Card 3: "Digital Transformation and Industry 4.0"
- Cards: icon area (top) + title text, white on dark blue

### Sling Model: HomeHeroModel
- @ValueMapValue String title
- @ValueMapValue String backgroundImagePath
- @ChildResource List<HeroCardItem> cards (max 3)

Inner HeroCardItem:
- @ValueMapValue String cardTitle
- @ValueMapValue String iconPath

### Dialog fields
- title (textfield, required)
- backgroundImagePath (pathfield)
- cards (multifield, max 3 items):
  - cardTitle (textfield)
  - iconPath (pathfield, rootPath=/content/dam/setia)

### _cq_editConfig.xml
- Drop target for background image

### HTL
- Inline background-image style using @ context='styleString'
- Dark overlay ::before via CSS class
- Large h1 title
- data-sly-list for hero cards (max 3 via Sling Model)

### SCSS (_home-hero.scss)
- height: 100vh, position relative
- ::before overlay
- .home-hero__title: large display text, white, centered
- .home-hero__cards: flex row, centered, gap
- .home-hero__card: dark blue semi-transparent, padding, border-radius, white text
- Mobile: cards stack vertically, title smaller

### Validation
1. mvn clean install -PautoInstallSinglePackage,aem-remote
2. Upload bg-home.jpg from docs/assets/images/
3. Add home-hero to home page, populate title + 3 cards
4. Verify full-height hero, overlay, cards row
5. Mobile: cards stack

### Done when
- [ ] Full-height hero renders with background + overlay
- [ ] Headline displays correctly
- [ ] 3 service cards render in a row (desktop) or stacked (mobile)
- [ ] Background image loads from DAM
- [ ] BUILD SUCCESS
```

---

---
# ════════════════════════════════════════════
# PHASE 4A — Editable Templates
# ════════════════════════════════════════════
---

## PHASE 4 — Prompt A: Editable Templates

```
Read CLAUDE.md and docs/plans/01-implementation-plan.md §Template & Policy Architecture before starting.
All components from Phase 3 are built and deployed.

## Task: Editable Templates + Policies — Full Implementation

Create both editable templates and all associated policies in ui.content.

### Template 1: landing-page
Path: /conf/setia/settings/wcm/templates/landing-page
Used by: Home page (/content/setia/us/en)
Structure (locked — authors cannot add/remove/reorder):
1. setia/components/experiencefragment (Header XF, locked, fragmentVariationPath=header master)
2. setia/components/home-hero (locked)
3. parsys/responsivegrid (editable container — authors can add content here)
4. setia/components/cta-section (locked)
5. setia/components/experiencefragment (Footer XF, locked, fragmentVariationPath=footer master)

Allowed components in the parsys (policy_landing_main):
- setia/components/section-heading
- setia/components/card-grid
- setia/components/rich-text-section
- setia/components/image-section

### Template 2: page-content
Path: /conf/setia/settings/wcm/templates/page-content
Used by: Company, Services, Solutions pages
Structure (locked):
1. setia/components/experiencefragment (Header XF, locked)
2. setia/components/page-banner (locked)
3. parsys/responsivegrid (editable container)
4. setia/components/cta-section (locked)
5. setia/components/experiencefragment (Footer XF, locked)

Allowed components in the parsys (policy_content_main):
- setia/components/section-heading
- setia/components/card-grid
- setia/components/rich-text-section
- setia/components/logo-gallery
- setia/components/download-list
- setia/components/accordion-section
- setia/components/image-section

### Policies
policy_cardgrid: cq:styleGroups — "Default Theme" / "Green Theme"
policy_cta: no style groups needed
policy_pagebanner: cq:styleGroups — "Standard Height" / "Tall"
policy_content_main: allowed components list above
policy_landing_main: allowed components list above

### XF template (for XF creation)
Path: /conf/setia/settings/wcm/templates/xf-web-variation
(May already exist from Phase 2 — verify, create if missing)

### filter.xml
Ensure /conf/setia is covered with replace mode.

### Validation
1. mvn clean install -PautoInstallSinglePackage,aem-remote
2. Go to AEM Templates console: http://localhost:4502/libs/wcm/core/content/sites/templates.html/conf/setia
3. Confirm both templates appear
4. Create a test page from landing-page template — confirm locked structure visible
5. Create a test page from page-content template — confirm locked structure visible
6. Try adding a component NOT in the allowed list — confirm it is blocked

### Done when
- [ ] Both templates visible in Templates console
- [ ] Landing page template: correct locked structure
- [ ] Content page template: correct locked structure
- [ ] XF references in structure use correct fragmentVariationPath
- [ ] Allowed-component policies work (blocked components not addable)
- [ ] BUILD SUCCESS
```

---

---
# ════════════════════════════════════════════
# PHASE 4B — Page Assembly (one per page)
# ════════════════════════════════════════════
---

## PHASE 4 — Prompt B: Home Page

```
Read CLAUDE.md and docs/plans/09-boostrap-initial-content.md §Home before starting.
Templates from Phase 4A are deployed. All components are available.

## Task: Home Page — Create, Populate, and Publish

### Page details
- Path: /content/setia/us/en  ← this IS the home page, no /home child
- Template: landing-page
- Page title: "Home"
- Page name: en (locale root, already exists as folder — set jcr:content)

### Component configuration (from docs/plans/09-boostrap-initial-content.md)

home-hero (locked):
- title: "Your global software outsourcing partner"
- backgroundImagePath: /content/dam/setia/images/hero-backgrounds/bg-home.jpg
- cards:
  1. "Blockchain, Smart Contracts and NFTs"
  2. "Cloud-Native Software Development"
  3. "Digital Transformation and Industry 4.0"

section-heading (in parsys):
- title: "Delivering Innovative and Quality Solutions"
- subtitle: "Leading you through the journey to transform your ideas into accessible digital products."
- alignment: center

section-heading (in parsys):
- title: "What we do"
- subtitle: "Digital Products for Future-Ready Businesses"
- alignment: center

card-grid (in parsys, green theme, 6 cards):
1. Blockchain Application Development
2. Mobile Application Development
3. Cloud-native Applications Development
4. Enterprise Application Development
5. Enterprise Application Integration
6. Robotic Process Automation Implementation

rich-text-section (in parsys):
- title: "Engagement. Gamification as a key element"
- richText: "We leverage gamification and engagement mechanics to drive user adoption and deliver measurable business results."

cta-section (locked):
- title: "Ready to tackle your next challenge?"
- subtitle: "Setia can help you build value through innovation."
- buttonText: "Get in touch"
- buttonLink: "mailto:connect@setia.com.br"
- mascotPath: /content/dam/setia/logos/brand/contact_area.png

### DAM assets to upload first (from docs/assets/)
- docs/assets/images/bg-home.jpg → /content/dam/setia/images/hero-backgrounds/bg-home.jpg
- docs/assets/images/contact_area.png → /content/dam/setia/logos/brand/contact_area.png

Upload via curl to AEM DAM API or via AEM Assets UI.

### After authoring
1. Activate/publish the page
2. Export page content to ui.content via Package Manager
3. Update ui.content/META-INF/vault/filter.xml to include /content/setia/us/en

### Validation
Compare http://localhost:4502/content/setia/us/en.html against docs/images/website_home.png:
- [ ] Hero: full-height, bg image, overlay, headline, 3 cards
- [ ] "What we do" card grid: 6 cards, green theme
- [ ] Engagement section renders
- [ ] CTA section: mascot + button
- [ ] Header + Footer render
- [ ] No empty parsys / "Drag components here" visible

### Done when
- [ ] Page visually matches screenshot
- [ ] All components populated (no empty required fields)
- [ ] Page published
- [ ] Content exported to ui.content
```

---

## PHASE 4 — Prompt C: Company Page

```
Read CLAUDE.md and docs/plans/09-boostrap-initial-content.md §Company before starting.

## Task: Company Page — Create, Populate, and Publish

### Page details
- Path: /content/setia/us/en/company
- Template: page-content
- Page title: "Company", name: "company"

### Component configuration

page-banner (locked):
- title: "We are Setia"
- subtitle: "Delivering Innovative and Quality Solutions"
- backgroundImagePath: /content/dam/setia/images/banners/bg-content.jpg

rich-text-section — "Our Story":
- title: "Our Story"
- richText: "Offering solutions to a wide range of clients in a variety of fields for more than 21 years, Setia is a global software development company with deep expertise in digital transformation."

rich-text-section — "What makes Setia different?":
- title: "What makes Setia different?"
- subtitle: "We are the ally for your business, industry and profitability"
- showList: true
- listItems:
  1. label: "COMPLIANCE-FOCUSED APPROACH" | description: "We build solutions that meet regulatory standards."
  2. label: "HIGH-AVAILABILITY QUALITY SYSTEMS" | description: "Innovative, robust architectures built to last."
  3. label: "BEST TECHNOLOGY AVAILABLE" | description: "We choose the right tech for your business challenges."
  4. label: "DEDICATED PROJECT COORDINATION" | description: "Full delivery support from kickoff to launch."
  5. label: "INVOLVEMENT AT DIFFERENT LEVELS" | description: "Strategic and operational engagement throughout."
- showLogos: true
- logoPaths:
  - /content/dam/setia/logos/certifications/logo_ibm.png
  - /content/dam/setia/logos/certifications/logo_oracle.png
  - /content/dam/setia/logos/certifications/logo_redhat.png
  - /content/dam/setia/logos/certifications/parceria_microsoft.png

download-list:
- title: "Downloads"
- items:
  1. label: "Institutional Presentation" | filePath: /content/dam/setia/documents/presentations/setia-institucional.pdf
  2. label: "Software Development Method" | filePath: /content/dam/setia/documents/presentations/metodo-trabalho-setia.pdf

card-grid (green theme, 6 cards — same as Home):
1–6: same service cards as Home page

logo-gallery — Partners:
- title: "Partners"
- logos: AWS, Neo4j, IBM, ITS (from /content/dam/setia/logos/partners/)

cta-section (locked): same as Home

### DAM assets to upload
- docs/assets/images/bg-content.jpg → /content/dam/setia/images/banners/bg-content.jpg
- docs/assets/images/team_certifications/* → /content/dam/setia/logos/certifications/
- docs/assets/images/partners/* → /content/dam/setia/logos/partners/
- docs/assets/pdf/* → /content/dam/setia/documents/presentations/

### Validation
Compare against docs/images/website_company.png:
- [ ] Page banner: "We are Setia" over bg-content.jpg
- [ ] Our Story section renders
- [ ] Differentiator list + certification logos render
- [ ] Downloads work (PDFs open)
- [ ] Service card grid renders (green)
- [ ] Partner logos render (AWS, Neo4j, IBM, ITS — no SAP)
- [ ] Header + Footer render
- [ ] Page published + exported to ui.content
```

---

## PHASE 4 — Prompt D: Services Page

```
Read CLAUDE.md and docs/plans/09-boostrap-initial-content.md §Services before starting.

## Task: Services Page — Create, Populate, and Publish

### Page details
- Path: /content/setia/us/en/services
- Template: page-content
- Page title: "Services", name: "services"

### Component configuration

page-banner (locked):
- title: "We are a world-class Software Developer Company"
- backgroundImagePath: /content/dam/setia/images/banners/bg-content.jpg

rich-text-section:
- title: "Turn Opportunities Into Results using Technology"
- richText: "We combine technology expertise with business acumen to deliver software solutions that create real value."

section-heading + card-grid (green, 6 cards): same as Home

image-section — "and how...":
- title: "and how..."
- imagePath: /content/dam/setia/images/content-images/components.png
- imageAlt: "Our process components"
- fullWidth: false

accordion-section:
- title: (empty)
- items:
  1. "Blockchain Application Development" | "We design and develop blockchain solutions tailored to your business."
  2. "Enterprise Application Development and Integration" | "Connecting your enterprise systems for seamless data flow."
  3. "Gamification Content and Solutions" | "Engagement mechanics that drive user adoption and retention."
  4. "Robotic Process Automation (RPA)" | "Automating repetitive tasks to free your team for higher value work."

image-section — Strategy:
- title: "Our Strategy: Component-driven Acceleration"
- subtitle: "Business blocks as editable components to empower and accelerate your business"
- imagePath: /content/dam/setia/images/diagrams/strategy.jpg
- imageAlt: "Component-driven acceleration strategy diagram"
- fullWidth: true

logo-gallery: same 4 partners as Company

cta-section (locked): same as Home

### DAM assets to upload
- docs/assets/images/components.png → /content/dam/setia/images/content-images/
- docs/assets/images/strategy.jpg → /content/dam/setia/images/diagrams/

### Validation
Compare against docs/images/website_services.png:
- [ ] Page banner renders
- [ ] "and how..." image + accordion below it
- [ ] Accordion expands/collapses correctly
- [ ] Strategy diagram renders full-width
- [ ] Partner logos render
- [ ] Page published + exported to ui.content
```

---

## PHASE 4 — Prompt E: Solutions Page

```
Read CLAUDE.md and docs/plans/09-boostrap-initial-content.md §Solutions before starting.

## Task: Solutions Page — Create, Populate, and Publish

### Page details
- Path: /content/setia/us/en/solutions
- Template: page-content
- Page title: "Solutions", name: "solutions"

### Component configuration

page-banner (locked):
- title: "Blockchain"
- subtitle: "Authenticity and Ownership"
- backgroundImagePath: /content/dam/setia/images/banners/bg-content.jpg

rich-text-section:
- title: "We are on top of today's leading technologies"
- subtitle: "Leverage Blockchain Outsourcing Services from Setia"
- richText: "Our blockchain practice delivers secure, scalable distributed ledger solutions for asset management, traceability, and digital transformation."

image-section — Platform Diagram:
- title: "Digital Assets Management Platform"
- subtitle: "Business blocks as editable components to empower and accelerate your business"
- imagePath: /content/dam/setia/images/diagrams/digital-assets.jpg
- imageAlt: "Digital Assets Management Platform architecture"
- fullWidth: true

card-grid (default theme, 6 feature cards):
1. "Built-in Stellar" | "Native Stellar blockchain integration for digital asset management."
2. "Securitized Assets" | "Tokenize real-world assets with full regulatory compliance."
3. "Marketplace and Digital Collections" | "Launch NFT marketplaces and curated digital collections."
4. "Rarity Factor" | "Algorithmic rarity scoring for unique digital assets."
5. "Concurrency Management" | "Handle high-volume concurrent transactions reliably."
6. "Non-Fungible Tokens" | "End-to-end NFT lifecycle management."

cta-section (locked): same as Home

### DAM assets to upload
- docs/assets/images/digital-assets.jpg → /content/dam/setia/images/diagrams/

### Validation
Compare against docs/images/website_solutions.png:
- [ ] Page banner: "Blockchain" / "Authenticity and Ownership"
- [ ] Platform diagram renders full-width
- [ ] 6 feature cards render (default/white theme)
- [ ] Card titles use screenshot wording (Built-in Stellar, Non-Fungible Tokens, etc.)
- [ ] Page published + exported to ui.content

### After this page
Export all authored content (all 4 pages + XFs + DAM structure) to ui.content:
mvn -PautoInstallSinglePackage,aem-remote clean install (to verify full reproducibility)
```

---

---
# ════════════════════════════════════════════
# PHASE 5 — Bootstrap & Final Validation
# ════════════════════════════════════════════
---

## PHASE 5 — Single Prompt: Bootstrap Content Validation + Export

```
Read CLAUDE.md and docs/plans/09-boostrap-initial-content.md in full before starting.
All 4 pages are authored and published. All DAM assets are uploaded.

## Task: Bootstrap Validation + ui.content Export

### Step 1: Full reproducibility test
On a clean instance (or by deleting /content/setia and reimporting):
mvn clean install -PautoInstallSinglePackage,aem-remote
Confirm all 4 pages render with full content (not empty grids).

### Step 2: Navigation validation
- Open http://localhost:4502/content/setia/us/en.html
- Header logo → clicks to /content/setia/us/en ✓
- "Company" nav link → /content/setia/us/en/company ✓
- "Services" nav link → /content/setia/us/en/services ✓
- "Solutions" nav link → /content/setia/us/en/solutions ✓
- No broken links on any page

### Step 3: Visual comparison checklist
For each page, compare against docs/images/website_*.png:
- [ ] Home: hero, 6-card grid (green), engagement, CTA
- [ ] Company: banner, story, differentiators, cert logos, downloads, cards, partners, CTA
- [ ] Services: banner, intro, cards, "and how", accordion, strategy diagram, partners, CTA
- [ ] Solutions: banner, intro, platform diagram, 6 feature cards, CTA

### Step 4: DAM asset audit
Verify no broken images on any page:
- Logo: /content/dam/setia/logos/brand/logo_white.png
- Mascot: /content/dam/setia/logos/brand/contact_area.png
- Hero: /content/dam/setia/images/hero-backgrounds/bg-home.jpg
- Banners: /content/dam/setia/images/banners/bg-content.jpg
- Partners: /content/dam/setia/logos/partners/ (AWS, Neo4j, IBM, ITS)
- Certs: /content/dam/setia/logos/certifications/ (IBM, Oracle, Red Hat, Microsoft)
- Diagrams: strategy.jpg, digital-assets.jpg
- Downloads: setia-institucional.pdf, metodo-trabalho-setia.pdf

### Step 5: Responsive validation
At each viewport (375px, 768px, 1024px, 1440px):
- [ ] Header: hamburger on mobile, horizontal nav on desktop
- [ ] Hero: full height, cards stack on mobile
- [ ] Card grids: 3-col desktop, 1-col mobile
- [ ] Page banners: correct height
- [ ] Footer: single column on mobile

### Step 6: filter.xml audit
ui.content/META-INF/vault/filter.xml must include:
- /conf/setia (replace)
- /content/setia (replace)
- /content/experience-fragments/setia (replace)
- /content/dam/setia (merge)

### Step 7: Acceptance criteria (from plan 09)
- [ ] Pages visually resemble screenshots
- [ ] No page has an empty responsive grid
- [ ] Navigation works end-to-end
- [ ] Header + Footer render on every page
- [ ] Responsive behavior correct at all breakpoints
- [ ] DAM assets resolve (no broken images or PDFs)
- [ ] Lighthouse mobile score > 85 on each page

### Step 8: Commit
git add -A
git commit -m "feat: bootstrap initial content for all 4 pages

- Home, Company, Services, Solutions pages authored
- XF Header + Footer wired to all templates
- DAM assets uploaded and referenced
- ui.content exported and version-controlled
- filter.xml covers all authored paths"

### Done when ALL acceptance criteria above pass
```

---

## QUICK REFERENCE: Validation Commands

```bash
# Build only (no deploy)
mvn clean install -DskipTests

# Build + deploy everything
mvn clean install -PautoInstallSinglePackage,aem-remote

# Deploy only ui.apps (fastest for SCSS/HTL changes)
mvn clean install -pl ui.apps -PautoInstallPackage,aem-remote

# Deploy only core bundle (Java model changes)
mvn clean install -pl core -PautoInstallBundle,aem-remote

# Frontend compile only
cd ui.frontend && npm run prod

# Unit tests only
mvn clean test

# AEM console URLs
# Bundles:    http://localhost:4502/system/console/bundles
# CRX/DE:     http://localhost:4502/crx/de
# Templates:  http://localhost:4502/libs/wcm/core/content/sites/templates.html/conf/setia
# DAM:        http://localhost:4502/assets.html/content/dam/setia
# XFs:        http://localhost:4502/aem/experience-fragments.html/content/experience-fragments/setia
# ClientLibs: http://localhost:4502/libs/granite/ui/content/dumplibs.rebuild.html
```
