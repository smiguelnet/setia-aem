/**
 * Accordion Component
 * Handles expand/collapse interactions with accessible aria-expanded state.
 * (Canonical class-based pattern — see docs/06-frontend-tech-details.md)
 */
class Accordion {
    constructor(element) {
        this.accordion = element;
        this.headers = element.querySelectorAll('.accordion-item__header');
        this.init();
    }

    init() {
        this.headers.forEach((header) => {
            header.addEventListener('click', (e) => this.toggle(e));
        });
    }

    toggle(event) {
        const header = event.currentTarget;
        const item = header.closest('.accordion-item');
        const isExpanded = item.classList.contains('accordion-item--expanded');

        // Close all items (single-open accordion)
        this.closeAll();

        // Open clicked item if it was previously closed
        if (!isExpanded) {
            item.classList.add('accordion-item--expanded');
            header.setAttribute('aria-expanded', 'true');
        }
    }

    closeAll() {
        const items = this.accordion.querySelectorAll('.accordion-item');
        items.forEach((item) => {
            item.classList.remove('accordion-item--expanded');
            const header = item.querySelector('.accordion-item__header');
            if (header) {
                header.setAttribute('aria-expanded', 'false');
            }
        });
    }
}

function initAccordions() {
    document.querySelectorAll('.accordion-section').forEach((el) => new Accordion(el));
}

if (document.readyState !== 'loading') {
    initAccordions();
} else {
    document.addEventListener('DOMContentLoaded', initAccordions);
}

export default Accordion;
