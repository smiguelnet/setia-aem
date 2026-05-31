# Component Dependency Graph - Setia AEM Website

## Overview

This document visualizes the dependencies between components, templates, and Experience Fragments to determine the optimal build order and identify potential blockers.

---

## Dependency Legend

```
→  Direct dependency (component A requires component B)
⇢  Template dependency (template requires component)
◆  No dependencies (can build first)
●  Has dependencies (must build after dependencies)
■  Critical path item (blocks multiple other components)
```

---

## Complete Dependency Graph

```
┌─────────────────────────────────────────────────────────────────────┐
│                          FOUNDATION LAYER                            │
│                        (No Dependencies)                             │
└─────────────────────────────────────────────────────────────────────┘

◆ page (Setia Page) (base page component)
   ↓
   ├─→ ClientLib categories (setia.base, setia.site, setia.grid)
   └─→ customheaderlibs.html / customfooterlibs.html


◆ Design System Foundation (SCSS variables, mixins)
   ├─→ _variables.scss (colors, typography, spacing, breakpoints)
   ├─→ _mixins.scss (responsive, flexbox, transitions)
   ├─→ _reset.scss (CSS reset/normalize)
   └─→ _typography.scss (font scales, weights)


┌─────────────────────────────────────────────────────────────────────┐
│                     EXPERIENCE FRAGMENTS LAYER                       │
│                    (Depends on: Foundation)                          │
└─────────────────────────────────────────────────────────────────────┘

◆ setia/components/experiencefragment  (generic XF reference component)
   Dependencies:
   └─→ Design System (none structural — it's a Core Components proxy)

   Note:
   └─→ One generic component, parameterized per instance via fragmentVariationPath.
       Replaces the deprecated xf-header-reference / xf-footer-reference pair.


■ Header Experience Fragment instance
   Dependencies:
   ├─→ Design System (for styling)
   ├─→ Navigation component (or placeholder)
   ├─→ setia/components/experiencefragment (the reference component)
   └─→ Logo image in DAM

   Blocks:
   └─→ All templates (wired via fragmentVariationPath in structure)

   Files:
   └─→ /content/experience-fragments/setia/us/en/site/header/master


■ Footer Experience Fragment instance
   Dependencies:
   ├─→ Design System (for styling)
   ├─→ Contact content
   ├─→ setia/components/experiencefragment (the reference component)
   └─→ Mascot image in DAM

   Blocks:
   └─→ All templates (wired via fragmentVariationPath in structure)

   Files:
   └─→ /content/experience-fragments/setia/us/en/site/footer/master


┌─────────────────────────────────────────────────────────────────────┐
│                      SIMPLE COMPONENTS LAYER                         │
│               (Depends on: Design System only)                       │
└─────────────────────────────────────────────────────────────────────┘

◆ page-banner
   Dependencies:
   └─→ Design System (_variables, _mixins)

   Used By:
   ├─→ Company page
   ├─→ Services page
   └─→ Solutions page

   Complexity: LOW
   Build Time: 1 day


◆ section-heading
   Dependencies:
   └─→ Design System

   Used By:
   └─→ ALL pages (most reused component)

   Complexity: LOW
   Build Time: 1 day


◆ rich-text-section
   Dependencies:
   └─→ Design System

   Used By:
   └─→ ALL pages

   Complexity: LOW
   Build Time: 1 day


◆ cta-section
   Dependencies:
   ├─→ Design System
   └─→ Mascot image in DAM (optional)

   Used By:
   └─→ ALL pages (typically at bottom)

   Complexity: LOW
   Build Time: 1 day


┌─────────────────────────────────────────────────────────────────────┐
│                     CONTENT COMPONENTS LAYER                         │
│          (Depends on: Design System + may need Sling Models)        │
└─────────────────────────────────────────────────────────────────────┘

● card-grid
   Dependencies:
   ├─→ Design System
   └─→ CardGridModel (Sling Model - optional but recommended)

   Used By:
   ├─→ Home page (service cards)
   ├─→ Company page (services overview)
   ├─→ Services page (service cards)
   └─→ Solutions page (feature cards)

   Complexity: MEDIUM
   Build Time: 2 days
   Files:
   ├─→ /apps/setia/components/card-grid/
   └─→ core/.../models/CardGridModel.java


◆ image-section
   Dependencies:
   └─→ Design System

   Used By:
   ├─→ Services page (strategy diagram)
   └─→ Solutions page (platform diagram)

   Complexity: LOW
   Build Time: 1 day


◆ logo-gallery
   Dependencies:
   ├─→ Design System
   └─→ Partner logos in DAM

   Used By:
   ├─→ Company page (partners)
   └─→ Services page (partners)

   Complexity: LOW
   Build Time: 1 day


● download-list
   Dependencies:
   ├─→ Design System
   ├─→ DownloadListModel (Sling Model - optional)
   └─→ PDF/document files in DAM

   Used By:
   ├─→ Company page (presentations, documents)
   └─→ Services page (optional)

   Complexity: LOW
   Build Time: 1 day


┌─────────────────────────────────────────────────────────────────────┐
│                    COMPLEX COMPONENTS LAYER                          │
│      (Depends on: Design System + Sling Models + JavaScript)        │
└─────────────────────────────────────────────────────────────────────┘

● home-hero
   Dependencies:
   ├─→ Design System
   ├─→ HomeHeroModel (Sling Model)
   ├─→ Hero background image in DAM
   └─→ Service card icons in DAM

   Used By:
   └─→ Home page ONLY

   Blocks:
   └─→ Landing Page Template (component must exist for template config)

   Complexity: HIGH
   Build Time: 2 days
   Files:
   ├─→ /apps/setia/components/home-hero/
   └─→ core/.../models/HomeHeroModel.java


● accordion-section
   Dependencies:
   ├─→ Design System
   ├─→ accordion-section.js (JavaScript for expand/collapse)
   └─→ AccordionModel (Sling Model - optional)

   Used By:
   └─→ Services page ("and how..." section)

   Complexity: MEDIUM
   Build Time: 2 days
   Files:
   ├─→ /apps/setia/components/accordion-section/
   └─→ ui.frontend/.../accordion-section.js


┌─────────────────────────────────────────────────────────────────────┐
│                         TEMPLATES LAYER                              │
│      (Depends on: All components + Experience Fragments)            │
└─────────────────────────────────────────────────────────────────────┘

■ Setia Landing Page Template
   Dependencies:
   ├─→ page (Setia Page)
   ├─→ setia/components/experiencefragment (Header XF instance, fragmentVariationPath)
   ├─→ setia/components/experiencefragment (Footer XF instance, fragmentVariationPath)
   ├─→ home-hero ✱ CRITICAL
   ├─→ section-heading
   ├─→ rich-text-section
   ├─→ card-grid
   └─→ cta-section

   Blocks:
   └─→ Home page creation

   Path: /conf/setia/settings/wcm/templates/landing-page


■ Setia Content Page Template
   Dependencies:
   ├─→ page (Setia Page)
   ├─→ setia/components/experiencefragment (Header XF instance, fragmentVariationPath)
   ├─→ setia/components/experiencefragment (Footer XF instance, fragmentVariationPath)
   ├─→ page-banner ✱ CRITICAL
   ├─→ section-heading
   ├─→ rich-text-section
   ├─→ card-grid
   ├─→ image-section
   ├─→ accordion-section
   ├─→ download-list
   ├─→ logo-gallery
   └─→ cta-section

   Blocks:
   ├─→ Company page creation
   ├─→ Services page creation
   └─→ Solutions page creation

   Path: /conf/setia/settings/wcm/templates/page-content


┌─────────────────────────────────────────────────────────────────────┐
│                           PAGES LAYER                                │
│                   (Depends on: Templates)                            │
└─────────────────────────────────────────────────────────────────────┘

● Home Page
   Dependencies:
   ├─→ Setia Landing Page Template
   ├─→ Header XF (published)
   ├─→ Footer XF (published)
   └─→ All allowed components

   Components Used:
   ├─→ home-hero
   ├─→ section-heading (×3)
   ├─→ card-grid
   ├─→ rich-text-section
   └─→ cta-section

   Path: /content/setia/us/en   (the locale root IS the home page; no separate /home node)


● Company Page
   Dependencies:
   ├─→ Setia Content Page Template
   ├─→ Header XF (published)
   ├─→ Footer XF (published)
   └─→ All allowed components

   Components Used:
   ├─→ page-banner
   ├─→ rich-text-section (×2)
   ├─→ download-list
   ├─→ card-grid
   ├─→ logo-gallery
   └─→ cta-section

   Path: /content/setia/us/en/company


● Services Page
   Dependencies:
   ├─→ Setia Content Page Template
   ├─→ Header XF (published)
   ├─→ Footer XF (published)
   └─→ All allowed components

   Components Used:
   ├─→ page-banner
   ├─→ rich-text-section
   ├─→ card-grid
   ├─→ accordion-section
   ├─→ image-section
   ├─→ logo-gallery
   └─→ cta-section

   Path: /content/setia/us/en/services


● Solutions Page
   Dependencies:
   ├─→ Setia Content Page Template
   ├─→ Header XF (published)
   ├─→ Footer XF (published)
   └─→ All allowed components

   Components Used:
   ├─→ page-banner
   ├─→ rich-text-section
   ├─→ image-section
   ├─→ card-grid
   └─→ cta-section

   Path: /content/setia/us/en/solutions
```

---

## Critical Path Analysis

### Critical Path Components
These components block template or page creation and must be built first:

1. **page (Setia Page)** → Blocks all templates
2. **setia/components/experiencefragment** → Blocks both XF instances → Blocks all templates
3. **Header XF instance** → wired into all templates via `fragmentVariationPath`
4. **Footer XF instance** → wired into all templates via `fragmentVariationPath`
5. **home-hero** → Blocks Landing Page Template → Blocks Home page
6. **page-banner** → Blocks Content Page Template → Blocks 3 inner pages

### Non-Critical Components
These can be built in any order after the critical path:

- section-heading
- rich-text-section
- card-grid
- image-section
- accordion-section
- download-list
- logo-gallery
- cta-section

---

## Parallel Development Opportunities

### Track 1: Core AEM Structure
```
Day 1-2:   page (Setia Page) + ClientLibs setup
Day 3-5:   Header XF + Footer XF
Day 6-7:   XF reference components
```

### Track 2: Frontend Foundation (Parallel to Track 1)
```
Day 1-2:   SCSS variables + mixins + reset
Day 3-5:   Global styles (typography, layout, utilities)
Day 6-7:   Header/Footer styles
```

### Track 3: Simple Components (After Day 7)
```
Developer A:              Developer B:
- page-banner             - section-heading
- rich-text-section       - cta-section
```

### Track 4: Content Components (After Track 3)
```
Developer A:              Developer B:
- card-grid + Model       - image-section
- download-list           - logo-gallery
```

### Track 5: Complex Components (After Track 4)
```
Developer A:              Developer B:
- home-hero + Model       - accordion-section + JS
```

---

## Dependency Matrix

| Component | Depends On | Blocks | Can Build With |
|-----------|-----------|--------|----------------|
| **page (Setia Page)** | None | All templates | — |
| **Design System** | None | All components (style) | — |
| **setia/components/experiencefragment** | Design System | Both XF instances → all templates | — |
| **Header XF instance** | Design System, experiencefragment | All templates (via fragmentVariationPath) | Footer XF (parallel) |
| **Footer XF instance** | Design System, experiencefragment | All templates (via fragmentVariationPath) | Header XF (parallel) |
| **page-banner** | Design System | Content Page Template | All simple components |
| **section-heading** | Design System | — | All simple components |
| **rich-text-section** | Design System | — | All simple components |
| **cta-section** | Design System | — | All simple components |
| **card-grid** | Design System, CardGridModel | — | image-section, logo-gallery |
| **image-section** | Design System | — | card-grid, logo-gallery |
| **download-list** | Design System | — | logo-gallery |
| **logo-gallery** | Design System | — | download-list |
| **home-hero** | Design System, HomeHeroModel | Landing Page Template | accordion-section |
| **accordion-section** | Design System, JS | — | home-hero |
| **Landing Page Template** | home-hero + 4 others | Home page | Content Page Template (parallel) |
| **Content Page Template** | page-banner + 7 others | 3 inner pages | Landing Page Template (parallel) |

---

## Build Sequence by Layer

### Layer 0: Prerequisites (External Dependencies)
- ✅ AEM SDK installed
- ✅ Maven configured
- ✅ Node.js installed
- ✅ Git repository cloned
- ✅ IDE configured

### Layer 1: Foundation (No Dependencies)
**Build First:**
1. page (Setia Page) component
2. SCSS Design System (_variables, _mixins, _reset, _typography)
3. ClientLib setup (categories, embedding)

**Duration:** 2 days  
**Parallel:** Yes (AEM dev + Frontend dev work separately)

### Layer 2: Shared Elements (Depends on Layer 1)
**Build Second:**
4. `setia/components/experiencefragment` generic reference component
5. Header Experience Fragment instance + styles
6. Footer Experience Fragment instance + styles

**Duration:** 3 days  
**Parallel:** Header and Footer can be built in parallel (both reuse the one generic component)

### Layer 3: Simple Components (Depends on Layer 1)
**Build Third:**
8. page-banner
9. section-heading
10. rich-text-section
11. cta-section

**Duration:** 3-4 days  
**Parallel:** All 4 can be built in parallel by 2 developers

### Layer 4: Content Components (Depends on Layer 1, some need Layer 3)
**Build Fourth:**
12. card-grid + CardGridModel
13. image-section
14. download-list
15. logo-gallery

**Duration:** 4 days  
**Parallel:** 2 developers can each build 2 components

### Layer 5: Complex Components (Depends on Layers 1-3)
**Build Fifth:**
16. home-hero + HomeHeroModel
17. accordion-section + JavaScript

**Duration:** 3-4 days  
**Parallel:** 2 developers can build in parallel

### Layer 6: Templates (Depends on Layers 1-5)
**Build Sixth:**
18. Landing Page Template (needs home-hero)
19. Content Page Template (needs page-banner)

**Duration:** 3-4 days  
**Parallel:** Can be built in parallel once components are ready

### Layer 7: Pages (Depends on Layer 6)
**Build Seventh:**
20. Home page
21. Company page
22. Services page
23. Solutions page

**Duration:** 6-8 days  
**Parallel:** After templates are ready, all 4 pages can be built in parallel

---

## Blocking Relationships

### What Blocks Templates?

**Landing Page Template is blocked by:**
- ✗ page (Setia Page) (base)
- ✗ setia/components/experiencefragment (generic reference component)
- ✗ Header XF instance + Footer XF instance (wired via fragmentVariationPath)
- ✗ **home-hero** (CRITICAL - unique to this template)
- ✓ section-heading (can add later)
- ✓ rich-text-section (can add later)
- ✓ card-grid (can add later)
- ✓ cta-section (can add later)

**Content Page Template is blocked by:**
- ✗ page (Setia Page) (base)
- ✗ setia/components/experiencefragment (generic reference component)
- ✗ Header XF instance + Footer XF instance (wired via fragmentVariationPath)
- ✗ **page-banner** (CRITICAL - primary component)
- ✓ All other components (can add to policy later)

### What Blocks Pages?

**All pages are blocked by:**
- Template must be created and enabled
- Header XF must be published
- Footer XF must be published
- At least one content component must be available

**Specific pages blocked by:**
- Home page → needs home-hero functional
- Inner pages → need page-banner functional

---

## Template Dependency Validation

Before either template is considered complete, validate:

- [ ] **Landing Page** template structure contains **both** a Header XF reference and a Footer XF reference (each a `setia/components/experiencefragment` instance with the correct `fragmentVariationPath`).
- [ ] **Content Page** template structure contains **both** a Header XF reference and a Footer XF reference.
- [ ] Both references use the generic `setia/components/experiencefragment` component — the deprecated `xf-header-reference` / `xf-footer-reference` components are **not** used.
- [ ] All template structure nodes (structure / initial / policies) are **exportable to `ui.content`** and reproduce on a clean instance.
- [ ] XF instances point at the canonical paths (`…/site/header/master`, `…/site/footer/master`).

> See [01 → Mandatory Template Wiring Validation](./01-implementation-plan.md#mandatory-template-wiring-validation) for the full author/publish validation checklist.

---

## Component Reuse Analysis

### Most Reused Components (Build Priority)
1. **section-heading** → Used on ALL 4 pages (multiple times)
2. **rich-text-section** → Used on ALL 4 pages
3. **cta-section** → Used on ALL 4 pages
4. **card-grid** → Used on ALL 4 pages
5. **logo-gallery** → Used on 2 pages
6. **image-section** → Used on 2 pages

### Single-Use Components (Lower Priority)
1. **home-hero** → Home page only (but CRITICAL for template)
2. **accordion-section** → Services page only
3. **download-list** → Company page only

### Strategy
- Build high-reuse components early (benefits all pages)
- Build single-use components only when needed for specific page
- Exception: home-hero is single-use but on critical path

---

## DAM Asset Dependencies

Components requiring DAM assets before full functionality:

| Component | Required Assets | Can Build Without? |
|-----------|----------------|-------------------|
| home-hero | Background image, 3 service icons | Yes (placeholder) |
| page-banner | Background images (optional) | Yes |
| card-grid | Icons for each card | Yes (placeholder) |
| image-section | Content images/diagrams | Yes (placeholder) |
| logo-gallery | Partner logos | No (need actual logos) |
| download-list | PDF/document files | No (need actual files) |
| cta-section | Mascot image | Yes (placeholder) |
| Header XF | Logo image | No (need actual logo) |

**Strategy:**
- Use placeholder images during development
- Upload actual assets in parallel with page creation
- Logo and mascot are critical (upload early)

---

## JavaScript Dependencies

Components requiring JavaScript:

1. **accordion-section** → expand/collapse functionality (REQUIRED)
2. **Header XF** → mobile menu toggle (REQUIRED for mobile)
3. Optional: Smooth scroll, animations, lazy loading

**Strategy:**
- Build accordion JS alongside component
- Build mobile menu JS alongside Header XF
- Optional JS can be added in refinement phase

---

## Sling Model Dependencies

Components with Sling Models:

| Component | Model | Required? |
|-----------|-------|-----------|
| card-grid | CardGridModel | Optional (recommended) |
| home-hero | HomeHeroModel | Optional (recommended) |
| download-list | DownloadListModel | Optional (for metadata) |
| accordion-section | AccordionModel | Optional |

**Strategy:**
- Can build components with HTL-only first
- Add Sling Models for cleaner logic/formatting
- Models enable better testability

---

## Recommended Checkpoints

### Checkpoint 1: Foundation Complete
**Verify:**
- ✅ page (Setia Page) renders
- ✅ SCSS compiles to CSS
- ✅ ClientLibs load in page

### Checkpoint 2: XF Complete
**Verify:**
- ✅ Header XF renders
- ✅ Footer XF renders
- ✅ XF references work in test page
- ✅ Changes to XF reflect on pages

### Checkpoint 3: Simple Components Complete
**Verify:**
- ✅ page-banner renders
- ✅ section-heading renders
- ✅ rich-text-section renders
- ✅ cta-section renders

### Checkpoint 4: All Components Complete
**Verify:**
- ✅ All 13 components render correctly
- ✅ All dialogs save properties
- ✅ All styles applied
- ✅ JavaScript interactions work

### Checkpoint 5: Templates Complete
**Verify:**
- ✅ Both templates available for page creation
- ✅ Components appear in insert menu
- ✅ Policies configured correctly

### Checkpoint 6: Pages Complete
**Verify:**
- ✅ All 4 pages created
- ✅ Navigation works
- ✅ Content populated
- ✅ Responsive on all devices

---

## Related Documentation

- [Implementation Plan](./01-implementation-plan.md)
- [Recommended Build Order](./03-recommended-build-order.md)
- [Component Contracts](./04-component-contracts.md)
- [Technical Risks](./05-technical-risks.md)

---

**Document Version:** 1.0  
**Last Updated:** 2026-05-31
