/**
 * Header mobile navigation toggle.
 * Injects a 3-bar hamburger button into the header, toggles aria-expanded
 * and the .is-open class on the nav group, and closes the menu when any
 * nav link is clicked.
 *
 * Depends on: experiencefragment_header.scss (desktop/mobile layout)
 * Selector contract:
 *   header.experiencefragment   → the XF wrapper element
 *   .cmp-navigation__group      → the <ul> that becomes the slide-out panel
 *   .cmp-navigation__item-link  → individual nav links
 *   .header-menu-toggle         → injected button (styled in SCSS)
 */
(function () {
  'use strict';

  function initHeader(): void {
    const header = document.querySelector<HTMLElement>('header.experiencefragment');
    if (!header) return;

    const navGroup = header.querySelector<HTMLElement>('.cmp-navigation__group');
    if (!navGroup) return;

    // --- Inject hamburger button ---
    const toggle = document.createElement('button');
    toggle.className = 'header-menu-toggle';
    toggle.setAttribute('aria-expanded', 'false');
    toggle.setAttribute('aria-label', 'Toggle navigation');
    toggle.setAttribute('type', 'button');
    // Three bar spans for the animated hamburger → X icon
    for (let i = 0; i < 3; i++) {
      toggle.appendChild(document.createElement('span'));
    }
    header.querySelector<HTMLElement>('.cmp-container')?.appendChild(toggle);

    // --- Toggle handler ---
    toggle.addEventListener('click', () => {
      const isOpen = navGroup.classList.contains('is-open');
      if (isOpen) {
        navGroup.classList.remove('is-open');
        toggle.setAttribute('aria-expanded', 'false');
      } else {
        navGroup.classList.add('is-open');
        toggle.setAttribute('aria-expanded', 'true');
      }
    });

    // --- Close on nav link click (SPA-style navigation or same-page anchors) ---
    header.querySelectorAll<HTMLAnchorElement>('.cmp-navigation__item-link').forEach((link) => {
      link.addEventListener('click', () => {
        navGroup.classList.remove('is-open');
        toggle.setAttribute('aria-expanded', 'false');
      });
    });

    // --- Close when clicking outside the header on mobile ---
    document.addEventListener('click', (e: MouseEvent) => {
      if (!header.contains(e.target as Node)) {
        navGroup.classList.remove('is-open');
        toggle.setAttribute('aria-expanded', 'false');
      }
    });
  }

  if (document.readyState !== 'loading') {
    initHeader();
  } else {
    document.addEventListener('DOMContentLoaded', initHeader);
  }
})();
