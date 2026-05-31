# 07 - Design System

## Introduction

This document defines the visual design system for the Setia corporate website. It serves as a single source of truth for colors, typography, spacing, and visual patterns used throughout the site.

---

## Brand Colors

### Primary Colors

<!-- TODO SECTION: Provide exact brand color values from official brand guidelines -->

Based on website screenshots, the following colors are identified:

```scss
// Primary Brand Colors
$color-primary-dark-blue: #10245a;   // Dark navy blue (headers, footers, backgrounds)
$color-primary-green: #7ED321;       // Bright green (cards, CTAs, accents)
$color-primary-light-blue: #4A90E2;  // Light blue (accents, links)

// Neutral Colors
$color-white: #FFFFFF;               // White (backgrounds, text on dark)
$color-light-gray: #F5F5F5;          // Light gray (alternate backgrounds)
$color-medium-gray: #E0E0E0;         // Medium gray (borders, dividers)
$color-dark-gray: #333333;           // Dark gray (body text)
$color-text-secondary: #666666;      // Secondary text color
```

### Color Usage Guidelines

| Color | Usage | Examples |
|-------|-------|----------|
| Dark Blue (#10245a) | Primary brand color | Header, footer, hero backgrounds, page banners |
| Bright Green (#7ED321) | Call-to-action, highlights | Service cards, buttons, interactive elements |
| Light Blue (#4A90E2) | Secondary accents | Links, icons, hover states |
| White (#FFFFFF) | Clean backgrounds | Page backgrounds, text on dark backgrounds |
| Light Gray (#F5F5F5) | Alternate sections | Section backgrounds for visual hierarchy |
| Dark Gray (#333333) | Primary text | Body copy, headings on light backgrounds |

### Accessibility Notes

<!-- TODO SECTION: Verify all color combinations meet WCAG AA contrast requirements -->

**Contrast Requirements (WCAG AA):**
- Normal text (< 18pt): 4.5:1 minimum
- Large text (≥ 18pt): 3:1 minimum
- Interactive elements: 3:1 minimum

**Verified Combinations:**
- Dark blue (#10245a) on white: ✓ Passes AA
- White text on dark blue: ✓ Passes AA
- White text on green (#7ED321): ⚠️ Verify (may need darker green)
- Dark gray (#333) on white: ✓ Passes AA

---

## Typography

### Font Families

<!-- TODO SECTION: Confirm exact font families from brand guidelines -->

**Primary Font Stack:**
```scss
$font-family-base: 'Helvetica Neue', 'Helvetica', 'Arial', sans-serif;
```

**Alternative (if custom font):**
```scss
$font-family-brand: 'BrandFont', 'Helvetica Neue', sans-serif;
```

**Monospace (code/technical):**
```scss
$font-family-mono: 'Courier New', 'Courier', monospace;
```

### Font Sizes

```scss
// Headings
$font-size-h1: 48px;    // 3rem    - Page titles, hero
$font-size-h2: 36px;    // 2.25rem - Section headings
$font-size-h3: 28px;    // 1.75rem - Subsection headings
$font-size-h4: 22px;    // 1.375rem - Card titles
$font-size-h5: 18px;    // 1.125rem - Small headings
$font-size-h6: 16px;    // 1rem    - Minimal headings

// Body
$font-size-base: 16px;       // 1rem    - Base body text
$font-size-large: 18px;      // 1.125rem - Lead paragraphs
$font-size-small: 14px;      // 0.875rem - Captions, labels
$font-size-xsmall: 12px;     // 0.75rem  - Fine print
```

### Responsive Typography

```scss
// Mobile (< 768px)
$font-size-h1-mobile: 32px;
$font-size-h2-mobile: 28px;
$font-size-h3-mobile: 24px;
$font-size-h4-mobile: 20px;
```

### Font Weights

```scss
$font-weight-light: 300;
$font-weight-normal: 400;
$font-weight-medium: 500;
$font-weight-semibold: 600;
$font-weight-bold: 700;
```

### Line Heights

```scss
$line-height-tight: 1.2;      // Headings
$line-height-normal: 1.5;     // Body text
$line-height-relaxed: 1.75;   // Lead paragraphs, easier reading
```

### Typography Scale Application

| Element | Font Size | Weight | Line Height | Color |
|---------|-----------|--------|-------------|-------|
| H1 (Hero) | 48px | 700 | 1.2 | White or Dark Blue |
| H2 (Section) | 36px | 600 | 1.2 | Dark Blue |
| H3 (Subsection) | 28px | 600 | 1.3 | Dark Blue |
| H4 (Card Title) | 22px | 600 | 1.4 | White or Dark Gray |
| Body | 16px | 400 | 1.6 | Dark Gray |
| Lead | 18px | 400 | 1.75 | Dark Gray |
| Caption | 14px | 400 | 1.5 | Medium Gray |

---

## Spacing System

### Base Unit

```scss
$spacing-unit: 8px;
```

All spacing should be multiples of the base unit for consistency.

### Spacing Scale

```scss
$spacing-0: 0;
$spacing-xs: 8px;     // $spacing-unit * 1
$spacing-sm: 16px;    // $spacing-unit * 2
$spacing-md: 24px;    // $spacing-unit * 3
$spacing-lg: 32px;    // $spacing-unit * 4
$spacing-xl: 48px;    // $spacing-unit * 6
$spacing-xxl: 64px;   // $spacing-unit * 8
$spacing-xxxl: 96px;  // $spacing-unit * 12
```

### Spacing Usage

| Scale | Size | Usage |
|-------|------|-------|
| xs | 8px | Tight spacing between related elements |
| sm | 16px | Standard spacing between UI elements |
| md | 24px | Comfortable spacing, paragraph margins |
| lg | 32px | Component padding |
| xl | 48px | Section padding (small) |
| xxl | 64px | Section padding (medium) |
| xxxl | 96px | Section padding (large) |

### Component Spacing Examples

```scss
// Card padding
.card {
  padding: $spacing-lg; // 32px
}

// Section spacing
.section {
  padding-top: $spacing-xxl;    // 64px
  padding-bottom: $spacing-xxl; // 64px
}

// Element margins
.section-heading {
  margin-bottom: $spacing-md; // 24px
}

.paragraph {
  margin-bottom: $spacing-sm; // 16px
}
```

---

## Layout Grid

### Container

```scss
$container-max-width: 1200px;
$container-padding: 24px;
```

**Usage:**
```scss
.container {
  max-width: $container-max-width;
  margin-left: auto;
  margin-right: auto;
  padding-left: $container-padding;
  padding-right: $container-padding;
}
```

### Grid System

**CSS Grid Approach:**
```scss
.grid {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  gap: $spacing-md;
}

.grid-item--span-4 {
  grid-column: span 4;
}

.grid-item--span-6 {
  grid-column: span 6;
}
```

### Responsive Grid

```scss
// Mobile: 1 column
@media (max-width: 767px) {
  .grid {
    grid-template-columns: 1fr;
  }
}

// Tablet: 2 columns
@media (min-width: 768px) and (max-width: 1023px) {
  .grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

// Desktop: flexible columns
@media (min-width: 1024px) {
  .grid {
    grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  }
}
```

---

## Breakpoints

```scss
$breakpoint-mobile: 576px;    // Small phones
$breakpoint-tablet: 768px;    // Tablets
$breakpoint-desktop: 1024px;  // Laptops
$breakpoint-wide: 1440px;     // Large desktops
```

### Breakpoint Usage

```scss
// Mobile-first approach
.component {
  // Base styles (mobile)
  font-size: 16px;
  padding: 16px;

  @media (min-width: $breakpoint-tablet) {
    font-size: 18px;
    padding: 24px;
  }

  @media (min-width: $breakpoint-desktop) {
    font-size: 20px;
    padding: 32px;
  }
}
```

---

## Shadows and Elevation

<!-- TODO SECTION: Define shadow system based on Material Design or custom approach -->

```scss
// Shadow levels
$shadow-sm: 0 1px 3px rgba(0, 0, 0, 0.12);
$shadow-md: 0 4px 6px rgba(0, 0, 0, 0.1);
$shadow-lg: 0 8px 16px rgba(0, 0, 0, 0.15);
$shadow-xl: 0 16px 32px rgba(0, 0, 0, 0.2);
```

### Shadow Usage

| Level | Shadow | Usage |
|-------|--------|-------|
| Small | 0 1px 3px | Subtle lift (input fields, small cards) |
| Medium | 0 4px 6px | Cards, buttons |
| Large | 0 8px 16px | Hover states, modals |
| XLarge | 0 16px 32px | Dropdowns, popovers |

---

## Border Radius

```scss
$border-radius-sm: 4px;
$border-radius-md: 8px;
$border-radius-lg: 16px;
$border-radius-round: 50%;
```

### Border Radius Usage

| Size | Value | Usage |
|------|-------|-------|
| Small | 4px | Buttons, inputs |
| Medium | 8px | Cards, panels |
| Large | 16px | Feature cards, large panels |
| Round | 50% | Avatar images, icon backgrounds |

**Example:**
```scss
.card {
  border-radius: $border-radius-md; // 8px
}

.button {
  border-radius: $border-radius-sm; // 4px
}
```

---

## Buttons

### Button Styles

**Primary Button (CTA):**
```scss
.button--primary {
  background-color: $color-primary-green;
  color: $color-white;
  padding: 12px 32px;
  font-size: 16px;
  font-weight: 600;
  border: none;
  border-radius: $border-radius-sm;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    background-color: darken($color-primary-green, 10%);
    transform: translateY(-2px);
    box-shadow: $shadow-md;
  }
}
```

**Secondary Button:**
```scss
.button--secondary {
  background-color: transparent;
  color: $color-primary-dark-blue;
  border: 2px solid $color-primary-dark-blue;
  padding: 10px 30px;
  // ... other styles
}
```

**Ghost Button (on dark background):**
```scss
.button--ghost {
  background-color: transparent;
  color: $color-white;
  border: 2px solid $color-white;
  // ... other styles
}
```

### Button Sizes

```scss
// Small button
.button--sm {
  padding: 8px 20px;
  font-size: 14px;
}

// Medium button (default)
.button--md {
  padding: 12px 32px;
  font-size: 16px;
}

// Large button
.button--lg {
  padding: 16px 48px;
  font-size: 18px;
}
```

---

## Cards

### Base Card Style

```scss
.card {
  background-color: $color-primary-green;
  color: $color-white;
  border-radius: $border-radius-md;
  padding: $spacing-lg;
  box-shadow: $shadow-sm;
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-4px);
    box-shadow: $shadow-lg;
  }
}
```

### Card Variations

**Service Card (Green):**
- Background: Bright green (#7ED321)
- Text: White
- Icon: White or outlined
- Padding: 32px
- Hover: Lift effect

**Feature Card (White):**
- Background: White
- Text: Dark gray
- Border: 1px solid light gray
- Padding: 24px
- Hover: Shadow increase

**Partner Card (Logo):**
- Background: White or transparent
- Border: Optional subtle border
- Logo: Grayscale default, color on hover
- Padding: 16px

---

## Icons

<!-- TODO SECTION: Define icon library and style guidelines -->

### Icon Sizes

```scss
$icon-size-sm: 16px;
$icon-size-md: 24px;
$icon-size-lg: 32px;
$icon-size-xl: 64px;
```

### Icon Usage

| Size | Dimension | Usage |
|------|-----------|-------|
| Small | 16px | Inline with text, small UI elements |
| Medium | 24px | Navigation, buttons |
| Large | 32px | Feature icons, section icons |
| XLarge | 64px | Hero sections, large cards |

### Icon Style

**Recommendation:** Use consistent icon style throughout
- Line icons (outlined) for UI elements
- Filled icons for feature highlights
- SVG format for scalability

---

## Forms

<!-- TODO SECTION: Design form input styles if contact forms are needed -->

### Input Fields

```scss
.input {
  width: 100%;
  padding: 12px 16px;
  font-size: 16px;
  border: 1px solid $color-medium-gray;
  border-radius: $border-radius-sm;
  transition: border-color 0.3s ease;

  &:focus {
    outline: none;
    border-color: $color-primary-dark-blue;
    box-shadow: 0 0 0 3px rgba($color-primary-dark-blue, 0.1);
  }

  &::placeholder {
    color: $color-text-secondary;
  }
}
```

### Form Labels

```scss
.label {
  display: block;
  font-size: 14px;
  font-weight: 600;
  color: $color-dark-gray;
  margin-bottom: $spacing-xs;
}
```

---

## Animations and Transitions

### Transition Timing

```scss
$transition-fast: 0.15s;
$transition-normal: 0.3s;
$transition-slow: 0.5s;

$transition-easing: ease-in-out;
```

### Common Transitions

```scss
// Hover lift effect
.card {
  transition: transform $transition-normal $transition-easing,
              box-shadow $transition-normal $transition-easing;

  &:hover {
    transform: translateY(-4px);
  }
}

// Fade in
.fade-in {
  animation: fadeIn $transition-normal $transition-easing;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

// Slide up
.slide-up {
  animation: slideUp $transition-slow $transition-easing;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
```

### Animation Best Practices

- ✅ Use `transform` and `opacity` for performance (GPU accelerated)
- ✅ Keep animations subtle and purposeful
- ✅ Respect `prefers-reduced-motion` for accessibility
- ✅ Use consistent timing functions
- ❌ Avoid animating `width`, `height`, `top`, `left` (causes reflow)

```scss
// Respect user motion preferences
@media (prefers-reduced-motion: reduce) {
  * {
    animation-duration: 0.01ms !important;
    animation-iteration-count: 1 !important;
    transition-duration: 0.01ms !important;
  }
}
```

---

## Z-Index Scale

```scss
$z-index-base: 1;
$z-index-dropdown: 100;
$z-index-sticky: 500;
$z-index-fixed: 1000;
$z-index-modal-backdrop: 1500;
$z-index-modal: 2000;
$z-index-popover: 2500;
$z-index-tooltip: 3000;
```

### Z-Index Usage

| Element | Z-Index | Usage |
|---------|---------|-------|
| Base content | 1 | Default stacking |
| Dropdown menus | 100 | Navigation dropdowns |
| Sticky header | 500 | Fixed scroll header |
| Fixed elements | 1000 | Fixed CTAs, scroll-to-top |
| Modal backdrop | 1500 | Overlay background |
| Modal content | 2000 | Modal dialogs |
| Popover | 2500 | Context menus |
| Tooltip | 3000 | Highest UI element |

---

## Component-Specific Design Patterns

### Hero Section

**Visual Characteristics:**
- Full-width dark blue background (#10245a)
- Tech pattern overlay (circuit board or dots pattern)
- Large white heading text (48px)
- Centered content
- Service cards overlapping bottom edge
- Minimum height: 500px

### Page Banner

**Visual Characteristics:**
- Dark blue background
- Centered text
- Title: 36px, white, bold
- Subtitle: 18px, white, regular weight
- Padding: 64px vertical

### Section Heading

**Visual Characteristics:**
- Centered alignment (typically)
- Title: 36px, dark blue, semibold
- Subtitle: 18px, medium gray, regular
- Spacing: 16px between title and subtitle
- Bottom margin: 48px

### Service Cards

**Visual Characteristics:**
- Bright green background (#7ED321)
- White text
- Icon: 64px, centered
- Title: 22px, white, semibold
- Description: 16px, white, regular
- Padding: 32px
- Border radius: 8px
- Hover: Lift 4px, increase shadow

### CTA Section

**Visual Characteristics:**
- Dark blue background (#10245a)
- White text
- Large heading: 36px
- Button: Bright green or white outline
- Mascot illustration: Right side
- Full-width section
- Padding: 64px vertical

---

## Patterns and Textures

<!-- TODO SECTION: Provide pattern image assets or guidelines -->

### Background Patterns

**Hero Tech Pattern:**
- Style: Subtle circuit board or dot matrix
- Color: Lighter blue on dark blue (low contrast)
- Opacity: 10-20%
- Usage: Hero sections, page banners

**Section Dividers:**
- Style: Subtle gradient or solid color
- Usage: Alternate section backgrounds
- Colors: White → Light gray alternating

---

## Imagery Guidelines

### Photography Style

<!-- TODO SECTION: Define photography style and guidelines -->

**Recommended Style:**
- Professional, corporate
- Technology-focused
- Diverse team representation
- Modern office environments
- Clean, high-contrast images

### Image Specifications

**Hero Images:**
- Dimensions: 1920x1080 minimum
- Format: JPEG (photography), WebP (with fallback)
- File size: < 500KB (compressed)

**Content Images:**
- Dimensions: 1200x800 minimum
- Format: JPEG or PNG
- File size: < 300KB

**Logos:**
- Format: SVG (preferred) or PNG
- Background: Transparent
- Size: Variable (maintain aspect ratio)

**Icons:**
- Format: SVG
- Size: 64x64, 32x32, 24x24, 16x16
- Style: Line or filled (consistent throughout)

---

## Design Tokens Export

For use in SCSS:

```scss
// _design-tokens.scss

// Colors
$dt-color-brand-primary: #10245a;
$dt-color-brand-secondary: #7ED321;
$dt-color-brand-accent: #4A90E2;
$dt-color-neutral-white: #FFFFFF;
$dt-color-neutral-black: #000000;
$dt-color-neutral-gray-100: #F5F5F5;
$dt-color-neutral-gray-200: #E0E0E0;
$dt-color-neutral-gray-600: #666666;
$dt-color-neutral-gray-900: #333333;

// Typography
$dt-font-family-base: 'Helvetica Neue', 'Arial', sans-serif;
$dt-font-size-base: 16px;
$dt-font-weight-normal: 400;
$dt-font-weight-semibold: 600;
$dt-font-weight-bold: 700;

// Spacing
$dt-spacing-unit: 8px;
$dt-spacing-xs: 8px;
$dt-spacing-sm: 16px;
$dt-spacing-md: 24px;
$dt-spacing-lg: 32px;
$dt-spacing-xl: 48px;
$dt-spacing-xxl: 64px;

// Breakpoints
$dt-breakpoint-mobile: 576px;
$dt-breakpoint-tablet: 768px;
$dt-breakpoint-desktop: 1024px;
$dt-breakpoint-wide: 1440px;

// Shadows
$dt-shadow-sm: 0 1px 3px rgba(0, 0, 0, 0.12);
$dt-shadow-md: 0 4px 6px rgba(0, 0, 0, 0.1);
$dt-shadow-lg: 0 8px 16px rgba(0, 0, 0, 0.15);

// Border Radius
$dt-radius-sm: 4px;
$dt-radius-md: 8px;
$dt-radius-lg: 16px;

// Transitions
$dt-transition-fast: 0.15s;
$dt-transition-normal: 0.3s;
$dt-transition-slow: 0.5s;
```

---

## Design Checklist

When implementing a new component, ensure:

- ✅ Colors match design system
- ✅ Typography follows scale and hierarchy
- ✅ Spacing uses 8px base unit multiples
- ✅ Responsive at all breakpoints
- ✅ Accessible (contrast, focus states, ARIA)
- ✅ Hover/active states defined
- ✅ Animations are subtle and performant
- ✅ Consistent with existing components
- ✅ Cross-browser tested
- ✅ Mobile-tested

---

## Related Documentation

- [04 - Component Specification](./04-component-specification.md)
- [05 - Authoring Guidelines](./05-authoring-guidelines.md)
- [06 - Frontend Tech Details](./06-frontend-tech-details.md)
