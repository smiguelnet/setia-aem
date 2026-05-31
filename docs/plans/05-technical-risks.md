# Technical Risks - Setia AEM Website

## Risk Assessment Overview

This document identifies technical risks, their impact, probability, and mitigation strategies for the Setia AEM website project.

**Risk Matrix:**
```
Impact vs Probability:
         Low      Medium    High
High     🟡       🟠        🔴
Medium   🟢       🟡        🟠
Low      🟢       🟢        🟡
```

---

## Critical Risks (🔴 High Impact, High Probability)

### R1: Component Complexity Underestimation
**Risk:** Complex components (home-hero, accordion) take longer than estimated  
**Impact:** HIGH - Delays template creation, blocks page development  
**Probability:** HIGH - First-time AEM development, learning curve  
**Timeline Impact:** +3-5 days

**Mitigation Strategies:**
- ✅ Allocate 2 days for home-hero (not 1)
- ✅ Build accordion JavaScript early and test independently
- ✅ Use Sling Models to simplify HTL logic
- ✅ Have senior developer review complex components
- ✅ Build buffer time into schedule (48 hours)
- ✅ Create simple prototype first, then enhance

**Contingency Plan:**
- Simplify home-hero multifield to 3 fixed card fields (not dynamic)
- Use CSS-only accordion as fallback (no JavaScript)
- Deprioritize animations and polish
- Focus on core functionality first

**Monitoring:**
- Track component build time daily
- Flag if any component exceeds 1.5 days

---

### R2: Browser Compatibility Issues
**Risk:** CSS Grid, modern JavaScript not working in older browsers  
**Impact:** HIGH - Site unusable for some users  
**Probability:** MEDIUM - Modern browsers common, but need IE11 support?  
**Timeline Impact:** +2-4 days

**Mitigation Strategies:**
- ✅ Define browser support matrix EARLY (week 1)
- ✅ Use autoprefixer for CSS
- ✅ Test in all target browsers weekly (not just at end)
- ✅ Use feature detection, not browser detection
- ✅ Provide graceful degradation
- ✅ Set up BrowserStack or similar testing tool

**Contingency Plan:**
- Drop IE11 support (discuss with stakeholders)
- Use Flexbox fallback for Grid (broader support)
- Polyfill critical features only
- Progressive enhancement approach

**Action Items:**
- [ ] Define browser support requirements in week 1
- [ ] Set up cross-browser testing tool
- [ ] Add polyfills to ui.frontend if needed
- [ ] Test one component in all browsers before building others

**Monitoring:**
- Test each component in target browsers
- Track browser-specific bugs

---

## High Risks (🟠 High Impact, Medium Probability)

### R3: Performance Issues
**Risk:** Large images, unoptimized JavaScript cause slow page loads  
**Impact:** HIGH - Poor user experience, SEO penalty  
**Probability:** MEDIUM - Common with rich media sites  
**Timeline Impact:** +2-3 days

**Mitigation Strategies:**
- ✅ Set Lighthouse score target > 90 from day 1
- ✅ Compress all images before uploading to DAM
- ✅ Use WebP format with JPEG fallback
- ✅ Lazy load below-fold images
- ✅ Minimize and bundle CSS/JS
- ✅ Enable ClientLib minification in production
- ✅ Use AEM image servlets for dynamic sizing
- ✅ Run Lighthouse weekly during development

**Contingency Plan:**
- Remove animations if performance suffers
- Reduce image sizes/quality
- Defer non-critical JavaScript
- Use CDN for static assets
- Simplify hero background (solid color instead of image)

**Action Items:**
- [ ] Set up Lighthouse CI in week 2
- [ ] Create image optimization guidelines (max size, format)
- [ ] Enable ClientLib minification early
- [ ] Document performance budget (page weight < 2MB)

**Monitoring:**
- Run Lighthouse on every page weekly
- Track page weight and load times
- Monitor Core Web Vitals

---

### R4: AEM Environment Issues
**Risk:** AEM SDK crashes, package deployment fails, environment instability  
**Impact:** HIGH - Blocks all development  
**Probability:** MEDIUM - AEM complexity, local resource constraints  
**Timeline Impact:** +1-3 days (cumulative)

**Mitigation Strategies:**
- ✅ Set up AEM environment in week 1 (don't delay)
- ✅ Increase Java heap size if needed (-Xmx4g)
- ✅ Use incremental deployments (deploy single modules)
- ✅ Keep AEM logs open to catch errors early
- ✅ Have backup developer environment
- ✅ Document AEM setup steps
- ✅ Clear cache/tmp folders regularly

**Contingency Plan:**
- Use AEM Cloud SDK instead of local if unstable
- Pair program on single environment temporarily
- Use AEM as a Cloud Service sandbox
- Split development across multiple local AEM instances

**Action Items:**
- [ ] Document AEM setup in CLAUDE.md
- [ ] Test full build/deploy cycle on day 1
- [ ] Create AEM restart script
- [ ] Monitor AEM memory usage

**Monitoring:**
- Track AEM restart frequency
- Monitor deployment success rate
- Log build/deploy times

---

### R5: Design-to-Implementation Gaps
**Risk:** Screenshots don't specify all details (colors, spacing, interactions)  
**Impact:** HIGH - Rework, stakeholder dissatisfaction  
**Probability:** MEDIUM - Common with screenshot-only designs  
**Timeline Impact:** +2-4 days (rework)

**Mitigation Strategies:**
- ✅ Extract design tokens from screenshots early (colors, fonts, spacing)
- ✅ Document assumptions in design system (07-design-system.md)
- ✅ Share component builds early for feedback (weekly demos)
- ✅ Use TODO sections to highlight unknown details
- ✅ Build one component → get feedback → iterate pattern
- ✅ Create design Q&A log

**Contingency Plan:**
- Use best judgment for unspecified details
- Document all assumptions clearly
- Plan refinement phase (already included in timeline)
- Get quick approvals via screenshot comparisons

**Action Items:**
- [ ] Schedule weekly demo with stakeholder/designer
- [ ] Create design Q&A document
- [ ] Fill TODO sections in design system doc
- [ ] Extract exact colors/fonts/spacing from screenshots

**Monitoring:**
- Track design feedback rounds per component
- Log all design assumptions

---

## Medium Risks (🟡 Medium Impact, Medium Probability)

### R6: Experience Fragment Publishing Issues
**Risk:** XF changes don't appear on pages, publishing confusion  
**Impact:** MEDIUM - Confusing for authors, debugging time  
**Probability:** MEDIUM - AEM XF workflow not intuitive  
**Timeline Impact:** +1 day

**Mitigation:**
- ✅ Document XF publishing workflow clearly
- ✅ Train authors on "Publish XF first, then pages"
- ✅ Add XF publication check to authoring checklist
- ✅ Test XF references thoroughly
- ✅ Create visual guide with screenshots

**Contingency:**
- Use components instead of XF if too confusing
- Automate XF publishing via workflow
- Lock XFs so only admins can edit

---

### R7: Responsive Design Complexity
**Risk:** Components don't stack/resize correctly on mobile  
**Impact:** MEDIUM - Poor mobile experience  
**Probability:** MEDIUM - CSS complexity, many components  
**Timeline Impact:** +2-3 days

**Mitigation:**
- ✅ Use mobile-first CSS approach
- ✅ Test on real devices weekly (not just browser DevTools)
- ✅ Use CSS Grid auto-fit for flexible layouts
- ✅ Test at 320px, 375px, 768px, 1024px, 1440px
- ✅ Build responsive pattern library (mixins)
- ✅ Test each component on mobile immediately after building

**Contingency:**
- Simplify mobile layouts (remove decorative elements)
- Use fixed breakpoints instead of fluid
- Stack all components vertically on mobile

**See:** [Responsive Strategy](./07-responsive-strategy.md)

---

### R8: JavaScript Interaction Bugs
**Risk:** Accordion doesn't expand, mobile menu doesn't work  
**Impact:** MEDIUM - Broken functionality  
**Probability:** MEDIUM - JavaScript can be fragile  
**Timeline Impact:** +1-2 days

**Mitigation:**
- ✅ Write simple, vanilla JavaScript (avoid complex libraries)
- ✅ Test interactions immediately after building
- ✅ Add console.log debugging during development
- ✅ Use event delegation for dynamic elements
- ✅ Test on mobile devices (touch events different)
- ✅ Add error handling

**Contingency:**
- CSS-only solutions where possible (checkbox hack for accordion)
- Simplify interactions
- Remove animations if they cause issues

---

### R9: Sling Model Complexity
**Risk:** Sling Models don't work, injection fails, models not registered  
**Impact:** MEDIUM - Components don't render  
**Probability:** MEDIUM - AEM Sling Model quirks  
**Timeline Impact:** +1-2 days

**Mitigation:**
- ✅ Start with HTL-only components (no models)
- ✅ Add models only when logic is complex
- ✅ Export model packages in core/pom.xml
- ✅ Use @DefaultInjectionStrategy.OPTIONAL
- ✅ Test models with unit tests
- ✅ Follow naming conventions (Model suffix)

**Contingency:**
- Use HTL logic instead of models (more verbose but works)
- Simplify model to getters only (no business logic)
- Use WCM Use-API instead of Sling Models

---

### R10: DAM Asset Management Issues
**Risk:** Images missing, wrong format, too large, not uploaded  
**Impact:** MEDIUM - Components incomplete, performance issues  
**Probability:** MEDIUM - Asset management often neglected  
**Timeline Impact:** +1-2 days

**Mitigation:**
- ✅ Create DAM folder structure early (week 1)
- ✅ Use placeholders during development
- ✅ Define asset requirements (format, size, dimensions)
- ✅ Upload critical assets early (logo, mascot, partner logos)
- ✅ Document asset naming conventions
- ✅ Create asset checklist

**Contingency:**
- Use placeholder images throughout
- Batch upload assets in week 5
- Source stock images if original assets delayed

**See:** [DAM Asset Strategy](./08-dam-asset-strategy.md)

---

## Low Risks (🟢 Low Impact or Low Probability)

### R11: ClientLib Category Conflicts
**Risk:** ClientLib categories conflict with core components  
**Impact:** LOW - CSS overrides, easy to fix  
**Probability:** LOW - Unique category names  
**Timeline Impact:** < 1 day

**Mitigation:**
- Use namespaced categories (setia.base, setia.site)
- Test ClientLib loading order
- Document ClientLib structure

---

### R12: Template Policy Misconfiguration
**Risk:** Wrong components allowed in template  
**Impact:** LOW - Authors confused, easy to fix  
**Probability:** LOW - Clear component list  
**Timeline Impact:** < 1 day

**Mitigation:**
- Test template policies immediately after creation
- Document allowed components per template
- Review policies before page creation phase

---

### R13: Content Authoring Errors
**Risk:** Authors enter invalid data, break pages  
**Impact:** LOW - Individual page issues  
**Probability:** LOW - Dialog validation prevents most issues  
**Timeline Impact:** < 1 day

**Mitigation:**
- Add dialog field validation (required fields, format)
- Provide authoring training (week 7)
- Create authoring guidelines document
- Test with invalid data during development

---

## Critical Risks Added in Implementation Review (🔴)

> These two risks materialized during implementation review and are the **top execution risks** for an AI agent. They carry their own IDs (R14, R15) to avoid collision with the existing R9/R10.

### R14: Empty AEM Rendering Risk
**Risk:** Templates render empty responsive grids instead of realistic pages — i.e. the skeleton deploys but no content is authored, so pages show "Drag components here."
**Impact:** HIGH — the deliverable looks broken/incomplete; visual validation is meaningless against empty containers.
**Probability:** HIGH — the default state of a freshly deployed template is empty; it stays empty unless content is explicitly bootstrapped.
**Timeline Impact:** +2-4 days if discovered late

**Mitigation Strategies:**
- ✅ Bootstrap initial content **early**, immediately after templates are wired (see [03 → Bootstrap Initial Authored Content](./03-recommended-build-order.md) and [09](./09-boostrap-initial-content.md))
- ✅ Export authored pages into source control (`ui.content`) so the realistic version is reproducible
- ✅ Validate **all** templates with realistic content, never empty containers
- ✅ Treat "empty responsive grid" as **invalid output** in every acceptance gate

**Contingency Plan:**
- Create content-seeding scripts (or a vlt/package) that author the pages deterministically
- Ship an initial authored package inside `ui.content` so a clean deploy is never empty

**Monitoring:**
- After every deploy, confirm each page renders header, footer, and at least the hero/banner + one content section

---

### R15: Template/XF Wiring Drift
**Risk:** Header/Footer Experience Fragments are not wired consistently across both templates (e.g. footer present on Content Page but missing on Landing Page), so some pages render without global chrome.
**Impact:** HIGH — inconsistent navigation/branding; hard to spot because one template looks fine.
**Probability:** MEDIUM-HIGH — two templates × two XFs = four wirings that drift easily during refactors.
**Timeline Impact:** +1-2 days

**Mitigation Strategies:**
- ✅ Run the [Mandatory Template Wiring Validation](./01-implementation-plan.md#mandatory-template-wiring-validation) checklist after **every** deployment
- ✅ Validate template structure (header + footer present in both) after each change
- ✅ Add screenshot-comparison tests (author + publish) that would catch a missing footer
- ✅ Use the single generic `setia/components/experiencefragment` component everywhere (no deprecated `xf-*-reference`)

**Contingency Plan:**
- Keep both templates' `structure/.content.xml` under review in PRs; diff them for symmetric XF nodes

---

## Risk Summary Table

| ID | Risk | Impact | Prob | Score | Status | Owner |
|----|------|--------|------|-------|--------|-------|
| R1 | Component complexity | HIGH | HIGH | 🔴 9 | Monitor | Dev Lead |
| R2 | Browser compatibility | HIGH | MED | 🟠 6 | Plan | Frontend Dev |
| R3 | Performance issues | HIGH | MED | 🟠 6 | Monitor | Frontend Dev |
| R4 | AEM environment | HIGH | MED | 🟠 6 | Plan | DevOps |
| R5 | Design gaps | HIGH | MED | 🟠 6 | Accept | All |
| R6 | XF publishing | MED | MED | 🟡 4 | Plan | AEM Dev |
| R7 | Responsive design | MED | MED | 🟡 4 | Monitor | Frontend Dev |
| R8 | JavaScript bugs | MED | MED | 🟡 4 | Monitor | Frontend Dev |
| R9 | Sling Model issues | MED | MED | 🟡 4 | Avoid | AEM Dev |
| R10 | DAM asset mgmt | MED | MED | 🟡 4 | Plan | Content Author |
| R11 | ClientLib conflicts | LOW | LOW | 🟢 1 | Accept | AEM Dev |
| R12 | Template policies | LOW | LOW | 🟢 1 | Accept | AEM Dev |
| R13 | Authoring errors | LOW | LOW | 🟢 1 | Accept | Content Author |
| R14 | Empty AEM rendering | HIGH | HIGH | 🔴 9 | Monitor | AEM Dev |
| R15 | Template/XF wiring drift | HIGH | MED | 🟠 6 | Plan | AEM Dev |

**Risk Score:** Impact (1-3) × Probability (1-3) = 1-9

**Status Legend:**
- **Monitor:** Track closely, may escalate
- **Plan:** Active mitigation planned
- **Avoid:** Take steps to prevent
- **Accept:** Acknowledge but no active mitigation

---

## Risk Monitoring

### Weekly Risk Review (Every Friday)

**Review Checklist:**
1. Have any risks materialized this week?
2. Are mitigations working?
3. Do new risks need to be added?
4. Should any risk scores be updated?
5. Are action items complete?

**Meeting Duration:** 30 minutes  
**Attendees:** Tech Lead, AEM Dev, Frontend Dev, PM

### Risk Indicators (Early Warning Signs)

**Development Velocity Risks:**
- ⚠️ Component build taking longer than 1 day
- ⚠️ More than 2 days spent on single bug
- ⚠️ Falling behind build order schedule by 2+ days

**Quality Risks:**
- ⚠️ Multiple rounds of design feedback (>2 per component)
- ⚠️ Lighthouse score dropping below 85
- ⚠️ More than 3 browser compatibility bugs per week
- ⚠️ Accessibility violations (aXe DevTools)

**Environment Risks:**
- ⚠️ AEM crashes more than once per day
- ⚠️ Build failures more than 10% of deploys
- ⚠️ Deployment taking longer than 5 minutes

**Team Risks:**
- ⚠️ Key team member unavailable
- ⚠️ Multiple blockers per day
- ⚠️ Unclear requirements causing delays

---

## Contingency Budget

### Timeline Buffer
**Built-in buffer:** 2-3 days in 8-week schedule  
**Location:** Week 7 (testing/refinement phase)  
**Usage criteria:** Critical risks materialize

### Scope Buffer (Can be descoped if needed)
**Optional features:**
- Animations and transitions (fade-in, hover effects)
- Advanced hover effects (card lift, parallax)
- Optional JavaScript enhancements
- Pattern overlays on hero
- Grayscale-to-color logo effects
- Smooth scroll

**Must-have features:**
- All 13 components functional
- All 4 pages created
- Responsive on mobile/desktop
- Accessible (WCAG AA)
- Performance (Lighthouse > 80)

### Budget Buffer
**Financial contingency:** 10-15% of total budget  
**Usage:** Extended development time, additional testing, external resources

---

## Risk Response Playbook

### When a Critical Risk Materializes

**Immediate Actions (Within 4 hours):**
1. Document the issue in detail
2. Assess impact on timeline
3. Notify stakeholders
4. Activate contingency plan
5. Reassign resources if needed

**Recovery Steps:**
1. Implement workaround/contingency
2. Re-estimate affected work
3. Update project schedule
4. Review other risks (cascade effects?)
5. Document lessons learned

### Escalation Criteria

**Escalate to Project Manager when:**
- Risk will delay project by > 3 days
- Risk requires additional budget
- Risk affects project scope
- Multiple high risks active simultaneously
- Team blocked for > 1 day

**Escalate to Stakeholder when:**
- Risk will delay launch date
- Risk requires scope reduction
- Risk impacts core functionality
- Budget increase needed

---

## Lessons Learned Log

**Purpose:** Document risks that materialized and how they were handled

**Template:**
```
Risk ID: [R#]
Date Occurred: [YYYY-MM-DD]
Description: [What happened]
Impact: [Actual impact vs predicted]
Resolution: [How it was resolved]
Lesson: [What we learned]
Prevention: [How to prevent in future]
```

**Review:** After project completion, update risk assessment for future projects

---

## Related Documentation

- [Implementation Plan](./01-implementation-plan.md)
- [Component Dependency Graph](./02-component-dependency-graph.md)
- [Recommended Build Order](./03-recommended-build-order.md)
- [AEM Authoring Strategy](./06-aem-authoring-strategy.md)
- [Responsive Strategy](./07-responsive-strategy.md)
- [DAM Asset Strategy](./08-dam-asset-strategy.md)

---

**Document Version:** 1.0  
**Last Updated:** 2026-05-31  
**Next Review:** Weekly during project
