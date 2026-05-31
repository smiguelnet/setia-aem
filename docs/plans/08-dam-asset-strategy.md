# DAM Asset Strategy - Setia AEM Website

## Overview

This document defines the Digital Asset Management (DAM) strategy for organizing, optimizing, and managing all digital assets used on the Setia website.

**Goals:**
- Organized, searchable asset library
- Optimized assets for web performance
- Clear naming conventions
- Efficient asset workflow
- Version control and governance

---

## DAM Folder Structure

### Complete Hierarchy

```
/content/dam/setia/
├── images/
│   ├── hero-backgrounds/           # Hero section background images
│   │   ├── blockchain-hero.jpg
│   │   ├── home-hero.jpg
│   │   └── tech-pattern-overlay.svg
│   ├── banners/                    # Page banner backgrounds
│   │   ├── company-banner.jpg
│   │   ├── services-banner.jpg
│   │   └── solutions-banner.jpg
│   ├── content-images/             # General content images
│   │   ├── team-collaboration.jpg
│   │   ├── office-workspace.jpg
│   │   └── client-meeting.jpg
│   └── diagrams/                   # Technical diagrams, infographics
│       ├── platform-architecture.svg
│       ├── strategy-diagram.svg
│       └── component-flow.svg
├── icons/
│   ├── services/                   # Service card icons
│   │   ├── blockchain-icon.svg
│   │   ├── cloud-icon.svg
│   │   ├── digital-transformation-icon.svg
│   │   ├── embedded-systems-icon.svg
│   │   ├── industry-40-icon.svg
│   │   └── web3-icon.svg
│   └── ui/                         # UI icons (arrows, chevrons, etc.)
│       ├── arrow-right.svg
│       ├── checkmark.svg
│       ├── download.svg
│       └── expand-icon.svg
├── logos/
│   ├── brand/                      # Setia brand assets
│   │   ├── setia-logo-white.svg
│   │   ├── setia-logo-dark.svg
│   │   ├── setia-logo-icon.svg
│   │   └── mascot-character.png
│   └── partners/                   # Partner/technology logos
│       ├── aws-logo.svg
│       ├── neo4j-logo.svg
│       ├── sap-logo.svg
│       └── its-logo.svg
├── documents/
│   ├── presentations/              # Company/technical presentations
│   │   ├── company-overview-2026.pdf
│   │   ├── technical-capabilities-v2.pdf
│   │   └── services-portfolio.pdf
│   └── whitepapers/                # Marketing documents, case studies
│       ├── blockchain-security-whitepaper.pdf
│       ├── digital-transformation-guide.pdf
│       └── industry-40-case-study.pdf
└── videos/                         # Future: promotional videos
    └── (placeholder for future content)
```

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
```
{organization}-logo.svg
Examples:
- setia-logo-white.svg
- setia-logo-dark.svg
- aws-logo.svg
- neo4j-logo.svg
```

**Optimization:**
- Use SVG for scalability
- Remove unnecessary SVG code
- Provide both light and dark versions of Setia logo
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

### home-hero
**Required assets:**
- [ ] Hero background image (1920×1080, < 500KB)
- [ ] 3 service icons (SVG, < 10KB each)
- [ ] Optional: Tech pattern overlay (SVG)

### page-banner
**Required assets:**
- [ ] Optional: Banner background image (1920×1080, < 500KB)

### card-grid
**Required assets:**
- [ ] Icon for each card (SVG, < 10KB)
- [ ] Optional: Card background images

### image-section
**Required assets:**
- [ ] Content image or diagram (1200×800, < 300KB)
- [ ] Alt text in metadata

### logo-gallery
**Required assets:**
- [ ] Partner logos (SVG, transparent background)
- [ ] Alt text for each logo

### download-list
**Required assets:**
- [ ] PDF documents (< 10MB)
- [ ] File descriptions

### cta-section
**Required assets:**
- [ ] Mascot character image (PNG, transparent)

### Header XF
**Required assets:**
- [ ] Setia logo (SVG, white version)

### Footer XF
**Required assets:**
- [ ] Mascot character image (PNG)

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
