package br.com.setia.core.models;

import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the {@code logo-gallery} component.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LogoGalleryModel {

    @ValueMapValue
    private String title;

    @ChildResource
    private List<Logo> logos;

    public String getTitle() {
        return title;
    }

    public List<Logo> getLogos() {
        return logos != null ? logos : Collections.emptyList();
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class Logo {

        @ValueMapValue
        private String imagePath;

        @ValueMapValue
        private String altText;

        @ValueMapValue
        private String linkUrl;

        @ValueMapValue
        private String width;

        public String getImagePath() {
            return imagePath;
        }

        public String getAltText() {
            return altText;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public String getWidth() {
            return width;
        }
    }
}
