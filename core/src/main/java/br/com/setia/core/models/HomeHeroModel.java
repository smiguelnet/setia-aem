package br.com.setia.core.models;

import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the {@code home-hero} component.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HomeHeroModel {

    @ValueMapValue
    private String mainTitle;

    @ValueMapValue
    private String subtitle;

    @ValueMapValue
    private String backgroundImage;

    @ChildResource
    private List<ServiceCard> serviceCards;

    public String getMainTitle() {
        return mainTitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public List<ServiceCard> getServiceCards() {
        return serviceCards != null ? serviceCards : Collections.emptyList();
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class ServiceCard {

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
