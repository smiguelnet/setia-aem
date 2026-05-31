# 04 - Component Specification

## Component Overview

This document provides detailed specifications for all AEM components used in the Setia website.

**Total Components:** 13  
**Component Group:** "Setia"  
**Base Path:** `/apps/setia/components/`

---

## Component Inventory Matrix

| Component | Type | Complexity | Pages Used | Sling Model | Author Dialog | Design Dialog | EditConfig | Container |
|-----------|------|------------|------------|-------------|---------------|---------------|-----------|-----------|
| `page` (Setia Page) | Base | Low | All | No (CC) | Page properties | — | inherited | — |
| `xf-header-reference` | Reference | Low | All | No | Fragment path | — | — | — |
| `xf-footer-reference` | Reference | Low | All | No | Fragment path | — | — | — |
| `home-hero` | Hero | High | Home | Optional | 8+ fields | Optional | **Yes** (image drop) | No |
| `page-banner` | Banner | Low | Company, Services, Solutions | Optional | 3 fields | Optional (theme) | **Yes** (image drop) | No |
| `section-heading` | Layout | Low | All | No | 2 fields | — | Optional | No |
| `rich-text-section` | Content | Low | All | No | 2-3 fields | — | Optional (inline) | No |
| `card-grid` | Container | Medium | All | Optional | Multifield | **Yes** (cols/theme) | **Yes** (refresh) | **Yes** |
| `image-section` | Content | Low | Services, Solutions | Optional | 4 fields | — | **Yes** (image drop) | No |
| `accordion-section` | Interactive | Medium | Services | Optional | Multifield | Optional | **Yes** (refresh) | **Yes** |
| `download-list` | Content | Low | Company, Services | Optional | Multifield | — | **Yes** (asset drop) | No |
| `logo-gallery` | Content | Low | Company, Services | No | Multifield | — | **Yes** (image drop) | No |
| `cta-section` | CTA | Low | All | No | 3 fields | **Yes** (theme) | Optional | No |

> **EditConfig, Design Dialog, and Container patterns:** see [03-aem-implementation-strategy.md → Template & Policy Configuration](./03-aem-implementation-strategy.md#template--policy-configuration) for the canonical structure. Each component spec below references that section rather than duplicating XML.

### Style System Theme Variants

Three components ship with policy-driven Style System theme groups. Authors switch themes via the component's edit toolbar (Styles button → Theme group). Theme classes are applied to the component's root element via `${currentStyle.cssClasses}`.

| Component | Group | Variant | CSS class |
|-----------|-------|---------|-----------|
| `cta-section` | Theme | Light (default) | `cta-section--theme-light` |
| `cta-section` | Theme | Dark | `cta-section--theme-dark` |
| `cta-section` | Theme | Accent (green) | `cta-section--theme-accent` |
| `card-grid` | Theme | Green (default) | `card-grid--theme-green` |
| `card-grid` | Theme | Dark Blue | `card-grid--theme-blue` |
| `card-grid` | Theme | Neutral (white) | `card-grid--theme-neutral` |
| `page-banner` | Theme | Dark Blue (default) | `page-banner--theme-dark` |
| `page-banner` | Theme | Light | `page-banner--theme-light` |
| `page-banner` | Theme | Accent (green) | `page-banner--theme-accent` |

`card-grid` additionally exposes a design dialog with `allowedColumns` (multi-select: 2/3/4) and `defaultColumns` (single-select), surfaced via the policy editor.

---

## Component Specifications

### 1. page (Setia Page)

**Purpose:** Base page component for all pages
**Location:** `/apps/setia/components/page`
**JCR Title:** `Setia Page`
**Super Type:** `core/wcm/components/page/v3/page`
**Component Group:** `.hidden` (referenced by templates, not authored directly)

#### Files
```
page/
├── .content.xml
├── customheaderlibs.html
├── customfooterlibs.html
└── (page properties dialog inherited from super-type)
```

> Every consumer in the repo (templates under `/conf/setia/...`, content pages, `setia/components/spa` super-type, `SimpleServlet.resourceTypes`) references `setia/components/page`. The folder is `page`, not `setia-page` — earlier doc revisions used the wrong name.

#### Dialog Fields
- Page Title
- Description
- Keywords (for SEO)
- Hide in Navigation
- Vanity URL
- Open Graph properties (optional)

#### Sling Model
Not required (uses core page model)

#### HTL Template
```html
<sly data-sly-use.page="com.adobe.cq.wcm.core.components.models.Page"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <sly data-sly-include="customheaderlibs.html"/>
</head>
<body>
    <sly data-sly-resource="${'root' @ resourceType='wcm/foundation/components/responsivegrid'}"/>
    <sly data-sly-include="customfooterlibs.html"/>
</body>
</html>
```

---

### 2. xf-header-reference

**Purpose:** Reference to Header Experience Fragment  
**Location:** `/apps/setia/components/xf-header-reference`  
**Super Type:** `core/wcm/components/experiencefragment/v1/experiencefragment`

#### Files
```
xf-header-reference/
├── .content.xml
└── _cq_dialog/.content.xml
```

#### Dialog Fields
- Fragment Variation Path (pathfield)
  - Root path: `/content/experience-fragments/setia/header`
  - Default: `/content/experience-fragments/setia/header/master`

#### Implementation Notes
- Lock in template structure
- Fixed at top of all pages
- Apply `position: fixed` CSS for scroll behavior

---

### 3. xf-footer-reference

**Purpose:** Reference to Footer Experience Fragment  
**Location:** `/apps/setia/components/xf-footer-reference`  
**Super Type:** `core/wcm/components/experiencefragment/v1/experiencefragment`

#### Files
```
xf-footer-reference/
├── .content.xml
└── _cq_dialog/.content.xml
```

#### Dialog Fields
- Fragment Variation Path (pathfield)
  - Root path: `/content/experience-fragments/setia/footer`
  - Default: `/content/experience-fragments/setia/footer/master`

#### Implementation Notes
- Lock in template structure
- Fixed at bottom of all pages

---

### 4. home-hero

**Purpose:** Home page hero banner with 3 service cards  
**Location:** `/apps/setia/components/home-hero`  
**Complexity:** High

#### Files
```
home-hero/
├── .content.xml
├── _cq_dialog/.content.xml
├── home-hero.html
└── (optional) clientlibs/
```

#### Dialog Fields

**Tab 1: Content**
- Main Title (textfield, required)
- Subtitle (textfield)
- Background Image (pathfield to DAM)

**Tab 2: Service Cards (multifield)**
- Card 1:
  - Icon (pathfield or dropdown)
  - Title (textfield)
  - Description (textarea)
  - Link URL (pathfield)
  - Link Text (textfield)
- Card 2: (same fields)
- Card 3: (same fields)

**Tab 3: Styling (optional)**
- Background Color
- Text Color
- Overlay Opacity

#### Files (additions)
- `_cq_editConfig.xml` — drop target for background image (see doc 03 §"Component Edit Configuration")
- `_cq_design_dialog/.content.xml` — optional, for template-locked styling variants

#### Sling Model (Optional)
```java
@Model(
    adaptables = Resource.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HomeHeroModel {

    @ValueMapValue
    private String mainTitle;

    @ValueMapValue
    private String subtitle;

    @ValueMapValue
    private String backgroundImage;

    @ChildResource
    private List<ServiceCard> serviceCards;

    @Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class ServiceCard {
        @ValueMapValue
        private String icon;
        
        @ValueMapValue
        private String title;
        
        @ValueMapValue
        private String description;
        
        @ValueMapValue
        private String linkUrl;
        
        @ValueMapValue
        private String linkText;
        
        // Getters...
    }
    
    // Getters...
}
```

#### HTL Template Structure
```html
<sly data-sly-use.model="br.com.setia.core.models.HomeHeroModel"/>

<section class="home-hero" style="background-image: url(${model.backgroundImage});">
    <div class="hero-content">
        <h1 class="hero-title">${model.mainTitle}</h1>
        <p class="hero-subtitle">${model.subtitle}</p>
    </div>

    <div class="service-cards">
        <sly data-sly-list.card="${model.serviceCards}">
            <div class="service-card">
                <img src="${card.icon}" alt="${card.title}"/>
                <h3>${card.title}</h3>
                <p>${card.description}</p>
                <a href="${card.linkUrl}">${card.linkText}</a>
            </div>
        </sly>
    </div>
</section>
```

> **Authoring rule:** when a Sling Model is bound, all data goes through the model (`${model.x}`). Don't mix `${properties.x}` and `${model.x}` in the same template.

#### Styling Notes
- Dark blue background (#10245a or similar)
- Tech pattern overlay
- Cards overlapping bottom of hero section
- Responsive: stack cards on mobile

---

### 5. page-banner

**Purpose:** Inner page banner with title and subtitle  
**Location:** `/apps/setia/components/page-banner`  
**Complexity:** Low

#### Files
```
page-banner/
├── .content.xml
├── _cq_dialog/.content.xml
└── page-banner.html
```

#### Dialog Fields
- Title (textfield, required)
- Subtitle (textfield)
- Background Image (pathfield, optional)

#### HTL Template
```html
<section class="page-banner" style="background-image: url(${properties.backgroundImage})">
    <div class="container">
        <h1 class="banner-title">${properties.title}</h1>
        <p class="banner-subtitle" data-sly-test="${properties.subtitle}">
            ${properties.subtitle}
        </p>
    </div>
</section>
```

#### Styling Notes
- Dark blue background
- White text
- Centered content
- Full-width section

---

### 6. section-heading

**Purpose:** Reusable title/subtitle pair for sections  
**Location:** `/apps/setia/components/section-heading`  
**Complexity:** Low

#### Files
```
section-heading/
├── .content.xml
├── _cq_dialog/.content.xml
└── section-heading.html
```

#### Dialog Fields
- Title (textfield, required)
- Subtitle (textfield)
- Heading Level (dropdown: H2, H3, H4, default: H2)
- Alignment (dropdown: left, center, right, default: center)

#### HTL Template
```html
<div class="section-heading" data-sly-test="${properties.title}">
    <h2 data-sly-test="${properties.headingLevel == 'h2'}" 
        class="section-title ${properties.alignment}">
        ${properties.title}
    </h2>
    <h3 data-sly-test="${properties.headingLevel == 'h3'}" 
        class="section-title ${properties.alignment}">
        ${properties.title}
    </h3>
    <h4 data-sly-test="${properties.headingLevel == 'h4'}" 
        class="section-title ${properties.alignment}">
        ${properties.title}
    </h4>
    <p class="section-subtitle ${properties.alignment}" 
       data-sly-test="${properties.subtitle}">
        ${properties.subtitle}
    </p>
</div>
```

---

### 7. rich-text-section

**Purpose:** Rich text content with optional title  
**Location:** `/apps/setia/components/rich-text-section`  
**Complexity:** Low

#### Files
```
rich-text-section/
├── .content.xml
├── _cq_dialog/.content.xml
└── rich-text-section.html
```

#### Dialog Fields
- Title (textfield, optional)
- Rich Text (richtext editor)
- Include List (checkbox, optional)
- List Items (multifield, if Include List = true)
  - Item Text (textfield)
  - Item Type (dropdown: bullet, checkmark, icon)

#### HTL Template
```html
<div class="rich-text-section">
    <h3 data-sly-test="${properties.title}" class="section-title">
        ${properties.title}
    </h3>
    <div class="rich-text-content">
        ${properties.text @ context='html'}
    </div>
    <ul data-sly-test="${properties.includeList}" 
        data-sly-list.item="${properties.listItems}" 
        class="content-list">
        <li>${item.text}</li>
    </ul>
</div>
```

---

### 8. card-grid

**Purpose:** Grid of cards for services, features, etc.  
**Location:** `/apps/setia/components/card-grid`  
**Complexity:** Medium

#### Files
```
card-grid/
├── .content.xml
├── _cq_dialog/.content.xml
├── card-grid.html
└── (optional) clientlibs/
```

#### Dialog Fields

**Tab 1: Layout**
- Columns (dropdown: 2, 3, 4, 6, default: 3)
- Gap Size (dropdown: small, medium, large, default: medium)

**Tab 2: Cards (multifield)**
- Card Item:
  - Icon/Image (pathfield)
  - Title (textfield, required)
  - Description (textarea)
  - Link URL (pathfield, optional)
  - Link Text (textfield, default: "Learn More")
  - Background Color (colorpicker, optional)

#### Component Definition (.content.xml)
```xml
<jcr:root xmlns:cq="http://www.day.com/jcr/cq/1.0"
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
          jcr:primaryType="cq:Component"
          jcr:title="Card Grid"
          componentGroup="Setia"
          cq:isContainer="{Boolean}true"/>
```

> `cq:isContainer="true"` is required so template policies can target nested-component allow-lists.

#### Files (additions)
- `_cq_editConfig.xml` — refresh-on-edit listeners for the multifield
- `_cq_design_dialog/.content.xml` — exposes column-count / theme options to the policy editor

#### Sling Model (Optional)
```java
@Model(
    adaptables = Resource.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CardGridModel {

    @ValueMapValue
    private String columns;

    @ChildResource
    private List<Card> cards;

    public String getGridClass() {
        return "card-grid-" + (columns != null ? columns : "3");
    }

    @Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class Card {
        @ValueMapValue
        private String icon;
        
        @ValueMapValue
        private String title;
        
        @ValueMapValue
        private String description;
        
        @ValueMapValue
        private String linkUrl;
        
        // Getters...
    }
    
    // Getters...
}
```

#### HTL Template
```html
<sly data-sly-use.model="br.com.setia.core.models.CardGridModel"/>

<div class="card-grid ${model.gridClass}">
    <sly data-sly-list.card="${model.cards}">
        <div class="card" style="background-color: ${card.backgroundColor}">
            <img src="${card.icon}" alt="${card.title}" class="card-icon"/>
            <h3 class="card-title">${card.title}</h3>
            <p class="card-description">${card.description}</p>
            <a href="${card.linkUrl}" 
               data-sly-test="${card.linkUrl}" 
               class="card-link">
                ${card.linkText || 'Learn More'}
            </a>
        </div>
    </sly>
</div>
```

#### Styling Notes
- Green background cards (#7ED321 or similar)
- White text
- Hover effects
- Responsive grid (CSS Grid or Flexbox)

---

### 9. image-section

**Purpose:** Text content alongside an image or diagram  
**Location:** `/apps/setia/components/image-section`  
**Complexity:** Low

#### Files
```
image-section/
├── .content.xml
├── _cq_dialog/.content.xml
└── image-section.html
```

#### Dialog Fields
- Title (textfield) — `./title`
- Subtitle (textfield) — `./subtitle`
- Description (richtext) — `./description`
- Image (pathfield to DAM, required) — `./imagePath` *(property is `imagePath`, not `image`, to avoid clashing with reserved/legacy names)*
- Image Alt Text (textfield) — `./imageAlt`
- Image Position (dropdown: left, right, default: right) — `./imagePosition`
- Image Width (dropdown: 40%, 50%, 60%, default: 50%) — `./imageWidth`

#### HTL Template
```html
<section class="image-section ${properties.imagePosition}">
    <div class="content-column">
        <h2 data-sly-test="${properties.title}">${properties.title}</h2>
        <p data-sly-test="${properties.subtitle}" class="subtitle">
            ${properties.subtitle}
        </p>
        <div class="description">
            ${properties.description @ context='html'}
        </div>
    </div>
    <div class="image-column" style="width: ${properties.imageWidth}">
        <img src="${properties.imagePath}" alt="${properties.imageAlt}"/>
    </div>
</section>
```

#### EditConfig
Drop target on `./imagePath` (`accept="[image/.*]"`, `groups="[media]"`), with `afteredit=REFRESH_PAGE`.

---

### 10. accordion-section

**Purpose:** Expandable/collapsible content sections  
**Location:** `/apps/setia/components/accordion-section`  
**Complexity:** Medium

#### Files
```
accordion-section/
├── .content.xml
├── _cq_dialog/.content.xml
├── accordion-section.html
├── _cq_editConfig.xml          # refresh-on-edit, container behavior
└── clientlibs/
    ├── js/accordion.js
    └── css/accordion.css
```

> Mark the component as `cq:isContainer="{Boolean}true"` in `.content.xml` so policies can constrain allowed nested components.

#### Dialog Fields
- Title (textfield, optional)
- Accordion Items (multifield):
  - Item Title (textfield, required)
  - Item Content (richtext, required)
  - Expanded by Default (checkbox)

#### HTL Template
```html
<div class="accordion-section">
    <h3 data-sly-test="${properties.title}">${properties.title}</h3>
    <div class="accordion" data-sly-list.item="${properties.accordionItems}">
        <div class="accordion-item ${item.expandedByDefault ? 'expanded' : ''}">
            <button class="accordion-header" aria-expanded="${item.expandedByDefault}">
                ${item.title}
                <span class="accordion-icon"></span>
            </button>
            <div class="accordion-content">
                ${item.content @ context='html'}
            </div>
        </div>
    </div>
</div>
```

#### JavaScript

The runtime accordion implementation lives in `ui.frontend` — see [06-frontend-tech-details.md → Accordion class](./06-frontend-tech-details.md#component-javascript-pattern). Use that class-based version (with `aria-expanded` updates); do not duplicate the IIFE here.

---

### 11. download-list

**Purpose:** List of downloadable files  
**Location:** `/apps/setia/components/download-list`  
**Complexity:** Low

#### Files
```
download-list/
├── .content.xml
├── _cq_dialog/.content.xml
└── download-list.html
```

#### Dialog Fields
- Title (textfield, optional)
- Download Items (multifield):
  - File Title (textfield, required)
  - File Description (textarea)
  - File Asset (pathfield to DAM, required)
  - File Type Icon (dropdown: PDF, DOC, XLS, PPT, ZIP, generic)
  - File Size (textfield, e.g., "2.5 MB")

#### Sling Model (Optional)
```java
@Model(
    adaptables = Resource.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DownloadListModel {

    @ChildResource
    private List<DownloadItem> items;

    @Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class DownloadItem {
        @ValueMapValue
        private String title;
        
        @ValueMapValue
        private String assetPath;
        
        @ValueMapValue
        private String fileSize;
        
        public String getFileExtension() {
            if (assetPath != null) {
                return assetPath.substring(assetPath.lastIndexOf('.') + 1).toUpperCase();
            }
            return "";
        }
        
        // Getters...
    }
    
    // Getters...
}
```

#### HTL Template
```html
<sly data-sly-use.model="br.com.setia.core.models.DownloadListModel"/>

<div class="download-list">
    <h3 data-sly-test="${properties.title}">${properties.title}</h3>
    <ul class="downloads">
        <sly data-sly-list.item="${model.items}">
            <li class="download-item">
                <span class="file-icon ${item.fileExtension}"></span>
                <div class="file-info">
                    <h4 class="file-title">${item.title}</h4>
                    <p class="file-description">${item.description}</p>
                    <span class="file-size">${item.fileSize}</span>
                </div>
                <a href="${item.assetPath}" 
                   download 
                   class="download-button">
                    Download
                </a>
            </li>
        </sly>
    </ul>
</div>
```

---

### 12. logo-gallery

**Purpose:** Grid of partner/technology logos  
**Location:** `/apps/setia/components/logo-gallery`  
**Complexity:** Low

#### Files
```
logo-gallery/
├── .content.xml
├── _cq_dialog/.content.xml
└── logo-gallery.html
```

#### Dialog Fields
- Title (textfield, optional)
- Logo Items (multifield):
  - Logo Image (pathfield to DAM, required)
  - Alt Text (textfield, required)
  - Link URL (pathfield, optional)
  - Logo Width (textfield, e.g., "120px", optional)

#### HTL Template
```html
<div class="logo-gallery">
    <h3 data-sly-test="${properties.title}">${properties.title}</h3>
    <div class="logo-grid">
        <sly data-sly-list.logo="${properties.logos}">
            <div class="logo-item">
                <a href="${logo.linkUrl}" 
                   data-sly-test="${logo.linkUrl}"
                   target="_blank" 
                   rel="noopener">
                    <img src="${logo.image}" 
                         alt="${logo.altText}" 
                         style="width: ${logo.width}"/>
                </a>
                <img data-sly-test="${!logo.linkUrl}" 
                     src="${logo.image}" 
                     alt="${logo.altText}" 
                     style="width: ${logo.width}"/>
            </div>
        </sly>
    </div>
</div>
```

#### Styling Notes
- Grayscale logos by default
- Color on hover
- Centered grid layout
- Consistent spacing

---

### 13. cta-section

**Purpose:** Call-to-action block for lead generation  
**Location:** `/apps/setia/components/cta-section`  
**Complexity:** Low

#### Files
```
cta-section/
├── .content.xml
├── _cq_dialog/.content.xml
└── cta-section.html
```

#### Dialog Fields
- Title (textfield, required)
- Subtitle (textfield)
- Button Text (textfield, default: "Get in touch")
- Button Link (pathfield)
- Background Color (colorpicker, default: blue)
- Include Mascot (checkbox, default: true)

#### HTL Template
```html
<section class="cta-section" style="background-color: ${properties.backgroundColor}">
    <div class="container">
        <div class="cta-content">
            <h2 class="cta-title">${properties.title}</h2>
            <p class="cta-subtitle" data-sly-test="${properties.subtitle}">
                ${properties.subtitle}
            </p>
        </div>
        <div class="cta-action">
            <a href="${properties.buttonLink}" class="cta-button">
                ${properties.buttonText}
            </a>
        </div>
        <div class="cta-mascot" data-sly-test="${properties.includeMascot}">
            <img src="/content/dam/setia/mascot.png" alt="Setia Mascot"/>
        </div>
    </div>
</section>
```

#### Styling Notes
- Dark blue background (#10245a)
- White text
- Large button
- Mascot character on right side

---

## Component Dialog Patterns

> AEM components ship up to **three** dialog/config files:
> - `_cq_dialog/.content.xml` — **author dialog** (per-instance content)
> - `_cq_design_dialog/.content.xml` — **design dialog** (template-locked options surfaced via policy)
> - `_cq_editConfig.xml` — **edit config** (drop targets, listeners, toolbar actions)
>
> The first two share the dialog structure shown below; the third has its own schema (see [03 → Component Edit Configuration](./03-aem-implementation-strategy.md#component-edit-configuration-_cq_editconfig)).

### Standard Dialog Structure

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jcr:root xmlns:sling="http://sling.apache.org/jcr/sling/1.0"
          xmlns:granite="http://www.granite.adobe.com/jcr/granite/1.0"
          xmlns:cq="http://www.day.com/jcr/cq/1.0"
          xmlns:jcr="http://www.jcp.org/jcr/1.0"
          xmlns:nt="http://www.jcp.org/jcr/nt/1.0"
          jcr:primaryType="nt:unstructured"
          jcr:title="Component Name"
          sling:resourceType="cq/gui/components/authoring/dialog">
    <content
        jcr:primaryType="nt:unstructured"
        sling:resourceType="granite/ui/components/coral/foundation/container">
        <items jcr:primaryType="nt:unstructured">
            <tabs
                jcr:primaryType="nt:unstructured"
                sling:resourceType="granite/ui/components/coral/foundation/tabs"
                maximized="{Boolean}true">
                <items jcr:primaryType="nt:unstructured">
                    <!-- Tab definitions here -->
                </items>
            </tabs>
        </items>
    </content>
</jcr:root>
```

### Multifield Pattern

```xml
<items jcr:primaryType="nt:unstructured">
    <multifield
        jcr:primaryType="nt:unstructured"
        sling:resourceType="granite/ui/components/coral/foundation/form/multifield"
        composite="{Boolean}true">
        <field
            jcr:primaryType="nt:unstructured"
            sling:resourceType="granite/ui/components/coral/foundation/container"
            name="./items">
            <items jcr:primaryType="nt:unstructured">
                <title
                    jcr:primaryType="nt:unstructured"
                    sling:resourceType="granite/ui/components/coral/foundation/form/textfield"
                    fieldLabel="Title"
                    name="./title"
                    required="{Boolean}true"/>
            </items>
        </field>
    </multifield>
</items>
```

---

## Component Testing Checklist

For each component, verify:

- ✅ Component renders correctly in edit mode
- ✅ Component renders correctly in preview mode
- ✅ Author dialog saves properties correctly; required fields validated
- ✅ Design dialog (if present) saves to the policy node, not the component instance
- ✅ EditConfig drop targets accept the right MIME types
- ✅ EditConfig refresh listeners (`afteredit`/`afterinsert`) update the editor view
- ✅ Sling Model (if present) loads correctly with `defaultInjectionStrategy = OPTIONAL`
- ✅ HTL template uses model bindings consistently (no mixed `properties.*`/`model.*`)
- ✅ HTL template handles missing properties gracefully
- ✅ Container components (`cq:isContainer="true"`) accept allowed nested components per policy
- ✅ ClientLibs are loaded correctly
- ✅ Component is responsive on mobile/tablet
- ✅ XSS protection is applied to user input
- ✅ Component appears in component group "Setia" and is allow-listed by the relevant template policy

---

## Related Documentation

- [03 - AEM Implementation Strategy](./03-aem-implementation-strategy.md)
- [05 - Authoring Guidelines](./05-authoring-guidelines.md)
- [06 - Frontend Tech Details](./06-frontend-tech-details.md)
