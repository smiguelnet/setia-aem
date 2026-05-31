# Recommended Build Order - Setia AEM Website

## Overview

This document provides the optimal sequence for building components, templates, and pages based on dependencies, risk, and team efficiency.

**Strategy:** Build from foundation up, prioritizing critical path items and maximizing parallel work.

---

## Build Order Summary

```
Week 1: Foundation (Days 1-5)
Week 2: Experience Fragments + Simple Components (Days 6-10)
Week 3: Content Components (Days 11-15)
Week 4: Complex Components (Days 16-20)
Week 5: Templates + Pages (Days 21-25)
Week 6-7: Testing + Refinement (Days 26-35)
Week 8: Launch (Days 36-40)
```

---

## Detailed Build Sequence

### 🔹 Day 1-2: Foundation Layer

#### Priority 1A: Base Page Setup (AEM Developer)
**Duration:** 1 day

```
1. page (Setia Page) component
   Location: /apps/setia/components/page/
   Files:
   - .content.xml (component definition; jcr:title="Setia Page", componentGroup=".hidden")
   - customheaderlibs.html (ClientLib CSS includes — calls setia.base + setia.site)
   - customfooterlibs.html (ClientLib JS includes — calls setia.base + setia.site, async)
   (Page properties dialog is inherited from core/wcm/components/page/v3/page; HTML rendering also inherited.)
```

**Build Steps:**
1. Create component folder structure at `/apps/setia/components/page`
2. Define component XML with super-type `core/wcm/components/page/v3/page`
3. Add ClientLib include statements (note: `setia.base` embeds `setia.grid` and CC libs — call only `setia.base` + `setia.site`)
4. Deploy and test: `mvn clean install -PautoInstallSinglePackage,aem-remote`

**Success Criteria:**
- ✅ Component appears in component browser
- ✅ Can create test page
- ✅ Page renders without errors

---

#### Priority 1B: Design System Foundation (Frontend Developer)
**Duration:** 2 days

```
ui.frontend/src/main/webpack/site/
├── _variables.scss (CRITICAL - used by all other files)
├── _mixins.scss
├── _reset.scss
├── _typography.scss
├── _layout.scss
├── _utilities.scss
└── main.scss (imports all above)
```

**Build Steps:**
1. Create `_variables.scss` with all design tokens:
   - Colors ($color-primary, $color-secondary, etc.)
   - Typography ($font-family, $font-size-*, etc.)
   - Spacing ($spacing-unit, $spacing-*, etc.)
   - Breakpoints ($breakpoint-*)
   - Shadows, borders, transitions

2. Create `_mixins.scss`:
   - respond-to (responsive breakpoints)
   - flex-center
   - container
   - button-reset
   - transition
   - visually-hidden

3. Create `_reset.scss` (CSS normalize)

4. Create `_typography.scss` (heading styles, body text)

5. Create `_layout.scss` (grid, containers, spacing utilities)

6. Create `_utilities.scss` (helper classes)

7. Create `main.scss` that imports all files

8. Configure webpack to compile SCSS

9. Test compilation: `npm run dev`

**Success Criteria:**
- ✅ SCSS compiles to CSS without errors
- ✅ Variables accessible in all SCSS files
- ✅ Output CSS includes all styles
- ✅ ClientLib generated in ui.apps

---

### 🔹 Day 3-5: Experience Fragments

#### Priority 2A: Header Experience Fragment (AEM + Frontend)
**Duration:** 2 days

**Day 3: AEM Structure (AEM Developer)**
1. Create XF structure in AEM UI:
   - Path: `/content/experience-fragments/setia/header`
   - Create `master` variation
   - Add responsive grid container
   - Add logo image component
   - Add navigation component (or placeholder text nodes)
   - Configure XF properties

2. Test XF renders in editor

**Day 3-4: Frontend Styling (Frontend Developer)**
```
ui.frontend/src/main/webpack/components/header.scss
ui.frontend/src/main/webpack/components/header.js (for mobile menu)
```

**Build Steps:**
1. Create header.scss:
   - Dark blue background (#10245a)
   - Fixed positioning (position: fixed, top: 0, z-index: 1000)
   - Logo positioning (left side)
   - Navigation layout (right side, horizontal)
   - Mobile menu styles (hamburger icon, slide-out menu)

2. Create header.js:
   - Mobile menu toggle functionality
   - Close menu on link click
   - Smooth scroll to anchors

3. Test responsive behavior at all breakpoints

4. Deploy and test on AEM page

**Success Criteria:**
- ✅ Header displays with logo and navigation
- ✅ Fixed scroll behavior works
- ✅ Mobile menu functions correctly
- ✅ Responsive across all devices

---

#### Priority 2B: Footer Experience Fragment (AEM + Frontend)
**Duration:** 1 day

**Day 5: Build (AEM Developer + Frontend Developer)**

**AEM Tasks:**
1. Create XF structure:
   - Path: `/content/experience-fragments/setia/footer`
   - Create `master` variation
   - Add "Get in touch" heading
   - Add contact email
   - Add mascot image
   - Add copyright text

**Frontend Tasks:**
```
ui.frontend/src/main/webpack/components/footer.scss
```

**Build Steps:**
1. Style footer layout:
   - Dark blue background
   - Content centered
   - "Get in touch" section styled
   - Mascot positioned (right side)
   - Copyright text styled

2. Make responsive (stack on mobile)

**Success Criteria:**
- ✅ Footer displays on all pages
- ✅ Mascot image positioned correctly
- ✅ Responsive layout works

---

#### Priority 2C: XF Reference Components (AEM Developer)
**Duration:** 1 day

**Day 5: Build Both References**

```
1. xf-header-reference
   Location: /apps/setia/components/xf-header-reference/
   Files:
   - .content.xml (super type: experiencefragment)
   - _cq_dialog/.content.xml (fragment path field)

2. xf-footer-reference
   (same structure)
```

**Build Steps:**
1. Create component folders
2. Set super type to core XF component
3. Create dialog with fragment path field
4. Set default fragment paths
5. Test in page component

**Success Criteria:**
- ✅ References load XF content
- ✅ Changes to XF appear on pages
- ✅ Can be locked in template structure

---

### 🔹 Day 6-8: Simple Components

**Strategy:** Build 4 simple components in parallel (2 per developer)

#### Developer A: page-banner + rich-text-section

**Day 6: page-banner**
```
Location: /apps/setia/components/page-banner/
Files:
- .content.xml
- _cq_dialog/.content.xml (title, subtitle, backgroundImage)
- page-banner.html
```

**Frontend:**
```
ui.frontend/src/main/webpack/components/page-banner.scss
```

**Build Steps:**
1. Create component structure
2. Create dialog (3 fields)
3. Create HTL template
4. Style (dark blue, centered text, optional bg image)
5. Test

**Day 7: rich-text-section**
```
Location: /apps/setia/components/rich-text-section/
Dialog: title (optional), richtext, includeList (checkbox), listItems (multifield)
```

**Build Steps:**
1. Create component structure
2. Create dialog (RTE + list)
3. Create HTL template
4. Style rich text formatting
5. Test

---

#### Developer B: section-heading + cta-section

**Day 6: section-heading**
```
Location: /apps/setia/components/section-heading/
Dialog: title, subtitle, headingLevel (dropdown), alignment (dropdown)
```

**Build Steps:**
1. Create component structure
2. Create dialog (4 fields)
3. Create HTL with conditional heading levels
4. Style (typography, spacing, alignment)
5. Test all heading levels and alignments

**Day 7: cta-section**
```
Location: /apps/setia/components/cta-section/
Dialog: title, subtitle, buttonText, buttonLink, backgroundColor, includeMascot
```

**Build Steps:**
1. Create component structure
2. Create dialog (6 fields)
3. Create HTL template
4. Style (full-width section, large button, mascot positioning)
5. Test

---

**Checkpoint 1: Simple Components Complete**
- ✅ All 4 simple components functional
- ✅ Can be added to test page
- ✅ Dialogs save correctly
- ✅ Styles applied
- ✅ Responsive behavior works

---

### 🔹 Day 9-12: Content Components

#### Developer A: card-grid (Day 9-10)

**Day 9: Component + Dialog**
```
Location: /apps/setia/components/card-grid/
Dialog: columns (dropdown), gapSize (dropdown), cards (multifield)
  Card fields: icon, title, description, linkUrl, linkText, backgroundColor
```

**Build Steps:**
1. Create component structure
2. Create complex dialog with multifield
3. Test dialog saves multifield items

**Day 10: Sling Model + Frontend**
```
Java: core/src/main/java/br/com/setia/core/models/CardGridModel.java
SCSS: ui.frontend/src/main/webpack/components/card-grid.scss
```

**Build Steps:**
1. Create CardGridModel:
   - @ChildResource for cards list
   - Card inner class with properties
   - getGridClass() method

2. Create HTL using model

3. Style cards:
   - CSS Grid layout
   - Green background (#7ED321)
   - White text
   - Hover effects (lift + shadow)
   - Responsive grid

4. Test with multiple column configurations

**Success Criteria:**
- ✅ Cards display in grid
- ✅ Different column options work
- ✅ Hover effects smooth
- ✅ Responsive at all breakpoints

---

#### Developer A: download-list (Day 11)

```
Location: /apps/setia/components/download-list/
Dialog: title (optional), downloadItems (multifield)
  Item fields: fileTitle, fileDescription, fileAsset (pathfield), fileType, fileSize
```

**Build Steps:**
1. Create component structure
2. Create dialog
3. Create HTL template
4. Style download items:
   - File icon based on type
   - File metadata display
   - Download button
   - Hover effects

5. Test with PDF file from DAM

---

#### Developer B: image-section (Day 9)

```
Location: /apps/setia/components/image-section/
Dialog: title, subtitle, description (RTE), image (DAM picker), imageAlt, imagePosition, imageWidth
```

**Build Steps:**
1. Create component structure
2. Create dialog (7 fields)
3. Create HTL with left/right variants
4. Style two-column layout
5. Make responsive (stack on mobile)
6. Test with placeholder image

---

#### Developer B: logo-gallery (Day 10)

```
Location: /apps/setia/components/logo-gallery/
Dialog: title (optional), logos (multifield)
  Logo fields: logoImage, altText, linkUrl, logoWidth
```

**Build Steps:**
1. Create component structure
2. Create dialog with multifield
3. Create HTL template
4. Style:
   - Grid layout
   - Grayscale filter (default)
   - Color on hover (filter: none)
   - Centered logos

5. Test with partner logos

---

**Checkpoint 2: Content Components Complete**
- ✅ card-grid fully functional
- ✅ image-section works with left/right positioning
- ✅ download-list works with DAM assets
- ✅ logo-gallery displays logos in grid

---

### 🔹 Day 13-16: Complex Components

#### Developer A: home-hero (Day 13-14)

**CRITICAL COMPONENT - Take time to get it right**

**Day 13: Component + Dialog**
```
Location: /apps/setia/components/home-hero/
Dialog Tabs:
  Tab 1 - Content: mainTitle, subtitle, backgroundImage
  Tab 2 - Service Cards: multifield (icon, title, description, linkUrl, linkText) × 3
  Tab 3 - Styling: backgroundColor, textColor (optional)
```

**Build Steps:**
1. Create component structure
2. Create complex dialog with tabs
3. Configure multifield for 3 service cards
4. Test dialog saves all properties

**Day 14: Sling Model + Frontend**
```
Java: core/src/main/java/br/com/setia/core/models/HomeHeroModel.java
SCSS: ui.frontend/src/main/webpack/components/home-hero.scss
```

**Build Steps:**
1. Create HomeHeroModel:
   - ServiceCard inner class
   - @ChildResource List<ServiceCard>
   - Getters for all properties

2. Create HTL using model

3. Style hero:
   - Full-width dark blue background
   - Tech pattern overlay (optional SVG pattern)
   - Large centered text (48px title)
   - Service cards positioned (overlapping bottom edge)
   - Card layout (3 columns)
   - Responsive (stack cards on mobile)
   - Min height: 500px

4. Add fade-in animation (optional)

5. Test with all 3 service cards

**Success Criteria:**
- ✅ Hero displays with background
- ✅ 3 service cards overlap bottom
- ✅ Fully responsive
- ✅ Matches design screenshot

---

#### Developer B: accordion-section (Day 13-14)

**Day 13: Component + Dialog**
```
Location: /apps/setia/components/accordion-section/
Dialog: title (optional), accordionItems (multifield)
  Item fields: itemTitle, itemContent (RTE), expandedByDefault (checkbox)
```

**Build Steps:**
1. Create component structure
2. Create dialog with multifield
3. Test dialog functionality

**Day 14: Frontend (SCSS + JS)**
```
SCSS: ui.frontend/src/main/webpack/components/accordion-section.scss
JS: ui.frontend/src/main/webpack/components/accordion-section.js
```

**Build Steps:**
1. Create HTL template with accordion structure

2. Style accordion:
   - Header bar with title
   - Expand/collapse icon
   - Content panel (hidden by default)
   - Transition for smooth expand/collapse

3. Create accordion.js:
   ```javascript
   class Accordion {
     constructor(element) {
       this.accordion = element;
       this.headers = element.querySelectorAll('.accordion-header');
       this.init();
     }
     
     init() {
       this.headers.forEach(header => {
         header.addEventListener('click', (e) => this.toggle(e));
       });
     }
     
     toggle(event) {
       const header = event.currentTarget;
       const item = header.closest('.accordion-item');
       const isExpanded = item.classList.contains('expanded');
       
       // Close all items
       this.closeAll();
       
       // Open clicked item if it was closed
       if (!isExpanded) {
         item.classList.add('expanded');
         header.setAttribute('aria-expanded', 'true');
       }
     }
     
     closeAll() {
       const items = this.accordion.querySelectorAll('.accordion-item');
       items.forEach(item => {
         item.classList.remove('expanded');
         const header = item.querySelector('.accordion-header');
         header.setAttribute('aria-expanded', 'false');
       });
     }
   }
   
   // Initialize all accordions
   document.addEventListener('DOMContentLoaded', () => {
     const accordions = document.querySelectorAll('.accordion-section');
     accordions.forEach(accordion => new Accordion(accordion));
   });
   ```

4. Test expand/collapse functionality

5. Test keyboard navigation (Space/Enter to toggle)

6. Verify ARIA attributes

**Success Criteria:**
- ✅ Accordion items expand on click
- ✅ Only one item open at a time
- ✅ Smooth transitions
- ✅ Keyboard accessible
- ✅ ARIA attributes correct

---

**Checkpoint 3: All Components Complete**
- ✅ All 13 components built and functional
- ✅ All dialogs save properties correctly
- ✅ All styles applied and responsive
- ✅ JavaScript interactions work (accordion, mobile menu)
- ✅ Sling Models (where present) work correctly

---

### 🔹 Day 17-20: Templates

**CRITICAL: Templates cannot be built until all required components exist**

#### Developer A: Landing Page Template (Day 17-18)

**Day 17: Create Template in AEM UI**

**Location:** Tools → Templates → Setia folder → Create

**Build Steps:**
1. Create template: "Setia Landing Page Template"

2. Edit Structure:
   - Add root responsive grid (locked)
   - Add xf-header-reference (locked, configure default path)
   - Add responsive grid for content (editable)
   - Add xf-footer-reference (locked, configure default path)

3. Edit Initial Content (optional):
   - Add home-hero as first component
   - Add section-heading as placeholder

4. Configure Policies:
   - Root container policy:
     - Allowed components: home-hero, section-heading, rich-text-section, card-grid, cta-section
     - Layout mode: responsive grid
   - Component-specific policies:
     - home-hero: default properties (if any)
     - card-grid: default column count

5. Enable template

**Day 18: Export and Test**

**Export Steps:**
1. Use Package Manager or vlt
2. Create package including `/conf/setia/settings/wcm/templates/landing-page`
3. Download and extract
4. Copy to `ui.content/src/main/content/jcr_root/conf/setia/...`
5. Update `ui.content/src/main/content/META-INF/vault/filter.xml`
6. Add filter entry for template path (and the matching `/conf/setia/settings/wcm/policies/...`)
7. Deploy: `mvn clean install -PautoInstallSinglePackage,aem-remote`
8. Verify template still works after deployment

**Success Criteria:**
- ✅ Template appears in page creation dialog
- ✅ Can create page from template
- ✅ Header/footer locked in place
- ✅ Can add allowed components
- ✅ Cannot add disallowed components

---

#### Developer B: Content Page Template (Day 17-18)

**Parallel to Landing Page Template**

**Build Steps:**
1. Create template: "Setia Content Page Template"

2. Edit Structure (same container structure)

3. Edit Initial Content:
   - Add page-banner as first component (optional)

4. Configure Policies:
   - Allowed components: page-banner, section-heading, rich-text-section, card-grid, image-section, accordion-section, download-list, logo-gallery, cta-section

5. Enable template

6. Export to ui.content

7. Deploy and test

**Success Criteria:**
- ✅ Template available for page creation
- ✅ All content components allowed
- ✅ Can create inner pages from template

---

**Checkpoint 4: Templates Complete**
- ✅ Both templates created and enabled
- ✅ Templates exported to ui.content
- ✅ Can create pages from both templates
- ✅ Component policies configured correctly
- ✅ Header/footer XF references locked

---

### 🔹 Day 21-28: Page Creation

**Strategy:** Create pages sequentially to catch issues early, then parallelize content population

#### Day 21-22: Home Page

**AEM Developer + Content Author**

**Build Steps:**
1. Navigate to Sites console: `/content/setia/us/en`
2. Create page:
   - Template: Setia Landing Page Template
   - Title: "Home"
   - Name: "home"

3. Open in page editor

4. Add components in order:
   - home-hero (configure with 3 service cards)
   - section-heading ("Delivering innovative and Quality Solutions")
   - section-heading ("What we do")
   - card-grid (configure 6 service cards, 3 columns)
   - rich-text-section ("Engagement. Gamification as a key element")
   - section-heading ("Ready to tackle your next challenge?")
   - cta-section (configure with contact link)

5. Configure each component:
   - Enter titles, descriptions
   - Upload/select images from DAM
   - Set links
   - Adjust styling options

6. Preview in different viewports

7. Test in preview mode

8. Publish page (when ready)

**Success Criteria:**
- ✅ Page matches design screenshot
- ✅ All components configured with content
- ✅ Navigation works
- ✅ Responsive on all devices
- ✅ No console errors

---

#### Day 23-24: Company Page

**Build Steps:**
1. Create page:
   - Template: Setia Content Page Template
   - Title: "Company"
   - Name: "company"

2. Add components:
   - page-banner ("We are Setia")
   - rich-text-section ("Our Story")
   - rich-text-section ("What makes Setia different?")
   - download-list (company documents)
   - card-grid (6 service cards)
   - logo-gallery (partner logos: AWS, Neo4j, IBM, ITS — files `parceria_aws.png`, `parceria_neo.png`, `parceria_ibm.png`, `parceria_its.png`)
   - cta-section

3. Upload assets to DAM:
   - Company presentation PDF
   - Technical presentation PDF
   - Partner logos (PNG/SVG)

4. Configure components with content

5. Test downloads work

6. Test responsive

7. Publish

**Success Criteria:**
- ✅ Page matches design
- ✅ Downloads functional
- ✅ Partner logos display
- ✅ Responsive layout works

---

#### Day 25-26: Services Page

**Build Steps:**
1. Create page:
   - Template: Setia Content Page Template
   - Title: "Services"
   - Name: "services"

2. Add components:
   - page-banner ("We are a world-class Software Developer company")
   - rich-text-section ("Turn Opportunities into Results...")
   - card-grid (6 service cards)
   - accordion-section ("and how..." with 3 items)
   - image-section (Strategy diagram)
   - logo-gallery (partners)
   - cta-section

3. Upload strategy diagram to DAM

4. Configure accordion items:
   - "Business and technical alignment"
   - "Product vision and roadmap"
   - "Sprint Planning and Execution"

5. Test accordion interactions

6. Test responsive

7. Publish

**Success Criteria:**
- ✅ Page matches design
- ✅ Accordion works correctly
- ✅ Strategy diagram displays
- ✅ All interactions functional

---

#### Day 27-28: Solutions Page

**Build Steps:**
1. Create page:
   - Template: Setia Content Page Template
   - Title: "Solutions"
   - Name: "solutions"

2. Add components:
   - page-banner ("Blockchain")
   - rich-text-section ("We are on top of today's leading technologies")
   - image-section (Platform diagram)
   - card-grid (6 feature cards)
   - cta-section

3. Upload platform architecture diagram

4. Configure feature cards:
   - Built-in Wallet
   - Encrypted Assets
   - Marketplace and Digital Collections
   - Smart Factory
   - Transparency Management
   - Item Tracelaw Tokens

5. Test responsive

6. Publish

**Success Criteria:**
- ✅ Page matches design
- ✅ Platform diagram displays correctly
- ✅ Feature cards styled properly
- ✅ Responsive layout works

---

**Checkpoint 5: All Pages Complete**
- ✅ All 4 pages created and published
- ✅ Navigation works between pages
- ✅ All components populated with content
- ✅ All assets uploaded to DAM
- ✅ Responsive on all devices
- ✅ No broken links

---

### 🔹 Day 29-35: Testing and Refinement

See [Implementation Plan](./01-implementation-plan.md) Phase 8 for detailed testing tasks.

**Focus Areas:**
- Functional testing (all components work)
- Responsive testing (all breakpoints)
- Accessibility testing (WCAG AA compliance)
- Performance testing (Lighthouse > 90)
- Cross-browser testing
- Bug fixing

---

### 🔹 Day 36-40: Launch

See [Implementation Plan](./01-implementation-plan.md) Phase 10 for launch tasks.

**Key Activities:**
- Final code review
- Production deployment
- Post-launch monitoring
- Quick fixes
- Retrospective

---

## Parallel Development Schedule

### Week 1: Foundation
```
AEM Dev:        Frontend Dev:
Day 1-2:        Day 1-2:
page (Setia)    SCSS variables/mixins/reset
ClientLibs      Typography, layout
```

### Week 2: XF + Simple Components
```
AEM Dev:        Frontend Dev:
Day 3-4:        Day 3-4:
Header XF       Header styles + JS
xf-header-ref   

Day 5:          Day 5:
Footer XF       Footer styles
xf-footer-ref   

Day 6:          Day 6:
page-banner     page-banner styles

Day 7:          Day 7:
rich-text       section-heading styles
                cta-section styles
```

### Week 3: Content Components
```
Dev A:          Dev B:
Day 9-10:       Day 9-10:
card-grid       image-section
+ Model         logo-gallery

Day 11:         Day 11:
download-list   (help Dev A if needed)
```

### Week 4: Complex Components
```
Dev A:          Dev B:
Day 13-14:      Day 13-14:
home-hero       accordion-section
+ Model         + JS
```

### Week 5: Templates + Pages
```
Dev A:          Dev B:
Day 17-18:      Day 17-18:
Landing Page    Content Page
Template        Template

Day 21-28:      Day 21-28:
Page creation   Page creation
(can alternate) (can alternate)
```

---

## Risk Mitigation in Build Order

### Early Risks Addressed
1. **Foundation First** - Ensures all components have base to build on
2. **XF Early** - Unblocks templates, allows early testing of global elements
3. **Simple Components First** - Builds confidence, establishes patterns
4. **Critical Path Prioritized** - home-hero and page-banner built before templates

### Late Risks Addressed
1. **Templates Before Pages** - Ensures page creation goes smoothly
2. **Testing Phase** - Dedicated time for fixing issues
3. **Buffer Time** - 2-3 days buffer built into 8-week schedule

---

## Alternative Build Orders

### Option B: Component-Type Grouping
Build all layout components, then all content components, then all complex components.

**Pros:** Consistent patterns within each group  
**Cons:** Delays testing of complete page flows

### Option C: Page-by-Page
Build all components for Home page, then all for Company, etc.

**Pros:** Complete pages earlier  
**Cons:** Less code reuse, harder to parallelize

### Recommended: Option A (This Document)
Build by dependency layer, maximizes parallel work, establishes patterns early.

---

## Daily Standup Checklist

### Every Morning:
- [ ] What did I complete yesterday?
- [ ] What am I building today?
- [ ] Any blockers? (dependencies, environment, unclear requirements)
- [ ] Any risks? (complexity, timeline)

### Every Afternoon:
- [ ] Did I complete today's goal?
- [ ] Did I deploy and test?
- [ ] Did I commit code?
- [ ] Any issues to raise?

---

## Related Documentation

- [Implementation Plan](./01-implementation-plan.md)
- [Component Dependency Graph](./02-component-dependency-graph.md)
- [Component Contracts](./04-component-contracts.md)
- [Technical Risks](./05-technical-risks.md)

---

**Document Version:** 1.0  
**Last Updated:** 2026-05-31
