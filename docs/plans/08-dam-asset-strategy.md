# DAM Asset Strategy - Setia AEM Website

## Overview

This document defines the Digital Asset Management (DAM) strategy for organizing, optimizing, and managing all digital assets used on the Setia website.

**Goals:**
- Organized, searchable asset library
- Optimized assets for web performance
- Clear naming conventions
- Efficient asset workflow
- Version control and governance

> **Source of truth:** the real assets ship in [`docs/assets/`](../assets/) (`images/`, `pdf/`, `favicon/`). The folder structure and filenames below reflect **those actual files** mapped into the DAM, not idealized placeholders. When bootstrapping (see [09 — Bootstrap Initial Content](./09-boostrap-initial-content.md)), copy from `docs/assets/` into `/content/dam/setia/` and keep the original filenames so references stay stable.

---

## Source Assets → DAM Mapping

The assets are delivered in `docs/assets/`. The table maps each source file to its DAM destination and the component(s) that consume it.

| Source (`docs/assets/`) | DAM destination | Used by |
|---|---|---|
| `images/logo_white.png` | `logos/brand/logo_white.png` | Header XF (logo on dark bg) |
| `images/logo.png`, `sologo-maior.png` | `logos/brand/` | Alt logo / favicon source |
| `images/contact_area.png` | `logos/brand/contact_area.png` | **Mascot** — CTA section + Footer XF "Get in touch" |
| `images/bg-home.jpg` | `images/hero-backgrounds/bg-home.jpg` | `home-hero` background |
| `images/bg-content.jpg` | `images/banners/bg-content.jpg` | `page-banner` background (inner pages) |
| `images/FundoAmarelo.jpg` | `images/backgrounds/FundoAmarelo.jpg` | Section background ("What we do" / Engagement) |
| `images/components.png` | `images/content-images/components.png` | Services "and how…" icon row |
| `images/strategy.jpg` | `images/diagrams/strategy.jpg` | Services `image-section` ("Component-driven Acceleration") |
| `images/digital-assets.jpg` | `images/diagrams/digital-assets.jpg` | Solutions `image-section` ("Digital Assets Management Platform") |
| `images/blockchain-icon.jpg` | `icons/services/blockchain-icon.jpg` | Service card icon |
| `images/team.png`, `clientes.jpg`, `tools.png`, `mpsbr.jpg` | `images/content-images/` | Company "Business Experience" / supporting imagery |
| `images/partners/parceria_aws.png` | `logos/partners/parceria_aws.png` | `logo-gallery` (AWS) |
| `images/partners/parceria_neo.png` | `logos/partners/parceria_neo.png` | `logo-gallery` (Neo4j) |
| `images/partners/parceria_ibm.png` | `logos/partners/parceria_ibm.png` | `logo-gallery` (IBM) |
| `images/partners/parceria_its.png` | `logos/partners/parceria_its.png` | `logo-gallery` (ITS) |
| `images/partners/parceria_microsoft.png` | `logos/partners/parceria_microsoft.png` | `logo-gallery` (Microsoft — available, not in current screenshots) |
| `images/team_certifications/{logo_ibm,logo_oracle,logo_redhat,parceria_microsoft}.png` | `logos/certifications/` | Company "Business Experience" cert row |
| `pdf/setia-institucional.pdf` | `documents/presentations/setia-institucional.pdf` | `download-list` — Company Presentation |
| `pdf/metodo-trabalho-setia.pdf` | `documents/presentations/metodo-trabalho-setia.pdf` | `download-list` — Work Method / Technical Presentation |
| `pdf/{setia-gamification,setia-integracao,setia-rpa,setia-webreports,setia-webstore,singular}.pdf` | `documents/presentations/` | `download-list` — additional brochures |
| `favicon/*`, `images/site.webmanifest`, `images/safari-pinned-tab.svg` | served at site root via page head | Favicon / PWA manifest (see [Favicon & PWA Assets](#favicon-and-pwa-assets)) |

> ⚠️ **Partners are AWS, Neo4j, IBM, ITS** (with Microsoft also available). Earlier doc revisions listed "SAP" — **there is no SAP asset** and SAP does not appear in the website screenshots. Do not reference SAP.

---

## Mandatory Initial Assets

These assets **must** be uploaded before content bootstrap (Phase 7 / doc 09). Without them, components render with broken images and bootstrap is incomplete. All exist under `docs/assets/`.

| Category | Required for bootstrap | Source file(s) |
|----------|------------------------|----------------|
| **Hero background** | home-hero | `images/bg-home.jpg` |
| **Banner background** | page-banner (inner pages) | `images/bg-content.jpg` |
| **Logos (brand)** | Header XF | `images/logo_white.png` |
| **Service icons** | home-hero, card-grid | `images/blockchain-icon.jpg` (+ source remaining icons) |
| **Partner logos** | logo-gallery | `images/partners/parceria_{aws,neo,ibm,its}.png` (Microsoft optional) |
| **Diagrams** | image-section | `images/strategy.jpg` (Services), `images/digital-assets.jpg` (Solutions) |
| **Footer mascot** | Footer XF | `images/contact_area.png` |
| **CTA assets** | cta-section | `images/contact_area.png` (mascot) |

> Full source→DAM→component mapping is in [Source Assets → DAM Mapping](#source-assets--dam-mapping) above. Documents (PDFs) for the download-list are listed there too.

---

## DAM Folder Structure

### Target Hierarchy (populated from `docs/assets/`)

```
/content/dam/setia/
├── images/
│   ├── hero-backgrounds/
│   │   └── bg-home.jpg              # home-hero background
│   ├── banners/
│   │   └── bg-content.jpg           # inner page-banner background
│   ├── backgrounds/
│   │   └── FundoAmarelo.jpg         # yellow/green halftone section bg
│   ├── content-images/
│   │   ├── components.png           # "and how..." icon row
│   │   ├── team.png
│   │   ├── clientes.jpg
│   │   ├── tools.png
│   │   └── mpsbr.jpg
│   └── diagrams/
│       ├── strategy.jpg             # Services strategy diagram
│       └── digital-assets.jpg       # Solutions platform diagram
├── icons/
│   └── services/
│       └── blockchain-icon.jpg      # (additional service icons as sourced)
├── logos/
│   ├── brand/
│   │   ├── logo_white.png           # header logo (white, on dark bg)
│   │   ├── logo.png
│   │   ├── sologo-maior.png
│   │   └── contact_area.png         # mascot (CTA + footer)
│   ├── partners/
│   │   ├── parceria_aws.png
│   │   ├── parceria_neo.png         # Neo4j
│   │   ├── parceria_ibm.png
│   │   ├── parceria_its.png
│   │   └── parceria_microsoft.png   # available; not in current screenshots
│   └── certifications/
│       ├── logo_ibm.png
│       ├── logo_oracle.png
│       ├── logo_redhat.png
│       └── parceria_microsoft.png
├── documents/
│   └── presentations/
│       ├── setia-institucional.pdf      # Company Presentation
│       ├── metodo-trabalho-setia.pdf    # Work Method
│       ├── setia-gamification.pdf
│       ├── setia-integracao.pdf
│       ├── setia-rpa.pdf
│       ├── setia-webreports.pdf
│       ├── setia-webstore.pdf
│       └── singular.pdf
└── videos/                          # Future: promotional videos
    └── (placeholder for future content)
```

> The source assets are mostly raster (`.jpg`/`.png`), not SVG. The "prefer SVG" guidance below remains the target for any **new** logos/icons/diagrams, but the delivered assets are bitmaps — optimize them (compress, correct dimensions) rather than assuming vector sources exist.

---

## Asset Requirements by Type

### Hero Background Images

**Purpose:** Full-width hero section backgrounds  
**Components:** home-hero, page-banner

**Specifications:**
- **Format:** JPEG (compressed) or WebP with JPEG fallback
- **Dimensions:** 1920×1080 minimum (16:9 aspect ratio)
- **File Size:** < 500KB (compressed)
- **Resolution:** 72 DPI (web standard)
- **Color Space:** sRGB

**Naming Convention:**
```
{page}-{descriptor}-{dimensions}.{ext}
Examples:
- home-hero-1920x1080.jpg
- company-banner-tech-pattern-1920x1080.jpg
- services-hero-background-1920x1080.jpg
```

**Optimization:**
- Compress with tools like TinyJPG, ImageOptim
- Use WebP format (80% quality) with JPEG fallback
- Consider lazy loading (below fold)

---

### Content Images

**Purpose:** Images within page content, image sections  
**Components:** image-section, rich-text-section

**Specifications:**
- **Format:** JPEG (photos), PNG (transparency needed), SVG (diagrams)
- **Dimensions:** 1200×800 minimum (3:2 aspect ratio)
- **File Size:** < 300KB
- **Resolution:** 72 DPI
- **Color Space:** sRGB

**Naming Convention:**
```
{category}-{descriptor}-{dimensions}.{ext}
Examples:
- content-team-collaboration-1200x800.jpg
- diagram-platform-architecture-1200x800.svg
- content-office-workspace-1200x800.jpg
```

**Optimization:**
- Compress JPEG to 70-80% quality
- Use SVG for diagrams (scalable, small file size)
- Provide 2x resolution for Retina displays (optional)

---

### Icons

**Purpose:** Service cards, feature highlights, UI elements  
**Components:** card-grid, home-hero, ui elements

**Specifications:**
- **Format:** SVG (preferred), PNG (fallback)
- **Dimensions:** 64×64 for service icons, scalable SVG
- **File Size:** < 10KB (SVG should be tiny)
- **Style:** Consistent (all line icons OR all filled icons)
- **Color:** Single color or colorless (styled via CSS)

**Naming Convention:**
```
{category}-{name}-icon.svg
Examples:
- service-blockchain-icon.svg
- service-cloud-icon.svg
- ui-arrow-right-icon.svg
- ui-checkmark-icon.svg
```

**Optimization:**
- Remove unnecessary SVG metadata
- Use SVGO tool for compression
- Ensure SVG viewBox is set correctly
- Test SVG renders correctly in all browsers

---

### Logos

**Purpose:** Brand logo, partner logos, technology logos  
**Components:** header XF, logo-gallery

**Specifications:**
- **Format:** SVG (preferred), PNG (transparent background)
- **Dimensions:** Variable (maintain aspect ratio)
- **File Size:** < 50KB
- **Background:** Transparent
- **Resolution:** Vector (SVG) or 300 DPI (PNG)

**Naming Convention:**

New logos should follow `{organization}-logo.{ext}`. The **delivered** partner logos keep their original `parceria_*` names so existing references stay valid:
```
Delivered (keep as-is):
- logo_white.png        (Setia, white — header)
- contact_area.png      (mascot)
- parceria_aws.png      (AWS)
- parceria_neo.png      (Neo4j)
- parceria_ibm.png      (IBM)
- parceria_its.png      (ITS)
- parceria_microsoft.png (Microsoft)

Pattern for any NEW logo:
- {organization}-logo.svg
```

**Optimization:**
- Prefer SVG for any new scalable logo; delivered partner logos are PNG with transparent backgrounds — keep them as-is unless a vector source becomes available
- Remove unnecessary SVG code
- Provide both light and dark versions of the Setia logo (`logo_white.png` is the white/header variant)
- Partner logos: ensure brand guidelines followed

---

### Documents (PDFs)

**Purpose:** Downloadable presentations, whitepapers, case studies  
**Components:** download-list

**Specifications:**
- **Format:** PDF (preferred)
- **File Size:** < 10MB
- **Compression:** Compressed/optimized PDF
- **Version:** Include version number in filename

**Naming Convention:**
```
{type}-{name}-{version}.pdf
Examples:
- presentation-company-overview-2026.pdf
- whitepaper-blockchain-security-v1.pdf
- case-study-industry-40-client-name.pdf
```

**Best Practices:**
- Include cover page with branding
- Ensure links work if embedded
- Test PDF opens correctly on mobile devices
- Provide file size in download-list component
- Include copyright/confidentiality notices

---

### Diagrams and Infographics

**Purpose:** Technical architecture, strategy diagrams, process flows  
**Components:** image-section

**Specifications:**
- **Format:** SVG (preferred for diagrams), PNG (complex graphics)
- **Dimensions:** 1200×800 minimum, scalable
- **File Size:** < 200KB
- **Background:** Transparent or white
- **Text:** Editable in SVG (not rasterized)

**Naming Convention:**
```
diagram-{topic}-{descriptor}.svg
Examples:
- diagram-platform-architecture.svg
- diagram-strategy-component-driven.svg
- diagram-development-process.svg
```

**Best Practices:**
- Use SVG for clean, scalable diagrams
- Ensure text is readable at all sizes
- Use brand colors consistently
- Include alt text description in metadata

---

## Asset Naming Conventions

### General Rules

**Format:**
```
{category}-{descriptor}-{specification}.{extension}
```

**Rules:**
- Use lowercase
- Use hyphens (not underscores or spaces)
- Be descriptive but concise
- Include dimensions for raster images
- Include version for documents
- Use consistent naming across similar assets

**Good Examples:**
```
✅ home-hero-1920x1080.jpg
✅ service-blockchain-icon.svg
✅ diagram-platform-architecture.svg
✅ presentation-company-overview-2026.pdf
✅ aws-logo.svg
```

**Bad Examples:**
```
❌ img1.jpg (not descriptive)
❌ Hero Background.jpg (spaces, capitalization)
❌ blockchain_icon.svg (underscores)
❌ presentation.pdf (missing descriptor)
❌ AWS_Logo_2024_Final_v3.svg (inconsistent, too verbose)
```

---

## Asset Metadata Standards

### Required Metadata (All Assets)

**Fields to complete in AEM:**

1. **Title** (required)
   - Descriptive name
   - Example: "Blockchain Service Icon"

2. **Description** (required)
   - What the asset shows/contains
   - Example: "Icon representing blockchain and Web3 services, used on service cards"

3. **Alt Text** (required for images)
   - Accessibility description
   - Example: "Blockchain icon showing interconnected blocks"

4. **Tags** (recommended)
   - Searchable keywords
   - Example: "blockchain, service, icon, web3"

5. **Copyright** (optional)
   - Owner/rights information
   - Example: "© 2026 Setia. All rights reserved."

### Metadata Best Practices

**Title:**
- Descriptive and searchable
- Include main subject
- Consistent format

**Description:**
- Explain what it is and where it's used
- Include context
- Mention any special requirements

**Alt Text (Images):**
- Describe what's visible
- Keep under 125 characters
- Don't say "image of" or "picture of"
- Be specific and concise

**Tags:**
- Use consistent tag vocabulary
- Include category (image, icon, logo, document)
- Include topic (blockchain, service, partner)
- Include usage (hero, banner, card)

---

## Asset Upload Workflow

### Step-by-Step Process

**1. Prepare Asset**
- Optimize file size
- Rename following naming convention
- Check dimensions and format
- Test file opens correctly

**2. Upload to DAM**
1. Navigate to AEM Assets: `http://localhost:4502/assets.html`
2. Browse to appropriate folder (e.g., `/content/dam/setia/images/hero-backgrounds/`)
3. Click: `Create` → `Files`
4. Drag and drop file(s) OR browse to select
5. Wait for upload to complete (green checkmark)

**3. Add Metadata**
1. Click uploaded asset
2. Click: `Properties` (info icon)
3. Fill in metadata:
   - Title
   - Description
   - Alt Text (for images)
   - Tags
   - Copyright (if applicable)
4. Click: `Save & Close`

**4. Publish Asset** (if needed on publish instance)
1. Select asset(s)
2. Click: `Quick Publish`
3. Wait for confirmation

**5. Verify**
- Preview asset in DAM
- Test asset in component
- Check file size and load time

**Time per asset:** 2-5 minutes

---

## Asset Organization Best Practices

### Folder Strategy

**DO:**
- ✅ Organize by type first (images, icons, logos, documents)
- ✅ Then by subtype (hero-backgrounds, service icons)
- ✅ Keep folder hierarchy shallow (max 3-4 levels)
- ✅ Use clear, descriptive folder names
- ✅ Create folders proactively (don't wait for assets to pile up)

**DON'T:**
- ❌ Put all assets in one folder
- ❌ Organize by date uploaded (hard to find)
- ❌ Use deep, complex folder structures
- ❌ Mix unrelated asset types in same folder

### Folder Permissions

**Content Authors:**
- Can upload to: `/content/dam/setia/images/`, `/icons/`, `/documents/`
- Cannot delete from: `/logos/brand/` (protected brand assets)

**Content Admins:**
- Can manage all folders
- Can create new folders
- Can delete assets (with caution)

**Developers:**
- Full access
- Can restructure folders
- Can configure metadata schemas

---

## Asset Versioning

### When to Version

**Create new version when:**
- Updating existing asset with changes
- Logo redesign (keep old version)
- Document updates (v1, v2, v3)
- Significant image edits

**Upload as new asset when:**
- Completely different image
- Different aspect ratio
- Different use case

### Version Naming

**Documents:**
```
presentation-company-overview-v1.pdf
presentation-company-overview-v2.pdf
presentation-company-overview-2026.pdf (year-based)
```

**Images (if versioning needed):**
```
home-hero-v1-1920x1080.jpg
home-hero-v2-1920x1080.jpg
```

**Logos:**
```
setia-logo-2024.svg
setia-logo-2026.svg
```

### Version Management

**AEM Version History:**
- AEM tracks asset versions automatically
- Access via: Asset Properties → Versions tab
- Can restore previous versions
- Compare versions side-by-side

---

## Asset Optimization Checklist

### Before Upload

**Images:**
- [ ] Compressed to appropriate file size
- [ ] Correct dimensions (not oversized)
- [ ] Correct format (JPEG/PNG/SVG/WebP)
- [ ] Color space: sRGB
- [ ] DPI: 72 (web standard)

**Icons:**
- [ ] SVG optimized (SVGO)
- [ ] ViewBox set correctly
- [ ] Single color or colorless
- [ ] No unnecessary metadata

**Logos:**
- [ ] Transparent background
- [ ] Vector format (SVG)
- [ ] Brand guidelines followed
- [ ] Both light/dark versions if needed

**Documents:**
- [ ] PDF optimized/compressed
- [ ] File size reasonable (< 10MB)
- [ ] Links work if embedded
- [ ] Cover page present

### After Upload

- [ ] Metadata complete (title, description, alt text, tags)
- [ ] Preview looks correct
- [ ] File loads quickly
- [ ] Tested in component
- [ ] Published (if needed)

---

## Asset Performance Monitoring

### Performance Metrics

**Track:**
- Asset file sizes (avg, max)
- Page weight with assets
- Image load times
- Lazy loading effectiveness
- WebP adoption rate

**Tools:**
- Lighthouse (performance score)
- Chrome DevTools (Network tab)
- WebPageTest (asset analysis)
- AEM Reports (DAM usage)

**Targets:**
- Hero images: < 500KB
- Content images: < 300KB
- Icons: < 10KB
- Total page weight: < 2MB

---

## DAM Governance

### Governance Rules

- **SVG preferred** for logos and icons (scalable, tiny). Delivered partner logos are PNG with transparent backgrounds — keep as-is until a vector source exists.
- **WebP preferred** for large raster images (hero/banner/content), with a JPEG fallback.
- **All assets require alt-text metadata** before they may be referenced on a published page (accessibility).
- **File-size validation before upload** — hero/banner < 500 KB, content images < 300 KB, icons < 10 KB, documents < 10 MB. Compress before uploading.
- **Naming-convention enforcement** — new assets follow the conventions in [Asset Naming Conventions](#asset-naming-conventions); delivered files keep their original names so references stay stable.

### Asset Lifecycle

**1. Creation/Acquisition**
- Design team creates
- Stock photos sourced
- Partners provide logos

**2. Upload**
- Content author uploads
- Metadata added
- Asset published

**3. Usage**
- Referenced in components
- Appears on published pages
- Tracked via references

**4. Update**
- Asset replaced/versioned
- Metadata updated
- Pages republished

**5. Archival**
- Old assets moved to archive folder
- Not deleted (may be referenced)
- Marked as "archived" in metadata

### Asset Review Schedule

**Quarterly:**
- Review unused assets (archive or delete)
- Check for outdated content (old dates, logos)
- Optimize oversized files
- Update metadata where missing

**Annually:**
- Full DAM audit
- Reorganize if needed
- Update naming conventions
- Train new content authors

---

## Common DAM Issues and Solutions

### Issue 1: Asset Not Appearing on Page

**Possible Causes:**
- Asset not published
- Wrong path referenced
- Asset deleted
- Permissions issue

**Solution:**
1. Verify asset exists in DAM
2. Check asset path in component dialog
3. Publish asset: `Quick Publish`
4. Clear dispatcher cache
5. Hard refresh browser

### Issue 2: Image Too Large / Slow Loading

**Possible Causes:**
- File size too large (not optimized)
- Wrong format (PNG instead of JPEG)
- Oversized dimensions

**Solution:**
1. Download asset from DAM
2. Compress with TinyJPG or ImageOptim
3. Re-upload optimized version
4. Or: Use AEM image servlet for dynamic sizing

### Issue 3: Can't Find Asset

**Possible Causes:**
- Uploaded to wrong folder
- No metadata/tags
- Inconsistent naming

**Solution:**
1. Use DAM search (search by filename, tags)
2. Check all folders manually
3. Add proper metadata to aid future searches
4. Re-upload to correct folder if needed

### Issue 4: Logo Looks Pixelated

**Possible Causes:**
- PNG instead of SVG
- Low resolution
- Image stretched beyond dimensions

**Solution:**
1. Use SVG format for logos (scalable)
2. If PNG, ensure 300 DPI minimum
3. Don't specify width/height that stretches image

---

## Asset Checklist by Component

Assets in **bold** are the actual delivered files from `docs/assets/`.

### home-hero
**Required assets:**
- [ ] Hero background — **`bg-home.jpg`**
- [ ] Service card icons (e.g. **`blockchain-icon.jpg`**; source remaining icons as needed)

### page-banner
**Required assets:**
- [ ] Banner background — **`bg-content.jpg`** (inner pages); optional per-page override

### card-grid
**Required assets:**
- [ ] Icon for each card (source as needed; **`components.png`** illustrates the "build-block" set)

### image-section
**Required assets:**
- [ ] Services: **`strategy.jpg`** ("Component-driven Acceleration") + alt text
- [ ] Solutions: **`digital-assets.jpg`** ("Digital Assets Management Platform") + alt text

### logo-gallery
**Required assets (partners — AWS, Neo4j, IBM, ITS; Microsoft optional):**
- [ ] **`parceria_aws.png`**, **`parceria_neo.png`**, **`parceria_ibm.png`**, **`parceria_its.png`**
- [ ] Alt text for each logo
- [ ] (Company "Business Experience") certifications: **`logo_ibm.png`**, **`logo_oracle.png`**, **`logo_redhat.png`**, **`parceria_microsoft.png`**

### download-list
**Required assets (Company page brochures):**
- [ ] **`setia-institucional.pdf`** (Company Presentation), **`metodo-trabalho-setia.pdf`** (Work Method)
- [ ] Additional: **`setia-gamification.pdf`**, **`setia-integracao.pdf`**, **`setia-rpa.pdf`**, **`setia-webreports.pdf`**, **`setia-webstore.pdf`**, **`singular.pdf`**
- [ ] File descriptions + sizes

### cta-section
**Required assets:**
- [ ] Mascot — **`contact_area.png`** (girl with laptop + headset, transparent PNG)

### Header XF
**Required assets:**
- [ ] Setia logo — **`logo_white.png`** (white, for dark background)

### Footer XF
**Required assets:**
- [ ] Mascot — **`contact_area.png`**

---

## Favicon and PWA Assets

A complete favicon / PWA icon set ships in [`docs/assets/favicon/`](../assets/favicon/) plus [`docs/assets/images/site.webmanifest`](../assets/images/site.webmanifest) and `safari-pinned-tab.svg`.

**Contents:**
- `favicon.ico`, `favicon-16x16.png`, `favicon-32x32.png`, `favicon-96x96.png`
- `apple-icon-*.png` (57–180px) + `apple-icon-precomposed.png`
- `android-icon-*.png` (36–192px) + `manifest.json`
- `ms-icon-*.png` (70–310px) + `browserconfig.xml`
- `safari-pinned-tab.svg` (mask icon)

**Implementation:** these are head-level resources, not DAM content components. The cleanest options for AEM:
1. Ship them as static files in a ClientLib's `resources/` folder and emit the `<link rel="icon">` / `<link rel="apple-touch-icon">` / `<meta name="msapplication-*">` tags from `customheaderlibs.html`, **or**
2. Serve from the site root via Dispatcher rewrites (favicons are conventionally requested at `/favicon.ico`, `/apple-icon.png`, etc.).

> The delivered `manifest.json` and `site.webmanifest` have placeholder `name`/icon paths — update `name`, `short_name`, `theme_color` (`#10245a` brand dark blue), and icon `src` paths to the deployed locations before launch.

---

## Related Documentation

- [AEM Authoring Strategy](./06-aem-authoring-strategy.md)
- [Authoring Guidelines](../05-authoring-guidelines.md)
- [Component Specification](../04-component-specification.md)
- [Technical Risks](./05-technical-risks.md)

---

**Document Version:** 1.0  
**Last Updated:** 2026-05-31  
**Target Audience:** Content Authors, Asset Managers
