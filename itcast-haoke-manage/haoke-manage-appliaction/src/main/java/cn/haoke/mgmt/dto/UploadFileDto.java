package cn.haoke.mgmt.dto;

import lombok.Data;

import java.io.File;
import java.io.Serializable;

@Data
public class UploadFileDto implements Serializable {

    private String url;
    private Integer orientation;
    private File file;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public void setOrientation(Integer orientation) {
        this.orientation = orientation;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
