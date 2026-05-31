# AEM Authoring Strategy - Setia Website

## Overview

This document defines the content authoring strategy, workflows, governance, and best practices for managing content in the Setia AEM website.

**Goals:**
- Empower content authors to create/edit pages independently
- Maintain brand consistency
- Ensure content quality
- Minimize developer dependency
- Enable rapid content updates

---

## Authoring Model

### Author vs Developer Responsibilities

```
DEVELOPERS own:                    AUTHORS own:
├── Component definitions          ├── Page content
├── HTL templates                  ├── Component configuration
├── Sling Models                   ├── Text and copy
├── CSS/JavaScript                 ├── Images and assets
├── Templates and policies         ├── Publishing workflow
└── Experience Fragments structure └── Content updates
```

### Content Authoring Workflow

```
1. Content Author creates page from template
2. Content Author adds components via drag-and-drop
3. Content Author configures each component via dialog
4. Content Author previews changes
5. Content Author publishes page
6. Published content appears on live site
```

**Time to publish:** < 5 minutes (after content is ready)

---

## Template Strategy for Authors

### When to Use Each Template

**Use Setia Landing Page Template when:**
- Creating the Home page
- Need unique hero with 3 service cards
- Want prominent service showcase

**Use Setia Content Page Template when:**
- Creating Company, Services, Solutions pages
- Creating additional inner pages
- Need standard banner + flexible content sections

### Template Selection Guide

```
Creating a page?
│
├─ Is it the Home page?
│  └─ YES → Use "Setia Landing Page Template"
│
└─ Is it any other page?
   └─ YES → Use "Setia Content Page Template"
```

---

## Component Selection Guide

### Component Picker (What to Use When)

**For page headers:**
- Use `page-banner` on all inner pages (not Home)
- Configure title and subtitle
- Optional background image

**For section titles:**
- Use `section-heading` before each major section
- Keep titles concise (< 60 characters)
- Use subtitle for additional context

**For text content:**
- Use `rich-text-section` for paragraphs and formatted text
- Use rich text editor for formatting (bold, lists, links)
- Optional: enable list for bullet points

**For services/features:**
- Use `card-grid` for multiple items
- 3 columns for services (typically 6 cards total)
- Enter title, description, icon for each card

**For images/diagrams:**
- Use `image-section` for text + image layouts
- Choose left or right image position
- Upload image to DAM first

**For expandable content:**
- Use `accordion-section` for Q&A or detailed info
- Each item has a title and expandable content
- Only use on Services page (or similar)

**For downloads:**
- Use `download-list` for PDFs and documents
- Upload files to DAM first
- Enter file title and description

**For partner logos:**
- Use `logo-gallery` for partner/technology logos
- Upload logos to DAM first (SVG or PNG)
- Enter alt text for accessibility

**For call-to-action:**
- Use `cta-section` at bottom of every page
- Configure heading and button text/link
- Mascot appears by default

---

## Component Configuration Best Practices

### General Guidelines

**Titles:**
- Keep concise (40-60 characters)
- Use sentence case (not ALL CAPS)
- Be descriptive, not clever
- Include keywords for SEO

**Descriptions:**
- 2-3 sentences maximum
- Front-load important information
- Use active voice
- Avoid jargon

**Images:**
- Always provide alt text
- Describe what the image shows
- Don't start with "Image of..."
- Use meaningful file names

**Links:**
- Use descriptive link text (not "click here")
- Link to relevant pages
- Test all links before publishing

### Component-Specific Guidelines

#### page-banner
```
✅ DO:
- Title: "We are Setia" (clear, brand-focused)
- Subtitle: "Delivering Innovative and Quality Solutions" (benefit statement)

❌ DON'T:
- Title: "Welcome!" (too generic)
- Subtitle: Long paragraph of text (keep it short)
```

#### section-heading
```
✅ DO:
- Title: "What we do" (clear)
- Subtitle: "Digital Products for Future-Ready Businesses" (descriptive)
- Alignment: Center (for major sections)

❌ DON'T:
- Skip subtitle when context is needed
- Use H4 for major sections (use H2)
```

#### card-grid
```
✅ DO:
- Use 3 columns for 6 service cards
- Keep card descriptions similar length
- Include link URLs for each card
- Use consistent icon style

❌ DON'T:
- Mix different card background colors randomly
- Leave card descriptions empty
- Use low-quality icons
```

#### accordion-section
```
✅ DO:
- Use question-like titles ("How do we approach projects?")
- Keep item titles consistent length
- First item can be expanded by default
- Use rich text for content (can include lists, links)

❌ DON'T:
- Use more than 7 accordion items (overwhelming)
- Leave content empty
- Use vague titles
```

---

## Experience Fragment Management

### What are Experience Fragments?

Experience Fragments (XF) are **reusable content blocks** that appear on multiple pages. Changes to an XF update all pages using it.

**Setia uses 2 Experience Fragments:**
1. **Header** - Navigation and logo (appears on all pages)
2. **Footer** - Contact section and copyright (appears on all pages)

### Editing Experience Fragments

**Important:** You cannot edit XF directly on pages. You must edit the XF itself.

**To edit Header or Footer:**

1. Navigate to: `Experience Fragments Console`
2. Find: `/content/experience-fragments/setia/header` (or `/footer`)
3. Open the `master` variation
4. Edit content
5. **CRITICAL:** Publish the Experience Fragment
6. Wait 1-2 minutes
7. Clear browser cache
8. Refresh pages to see changes

### XF Publishing Workflow

```
Edit XF → Save → Publish XF → Clear cache → Changes appear on all pages
```

**Common mistake:** Editing XF but forgetting to publish → changes don't appear

---

## Digital Asset Manager (DAM) Strategy

### DAM Folder Structure

```
/content/dam/setia/
├── images/
│   ├── hero-backgrounds/    (Hero background images)
│   ├── banners/             (Page banner backgrounds)
│   ├── content-images/      (Content section images)
│   └── diagrams/            (Architecture, strategy diagrams)
├── icons/
│   ├── services/            (Service card icons)
│   └── ui/                  (UI icons, arrows, etc.)
├── logos/
│   ├── partners/            (Partner logos: AWS, Neo4j, IBM, ITS; Microsoft optional)
│   ├── certifications/      (Cert logos: IBM, Oracle, Red Hat, Microsoft)
│   └── brand/               (Setia logo `logo_white.png`, mascot `contact_area.png`)
└── documents/
    ├── presentations/       (Company/technical PDFs)
    └── whitepapers/         (Marketing documents)
```

### Asset Naming Conventions

**Images:**
```
{type}-{descriptor}-{size}.{ext}
Examples:
- hero-blockchain-1920x1080.jpg
- icon-cloud-64x64.svg
- diagram-platform-architecture.svg
```

**Documents:**
```
{document-type}-{name}-{version}.{ext}
Examples:
- presentation-company-overview-v2.pdf
- whitepaper-blockchain-security-2026.pdf
```

### Asset Requirements

**Hero Background Images:**
- Format: JPEG (compressed) or WebP
- Dimensions: 1920x1080 minimum
- File size: < 500KB
- DPI: 72

**Content Images:**
- Format: JPEG or PNG (PNG for transparency)
- Dimensions: 1200x800 minimum
- File size: < 300KB

**Icons:**
- Format: SVG (preferred) or PNG
- Dimensions: 64x64 or scalable
- Background: Transparent
- Style: Consistent (all line or all filled)

**Logos:**
- Format: SVG (preferred) or PNG
- Background: Transparent
- High resolution (300 DPI if raster)
- Maintain aspect ratio

**Documents:**
- Format: PDF (preferred)
- File size: < 10MB
- Include cover page with branding
- Ensure links work if embedded

### Uploading Assets to DAM

**Step-by-step:**

1. Navigate to: `Assets Console` → `/content/dam/setia`
2. Navigate to appropriate folder (images/, icons/, etc.)
3. Click: `Create` → `Files`
4. Drag and drop files OR browse to select
5. Wait for upload to complete
6. Click on uploaded asset
7. Fill in metadata:
   - **Title:** Descriptive name
   - **Description:** What the asset shows
   - **Tags:** Searchable keywords
   - **Alt Text:** For images (accessibility)
8. Save metadata
9. Publish asset (if needed on publish instance)

**Bulk Upload:**
- Select multiple files at once
- Upload to single folder
- Metadata can be added later

---

## Page Creation Workflow

### Creating a New Page

**Step 1: Navigate to Sites Console**
- URL: `http://localhost:4502/sites.html/content/setia/us/en`
- Or: `Sites` → `Setia` → `US` → `EN`

**Step 2: Create Page**
1. Click: `Create` → `Page`
2. Select template:
   - `Setia Landing Page Template` (for Home)
   - `Setia Content Page Template` (for others)
3. Click: `Next`

**Step 3: Page Properties**
1. **Title:** Page display name (e.g., "Company")
2. **Name:** URL segment (e.g., "company")
   - Auto-generated from title
   - Can customize (lowercase, hyphens)
3. **Description:** For SEO (150-160 characters)
4. Click: `Create`
5. Click: `Open` to start editing

**Step 4: Add Components**
1. Click `+` icon in editable area
2. Select component from list
3. Component appears on page
4. Click component to configure
5. Fill in dialog fields
6. Click: `Done` (checkmark)

**Step 5: Preview**
1. Click: `Preview` mode (eye icon)
2. View page as visitors will see it
3. Test links and interactions
4. Exit preview mode

**Step 6: Publish**
1. Click: `Page Information` → `Publish Page`
2. Select: `Include referenced assets` (images, etc.)
3. Click: `Publish`
4. Wait for confirmation
5. Page is now live

---

## Publishing Workflow

### Publishing Strategy

**What to Publish:**
- Pages (after content is finalized)
- Experience Fragments (before referencing pages)
- DAM assets (images, documents used on pages)

**When to Publish:**
- After completing page edits
- Before end of business day (daily updates)
- Immediately for urgent changes
- After stakeholder approval

### Publication Methods

**Quick Publish (Most Common):**
1. Select page(s) in Sites console
2. Click: `Quick Publish` in toolbar
3. Page published immediately

**Manage Publication (Scheduled):**
1. Select page(s)
2. Click: `Manage Publication`
3. Choose: `Publish Later`
4. Set date/time
5. Click: `Next` → `Publish`

**Publishing Status Indicators:**
- **Gray dot:** Never published
- **Green dot:** Published, up-to-date
- **Yellow dot:** Published, but modified (needs republish)
- **Red dot:** Unpublished

### Publishing Best Practices

✅ **DO:**
- Publish Experience Fragments before pages
- Include referenced assets when publishing pages
- Preview before publishing
- Publish parent pages before child pages
- Test on preview mode before publishing

❌ **DON'T:**
- Publish pages with Lorem Ipsum placeholder text
- Publish broken links
- Publish without testing on mobile
- Forget to publish XFs (changes won't appear)
- Publish late on Friday (if issues arise, wait until Monday)

---

## Content Quality Checklist

### Before Publishing Any Page

**Content Quality:**
- [ ] All text is final (no placeholders)
- [ ] Spelling and grammar checked
- [ ] Links tested and working
- [ ] Images have alt text
- [ ] No broken images
- [ ] Headings follow hierarchy (H2 → H3 → H4)

**Component Configuration:**
- [ ] All required fields filled
- [ ] Image sizes optimized
- [ ] Card descriptions similar length
- [ ] CTAs have clear button text
- [ ] Download files uploaded and tested

**Responsive Check:**
- [ ] Page previewed on mobile (360px)
- [ ] Page previewed on tablet (768px)
- [ ] Page previewed on desktop (1440px)
- [ ] No horizontal scrolling
- [ ] Touch targets adequate (44px min)

**Accessibility:**
- [ ] Alt text on all images
- [ ] Link text descriptive (not "click here")
- [ ] Sufficient color contrast
- [ ] Form fields labeled (if applicable)

**SEO:**
- [ ] Page title set (< 60 characters)
- [ ] Meta description set (150-160 characters)
- [ ] Headings include keywords
- [ ] Images optimized (< 500KB)

---

## Common Authoring Tasks

### Task 1: Update Text on Existing Page

1. Navigate to page in Sites console
2. Click: `Edit`
3. Click component with text
4. Click: `Configure` (wrench icon)
5. Edit text in dialog
6. Click: `Done`
7. Click: `Page Information` → `Publish Page`

**Time:** 2-3 minutes

### Task 2: Add New Service Card to Grid

1. Edit page
2. Click existing card-grid component
3. Click: `Configure`
4. Scroll to `Cards` multifield
5. Click: `Add` (+ button)
6. Fill in: Icon, Title, Description, Link
7. Click: `Done`
8. Preview
9. Publish

**Time:** 5 minutes

### Task 3: Update Partner Logos

1. Upload new logos to DAM (`/content/dam/setia/logos/partners/`)
2. Edit page with logo-gallery
3. Click logo-gallery component
4. Click: `Configure`
5. Update logo items (add/remove/edit)
6. Select new logo from DAM
7. Click: `Done`
8. Publish

**Time:** 10 minutes (including DAM upload)

### Task 4: Change Hero Background Image

1. Upload new image to DAM (`/content/dam/setia/images/hero-backgrounds/`)
2. Edit Home page
3. Click home-hero component
4. Click: `Configure`
5. Background Image field → Browse DAM
6. Select new image
7. Click: `Done`
8. Preview (make sure text is readable)
9. Publish

**Time:** 5 minutes

### Task 5: Update Header Navigation

1. Navigate to: `Experience Fragments` → `/setia/header`
2. Open: `master` variation
3. Edit navigation component
4. Update links or text
5. Save
6. **Publish the Experience Fragment** (critical!)
7. Wait 1-2 minutes
8. Clear browser cache
9. Verify changes on all pages

**Time:** 10 minutes

---

## Troubleshooting Guide

### Issue: Changes Don't Appear After Publishing

**Possible causes:**
1. **Forgot to publish** → Publish the page
2. **Browser cache** → Hard refresh (Ctrl+Shift+R or Cmd+Shift+R)
3. **Dispatcher cache** → Clear dispatcher cache or wait 5 minutes
4. **Published to wrong environment** → Verify publish vs author URL

**Solution:**
- Check publication status (green dot?)
- Hard refresh browser
- Test in incognito/private window
- Ask admin to clear dispatcher cache

### Issue: Experience Fragment Changes Don't Appear

**Possible causes:**
1. **Forgot to publish XF** → Most common!
2. **Published page but not XF** → XF must be published separately
3. **Cache not cleared** → Dispatcher cache holding old version

**Solution:**
1. Go to XF console
2. Select XF (header or footer)
3. Click: `Quick Publish`
4. Wait 2 minutes
5. Hard refresh pages

### Issue: Image Doesn't Display

**Possible causes:**
1. **Image not published** → Publish asset from DAM
2. **Wrong path** → Verify DAM path is correct
3. **Image too large** → AEM may reject large files
4. **File format unsupported** → Use JPG, PNG, GIF, SVG only

**Solution:**
- Check image exists in DAM at specified path
- Verify image is published
- Check file size (< 10MB)
- Try re-uploading image

### Issue: Component Not in Insert Menu

**Possible causes:**
1. **Not allowed in template** → Template policy restricts component
2. **Wrong template** → Using wrong template for page type
3. **Component not deployed** → Developer needs to deploy

**Solution:**
- Check template policies (admin task)
- Verify using correct template
- Contact developer if component missing

---

## Authoring Training Plan

### Training Session 1: AEM Basics (1 hour)

**Topics:**
- Logging into AEM Author
- Sites console navigation
- Creating a page from template
- Adding components via drag-and-drop
- Configuring component dialogs
- Preview mode
- Publishing a page

**Hands-on:** Create a test page with 3 components

### Training Session 2: Component Usage (1.5 hours)

**Topics:**
- When to use each component
- Component best practices
- Uploading assets to DAM
- Using images in components
- Working with card grids
- Accordion sections
- Download lists

**Hands-on:** Build Company page from scratch

### Training Session 3: Advanced Topics (1 hour)

**Topics:**
- Experience Fragments (how to edit)
- Publishing workflow
- Scheduled publishing
- Unpublishing pages
- Page properties and SEO
- Troubleshooting common issues

**Hands-on:** Update header navigation, schedule publish

---

## Content Governance

### Roles and Permissions

**Content Author:**
- Create pages from templates
- Edit page content
- Upload assets to DAM
- Publish pages
- Cannot: Edit templates, change policies, delete Experience Fragments

**Content Admin:**
- All Author permissions
- Edit Experience Fragments
- Manage DAM structure
- Unpublish pages
- Change page properties

**Developer:**
- All Admin permissions
- Edit templates and policies
- Deploy code
- Configure AEM

### Content Review Process

**Before Publishing:**
1. Author creates/edits content
2. Author self-reviews (quality checklist)
3. Peer review (optional, for important pages)
4. Stakeholder approval (for major changes)
5. Author publishes

**After Publishing:**
- Monitor for errors (broken links, images)
- Gather user feedback
- Iterate and improve

---

## Related Documentation

- [Authoring Guidelines](../05-authoring-guidelines.md) - Detailed component usage
- [Website Map](../02-website-map.md) - Page structure reference
- [Design System](../07-design-system.md) - Visual standards
- [DAM Asset Strategy](./08-dam-asset-strategy.md) - Asset management details

---

**Document Version:** 1.0  
**Last Updated:** 2026-05-31  
**Target Audience:** Content Authors, Content Managers
