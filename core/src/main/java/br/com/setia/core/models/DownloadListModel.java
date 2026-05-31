package br.com.setia.core.models;

import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the {@code download-list} component.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DownloadListModel {

    @ValueMapValue
    private String title;

    @ChildResource
    private List<DownloadItem> downloadItems;

    public String getTitle() {
        return title;
    }

    public List<DownloadItem> getItems() {
        return downloadItems != null ? downloadItems : Collections.emptyList();
    }

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class DownloadItem {

        @ValueMapValue
        private String fileTitle;

        @ValueMapValue
        private String fileDescription;

        @ValueMapValue
        private String fileAsset;

        @ValueMapValue
        private String fileType;

        @ValueMapValue
        private String fileSize;

        public String getFileTitle() {
            return fileTitle;
        }

        public String getFileDescription() {
            return fileDescription;
        }

        public String getFileAsset() {
            return fileAsset;
        }

        public String getFileSize() {
            return fileSize;
        }

        /** Returns the explicit file type, or derives the extension from the asset path. */
        public String getFileType() {
            if (fileType != null && !fileType.isEmpty()) {
                return fileType.toUpperCase();
            }
            if (fileAsset != null && fileAsset.contains(".")) {
                return fileAsset.substring(fileAsset.lastIndexOf('.') + 1).toUpperCase();
            }
            return "";
        }
    }
}
