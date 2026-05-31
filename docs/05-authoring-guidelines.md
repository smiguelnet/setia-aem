# 05 - Authoring Guidelines

## Introduction

This document provides content authoring guidelines for the Setia AEM website. It is intended for content authors, marketers, and anyone responsible for creating and managing content in the AEM Author environment.

---

## Accessing AEM Author

**URL:** `http://localhost:4502` (local) or your organization's AEM author URL  
**Login:** Use your assigned credentials

### Main Authoring Interface

**Sites Console:** `http://localhost:4502/sites.html/content/setia`

From here you can:
- View site structure
- Create new pages
- Edit existing pages
- Manage page properties
- Publish/unpublish pages

---

## Creating a New Page

### Step-by-Step Process

1. Navigate to Sites console: `/sites.html/content/setia/us/en`
2. Click **Create** → **Page**
3. Select appropriate template:
   - **Setia Landing Page Template** - For home page only
   - **Setia Content Page Template** - For Company, Services, Solutions
4. Fill in page properties:
   - **Title** (required) - Display name
   - **Name** (required) - URL segment (auto-generated from title)
   - **Description** - For SEO
5. Click **Create**
6. Click **Open** to start editing

---

## Page Editor Interface

### Editor Modes

**Edit Mode**  
- Default mode for adding and configuring components
- Drag components from side panel
- Click components to configure

**Preview Mode**  
- View page as it will appear to visitors
- No editing controls visible

**Layout Mode**  
- Adjust responsive grid breakpoints
- Resize components in grid

**Targeting Mode**  
<!-- TODO SECTION: Document personalization/targeting if implemented -->
- Configure personalization (if enabled)

---

## Adding Components to a Page

### Method 1: Drag and Drop
1. Open side panel (click left edge icon)
2. Switch to **Components** tab
3. Find component under "Setia" group
4. Drag component to desired position on page
5. Drop into the blue highlighted drop zone

### Method 2: Insert Component
1. Click the **+** icon in an editable container
2. Select component from list
3. Component is inserted at that position

### Allowed Components by Template

The lists below are **template policies**, not code rules — administrators configure them in the Template editor (Policy mode). If a component is missing from the insert menu, the policy needs updating. See [03 → Template & Policy Configuration](./03-aem-implementation-strategy.md#template--policy-configuration) for the underlying mechanics.

**Setia Landing Page Template** (Home page):

Locked by template structure (cannot be moved/deleted):
- Header XF Reference
- Home Hero
- CTA Section
- Footer XF Reference

Allowed in the editable Content Container (per policy):
- Section Heading
- Rich Text Section
- Card Grid
- CTA Section

**Setia Content Page Template** (Company, Services, Solutions):

Locked by template structure:
- Header XF Reference
- Page Banner
- CTA Section
- Footer XF Reference

Allowed in the editable Content Container (per policy):
- Section Heading
- Rich Text Section
- Card Grid
- Image Section
- Accordion Section
- Download List
- Logo Gallery
- CTA Section

---

## Component Authoring Guidelines

### Header and Footer

**Type:** Experience Fragment References (locked in template)

**Notes:**
- Cannot be edited directly on pages
- Must edit the Experience Fragment itself
- Changes apply to all pages automatically

**To Edit:**
1. Navigate to Experience Fragments console
2. Open `/content/experience-fragments/setia/header` or `/footer`
3. Edit the master variation
4. Publish the Experience Fragment

---

### Home Hero Component

**Used On:** Home page only

**Fields:**
- **Main Title** (required) - Large headline text
- **Subtitle** - Supporting text below title
- **Background Image** - Browse DAM for image

**Service Cards (3 required):**
For each card:
- **Icon** - Browse DAM for icon image
- **Title** - Service name
- **Description** - Brief description (2-3 sentences)
- **Link URL** - Where clicking card goes
- **Link Text** - Button text (e.g., "Learn More")

**Best Practices:**
- Keep main title under 60 characters
- Use high-resolution background image (min 1920x1080)
- Keep card descriptions consistent length
- Use clear, action-oriented link text

**Example Content:**
```
Main Title: "Your global software outsourcing partner"
Subtitle: "Leading you through the journey to transform your ideas into successful digital products"

Card 1:
  Title: "Blockchain, Smart Contracts and NFTs"
  Description: "Leverage our blockchain expertise to build secure, decentralized solutions..."
```

---

### Page Banner Component

**Used On:** Company, Services, Solutions pages

**Fields:**
- **Title** (required) - Page heading
- **Subtitle** - Supporting text
- **Background Image** (optional) - Browse DAM

**Best Practices:**
- Keep title concise (40-60 characters)
- Subtitle should provide context (1-2 sentences)
- Background image optional; default styling is dark blue

**Example Content:**
```
Title: "We are Setia"
Subtitle: "Delivering Innovative and Quality Solutions"
```

---

### Section Heading Component

**Used On:** All pages

**Fields:**
- **Title** (required) - Section title
- **Subtitle** - Additional context
- **Heading Level** - H2, H3, or H4 (default: H2)
- **Alignment** - Left, Center, or Right (default: Center)

**Best Practices:**
- Use H2 for main section headings
- Use H3 for subsections
- Keep titles clear and descriptive
- Center alignment for major sections, left for subsections

**Example Content:**
```
Title: "What we do"
Subtitle: "Digital Products for Future-Ready Businesses"
Heading Level: H2
Alignment: Center
```

---

### Rich Text Section Component

**Used On:** All pages

**Fields:**
- **Title** (optional) - Section title
- **Rich Text** (required) - Main content with formatting
- **Include List** (checkbox) - Add structured list
- **List Items** (if list enabled) - Bullet points

**Rich Text Editor Tools:**
- Bold, Italic, Underline
- Headings (H2-H6)
- Bulleted and numbered lists
- Links (internal and external)
- Images (from DAM)
- Tables

**Best Practices:**
- Use rich text editor for formatting (don't paste from Word)
- Keep paragraphs short (3-4 sentences)
- Use lists for readability
- Add links to relevant internal pages or resources
- Optimize images before uploading to DAM

**Example Content:**
```
Title: "Our Story"
Rich Text: "Founded in 2005, Setia has been a pioneer and successful company working on a wide range of clients in a variety of fields for more than 17 years..."
```

---

### Card Grid Component

**Used On:** All pages

**Fields:**
- **Columns** - 2, 3, or 4 (default: 3)
- **Gap Size** - Small, Medium, Large (default: Medium)

**Card Items (multifield):**
For each card:
- **Icon/Image** - Browse DAM
- **Title** (required) - Card heading
- **Description** - Card content
- **Link URL** (optional) - Destination page
- **Link Text** - Button text (default: "Learn More")
- **Background Color** (optional) - Custom color

**Best Practices:**
- Use 3 columns for desktop (responsive auto-adjust for mobile)
- Keep all card descriptions similar length
- Use consistent icon style (all line icons or all filled)
- Provide link URLs for actionable cards
- Default theme is **Green** (brand color #7ED321); switch via the Styles toolbar (Theme group: Green / Dark Blue / Neutral)

**Common Use Cases:**
- Service offerings (6 cards, 3 columns)
- Feature highlights (4 cards, 2 columns)
- Technology stack (4 cards, 4 columns)

**Example Card:**
```
Icon: /content/dam/setia/icons/blockchain.svg
Title: "Blockchain and Web3"
Description: "Build secure, decentralized applications leveraging blockchain technology..."
Link URL: /content/setia/us/en/services/blockchain
Link Text: "Explore Blockchain"
Background Color: #7ED321
```

---

### Image Section Component

**Used On:** Services, Solutions pages

**Fields:**
- **Title** - Section title
- **Subtitle** - Supporting text
- **Description** - Rich text content
- **Image** (required) - Browse DAM
- **Image Alt Text** (required) - Accessibility description
- **Image Position** - Left or Right (default: Right)
- **Image Width** - 40%, 50%, or 60% (default: 50%)

**Best Practices:**
- Use high-quality images (min 800px width)
- Always provide descriptive alt text
- Balance text-to-image ratio (50/50 usually works best)
- Use diagrams/infographics for technical content
- SVG format preferred for diagrams (scales well)

**Example Content:**
```
Title: "Our Strategy: Component-Driven Acceleration"
Subtitle: "Fast and effective development to improve and accelerate your business"
Image: /content/dam/setia/diagrams/component-architecture.svg
Image Alt Text: "Component-driven architecture diagram showing business blocks and technology layers"
Image Position: Right
Image Width: 50%
```

---

### Accordion Section Component

**Used On:** Services page (expandable content)

**Fields:**
- **Title** (optional) - Section title above accordion

**Accordion Items (multifield):**
For each item:
- **Item Title** (required) - Collapsed heading
- **Item Content** (required) - Rich text that expands
- **Expanded by Default** (checkbox) - Open on page load

**Best Practices:**
- Use descriptive, question-like titles ("How do we approach projects?")
- Keep item titles consistent length
- First item can be expanded by default
- Rich content can include lists, links, images
- Limit to 5-7 items for usability

**Example Accordion:**
```
Section Title: "and how..."

Item 1:
  Title: "Business and technical alignment"
  Content: "We align business goals with technical implementation..."
  Expanded: true

Item 2:
  Title: "Product vision and roadmap"
  Content: "Defining clear product vision and strategic roadmap..."
  Expanded: false
```

---

### Download List Component

**Used On:** Company, Services pages

**Fields:**
- **Title** (optional) - Section title

**Download Items (multifield):**
For each file:
- **File Title** (required) - Display name
- **File Description** - What the file contains
- **File Asset** (required) - Browse DAM for PDF/DOC/etc
- **File Type Icon** - PDF, DOC, XLS, PPT, ZIP (auto-detected)
- **File Size** - e.g., "2.5 MB" (auto-calculated or manual)

**Best Practices:**
- Upload files to DAM first before adding to component
- Use descriptive file names
- Keep file sizes reasonable (<10MB when possible)
- Always provide description
- Group related downloads together

**Example Download:**
```
Title: "Company Resources"

Item 1:
  File Title: "Company Presentation"
  Description: "Overview of Setia's capabilities and services"
  File Asset: /content/dam/setia/documents/setia-company-presentation.pdf
  File Size: "3.2 MB"

Item 2:
  File Title: "Technical Capabilities"
  Description: "Detailed technical stack and methodologies"
  File Asset: /content/dam/setia/documents/technical-presentation.pdf
  File Size: "2.8 MB"
```

---

### Logo Gallery Component

**Used On:** Company, Services pages

**Fields:**
- **Title** (optional) - Section title (e.g., "Partners")

**Logo Items (multifield):**
For each logo:
- **Logo Image** (required) - Browse DAM
- **Alt Text** (required) - Company name
- **Link URL** (optional) - Partner website
- **Logo Width** (optional) - e.g., "120px"

**Best Practices:**
- Upload high-resolution logo images (SVG preferred)
- Use consistent logo sizing (120-150px width typical)
- Provide company name as alt text
- Link to partner website if public relationship
- Logos should have transparent backgrounds
- Organize logos in logical order (alphabetical or by importance)

**Example Logo:**
```
Title: "Partners"

Logo 1:
  Logo Image: /content/dam/setia/logos/aws-logo.svg
  Alt Text: "Amazon Web Services"
  Link URL: https://aws.amazon.com
  Logo Width: "120px"

Logo 2:
  Logo Image: /content/dam/setia/logos/neo4j-logo.svg
  Alt Text: "Neo4j"
  Link URL: https://neo4j.com
  Logo Width: "100px"
```

---

### CTA Section Component

**Used On:** All pages (typically at bottom)

**Fields:**
- **Title** (required) - Call-to-action heading
- **Subtitle** - Supporting text
- **Button Text** - Action button label (default: "Get in touch")
- **Button Link** - Where button goes (contact page, form, etc.)
- **Include Mascot** (checkbox) - Show mascot character (default: true)
- **Mascot Image** - Browse DAM (shown when Include Mascot = true)
- **Mascot Alt Text** - Accessibility description (shown when Include Mascot = true)

**Theme:** Background color is set via the Styles toolbar (Theme group: Light / Dark / Accent), not as a per-instance color picker. Default is Light.

**Best Practices:**
- Keep title action-oriented and concise
- Use clear, specific button text ("Contact Us", "Get Started", "Schedule Demo")
- Standard CTA appears on every page for consistency
- Mascot adds personality and visual interest

**Example Content:**
```
Title: "Ready to tackle your next challenge?"
Subtitle: "Let us help you build your next great digital product"
Button Text: "Get in touch"
Button Link: /content/setia/us/en/contact
Include Mascot: true
Mascot Image: /content/dam/setia/images/mascot.png
Mascot Alt Text: "Setia mascot character with laptop"
```

---

## Working with the DAM (Digital Asset Manager)

### Accessing DAM

**URL:** `http://localhost:4502/assets.html/content/dam/setia`

### Uploading Assets

1. Navigate to appropriate folder in DAM
2. Click **Create** → **Files**
3. Drag and drop files or browse to select
4. Files are uploaded and processed

### Recommended Folder Structure

```
/content/dam/setia/
├── images/
│   ├── hero-backgrounds/
│   ├── banners/
│   ├── content-images/
│   └── diagrams/
├── icons/
│   ├── services/
│   └── ui/
├── logos/
│   ├── partners/
│   └── technologies/
├── documents/
│   ├── presentations/
│   └── whitepapers/
└── videos/ (future)
```

### Asset Requirements

**Images:**
- Format: JPG (photos), PNG (graphics with transparency), SVG (logos/icons)
- Max file size: 5MB for web images
- Hero backgrounds: 1920x1080 minimum
- Content images: 1200px width minimum
- Compress before upload

**Documents:**
- Format: PDF preferred
- Max file size: 10MB
- Include cover page with branding
- Ensure links work if embedded

**Icons/Logos:**
- Format: SVG preferred (scales perfectly)
- PNG fallback: 512x512 minimum
- Transparent background
- Consistent style across icon set

### Asset Metadata

Always fill in:
- **Title** - Descriptive name
- **Description** - What the asset shows
- **Tags** - Searchable keywords
- **Alt Text** - Accessibility description

---

## Page Properties

Access via: Page → **Properties** in toolbar

### Basic Tab
- **Title** (required) - Page display name
- **Subtitle** - Additional context
- **Description** - For SEO (150-160 characters)
- **On Time / Off Time** - Schedule publish/unpublish
- **Vanity URL** - Custom short URL

### Advanced Tab
- **Language** - Content language (en)
- **Redirect** - Redirect to another page
- **Configuration** - Template configuration path

### Thumbnail Tab
- **Page Image** - Thumbnail for page lists

### Social Media Tab
<!-- TODO SECTION: Document Open Graph and social media metadata strategy -->
- Open Graph properties (future implementation)
- Twitter Card properties (future implementation)

### SEO Tab
<!-- TODO SECTION: Document SEO metadata requirements and best practices -->
- Meta keywords
- Canonical URL
- robots directives

---

## Publishing Workflow

### Publishing a Page

1. Select page in Sites console
2. Click **Manage Publication** in toolbar
3. Choose options:
   - **Publish** - Make live
   - **Unpublish** - Remove from live site
   - **Publish Later** - Schedule
4. Select options:
   - Include child pages
   - Include referenced assets
   - Include modified pages
5. Click **Next** → **Publish**

### Publishing Status

- **Gray dot** - Never published
- **Green dot** - Published, no changes
- **Yellow dot** - Published, but modified (needs republish)
- **Red dot** - Unpublished

### Quick Publish

For immediate publishing:
1. Select page
2. Click **Quick Publish** in toolbar
3. Page and references published immediately

### Managing Publication

- Always publish Experience Fragments before referencing pages
- Publish parent pages before child pages
- Include referenced assets (images, documents) in publication
- Preview on publish instance before public launch

---

## Content Best Practices

### Writing for the Web

**Keep it Scannable:**
- Use short paragraphs (3-4 sentences)
- Break content with headings
- Use bullet points and lists
- Highlight key information

**Be Concise:**
- Get to the point quickly
- Remove unnecessary words
- One idea per paragraph
- Active voice preferred

**Use Clear Headings:**
- Descriptive, not clever
- Include keywords
- Logical hierarchy (H2 → H3 → H4)

### SEO Considerations

<!-- TODO SECTION: Expand SEO guidelines with specific requirements -->

**Page Titles:**
- 50-60 characters
- Include primary keyword
- Unique for each page
- Brand name at end

**Meta Descriptions:**
- 150-160 characters
- Compelling summary
- Include call-to-action
- Unique for each page

**Content:**
- Use keywords naturally
- Internal linking to related pages
- Alt text for all images
- Descriptive anchor text for links

### Accessibility

**Images:**
- Always provide alt text
- Describe what the image shows
- Don't start with "Image of..."
- Empty alt ("") for decorative images

**Links:**
- Use descriptive link text (not "click here")
- Indicate if link opens new window
- Test all links before publishing

**Headings:**
- Use semantic hierarchy (don't skip levels)
- One H1 per page (page title)
- H2 for main sections, H3 for subsections

**Color and Contrast:**
- Ensure sufficient contrast (WCAG AA minimum)
- Don't rely on color alone to convey information

---

## Common Authoring Tasks

### Creating the Home Page

1. Create page with "Setia Landing Page Template"
2. Add components in order:
   - Home Hero (configure with 3 service cards)
   - Section Heading ("Delivering innovative...")
   - Section Heading ("What we do")
   - Card Grid (6 service cards)
   - Section Heading ("Engagement...")
   - Rich Text Section
   - Section Heading (CTA title)
   - CTA Section
3. Configure each component with content
4. Preview before publishing
5. Publish page and all referenced assets

### Creating Inner Pages (Company, Services, Solutions)

1. Create page with "Setia Content Page Template"
2. Add Page Banner at top
3. Build content sections:
   - Section Heading + Rich Text Section (for each content block)
   - Card Grid (for services/features)
   - Image Section (for diagrams/visuals)
   - Accordion Section (for expandable content)
   - Download List (for resources)
   - Logo Gallery (for partners)
   - CTA Section (at bottom)
4. Preview on desktop and mobile
5. Publish

### Editing Experience Fragments (Header/Footer)

1. Navigate to Experience Fragments console
2. Open `/content/experience-fragments/setia/header` (or footer)
3. Edit the `master` variation
4. Make changes to content
5. **Publish the Experience Fragment** (important!)
6. Changes appear on all pages referencing it
7. Clear dispatcher cache if changes don't appear

### Updating Existing Content

1. Navigate to page in Sites console
2. Click **Edit** to open page editor
3. Click component to configure
4. Make changes in dialog
5. Save changes
6. Preview changes
7. Publish page

---

## Troubleshooting

### Component Not Appearing in Insert Menu
- Check that component is in "Setia" group
- Verify component is allowed in template policy
- Try refreshing page editor

### Dialog Changes Not Saving
- Check for required fields (marked with *)
- Ensure proper data format (e.g., valid URLs)
- Check browser console for JavaScript errors

### Image Not Displaying
- Verify image exists in DAM at specified path
- Check image file format is supported (JPG, PNG, GIF, SVG)
- Ensure image is published (if on publish instance)
- Check file permissions

### Page Looks Different on Publish
- Clear dispatcher cache
- Verify all components are published
- Check that Experience Fragments are published
- Ensure ClientLibs are deployed
- Test in incognito/private browser window

### Changes Not Visible After Publishing
- Clear dispatcher cache
- Hard refresh browser (Ctrl+Shift+R or Cmd+Shift+R)
- Check publication status (green dot)
- Verify references were published with page

---

## Getting Help

### Documentation Resources
- [AEM Documentation](https://experienceleague.adobe.com/docs/experience-manager-cloud-service.html)
- Project documentation in `/docs/` folder
- Component specifications: [04-component-specification.md](./04-component-specification.md)

### Support Contacts
<!-- TODO SECTION: Add project-specific support contacts -->
- Development Team: [contact info]
- AEM Administrator: [contact info]
- Content Lead: [contact info]

---

## Related Documentation

- [02 - Website Map](./02-website-map.md)
- [04 - Component Specification](./04-component-specification.md)
- [07 - Design System](./07-design-system.md)
