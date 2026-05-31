package br.com.setia.core.models;

import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the {@code card-grid} component.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CardGridModel {

    @ValueMapValue
    private String columns;

    @ValueMapValue
    private String gap;

    @ChildResource
    private List<Card> cardItems;

    public String getColumns() {
        return columns != null ? columns : "3";
    }

    public String getGap() {
        return gap != null ? gap : "medium";
    }

    /** CSS class derived from the configured column count. */
    public String getGridClass() {
        return "card-grid--" + getColumns() + "-col";
    }

    public List<Card> getCards() {
        return cardItems != null ? cardItems : Collections.emptyList();
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class Card {

        @ValueMapValue
        private String icon;

        @ValueMapValue
        private String title;

        @ValueMapValue
        private String description;

        @ValueMapValue
        private String linkUrl;

        @ValueMapValue
        private String linkText;

        public String getIcon() {
            return icon;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public String getLinkText() {
            return linkText != null ? linkText : "Learn More";
        }
    }
}
