# 06 - Frontend Tech Details

## Frontend Architecture Overview

The Setia project uses a **webpack-based frontend build system** integrated with AEM ClientLibs. Frontend assets are developed in `ui.frontend` module and compiled into ClientLibs deployed to AEM.

---

## Technology Stack

### Build Tools
- **Webpack 5** - Module bundler
- **Maven Frontend Plugin** - Integrates npm with Maven build
- **npm** - Package management

### CSS
- **SCSS/Sass** - CSS preprocessor
- **PostCSS** - CSS post-processing
- **Autoprefixer** - Vendor prefix automation

### JavaScript
- **ES6+** - Modern JavaScript
- **Babel** (optional) - Transpilation for older browsers
- **ESLint** (optional) - Code quality

### Frameworks/Libraries
<!-- TODO SECTION: Specify if jQuery, React, Vue, or vanilla JS will be used -->
- Vanilla JavaScript preferred for lightweight interactions
- Consider framework-free approach for performance
- Optional: jQuery if needed for legacy compatibility

---

## Project Structure

```
ui.frontend/
├── src/
│   └── main/
│       └── webpack/
│           ├── components/        # Component-specific styles/scripts
│           │   ├── home-hero.scss
│           │   ├── card-grid.scss
│           │   ├── accordion-section.scss
│           │   └── accordion-section.js
│           ├── site/              # Global site styles/scripts
│           │   ├── main.scss      # Main SCSS entry point
│           │   ├── main.js        # Main JS entry point
│           │   ├── _variables.scss
│           │   ├── _mixins.scss
│           │   ├── _reset.scss
│           │   ├── _typography.scss
│           │   ├── _layout.scss
│           │   └── _utilities.scss
│           ├── resources/         # Fonts, icons
│           │   ├── fonts/
│           │   └── icons/
│           └── static/            # Static assets
│               └── (images copied as-is)
├── clientlib.config.js           # ClientLib generation config
├── package.json                  # npm dependencies
├── webpack.common.js             # Shared webpack config
├── webpack.dev.js                # Development config
├── webpack.prod.js               # Production config
└── pom.xml                       # Maven build config
```

---

## Build Process

### Development Build

```bash
cd ui.frontend
npm install
npm run dev
```

**Output:** Unminified, with source maps

### Production Build

```bash
cd ui.frontend
npm run prod
```

**Output:** Minified, optimized, no source maps

### Maven Integration

```bash
# From project root
mvn clean install -PautoInstallSinglePackage
```

**What Happens:**
1. Maven executes frontend-maven-plugin
2. Runs `npm install` in ui.frontend
3. Runs `npm run prod` (production build)
4. Webpack compiles SCSS → CSS
5. Webpack bundles JS
6. `aem-clientlib-generator` creates ClientLibs
7. ClientLibs copied to `ui.apps/src/main/content/jcr_root/apps/setia/clientlibs/`
8. Entire package deployed to AEM

---

## ClientLib Architecture

### ClientLib Categories

| ClientLib | Category | Description | Loaded On |
|-----------|----------|-------------|-----------|
| `clientlib-base` | `setia.base` | CSS reset, normalize, variables | All pages |
| `clientlib-site` | `setia.site` | Main site CSS and JS | All pages |
| `clientlib-grid` | `setia.grid` | Responsive grid styles | All pages |
| `clientlib-dependencies` | `setia.dependencies` | Third-party libraries | All pages (if needed) |

### ClientLib Structure

```
ui.apps/src/main/content/jcr_root/apps/setia/clientlibs/
├── clientlib-base/
│   ├── css/
│   │   └── site.css (compiled)
│   ├── css.txt
│   ├── js/
│   │   └── site.js (compiled)
│   ├── js.txt
│   ├── resources/ (fonts, icons)
│   └── .content.xml
│
├── clientlib-site/
│   ├── css/
│   ├── js/
│   └── .content.xml
│
└── clientlib-grid/
    ├── css/
    └── .content.xml
```

### Embedding ClientLibs

**In page component HTL:**

```html
<!-- customheaderlibs.html -->
<sly data-sly-use.clientLib="/libs/granite/sightly/templates/clientlib.html">
    <sly data-sly-call="${clientLib.css @ categories='setia.base'}"/>
    <sly data-sly-call="${clientLib.css @ categories='setia.grid'}"/>
    <sly data-sly-call="${clientLib.css @ categories='setia.site'}"/>
</sly>

<!-- customfooterlibs.html -->
<sly data-sly-use.clientLib="/libs/granite/sightly/templates/clientlib.html">
    <sly data-sly-call="${clientLib.js @ categories='setia.site'}"/>
</sly>
```

---

## SCSS Architecture

### File Organization

```scss
// site/main.scss - Main entry point

// 1. Configuration
@import 'variables';
@import 'mixins';

// 2. Base
@import 'reset';
@import 'typography';
@import 'layout';

// 3. Components
@import '../components/home-hero';
@import '../components/page-banner';
@import '../components/section-heading';
@import '../components/card-grid';
@import '../components/accordion-section';
// ... other components

// 4. Utilities
@import 'utilities';
```

### Variables

**File:** `site/_variables.scss`

> Variable names below are the canonical SCSS tokens. They mirror the design system in [07 → Brand Colors](./07-design-system.md#brand-colors). The `$dt-*` aliases in doc 07 are a separate design-tokens export layer for non-SCSS consumers — **do not use `$dt-*` in component styles**; use the names below.

```scss
// Colors — see 07-design-system.md for usage rules and contrast notes
$color-primary-dark-blue: #10245a;   // Headers, footers, hero/banner backgrounds
$color-primary-green: #7ED321;       // Cards, CTAs, accents
$color-primary-light-blue: #4A90E2;  // Links, secondary accents
$color-text: #333333;                // Body copy
$color-text-secondary: #666666;      // Captions, muted text
$color-background: #FFFFFF;          // Page background
$color-background-alt: #F5F5F5;      // Alternate sections
$color-border: #E0E0E0;              // Borders, dividers

// Typography
$font-family-base: 'Helvetica Neue', Arial, sans-serif;
$font-family-heading: 'Helvetica Neue', Arial, sans-serif;
$font-size-base: 16px;
$line-height-base: 1.6;

// Font Sizes
$font-size-h1: 48px;
$font-size-h2: 36px;
$font-size-h3: 28px;
$font-size-h4: 22px;
$font-size-h5: 18px;
$font-size-h6: 16px;

// Spacing
$spacing-unit: 8px;
$spacing-xs: $spacing-unit * 1;   // 8px
$spacing-sm: $spacing-unit * 2;   // 16px
$spacing-md: $spacing-unit * 3;   // 24px
$spacing-lg: $spacing-unit * 4;   // 32px
$spacing-xl: $spacing-unit * 6;   // 48px
$spacing-xxl: $spacing-unit * 8;  // 64px

// Breakpoints
$breakpoint-mobile: 576px;
$breakpoint-tablet: 768px;
$breakpoint-desktop: 1024px;
$breakpoint-wide: 1440px;

// Layout
$container-max-width: 1200px;
$grid-gutter: 24px;

// Z-index
$z-index-header: 1000;
$z-index-modal: 2000;
$z-index-dropdown: 1500;

// Transitions — see 07-design-system.md for usage guidance
$transition-fast: 0.15s;
$transition-normal: 0.3s;
$transition-slow: 0.5s;
$transition-easing: ease-in-out;
```

### Mixins

**File:** `site/_mixins.scss`

```scss
// Responsive breakpoint mixin
@mixin respond-to($breakpoint) {
  @if $breakpoint == mobile {
    @media (max-width: #{$breakpoint-mobile - 1}) { @content; }
  }
  @else if $breakpoint == tablet {
    @media (min-width: #{$breakpoint-tablet}) { @content; }
  }
  @else if $breakpoint == desktop {
    @media (min-width: #{$breakpoint-desktop}) { @content; }
  }
  @else if $breakpoint == wide {
    @media (min-width: #{$breakpoint-wide}) { @content; }
  }
}

// Flexbox centering
@mixin flex-center {
  display: flex;
  align-items: center;
  justify-content: center;
}

// Container
@mixin container {
  max-width: $container-max-width;
  margin-left: auto;
  margin-right: auto;
  padding-left: $spacing-md;
  padding-right: $spacing-md;
}

// Button reset
@mixin button-reset {
  background: none;
  border: none;
  padding: 0;
  margin: 0;
  font: inherit;
  cursor: pointer;
  outline: inherit;
}

// Transition — defaults to $transition-normal; pass a different speed for fast/slow variants
@mixin transition($properties...) {
  transition: $properties $transition-normal $transition-easing;
}

// Visually hidden (accessible but not visible)
@mixin visually-hidden {
  position: absolute;
  width: 1px;
  height: 1px;
  margin: -1px;
  padding: 0;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border: 0;
}
```

---

## Component Styling Pattern

### Example: Card Grid Component

**File:** `components/card-grid.scss`

```scss
@import '../site/variables';
@import '../site/mixins';

.card-grid {
  @include container;
  padding-top: $spacing-xl;
  padding-bottom: $spacing-xl;

  // Grid layout
  &__container {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
    gap: $spacing-md;

    @include respond-to(desktop) {
      gap: $spacing-lg;
    }
  }

  // Specific column overrides
  &--2-col &__container {
    grid-template-columns: repeat(2, 1fr);
  }

  &--3-col &__container {
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  }

  &--4-col &__container {
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  }
}

.card {
  background-color: $color-primary-green;
  border-radius: 8px;
  padding: $spacing-lg;
  color: #ffffff;
  text-align: center;
  @include transition(transform, box-shadow);

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
  }

  &__icon {
    width: 64px;
    height: 64px;
    margin: 0 auto $spacing-md;
    
    img {
      width: 100%;
      height: 100%;
      object-fit: contain;
    }
  }

  &__title {
    font-size: $font-size-h4;
    font-weight: 600;
    margin-bottom: $spacing-sm;
  }

  &__description {
    font-size: $font-size-base;
    line-height: $line-height-base;
    margin-bottom: $spacing-md;
  }

  &__link {
    display: inline-block;
    color: #ffffff;
    text-decoration: underline;
    font-weight: 500;
    @include transition(opacity);

    &:hover {
      opacity: 0.8;
    }
  }
}
```

---

## JavaScript Architecture

### File Organization

```javascript
// site/main.js - Main entry point

// Import utilities
import { ready } from './utils/dom-ready';
import { debounce } from './utils/debounce';

// Import component scripts
import './header';
import './accordion';
import './smooth-scroll';

// Initialize on DOM ready
ready(() => {
  console.log('Setia site initialized');
});
```

### Component JavaScript Pattern

**File:** `components/accordion-section.js`

```javascript
/**
 * Accordion Component
 * Handles expand/collapse interactions
 */

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

// Initialize all accordions on page
document.addEventListener('DOMContentLoaded', () => {
  const accordions = document.querySelectorAll('.accordion-section');
  accordions.forEach(accordion => new Accordion(accordion));
});

export default Accordion;
```

### Utility Functions

**File:** `site/utils/dom-ready.js`

```javascript
/**
 * DOM Ready utility
 * @param {Function} fn - Callback to execute when DOM is ready
 */
export function ready(fn) {
  if (document.readyState !== 'loading') {
    fn();
  } else {
    document.addEventListener('DOMContentLoaded', fn);
  }
}
```

**File:** `site/utils/debounce.js`

```javascript
/**
 * Debounce utility
 * @param {Function} func - Function to debounce
 * @param {number} wait - Wait time in ms
 * @returns {Function}
 */
export function debounce(func, wait = 250) {
  let timeout;
  return function executedFunction(...args) {
    const later = () => {
      clearTimeout(timeout);
      func(...args);
    };
    clearTimeout(timeout);
    timeout = setTimeout(later, wait);
  };
}
```

---

## Responsive Design Strategy

### Breakpoint Approach

**Mobile-First:** Start with mobile layout, enhance for larger screens

```scss
// Base styles (mobile)
.component {
  padding: $spacing-md;
  font-size: $font-size-base;
}

// Tablet and up
@include respond-to(tablet) {
  .component {
    padding: $spacing-lg;
    font-size: $font-size-h5;
  }
}

// Desktop and up
@include respond-to(desktop) {
  .component {
    padding: $spacing-xl;
    font-size: $font-size-h4;
  }
}
```

### Responsive Grid

Use CSS Grid with `auto-fit` for flexible columns:

```scss
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: $spacing-md;
}
```

### Responsive Images

**In HTL:**
```html
<img src="${properties.image}" 
     srcset="${properties.image}?width=600 600w,
             ${properties.image}?width=1200 1200w,
             ${properties.image}?width=1800 1800w"
     sizes="(max-width: 768px) 100vw, 
            (max-width: 1200px) 50vw,
            33vw"
     alt="${properties.alt}"/>
```

---

## Performance Optimization

### CSS Optimization
- ✅ Use CSS Grid and Flexbox (avoid floats)
- ✅ Minimize nesting (max 3 levels)
- ✅ Use CSS custom properties for dynamic values
- ✅ Avoid `@import` in CSS (use SCSS `@import` instead)
- ✅ Minify and compress in production

### JavaScript Optimization
- ✅ Lazy load non-critical JavaScript
- ✅ Use event delegation for dynamic elements
- ✅ Debounce scroll and resize handlers
- ✅ Minimize DOM queries (cache selectors)
- ✅ Use `requestAnimationFrame` for animations

### Image Optimization
- ✅ Use appropriate formats (WebP with JPEG fallback)
- ✅ Compress images before upload to DAM
- ✅ Use responsive images with `srcset`
- ✅ Lazy load below-the-fold images
- ✅ SVG for icons and logos

### AEM-Specific Optimization
- ✅ Combine ClientLibs (reduce HTTP requests)
- ✅ Enable ClientLib minification in production
- ✅ Use AEM image servlets for dynamic sizing
- ✅ Enable Dispatcher caching for static assets
- ✅ Use CDN for asset delivery

---

## Browser Support

<!-- TODO SECTION: Define specific browser support matrix -->

### Target Browsers (Recommended)
- Chrome (last 2 versions)
- Firefox (last 2 versions)
- Safari (last 2 versions)
- Edge (last 2 versions)
- Mobile Safari (iOS 12+)
- Chrome Mobile (Android 8+)

### Polyfills (if needed)
<!-- TODO SECTION: Identify required polyfills based on feature usage -->
- CSS Grid (IE 11 if required)
- Fetch API
- Promises
- IntersectionObserver (for lazy loading)

---

## Icons and Fonts

### Icon Strategy

<!-- TODO SECTION: Define icon library (Font Awesome, custom SVG sprite, etc.) -->

**Recommended:** SVG icon sprite

**Implementation:**
```html
<!-- Symbol definitions in hidden SVG -->
<svg style="display: none;">
  <symbol id="icon-arrow" viewBox="0 0 24 24">
    <path d="M12 4l-1.41 1.41L16.17 11H4v2h12.17l-5.58 5.59L12 20l8-8z"/>
  </symbol>
</svg>

<!-- Usage in components -->
<svg class="icon" aria-hidden="true">
  <use href="#icon-arrow"></use>
</svg>
```

### Web Fonts

<!-- TODO SECTION: Define web font strategy and font files -->

**File:** `resources/fonts/`

**Loading Strategy:**
```scss
// Preload in HTML head
<link rel="preload" href="/apps/setia/clientlibs/clientlib-base/resources/fonts/font.woff2" as="font" type="font/woff2" crossorigin>

// Font-face declaration
@font-face {
  font-family: 'BrandFont';
  src: url('resources/fonts/font.woff2') format('woff2'),
       url('resources/fonts/font.woff') format('woff');
  font-weight: 400;
  font-style: normal;
  font-display: swap;
}
```

---

## Accessibility

### Focus Management

```scss
// Visible focus indicator
:focus {
  outline: 2px solid $color-primary;
  outline-offset: 2px;
}

// Skip to content link
.skip-to-content {
  @include visually-hidden;

  &:focus {
    position: static;
    width: auto;
    height: auto;
    clip: auto;
  }
}
```

### ARIA Attributes

```javascript
// Dynamic ARIA updates
button.setAttribute('aria-expanded', isExpanded);
button.setAttribute('aria-pressed', isPressed);
```

### Color Contrast

<!-- TODO SECTION: Verify all color combinations meet WCAG AA standards -->

Ensure contrast ratios meet WCAG AA:
- Normal text: 4.5:1 minimum
- Large text: 3:1 minimum
- Interactive elements: 3:1 minimum

---

## Development Workflow

### Local Development

```bash
# Terminal 1: Watch frontend changes
cd ui.frontend
npm run watch

# Terminal 2: Deploy to AEM when ready
cd ..
mvn clean install -PautoInstallSinglePackage
```

### Hot Reload (Optional)

<!-- TODO SECTION: Set up webpack-dev-server proxy to AEM if desired -->

Configure webpack-dev-server to proxy AEM:
- Faster iteration on CSS/JS changes
- No need to rebuild entire package
- Requires CORS configuration in AEM

---

## Testing Frontend

### Manual Testing
- ✅ Test in all target browsers
- ✅ Test at multiple viewport sizes
- ✅ Test with JavaScript disabled
- ✅ Test keyboard navigation
- ✅ Test with screen reader

### Automated Testing (Future)
<!-- TODO SECTION: Set up frontend testing framework -->
- Unit tests (Jest)
- Integration tests (Cypress)
- Visual regression tests (Percy, BackstopJS)
- Performance tests (Lighthouse CI)

---

## Common Issues and Solutions

### Issue: CSS Changes Not Appearing
**Solution:**
1. Clear browser cache (hard refresh)
2. Rebuild ui.frontend: `npm run prod`
3. Redeploy to AEM: `mvn clean install -PautoInstallSinglePackage`
4. Clear AEM ClientLib cache: `/libs/granite/ui/content/dumplibs.rebuild.html`
5. Clear Dispatcher cache

### Issue: JavaScript Not Loading
**Solution:**
1. Check browser console for errors
2. Verify ClientLib category is embedded in page
3. Check js.txt includes your file
4. Verify file is in ClientLib js/ folder
5. Check for syntax errors in JS

### Issue: Fonts Not Loading
**Solution:**
1. Check CORS headers (fonts must be served from same origin)
2. Verify font file paths in @font-face
3. Check font MIME types in AEM
4. Preload fonts in HTML head

### Issue: Images Not Responsive
**Solution:**
1. Use AEM image servlet for dynamic sizing
2. Implement srcset and sizes attributes
3. Use CSS object-fit for aspect ratio control
4. Test at various viewport sizes

---

## Related Documentation

- [03 - AEM Implementation Strategy](./03-aem-implementation-strategy.md)
- [04 - Component Specification](./04-component-specification.md)
- [07 - Design System](./07-design-system.md)
