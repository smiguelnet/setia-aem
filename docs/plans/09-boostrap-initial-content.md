# Task: Bootstrap Initial Setia Website Content in AEM

Using the existing AEM components, templates, and screenshots, create the initial authored content for the Setia website.

## Goal

Make the local AEM pages visually match the current production website as closely as possible.

## Pages to Bootstrap

Create and configure:

- `/content/setia/us/en/home`
- `/content/setia/us/en/company`
- `/content/setia/us/en/services`
- `/content/setia/us/en/solutions`

## Required Work

1. Create the pages using the correct editable templates.
2. Add the required components to each page in the correct order.
3. Populate all component dialogs with initial content from the screenshots/specs.
4. Upload or reference required DAM assets:
    - hero background (./assets/images/bg-home.jpg)
    - logo (./assets/images/logo_white.png)
    - service icons (./assets/images/components.png)
    - partner logos (./assets/images/partners/*)
    - diagrams (./assets/images/strategy.jpg)
    - mascot/footer (./assets/images/contact_area.png)
5. Configure Header and Footer Experience Fragments.
6. Ensure navigation links point to the correct pages.
7. Export the bootstrapped content into `ui.content` so it can be version-controlled and reproduced.

## Expected Output

The agent should update/create:

- `/content/setia/...` page content
- `/content/experience-fragments/setia/header`
- `/content/experience-fragments/setia/footer`
- `/content/dam/setia/...` assets if available
- `/conf/setia/...` if template/policy changes are needed
- `ui.content` package filters for the exported content

### Expected Output - Screenshots

- **Home:** [images/website_home.png](./images/website_home.png)
- **Company:** [images/website_company.png](./images/website_company.png)
- **Services:** [images/website_services.png](./images/website_services.png)
- **Solutions:** [images/website_solutions.png](./images/website_solutions.png)

## Validation

After deployment, the website should not show empty editable areas only. It should render a first complete version of:

- Home
- Company
- Services
- Solutions

matching the provided screenshots (ref: Expected Output - Screenshots).