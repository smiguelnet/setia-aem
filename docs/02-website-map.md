# 02 - Website Map

## Site Structure

```
/content/setia/
└── us/en/
    ├── home
    ├── company
    ├── services
    └── solutions
```

## Navigation Structure

### Primary Navigation
- Home
- Company
- Services
- Solutions

### Global Elements
- Header (fixed on scroll)
- Footer with contact information

---

## Page Breakdown

### 1. Home Page

**Template:** Setia Landing Page Template  
**URL:** `/content/setia/us/en/home`  
**Screenshot:** [images/website_home.png](./images/website_home.png)

#### Page Sections

1. **Header** (Experience Fragment)
   - Logo
   - Navigation menu
   - Fixed on scroll behavior

2. **Hero Banner**
   - Main title: "Your global software outsourcing partner"
   - Subtitle
   - 3 Service Highlight Cards:
     - Blockchain, Smart Contracts and NFTs
     - Cloud-native Software Development
     - Digital Transformation and Industry 4.0

3. **Section 1: Tagline**
   - Title: "Delivering innovative and Quality Solutions"
   - Subtitle description

4. **Section 2: What We Do**
   - Title: "What we do"
   - Subtitle: "Digital Products for Future-Ready Businesses"
   - Card Grid (6 cards in 2 rows):
     - Blockchain and Web3
     - Cloud Solutions
     - Digital Products and Applications
     - Digital Transformation
     - Embedded Application Development
     - Digital Products for Industry 4.0

5. **Section 3: Engagement**
   - Title: "Engagement. Gamification as a key element"
   - Subtitle
   - Rich text description

6. **Section 4: CTA**
   - Title: "Ready to tackle your next challenge?"
   - Subtitle

7. **Footer** (Experience Fragment)
   - "Get in touch" section
   - Contact information
   - Mascot illustration
   - Copyright information

---

### 2. Company Page

**Template:** Setia Content Page Template  
**URL:** `/content/setia/us/en/company`  
**Screenshot:** [images/website_company.png](./images/website_company.png)

#### Page Sections

1. **Header** (Experience Fragment)

2. **Banner Section**
   - Title: "We are Setia"
   - Subtitle: "Delivering Innovative and Quality Solutions"

3. **Section 1: Our Story**
   - Title: "Our Story"
   - Subtitle
   - Rich text content

4. **Section 2: What Makes Setia Different**
   - Title: "What makes Setia different?"
   - Subtitle: "We are the ally for your business, industry and profitability"
   - Rich text introduction
   - List items:
     - COMPLIANCE-FOCUSED APPROACH
     - WE BUILD INNOVATIVE, HIGH-AVAILABILITY, QUALITY SYSTEMS
     - THE BEST TECHNOLOGY AVAILABLE TO MEET THE PROJECT'S BUSINESS CHALLENGES
     - DEDICATED PROJECT COORDINATION AND DELIVERY SUPPORT
     - INVOLVEMENT AT DIFFERENT LEVELS
   - **Business Experience** subsection
   - **Download Files:**
     - Company Presentation
     - Technical Presentation
     - PDF file

5. **Section 3: Our Services**
   - Title: "Our services"
   - Subtitle: "Digital Products for Future-Ready Businesses"
   - Card Grid (6 service cards)

6. **Section 4: Partners**
   - Title: "Partners"
   - Logo Gallery:
     - AWS
     - Neo4j
     - SAP
     - ITS

7. **Section 5: CTA**
   - Title: "Ready to tackle your next challenge?"
   - Subtitle

8. **Footer** (Experience Fragment)

---

### 3. Services Page

**Template:** Setia Content Page Template  
**URL:** `/content/setia/us/en/services`  
**Screenshot:** [images/website_services.png](./images/website_services.png)

#### Page Sections

1. **Header** (Experience Fragment)

2. **Banner Section**
   - Title: "We are a world-class Software Developer company"

3. **Section 1: Overview**
   - Title: "Turn Opportunities into Results using Technology"
   - Subtitle
   - Rich text content

4. **Section 2: What We Do**
   - Title: "What we do"
   - Subtitle: "Digital Products for Future-Ready Businesses"
   - Card Grid (6 service cards)

5. **Section 3: And How**
   - Title: "and how..."
   - Icon row (5 icons representing process)
   - Rich text explanation
   - Accordion/List items:
     - Business and technical alignment
     - Product vision and roadmap
     - Sprint Planning and Execution

6. **Section 4: Our Strategy**
   - Title: "Our Strategy: Component-Driven Acceleration"
   - Subtitle
   - Strategy Diagram/Image
     - Shows component architecture flow
     - Business blocks, technology components
     - Integration layers

7. **Section 5: Partners**
   - Title: "Partners"
   - Logo Gallery (AWS, Neo4j, SAP, ITS)

8. **Section 6: CTA**
   - Title: "Ready to tackle your next challenge?"
   - Subtitle

9. **Footer** (Experience Fragment)

---

### 4. Solutions Page

**Template:** Setia Content Page Template  
**URL:** `/content/setia/us/en/solutions`  
**Screenshot:** [images/website_solutions.png](./images/website_solutions.png)

#### Page Sections

1. **Header** (Experience Fragment)

2. **Banner Section**
   - Title: "Blockchain"
   - Subtitle: "Authenticity and Ownership"

3. **Section 1: Overview**
   - Title: "We are on top of today's leading technologies"
   - Subtitle: "Leverage Secured Blockchain Services from Setia"
   - Rich text content

4. **Section 2: Platform Diagram**
   - Title: "Digital Assets Management Platform"
   - Subtitle: "Business blocks as editable components to empower and accelerate your business"
   - Platform Architecture Diagram:
     - 5 API layers (S-API, S-Gateway, S-Notary, S-View, S-Check)
     - Component blocks showing features
     - Repository layers (Versioning, Local Ledger, Blockchain)

5. **Section 3: Feature Cards**
   - Grid of feature cards (6 cards):
     - Built-in Wallet
     - Encrypted Assets
     - Marketplace and Digital Collections
     - Smart Factory
     - Transparency Management
     - Item Tracelaw Tokens

6. **Section 4: CTA**
   - Title: "Ready to tackle your next challenge?"
   - Subtitle

7. **Footer** (Experience Fragment)

---

## Shared Components Across Pages

### Experience Fragments
1. **Header**
   - Logo (top left)
   - Navigation: Company, Services, Solutions
   - Dark blue background
   - Fixed positioning on scroll

2. **Footer**
   - "Get in touch" heading
   - Contact email
   - Mascot illustration (character with laptop)
   - Copyright notice
   - Dark blue background

### Recurring Sections
- **CTA Section**: Appears on all pages with consistent styling
- **Card Grid**: Reused on Home, Company, Services pages
- **Logo Gallery/Partners**: Appears on Company and Services pages
- **Page Banner**: Appears on Company, Services, Solutions pages

---

## Content Repository Structure

```
/content/setia/us/en/
├── home/
│   └── jcr:content/
│       ├── root/
│       │   ├── responsivegrid/
│       │   │   ├── xf-header-reference
│       │   │   ├── home-hero
│       │   │   ├── section-heading
│       │   │   ├── card-grid
│       │   │   ├── rich-text-section
│       │   │   ├── cta-section
│       │   │   └── xf-footer-reference
│
├── company/
│   └── jcr:content/
│       ├── root/
│       │   ├── responsivegrid/
│       │   │   ├── xf-header-reference
│       │   │   ├── page-banner
│       │   │   ├── rich-text-section (multiple)
│       │   │   ├── download-list
│       │   │   ├── card-grid
│       │   │   ├── logo-gallery
│       │   │   ├── cta-section
│       │   │   └── xf-footer-reference
│
├── services/
│   └── jcr:content/
│       ├── root/
│       │   ├── responsivegrid/
│       │   │   ├── xf-header-reference
│       │   │   ├── page-banner
│       │   │   ├── rich-text-section
│       │   │   ├── card-grid
│       │   │   ├── accordion-section
│       │   │   ├── image-section
│       │   │   ├── logo-gallery
│       │   │   ├── cta-section
│       │   │   └── xf-footer-reference
│
└── solutions/
    └── jcr:content/
        ├── root/
        │   ├── responsivegrid/
        │   │   ├── xf-header-reference
        │   │   ├── page-banner
        │   │   ├── rich-text-section
        │   │   ├── image-section
        │   │   ├── card-grid
        │   │   ├── cta-section
        │   │   └── xf-footer-reference
```

---

## URL Structure

<!-- TODO SECTION: Define URL naming conventions and redirects -->

**Base URL:** `https://www.setia.com.br` (example)

| Page | Path | Full URL |
|------|------|----------|
| Home | `/` | `https://www.setia.com.br/` |
| Company | `/company` | `https://www.setia.com.br/company` |
| Services | `/services` | `https://www.setia.com.br/services` |
| Solutions | `/solutions` | `https://www.setia.com.br/solutions` |

### Future Expansion
- Additional service detail pages
- Solution detail pages
- Blog/resources section
- Case studies
- Contact form page

---

## SEO and Metadata

<!-- TODO SECTION: Define SEO strategy and metadata requirements -->

### Per-Page Requirements
- Page title
- Meta description
- Open Graph tags
- Canonical URLs
- Structured data (Organization, BreadcrumbList)

### Global SEO Elements
- XML sitemap
- robots.txt
- Schema.org markup
- Social media integration

---

## Related Documentation

- [01 - Project Overview](./01-project-overview.md)
- [04 - Component Specification](./04-component-specification.md)
- [05 - Authoring Guidelines](./05-authoring-guidelines.md)
