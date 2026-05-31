# AEM Website Implementation Strategy - Setia Website

# 1. Objective

This document defines the recommended implementation strategy for the Setia institutional website using Adobe Experience
Manager (AEM).

The goal is to provide an implementation-oriented structure that an AI agent or development team can use to build the
website efficiently using reusable AEM components, templates, Experience Fragments, and editable authoring structures.

# 2. Website Context

# 2.1 Website Main Links

Institutional corporate website focused on:

- Home
- Company presentation
- Services showcase
- Technology solutions
- Lead generation
- Contact/CTA

## 2.2 Website Map

### 2.2.1 Website Map - Simple Structure

```text
Setia Website
│
├── Home
│   ├── Hero Banner
│   ├── Service Highlights
│   ├── What We Do
│   ├── Engagement Section
│   ├── CTA
│   └── Footer
│
├── Company
│   ├── Company Story
│   ├── Why Setia
│   ├── Downloads
│   ├── Services Overview
│   ├── Partners
│   ├── CTA
│   └── Footer
│
├── Services
│   ├── Services Overview
│   ├── Service Cards
│   ├── Accordion / Detailed Services
│   ├── Strategy Diagram
│   ├── Partners
│   ├── CTA
│   └── Footer
│
└── Solutions
    ├── Solutions Banner
    ├── Platform Overview
    ├── Platform Diagram
    ├── Feature Grid
    ├── CTA
    └── Footer
```

### 2.2.2 Website Map - Detailed Structure

```text
Home
├── Header (fixed on scroll)
├── Hero Banner
│   ├── Main Title
│   ├── Subtitle
│   └── 3 Service Highlight Cards
├── Section 1
│   ├── Title
│   └── Subtitle
├── Section 2
│   ├── Title
│   ├── Subtitle
│   └── Card Grid
├── Section 3
│   ├── Title
│   ├── Subtitle
│   └── Rich Text
├── Section 4
│   ├── Title
│   └── Subtitle
├── CTA Section
└── Footer


Company
├── Header (fixed on scroll)
├── Banner Section
│   ├── Title
│   └── Subtitle
├── Section 1
│   ├── Title
│   ├── Subtitle
│   └── Rich Text
├── Section 2
│   ├── Title
│   ├── Subtitle
│   ├── Rich Text
│   ├── List Items
│   └── Download Files
├── Section 3
│   ├── Title
│   ├── Subtitle
│   └── Card Grid
├── Section 4
│   ├── Title
│   └── Image Gallery
├── Section 5
│   ├── Title
│   └── Subtitle
├── CTA Section
└── Footer


Services
├── Header (fixed on scroll)
├── Banner Section
│   ├── Title
│   └── Subtitle
├── Section 1
│   ├── Title
│   ├── Subtitle
│   └── Rich Text
├── Section 2
│   ├── Title
│   ├── Subtitle
│   └── Card Grid
├── Section 3
│   ├── Title
│   ├── Subtitle
│   ├── Rich Text
│   └── Accordion/List Items
├── Section 4
│   ├── Title
│   ├── Subtitle
│   └── Image
├── Section 5
│   ├── Title
│   └── Image Gallery
├── Section 6
│   ├── Title
│   └── Subtitle
├── CTA Section
└── Footer


Solutions
├── Header (fixed on scroll)
├── Banner Section
│   ├── Title
│   └── Subtitle
├── Section 1
│   ├── Title
│   ├── Subtitle
│   └── Rich Text
├── Section 2
│   ├── Title
│   ├── Subtitle
│   └── Image / Diagram
├── Section 3
│   └── Feature Components
│       ├── Title
│       └── Description
├── Section 4
│   ├── Title
│   └── Subtitle
├── CTA Section
└── Footer
```

### 2.2.3 Website Screenshots

The following screenshots are provided as visual references for the website implementation:

| Page      | Screenshot File                      |
|-----------|--------------------------------------|
| Home      | ![Home Page](./website_home.png)     |
| Company   | ![Company](./website_company.png)    |
| Services  | ![Services](./website_services.png)  |
| Solutions | ![Solution](./website_solutions.png) |

These screenshots should be used as references for:

* Layout structure
* Component composition
* Visual hierarchy
* Section organization
* Responsive behavior expectations
* Branding and styling direction

# Shared Reusable Components

```text
Header
Footer
Hero Banner
Page Banner
Section Heading
Rich Text Section
Card Grid
Feature Card
Image Section
Accordion Section
Download List
Logo Gallery
CTA Section
```

# Recommended AEM Structure

```text
/content/setia
├── home
├── company
├── services
└── solutions
```

# Recommended Experience Fragments

```text
/content/experience-fragments/setia
├── header
└── footer
```

# Recommended Editable Templates

```text
Setia Landing Page Template
Setia Content Page Template
```

---

# 2. Recommended Template Strategy

Create **2 editable templates**.

| Template                      | Used By                      | Reason                                                                                       |
|-------------------------------|------------------------------|----------------------------------------------------------------------------------------------|
| `Setia Landing Page Template` | Home                         | The Home page contains a unique hero with 3 overlapping service cards and a specific layout. |
| `Setia Content Page Template` | Company, Services, Solutions | These pages share a common structure and layout strategy.                                    |

## Recommendation

Do NOT create one template per page unless layout requirements become significantly different later.

---

# 3. Recommended Experience Fragment Strategy

Create **2 Experience Fragments**.

| Experience Fragment | Purpose                                       |
|---------------------|-----------------------------------------------|
| `Header XF`         | Shared top navigation and logo                |
| `Footer XF`         | Shared footer, contact section, and copyright |

## Suggested Paths

```text
/content/experience-fragments/setia/header
/content/experience-fragments/setia/footer
```

## Why Experience Fragments?

Because header and footer are repeated across all pages and should be centrally maintained.

This allows:

- Single place maintenance
- Easier updates
- Consistent navigation
- Shared branding
- Better scalability

---

# 4. Recommended Component Strategy

Create approximately **12 reusable AEM components**.

| Component             | Used In                      | Purpose                           |
|-----------------------|------------------------------|-----------------------------------|
| `setia-page`          | all pages                    | Base page component               |
| `xf-header-reference` | all pages                    | Includes Header XF                |
| `xf-footer-reference` | all pages                    | Includes Footer XF                |
| `home-hero`           | Home only                    | Hero section with 3 service cards |
| `page-banner`         | Company, Services, Solutions | Inner page banner                 |
| `section-heading`     | all pages                    | Reusable section title/subtitle   |
| `rich-text-section`   | all pages                    | Rich text content section         |
| `card-grid`           | all pages                    | Reusable card grid                |
| `image-section`       | Services, Solutions          | Text + image/diagram              |
| `accordion-section`   | Services                     | Expandable content                |
| `download-list`       | Company, Services            | Downloadable documents            |
| `logo-gallery`        | Company, Services            | Partner logos/image gallery       |
| `cta-section`         | all pages                    | CTA block                         |

---

# 5. Page-by-Page Strategy

# 5.1 Home Page

## Template

```text
Setia Landing Page Template
```

## Structure

```text
Header XF
Home Hero
Section Heading
Card Grid
Rich Text Section
CTA Section
Footer XF
```

## Hero Component Requirements

The `home-hero` component should support:

- Background image
- Main title
- Subtitle
- List of 3 service cards

## Important Recommendation

The 3 service cards should be implemented as:

```text
multifield items inside the Hero dialog
```

instead of separate child components, because the layout is tightly coupled to the hero design.

---

# 5.2 Company Page

## Template

```text
Setia Content Page Template
```

## Structure

```text
Header XF
Page Banner
Rich Text Section - Our Story
Rich Text Section - Why Setia
Download List
Card Grid - Services
Logo Gallery - Partners
CTA Section
Footer XF
```

## Notes

The "Why Setia?" list may be implemented as:

- Multifield inside `rich-text-section`
- OR dedicated reusable list component

Recommended approach:

```text
rich-text-section with optional list support
```

---

# 5.3 Services Page

## Template

```text
Setia Content Page Template
```

## Structure

```text
Header XF
Page Banner
Rich Text Section
Card Grid
Accordion Section
Image Section
Logo Gallery
CTA Section
Footer XF
```

## Notes

The accordion should be a dedicated reusable component because it has:

- Expand/collapse behavior
- Dynamic item structure
- Interactive frontend logic

---

# 5.4 Solutions Page

## Template

```text
Setia Content Page Template
```

## Structure

```text
Header XF
Page Banner
Rich Text Section
Image Section - Platform Diagram
Card Grid - Features
CTA Section
Footer XF
```

## Notes

The platform diagram should initially be managed as:

```text
DAM image asset
```

instead of HTML recreation unless responsive editable internals become necessary later.

---

# 6. Component Creation Order

Recommended implementation order:

```text
1. setia-page
2. Header Experience Fragment
3. Footer Experience Fragment
4. page-banner
5. section-heading
6. rich-text-section
7. card-grid
8. home-hero
9. image-section
10. accordion-section
11. download-list
12. logo-gallery
13. cta-section
```

After component creation:

```text
1. Create templates
2. Configure policies
3. Create pages
4. Add components visually
```

---

# 7. Authorable Content Strategy

The following items should be editable by content authors:

- Titles
- Subtitles
- Rich text
- Images
- Background images
- CTA labels
- CTA links
- Card items
- Icons
- Downloads
- Accordion items
- Partner logos
- Navigation links

---

# 8. Code-Controlled Items

The following should remain controlled by developers/CSS:

- Spacing
- Layout
- Responsive behavior
- Breakpoints
- Animations
- Sticky/fixed header behavior
- Colors
- Typography system
- Grid system

---

# 9. Recommended AEM Responsibilities

| Item                   | Location      |
|------------------------|---------------|
| Components             | `ui.apps`     |
| HTL                    | `ui.apps`     |
| Dialogs                | `ui.apps`     |
| Sling Models           | `core`        |
| OSGi Services          | `core`        |
| CSS/JS                 | `ui.frontend` |
| Pages                  | AEM Author    |
| Images                 | DAM           |
| Templates              | AEM Author    |
| Policies               | AEM Author    |
| Experience Fragments   | AEM Author    |
| Initial content export | `ui.content`  |

---

# 10. Synchronization Strategy

# Code → AEM

Deploy after changes to:

- Components
- HTL
- Dialogs
- Sling Models
- CSS/JS

Command:

```bash
mvn clean install -PautoInstallSinglePackage
```

---

# AEM → Git

Export/sync after changes to:

- Templates
- Policies
- Experience Fragments
- Initial pages
- Site structure

Store in:

```text
ui.content
```

---

# 11. Recommended Authoring Strategy

## Developers

Developers should focus on:

```text
ui.apps
core
ui.frontend
```

---

## Content Authors

Content authors should focus on:

```text
AEM Author
```

using visual editing only.

---

# 12. Final Recommended Architecture

## Templates

```text
2 Editable Templates
```

---

## Experience Fragments

```text
2 Experience Fragments
```

- Header
- Footer

---

## Pages

```text
4 Pages
```

- Home
- Company
- Services
- Solutions

---

## Reusable Components

```text
~12 reusable components
```

---

# 13. Final Recommendation

This strategy provides:

- High reuse
- Clean authoring experience
- Maintainable architecture
- Scalable content model
- Reduced duplication
- Better future extensibility

without overengineering the solution.
