# Task: Bootstrap Initial Setia Website Content in AEM

Using the existing AEM components, templates, and screenshots, create the initial authored content for the Setia website.

## Goal

Make the local AEM pages visually match the current production website as closely as possible, then export the result so it is reproducible from source control.

## Pages to Bootstrap

| Page | Path | Template |
|------|------|----------|
| Home | `/content/setia/us/en` | Setia Landing Page Template |
| Company | `/content/setia/us/en/company` | Setia Content Page Template |
| Services | `/content/setia/us/en/services` | Setia Content Page Template |
| Solutions | `/content/setia/us/en/solutions` | Setia Content Page Template |

> **Home page path:** the locale root `/content/setia/us/en` **is** the home page — there is no separate `/home` node. (Earlier revisions of this task said `/content/setia/us/en/home`, which does not exist.) Confirm against [01 — Implementation Plan §7.1](./01-implementation-plan.md#71-home-page).

## Source Assets

All assets ship in [`docs/assets/`](../assets/) (paths below are relative to the repo root). Copy them into the DAM and keep the original filenames. The full source→DAM→component mapping lives in [08 — DAM Asset Strategy](./08-dam-asset-strategy.md#source-assets--dam-mapping).

| Purpose | Source file | DAM destination |
|---------|-------------|-----------------|
| Header logo (white) | `docs/assets/images/logo_white.png` | `/content/dam/setia/logos/brand/logo_white.png` |
| Mascot (CTA + footer) | `docs/assets/images/contact_area.png` | `/content/dam/setia/logos/brand/contact_area.png` |
| Home hero background | `docs/assets/images/bg-home.jpg` | `/content/dam/setia/images/hero-backgrounds/bg-home.jpg` |
| Inner page banner bg | `docs/assets/images/bg-content.jpg` | `/content/dam/setia/images/banners/bg-content.jpg` |
| Section background | `docs/assets/images/FundoAmarelo.jpg` | `/content/dam/setia/images/backgrounds/FundoAmarelo.jpg` |
| Services "and how…" icons | `docs/assets/images/components.png` | `/content/dam/setia/images/content-images/components.png` |
| Services strategy diagram | `docs/assets/images/strategy.jpg` | `/content/dam/setia/images/diagrams/strategy.jpg` |
| Solutions platform diagram | `docs/assets/images/digital-assets.jpg` | `/content/dam/setia/images/diagrams/digital-assets.jpg` |
| Partner logos | `docs/assets/images/partners/parceria_{aws,neo,ibm,its,microsoft}.png` | `/content/dam/setia/logos/partners/` |
| Cert logos | `docs/assets/images/team_certifications/{logo_ibm,logo_oracle,logo_redhat,parceria_microsoft}.png` | `/content/dam/setia/logos/certifications/` |
| Company brochures (PDFs) | `docs/assets/pdf/setia-institucional.pdf`, `metodo-trabalho-setia.pdf`, … | `/content/dam/setia/documents/presentations/` |
| Favicon / PWA set | `docs/assets/favicon/*`, `docs/assets/images/site.webmanifest` | site head / ClientLib `resources/` — see [08 §Favicon & PWA](./08-dam-asset-strategy.md#favicon-and-pwa-assets) |

> **Partners are AWS, Neo4j, IBM, ITS** (Microsoft logo is available but not shown in the current screenshots). **There is no SAP asset** — do not reference SAP.

## Per-Page Content (from screenshots)

Screenshots: see [Expected Output — Screenshots](#expected-output---screenshots) below.

### Home — `/content/setia/us/en` (Landing Page Template)
- **home-hero** (structure-locked, content editable): title "Your global software outsourcing partner"; background `bg-home.jpg`; 3 service cards — *Blockchain, Smart Contracts and NFTs* / *Cloud-Native Software Development* / *Digital Transformation and Industry 4.0*.
- **section-heading**: "Delivering Innovative and Quality Solutions" + "Leading you through the journey to transform your ideas into accessible digital products."
- **section-heading**: "What we do" / "Digital Products for Future-Ready Businesses".
- **card-grid** (6 cards, green theme): Blockchain Application Development, Mobile Application Development, Cloud-native Applications Development, Enterprise Application Development, Enterprise Application Integration, Robotic Process Automation Implementation.
- **rich-text-section**: "Engagement. Gamification as a key element" + descriptive copy.
- **section-heading**: "Ready to tackle your next challenge?" / "Setia can help you build value through innovation."
- **cta-section** (structure-locked): "Get in touch" / `connect@setia.com.br`, mascot `contact_area.png`.

### Company — `/content/setia/us/en/company` (Content Page Template)
- **page-banner** (structure-locked): "We are Setia" / "Delivering Innovative and Quality Solutions"; bg `bg-content.jpg`.
- **rich-text-section**: "Our Story" — "Offering solutions to a wide range of clients in a variety of fields for more than 21 years…"
- **rich-text-section**: "What makes Setia different?" with the differentiator list (Turn opportunities into results; Processes, products and people; Engagement first…) + "Business Experience" cert row (`logo_ibm`, `logo_oracle`, `logo_redhat`, `parceria_microsoft`).
- **download-list**: Institutional Presentation (`setia-institucional.pdf`), Software Development Method (`metodo-trabalho-setia.pdf`), MPS.BR.
- **section-heading**: "Our services" / "Digital Products for Future-Ready Businesses".
- **card-grid** (same 6 services as Home).
- **logo-gallery**: "Partners" — AWS, Neo4j, IBM, ITS.
- **cta-section**.

### Services — `/content/setia/us/en/services` (Content Page Template)
- **page-banner**: "We are a world-class Software Developer Company"; bg `bg-content.jpg`.
- **rich-text-section**: "Turn Opportunities Into Results using Technology" + intro.
- **section-heading** + **card-grid**: "What we do" (6 services).
- **image-section** ("and how…"): icon row `components.png` + intro, followed by an **accordion-section**: *Blockchain Application Development*, *Enterprise Application Development and Integration*, *Gamification Content and Solutions*, *Robotic Process Automation (RPA)*.
- **image-section**: "Our Strategy: Component-driven Acceleration" + diagram `strategy.jpg`.
- **logo-gallery**: Partners (AWS, Neo4j, IBM, ITS).
- **cta-section**.

### Solutions — `/content/setia/us/en/solutions` (Content Page Template)
- **page-banner**: "Blockchain" / "Authenticity and Ownership"; bg `bg-content.jpg`.
- **rich-text-section**: "We are on top of today's leading technologies" / "Leverage Blockchain Outsourcing Services from Setia."
- **image-section**: "Digital Assets Management Platform" + diagram `digital-assets.jpg`.
- **card-grid** (6 feature cards): Built-in Stellar, Securitized Assets, Marketplace and Digital Collections, Rarity Factor, Concurrency Management, Non-Fungible Tokens.
- **cta-section**.

> The exact feature-card wording differs slightly between the website map (doc 02) and the Solutions screenshot (e.g. "Built-in Wallet" vs "Built-in Stellar", "Item Tracelaw Tokens" vs "Non-Fungible Tokens"). Use the **screenshot** wording above and reconcile doc 02 separately.

## Required Work

1. Create the pages using the correct editable templates (table above).
2. Add the required components to each page in the order listed under [Per-Page Content](#per-page-content-from-screenshots).
3. Populate all component dialogs with the initial content above.
4. Upload the [Source Assets](#source-assets) into `/content/dam/setia/...` and reference them.
5. Configure the Header and Footer Experience Fragments (`/content/experience-fragments/setia/us/en/site/{header,footer}`) — and **wire them into both template structures** (see the 🔴 blockers in [01 §2.2](./01-implementation-plan.md#22-footer-experience-fragment)).
6. Ensure navigation links point to the correct pages (Company, Services, Solutions; logo → home root).
7. Export the bootstrapped content into `ui.content` so it is version-controlled and reproducible.

## Expected Output

The task should create/update:

- `/content/setia/...` — page content for all 4 pages
- `/content/experience-fragments/setia/us/en/site/header` and `/footer`
- `/content/dam/setia/...` — assets copied from `docs/assets/`
- `/conf/setia/...` — only if template/policy changes are needed
- `ui.content/src/main/content/META-INF/vault/filter.xml` — filters covering the exported content (see filter-mode guidance in [01 §6.5](./01-implementation-plan.md#65-vault-filter-modes-uicontentfilterxml))

### Expected Output

#### Screenshots
Screenshots live in [`docs/images/`](../images/) (not `docs/assets/`):

- **Home:** [../images/website_home.png](../images/website_home.png)
- **Company:** [../images/website_company.png](../images/website_company.png)
- **Services:** [../images/website_services.png](../images/website_services.png)
- **Solutions:** [../images/website_solutions.png](../images/website_solutions.png)

#### HTML layout
Important: Just to use as a reference
- **Home:** [../html/website_home.html](../html/website_home.html)
- **Company:** [../html/website_company.html](../html/website_company.html)
- **Services:** [../html/website_services.html](../html/website_services.html)
- **Solutions:** [../html/website_solutions.html](../html/website_solutions.html)

## Validation

After deployment, the site must not show only empty editable areas. It should render a first complete version of Home, Company, Services, and Solutions, matching the [screenshots](#expected-output---screenshots):

- [ ] Header (logo + nav) and Footer (Get in touch + mascot) render on every page
- [ ] Home hero shows title + 3 service cards over `bg-home.jpg`
- [ ] Card grids render in the green theme with the 6 services
- [ ] Company download list serves the real PDFs; partner logos (AWS/Neo4j/IBM/ITS) display
- [ ] Services accordion expands/collapses; `strategy.jpg` and `components.png` render
- [ ] Solutions `digital-assets.jpg` platform diagram + 6 feature cards render
- [ ] No archetype-default leftovers (Hello World, Epic Journey teasers, San Jose footer)

## Bootstrap Acceptance Criteria

The bootstrap is **done** only when **all** of these pass:

- [ ] Pages **visually resemble** the reference screenshots (`docs/images/website_*.png`)
- [ ] **No page contains an empty responsive grid** — every editable container has realistic content (see [05 → R14](./05-technical-risks.md))
- [ ] **Navigation works** — header links resolve to Company / Services / Solutions; logo → home root
- [ ] **Header and Footer render** on every page (both XFs wired and published)
- [ ] **Responsive behavior works** at every profile in [07 → Responsive Validation Checklist](./07-responsive-strategy.md#responsive-validation-checklist)
- [ ] **DAM assets resolve correctly** (no broken images; PDFs download)
- [ ] **Lighthouse mobile score > 85** on each page

## Export Requirements

All authored output is part of the deliverable and must be exported to source control:

- [ ] **`ui.content`** — authored pages under `/content/setia/...`
- [ ] **`filter.xml`** — `ui.content/src/main/content/META-INF/vault/filter.xml` covers every exported path (pages, XFs, DAM, conf) with the correct mode (see [01 §6.5](./01-implementation-plan.md#65-vault-filter-modes-uicontentfilterxml))
- [ ] **DAM assets** — binaries under `/content/dam/setia/...`
- [ ] **Experience Fragments** — `/content/experience-fragments/setia/us/en/site/{header,footer}`
- [ ] **Templates** — `/conf/setia/...` **if** template/policy changes were made during bootstrap

> Verify reproducibility: a fresh clone + `mvn clean install -PautoInstallSinglePackage,aem-remote` must recreate the full authored site on a clean instance.

## Visual Validation

- **Screenshot comparison process** — capture each page (Home / Company / Services / Solutions) and diff against `docs/images/website_*.png`; flag layout, color, and spacing deviations.
- **Author vs Publish validation** — confirm each page renders identically in Author preview and on Publish (catches unpublished XFs/assets).
- **Responsive validation** — re-run the [07 checklist](./07-responsive-strategy.md#responsive-validation-checklist) at all device profiles.
- **Spacing / typography verification** — section padding uses the 8px scale; headings follow the responsive type scale; no clipped or overflowing text.

## Related Documentation

- [01 — Implementation Plan](./01-implementation-plan.md) (Phase 7 page creation; §6.5 filter modes)
- [02 — Website Map](../02-website-map.md)
- [08 — DAM Asset Strategy](./08-dam-asset-strategy.md) (full asset mapping)
- [06 — AEM Authoring Strategy](./06-aem-authoring-strategy.md)
