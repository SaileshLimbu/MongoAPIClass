package np.com.softwarica.mongoapiclass.models;

import com.google.gson.annotations.SerializedName;

public class ImageResponse {
    @SerializedName("filename")
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
