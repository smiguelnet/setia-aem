# Setia Corporate Website

An institutional corporate website built on **Adobe Experience Manager as a Cloud Service (AEMaaCS)**. The site showcases Setia's services, technology solutions, and partnerships, and drives engagement through call-to-action sections across four pages: **Home**, **Company**, **Services**, and **Solutions**.

- **Platform:** AEM as a Cloud Service
- **Group ID:** `br.com.setia` · **Artifact:** `setia`
- **Version:** 1.0.0-SNAPSHOT
- **AEM SDK:** 2026.5.26309.20260526T180029Z-260500
- **Core Components:** 2.28.0
- **Java:** 11+ · **Maven:** 3.3.9+

## Documentation

Detailed project specifications live in [`docs/`](docs/):

| Doc | Description |
|-----|-------------|
| [01 - Project Overview](docs/01-project-overview.md) | Business objectives, audience, scope, technical requirements |
| [02 - Website Map](docs/02-website-map.md) | Site structure, page breakdown, content repository layout |
| [03 - AEM Implementation Strategy](docs/03-aem-implementation-strategy.md) | Modules, templates, policies, Experience Fragments, workflow |
| [04 - Component Specification](docs/04-component-specification.md) | Detailed specs for all 13 components |
| [05 - Authoring Guidelines](docs/05-authoring-guidelines.md) | Content authoring guide for authors and marketers |
| [06 - Frontend Tech Details](docs/06-frontend-tech-details.md) | Webpack build, ClientLibs, SCSS/JS architecture |
| [07 - Design System](docs/07-design-system.md) | Colors, typography, spacing, component patterns |
| [CLAUDE.md](CLAUDE.md) | AI development guidance |

## Site Structure

```
/content/setia/us/en/
├── home        # Setia Landing Page Template
├── company     # Setia Content Page Template
├── services    # Setia Content Page Template
└── solutions   # Setia Content Page Template
```

Shared **Header** and **Footer** are authored as Experience Fragments under `/content/experience-fragments/setia/` and referenced (locked) in both templates.

## Components

13 reusable components in the **"Setia"** component group, under `/apps/setia/components/`:

**Global:** `page` · `xf-header-reference` · `xf-footer-reference` · `cta-section`
**Layout:** `home-hero` · `page-banner` · `section-heading`
**Content:** `rich-text-section` · `card-grid` · `image-section` · `accordion-section` · `download-list` · `logo-gallery`

Sling Models live under `br.com.setia.core.models.*`. See [04 - Component Specification](docs/04-component-specification.md) for full details.

## Modules

The main parts of the project are:

* **core:** Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets, Sling Models (`br.com.setia.core.models.*`), or request filters.
* **it.tests:** Java based integration tests.
* **ui.apps:** contains the `/apps` (and `/etc`) parts of the project, ie JS&CSS clientlibs, components, and templates.
* **ui.content:** contains the editable templates, policies, Experience Fragment structure, and sample content using the components from `ui.apps`.
* **ui.config:** contains runmode specific OSGi configs for the project.
* **ui.frontend:** the dedicated front-end build mechanism — a webpack project (SCSS + TypeScript) compiled into AEM ClientLibs.
* **ui.tests:** Cypress based UI tests (for other frameworks check [aem-test-samples](https://github.com/adobe/aem-test-samples) repository).
* **ui.apps.structure:** defines the repository structure (`/apps/setia`) that the content packages deploy into.
* **dispatcher:** Dispatcher configuration.
* **all:** a single content package that embeds all of the compiled modules (bundles and content packages) including any vendor dependencies.
* **analyse:** runs analysis on the project which provides additional validation for deploying into AEMaaCS.

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

To build all the modules and deploy the `all` package to a local instance of AEM, run in the project root directory the following command:

    mvn clean install -PautoInstallSinglePackage

Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallSinglePackagePublish

Or alternatively

    mvn clean install -PautoInstallSinglePackage -Daem.port=4503

Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

Or to deploy only a single content package, run in the sub-module directory (i.e `ui.apps`)

    mvn clean install -PautoInstallPackage

### Deploying to a remote / secured instance

For any environment with a non-default password, combine the deploy profile with the `aem-remote` profile, which supplies the Sling credentials. Without it, deploys fall back to `admin/admin` and fail against secured instances:

    mvn clean install -PautoInstallSinglePackage,aem-remote

Define the `aem-remote` profile (host/port and `sling.user` / `sling.password`) in `~/.m2/settings.xml` so credentials never land in the project repo. See [03 - AEM Implementation Strategy → Deployment Profiles](docs/03-aem-implementation-strategy.md#deployment-profiles) for the full configuration.

## Development Workflow

Two complementary flows are used (details in [03 - AEM Implementation Strategy → Development Workflow](docs/03-aem-implementation-strategy.md#development-workflow)):

- **Code → AEM** (components, HTL, Java, CSS/JS): develop in `ui.apps` / `core` / `ui.frontend`, then `mvn clean install -PautoInstallSinglePackage`.
- **AEM → Code** (templates, policies, Experience Fragments): create in the AEM Author UI, then export the `/conf/setia` subtree (and XF structure) into `ui.content` for version control.

> Keep page content and DAM assets in AEM (not Git). Code-controlled elements (components, dialogs, Sling Models, ClientLibs, OSGi configs) deploy from Git into AEM.

### Frontend development

The `ui.frontend` module is a webpack project. For local iteration:

    cd ui.frontend
    npm install
    npm run watch        # rebuild on change

    # in another terminal, deploy when ready
    mvn clean install -PautoInstallSinglePackage

See [06 - Frontend Tech Details](docs/06-frontend-tech-details.md) for the build pipeline, SCSS/JS architecture, and ClientLib categories (`setia.base`, `setia.site`, `setia.grid`).

## Testing

There are three levels of testing contained in the project:

### Unit tests

This show-cases classic unit testing of the code contained in the bundle. To
test, execute:

    mvn clean test

### Integration tests

This allows running integration tests that exercise the capabilities of AEM via
HTTP calls to its API. To run the integration tests, run:

    mvn clean verify -Plocal

Test classes must be saved in the `src/main/java` directory (or any of its
subdirectories), and must be contained in files matching the pattern `*IT.java`.

The configuration provides sensible defaults for a typical local installation of
AEM. If you want to point the integration tests to different AEM author and
publish instances, you can use the following system properties via Maven's `-D`
flag.

| Property              | Description                                         | Default value           |
|-----------------------|-----------------------------------------------------|-------------------------|
| `it.author.url`       | URL of the author instance                          | `http://localhost:4502` |
| `it.author.user`      | Admin user for the author instance                  | `admin`                 |
| `it.author.password`  | Password of the admin user for the author instance  | `admin`                 |
| `it.publish.url`      | URL of the publish instance                         | `http://localhost:4503` |
| `it.publish.user`     | Admin user for the publish instance                 | `admin`                 |
| `it.publish.password` | Password of the admin user for the publish instance | `admin`                 |

The integration tests in this project use the [AEM Testing
Clients](https://github.com/adobe/aem-testing-clients) and showcase some
recommended [best
practices](https://github.com/adobe/aem-testing-clients/wiki/Best-practices) to
be put in use when writing integration tests for AEM.

### UI tests

They will test the UI layer of the AEM application using the Cypress framework.

Check the README file in the `ui.tests` module for more details.

Examples of UI tests in different frameworks can be found here: https://github.com/adobe/aem-test-samples

## Static Analysis

The `analyse` module performs static analysis on the project for deploying into AEMaaCS. It is automatically
run when executing

    mvn clean install

from the project root directory. Additional information about this analysis and how to further configure it
can be found here https://github.com/adobe/aemanalyser-maven-plugin

## ClientLibs

The frontend module is made available using an [AEM ClientLib](https://helpx.adobe.com/experience-manager/6-5/sites/developing/using/clientlibs.html). When executing the NPM build script, the app is built and the [`aem-clientlib-generator`](https://github.com/wcm-io-frontend/aem-clientlib-generator) package takes the resulting build output and transforms it into such a ClientLib.

A ClientLib will consist of the following files and directories:

- `css/`: CSS files which can be requested in the HTML
- `css.txt` (tells AEM the order and names of files in `css/` so they can be merged)
- `js/`: JavaScript files which can be requested in the HTML
- `js.txt` (tells AEM the order and names of files in `js/` so they can be merged
- `resources/`: Source maps, non-entrypoint code chunks (resulting from code splitting), static assets (e.g. icons), etc.

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html
