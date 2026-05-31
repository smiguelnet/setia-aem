package br.com.setia.core.models;

import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the {@code accordion-section} component.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccordionSectionModel {

    @ValueMapValue
    private String title;

    @ChildResource
    private List<AccordionItem> accordionItems;

    public String getTitle() {
        return title;
    }

    public List<AccordionItem> getItems() {
        return accordionItems != null ? accordionItems : Collections.emptyList();
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class AccordionItem {

        @ValueMapValue
        private String itemTitle;

        @ValueMapValue
        private String itemContent;

        @ValueMapValue
        private boolean expandedByDefault;

        public String getItemTitle() {
            return itemTitle;
        }

        public String getItemContent() {
            return itemContent;
        }

        public boolean isExpandedByDefault() {
            return expandedByDefault;
        }
    }
}
