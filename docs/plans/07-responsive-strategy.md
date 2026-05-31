# Responsive Strategy - Setia AEM Website

## Overview

This document defines the responsive design strategy, breakpoints, and mobile-first approach for the Setia website to ensure optimal user experience across all devices.

**Goals:**
- Seamless experience on mobile, tablet, and desktop
- Mobile-first development approach
- Performance optimized for all devices
- Touch-friendly interactions
- Accessibility maintained across viewports

---

## Breakpoint Strategy

### Defined Breakpoints

```scss
// breakpoints in _variables.scss
$breakpoint-mobile:  576px;   // Small phones → Large phones
$breakpoint-tablet:  768px;   // Tablets (portrait)
$breakpoint-desktop: 1024px;  // Tablets (landscape) / Small laptops
$breakpoint-wide:    1440px;  // Desktop / Large screens
```

### Breakpoint Usage

| Device Category | Viewport Range | Design Priority |
|----------------|----------------|-----------------|
| **Small Mobile** | 320px - 575px | High (mobile-first base) |
| **Large Mobile** | 576px - 767px | High |
| **Tablet** | 768px - 1023px | Medium |
| **Desktop** | 1024px - 1439px | High |
| **Wide Desktop** | 1440px+ | Medium |

### Testing Viewports

**Required test sizes:**
- 320px (iPhone SE, small Android)
- 375px (iPhone 12/13/14)
- 768px (iPad portrait)
- 1024px (iPad landscape)
- 1440px (Standard desktop)
- 1920px (Large desktop)

---

## Mobile-First Approach

### Development Philosophy

```
1. Design for mobile FIRST (320px)
2. Enhance for larger screens
3. Never design desktop-down
```

**Why Mobile-First?**
- 🏆 Forces focus on essential content
- 🏆 Better performance (load only what's needed)
- 🏆 Progressive enhancement mindset
- 🏆 Easier to scale up than down

### CSS Pattern

```scss
// ❌ WRONG: Desktop-first
.component {
  display: grid;
  grid-template-columns: repeat(3, 1fr); // Desktop default
  
  @media (max-width: 768px) {
    grid-template-columns: 1fr; // Override for mobile
  }
}

// ✅ CORRECT: Mobile-first
.component {
  display: grid;
  grid-template-columns: 1fr; // Mobile default (single column)
  
  @media (min-width: 768px) {
    grid-template-columns: repeat(2, 1fr); // Tablet (2 columns)
  }
  
  @media (min-width: 1024px) {
    grid-template-columns: repeat(3, 1fr); // Desktop (3 columns)
  }
}
```

---

## Component Responsive Behavior

### 1. Header (Fixed Navigation)

**Mobile (< 768px):**
- Logo left (reduced size)
- Hamburger menu icon right
- Menu slides in from right on tap
- Fixed position maintained

**Tablet/Desktop (≥ 768px):**
- Logo left (full size)
- Horizontal navigation right
- No hamburger menu
- Fixed position on scroll

```scss
.header {
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 1000;
  padding: 12px 16px; // Mobile
  
  @media (min-width: $breakpoint-tablet) {
    padding: 16px 32px; // Tablet+
  }
}

.nav-menu {
  display: none; // Hidden on mobile
  
  @media (min-width: $breakpoint-tablet) {
    display: flex; // Horizontal on tablet+
    gap: 24px;
  }
}

.mobile-menu-toggle {
  display: block; // Visible on mobile
  
  @media (min-width: $breakpoint-tablet) {
    display: none; // Hidden on tablet+
  }
}
```

---

### 2. home-hero (Hero Section)

**Mobile (< 768px):**
- Single column layout
- Title: 32px (reduced)
- Service cards stack vertically
- Min height: 400px
- Background image scaled

**Tablet (768px - 1023px):**
- Title: 40px
- Service cards: 2 columns
- Min height: 500px

**Desktop (≥ 1024px):**
- Title: 48px (full size)
- Service cards: 3 columns (horizontal)
- Cards overlap hero bottom edge
- Min height: 600px

```scss
.home-hero {
  min-height: 400px; // Mobile
  padding: 60px 16px 120px; // Extra bottom for cards
  
  @media (min-width: $breakpoint-tablet) {
    min-height: 500px;
    padding: 80px 32px 120px;
  }
  
  @media (min-width: $breakpoint-desktop) {
    min-height: 600px;
    padding: 100px 48px 120px;
  }
}

.hero-title {
  font-size: 32px; // Mobile
  line-height: 1.2;
  
  @media (min-width: $breakpoint-tablet) {
    font-size: 40px; // Tablet
  }
  
  @media (min-width: $breakpoint-desktop) {
    font-size: 48px; // Desktop
  }
}

.service-cards {
  display: grid;
  grid-template-columns: 1fr; // Mobile: stack
  gap: 16px;
  
  @media (min-width: $breakpoint-tablet) {
    grid-template-columns: repeat(2, 1fr); // Tablet: 2 cols
    gap: 24px;
  }
  
  @media (min-width: $breakpoint-desktop) {
    grid-template-columns: repeat(3, 1fr); // Desktop: 3 cols
    gap: 32px;
  }
}
```

---

### 3. page-banner (Inner Page Banner)

**Mobile (< 768px):**
- Title: 28px
- Subtitle: 16px
- Padding: 40px 16px

**Tablet/Desktop (≥ 768px):**
- Title: 36px
- Subtitle: 18px
- Padding: 64px 32px

```scss
.page-banner {
  padding: 40px 16px; // Mobile
  text-align: center;
  
  @media (min-width: $breakpoint-tablet) {
    padding: 64px 32px; // Tablet+
  }
}

.banner-title {
  font-size: 28px; // Mobile
  margin-bottom: 12px;
  
  @media (min-width: $breakpoint-tablet) {
    font-size: 36px; // Tablet+
    margin-bottom: 16px;
  }
}
```

---

### 4. card-grid (Service/Feature Cards)

**Mobile (< 576px):**
- 1 column (cards stack)
- Full-width cards

**Mobile Large (576px - 767px):**
- 2 columns (if space allows)

**Tablet (768px - 1023px):**
- 2 or 3 columns (depends on configuration)

**Desktop (≥ 1024px):**
- 3, 4, or 6 columns (depends on configuration)
- Auto-fit: flexible column count

```scss
.card-grid__container {
  display: grid;
  gap: 16px; // Mobile
  
  // Mobile: always single column
  grid-template-columns: 1fr;
  
  // Mobile Large: 2 columns if enough space
  @media (min-width: $breakpoint-mobile) {
    grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
    gap: 20px;
  }
  
  // Tablet+: respect column configuration
  @media (min-width: $breakpoint-tablet) {
    gap: 24px;
  }
  
  // Desktop: full column layout
  @media (min-width: $breakpoint-desktop) {
    grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
    gap: 32px;
  }
}

// Specific column overrides
.card-grid--3-col .card-grid__container {
  @media (min-width: $breakpoint-desktop) {
    grid-template-columns: repeat(3, 1fr);
  }
}

.card-grid--4-col .card-grid__container {
  @media (min-width: $breakpoint-desktop) {
    grid-template-columns: repeat(4, 1fr);
  }
}
```

---

### 5. image-section (Text + Image)

**Mobile (< 768px):**
- Single column (stacked)
- Image full-width
- Text below image
- Image position ignored

**Tablet/Desktop (≥ 768px):**
- Two columns side-by-side
- Image position (left/right) respected
- Image width configurable (40%, 50%, 60%)

```scss
.image-section {
  display: flex;
  flex-direction: column; // Mobile: stack
  gap: 24px;
  
  @media (min-width: $breakpoint-tablet) {
    flex-direction: row; // Tablet+: side-by-side
    align-items: center;
  }
}

// Image position only applies on tablet+
.image-section--image-left {
  @media (min-width: $breakpoint-tablet) {
    flex-direction: row-reverse; // Swap order
  }
}

.content-column {
  flex: 1;
}

.image-column {
  width: 100%; // Mobile: full width
  
  @media (min-width: $breakpoint-tablet) {
    width: 50%; // Tablet+: configurable width
  }
}
```

---

### 6. accordion-section (Expandable Content)

**Mobile (< 768px):**
- Full-width accordion items
- Smaller padding
- Larger touch targets (44px min)

**Tablet/Desktop (≥ 768px):**
- Comfortable padding
- Hover states

```scss
.accordion-header {
  padding: 16px; // Mobile
  min-height: 44px; // Touch target
  font-size: 16px;
  
  @media (min-width: $breakpoint-tablet) {
    padding: 20px 24px; // Tablet+
    font-size: 18px;
    
    &:hover {
      background-color: lighten($color-primary, 5%);
    }
  }
}

.accordion-content {
  padding: 16px; // Mobile
  
  @media (min-width: $breakpoint-tablet) {
    padding: 24px; // Tablet+
  }
}
```

---

### 7. logo-gallery (Partner Logos)

**Mobile (< 576px):**
- 2 logos per row
- Smaller logos

**Tablet (576px - 1023px):**
- 3-4 logos per row

**Desktop (≥ 1024px):**
- 4-6 logos per row
- Full logo size

```scss
.logo-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr); // Mobile: 2 per row
  gap: 24px;
  
  @media (min-width: $breakpoint-mobile) {
    grid-template-columns: repeat(3, 1fr); // Mobile Large: 3
  }
  
  @media (min-width: $breakpoint-tablet) {
    grid-template-columns: repeat(4, 1fr); // Tablet: 4
  }
  
  @media (min-width: $breakpoint-desktop) {
    grid-template-columns: repeat(auto-fit, minmax(120px, 1fr)); // Desktop: flexible
  }
}

.logo-item img {
  width: 80px; // Mobile: smaller
  
  @media (min-width: $breakpoint-tablet) {
    width: 100px; // Tablet
  }
  
  @media (min-width: $breakpoint-desktop) {
    width: 120px; // Desktop: full size
  }
}
```

---

### 8. cta-section (Call-to-Action)

**Mobile (< 768px):**
- Single column (stacked)
- Mascot below button
- Button full-width

**Tablet/Desktop (≥ 768px):**
- Flexible layout
- Mascot positioned right
- Button inline

```scss
.cta-section {
  padding: 40px 16px; // Mobile
  
  @media (min-width: $breakpoint-tablet) {
    padding: 64px 32px; // Tablet+
  }
}

.cta-section .container {
  display: flex;
  flex-direction: column; // Mobile: stack
  align-items: center;
  gap: 24px;
  
  @media (min-width: $breakpoint-tablet) {
    flex-direction: row; // Tablet+: horizontal
    justify-content: space-between;
  }
}

.cta-button {
  width: 100%; // Mobile: full width
  max-width: 300px;
  
  @media (min-width: $breakpoint-tablet) {
    width: auto; // Tablet+: auto width
  }
}

.cta-mascot {
  display: none; // Hidden on small mobile
  
  @media (min-width: $breakpoint-mobile) {
    display: block; // Visible on larger screens
  }
}
```

---

## Typography Scaling

### Responsive Font Sizes

```scss
// Heading scale
h1, .hero-title {
  font-size: 32px; // Mobile
  
  @media (min-width: $breakpoint-tablet) {
    font-size: 40px; // Tablet
  }
  
  @media (min-width: $breakpoint-desktop) {
    font-size: 48px; // Desktop
  }
}

h2, .section-title {
  font-size: 28px; // Mobile
  
  @media (min-width: $breakpoint-tablet) {
    font-size: 32px; // Tablet
  }
  
  @media (min-width: $breakpoint-desktop) {
    font-size: 36px; // Desktop
  }
}

h3 {
  font-size: 22px; // Mobile
  
  @media (min-width: $breakpoint-desktop) {
    font-size: 28px; // Desktop
  }
}

// Body text
body, p {
  font-size: 16px; // All devices (maintain readability)
  line-height: 1.6;
}

.lead {
  font-size: 16px; // Mobile
  
  @media (min-width: $breakpoint-tablet) {
    font-size: 18px; // Tablet+
  }
}
```

---

## Spacing and Layout

### Container Padding

```scss
.container {
  max-width: 1200px;
  margin-left: auto;
  margin-right: auto;
  padding-left: 16px;  // Mobile
  padding-right: 16px;
  
  @media (min-width: $breakpoint-tablet) {
    padding-left: 32px;  // Tablet
    padding-right: 32px;
  }
  
  @media (min-width: $breakpoint-desktop) {
    padding-left: 48px;  // Desktop
    padding-right: 48px;
  }
}
```

### Section Spacing

```scss
.section {
  padding-top: 40px;    // Mobile
  padding-bottom: 40px;
  
  @media (min-width: $breakpoint-tablet) {
    padding-top: 64px;    // Tablet
    padding-bottom: 64px;
  }
  
  @media (min-width: $breakpoint-desktop) {
    padding-top: 80px;    // Desktop
    padding-bottom: 80px;
  }
}
```

---

## Touch Interactions

### Touch Target Sizes

**Minimum touch target:** 44px × 44px (Apple/WCAG guideline)

```scss
// Buttons
.button, .cta-button {
  min-height: 44px;
  padding: 12px 32px;
  font-size: 16px;
}

// Accordion headers
.accordion-header {
  min-height: 44px;
  padding: 16px;
}

// Navigation links
.nav-link {
  min-height: 44px;
  padding: 12px 16px;
  display: inline-flex;
  align-items: center;
}
```

### Touch-Friendly Spacing

```scss
// Mobile: larger gaps for touch
.card-grid {
  gap: 16px; // Mobile: easier to tap individual cards
  
  @media (min-width: $breakpoint-desktop) {
    gap: 24px; // Desktop: mouse precision
  }
}
```

### Hover States (Desktop Only)

```scss
.card {
  transition: transform 0.3s ease;
  
  // Hover effect only on desktop (not touch devices)
  @media (hover: hover) and (pointer: fine) {
    &:hover {
      transform: translateY(-4px);
      box-shadow: $shadow-lg;
    }
  }
}
```

---

## Images and Media

### Responsive Images

**HTML Pattern:**
```html
<img src="${properties.image}" 
     srcset="${properties.image}?width=600 600w,
             ${properties.image}?width=1200 1200w,
             ${properties.image}?width=1800 1800w"
     sizes="(max-width: 768px) 100vw, 
            (max-width: 1200px) 50vw,
            600px"
     alt="${properties.alt}"/>
```

**CSS Pattern:**
```scss
img {
  max-width: 100%; // Never overflow container
  height: auto;    // Maintain aspect ratio
  display: block;  // Remove inline spacing
}
```

### Background Images

```scss
.hero {
  background-image: url('/path/to/image-mobile.jpg'); // Mobile
  background-size: cover;
  background-position: center;
  
  @media (min-width: $breakpoint-tablet) {
    background-image: url('/path/to/image-desktop.jpg'); // Desktop
  }
}
```

---

## Performance Considerations

### Mobile Performance Optimization

**Critical CSS:**
- Inline critical CSS for above-the-fold content
- Defer non-critical CSS

**Image Optimization:**
- Use WebP with JPEG fallback
- Lazy load below-fold images
- Use AEM image servlets for dynamic sizing

**JavaScript:**
- Defer non-critical JavaScript
- Use smaller libraries or vanilla JS
- Avoid layout thrashing

### Responsive Performance Budget

| Device | Page Weight | Load Time | Lighthouse |
|--------|-------------|-----------|------------|
| Mobile | < 1.5MB | < 3s | > 85 |
| Tablet | < 2MB | < 2.5s | > 90 |
| Desktop | < 2.5MB | < 2s | > 90 |

---

## Responsive Validation Checklist

Validate **every page** at these device profiles:

- [ ] iPhone SE (375 × 667)
- [ ] iPhone 14 (390 × 844)
- [ ] iPad (768 × 1024)
- [ ] 1024 desktop / small laptop
- [ ] 1440 desktop
- [ ] Ultra-wide desktop (≥ 1920)

At each profile, validate these elements render and behave correctly:

- [ ] **Menu** (hamburger ↔ horizontal nav transition; open/close)
- [ ] **Hero** (title scaling, service-card stacking/overlap)
- [ ] **Card grids** (column reflow 1 → 2 → 3/4)
- [ ] **Accordion** (touch targets, expand/collapse)
- [ ] **CTA** (button + mascot layout)
- [ ] **Footer** (XF rendering, contact + mascot)
- [ ] **XF rendering** (header + footer present on every page)
- [ ] **Image scaling** (no overflow, correct aspect ratio)
- [ ] **Typography wrapping** (no clipped/overflowing headings)

### Component Requirements

**All components must support:**
- **Mobile-first layout** (base styles target small screens; enhance upward)
- **Touch interactions** (≥ 44px touch targets; no hover-only affordances)
- **Keyboard accessibility** (focusable, operable controls; logical tab order)
- **Reduced-motion preference** (`@media (prefers-reduced-motion: reduce)` honored)

---

## Testing Strategy

### Manual Testing Checklist

**Per Component:**
- [ ] Test at 320px (small mobile)
- [ ] Test at 375px (iPhone)
- [ ] Test at 768px (tablet portrait)
- [ ] Test at 1024px (tablet landscape)
- [ ] Test at 1440px (desktop)
- [ ] Test landscape and portrait orientations
- [ ] Test on real iOS device
- [ ] Test on real Android device

**Interaction Testing:**
- [ ] Touch targets adequate (44px min)
- [ ] Scrolling smooth
- [ ] Forms usable on mobile
- [ ] Mobile menu works
- [ ] Accordions expand/collapse
- [ ] Links tapable without precision

**Visual Testing:**
- [ ] No horizontal scrolling
- [ ] No content overflow
- [ ] Images scale correctly
- [ ] Text readable (not too small)
- [ ] Sufficient spacing
- [ ] Touch targets not overlapping

### Tools for Testing

**Browser DevTools:**
- Chrome DevTools Device Mode
- Firefox Responsive Design Mode
- Safari Responsive Design Mode

**Real Devices:**
- iPhone 12/13/14 (375px)
- iPad (768px / 1024px)
- Android phone (360px - 412px)

**Testing Services:**
- BrowserStack (cross-device testing)
- LambdaTest (cloud testing)
- Physical device lab

---

## Common Responsive Patterns

### Pattern 1: Stack on Mobile

```scss
.two-column {
  display: flex;
  flex-direction: column; // Mobile: stack
  gap: 24px;
  
  @media (min-width: $breakpoint-tablet) {
    flex-direction: row; // Tablet+: side-by-side
  }
}
```

### Pattern 2: Grid Auto-Fit

```scss
.grid {
  display: grid;
  grid-template-columns: 1fr; // Mobile: single column
  gap: 16px;
  
  @media (min-width: $breakpoint-tablet) {
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); // Flexible
  }
}
```

### Pattern 3: Hide on Mobile

```scss
.decorative-element {
  display: none; // Hidden on mobile (performance)
  
  @media (min-width: $breakpoint-tablet) {
    display: block; // Show on tablet+
  }
}
```

### Pattern 4: Different Layout

```scss
.hero {
  // Mobile: vertical layout
  .hero-content { order: 1; }
  .hero-image { order: 2; }
  
  @media (min-width: $breakpoint-tablet) {
    // Tablet+: horizontal layout
    display: flex;
    .hero-content { order: 1; flex: 1; }
    .hero-image { order: 2; flex: 1; }
  }
}
```

---

## Accessibility Considerations

### Responsive Accessibility

**Touch Targets:**
- Minimum 44px × 44px
- Adequate spacing between tappable elements
- Avoid overlapping clickable areas

**Text Readability:**
- Minimum 16px font size (never scale below)
- Sufficient line height (1.5 minimum)
- Adequate color contrast at all sizes

**Keyboard Navigation:**
- Works on all devices
- Focus indicators visible
- Tab order logical

**Screen Readers:**
- Content order makes sense when linearized
- ARIA labels present
- Alt text on all images

---

## Related Documentation

- [Frontend Tech Details](../06-frontend-tech-details.md)
- [Design System](../07-design-system.md)
- [Component Specification](../04-component-specification.md)
- [Technical Risks](./05-technical-risks.md)

---

**Document Version:** 1.0  
**Last Updated:** 2026-05-31  
**Target Audience:** Frontend Developers
