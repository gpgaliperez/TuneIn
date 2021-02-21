package com.TuneIn.Extra.Concierto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("primary")
    @Expose
    private Boolean primary;
    @SerializedName("images")
    @Expose
    private Images_ images;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("document_source")
    @Expose
    private DocumentSource_ documentSource;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public Images_ getImages() {
        return images;
    }

    public void setImages(Images_ images) {
        this.images = images;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DocumentSource_ getDocumentSource() {
        return documentSource;
    }

    public void setDocumentSource(DocumentSource_ documentSource) {
        this.documentSource = documentSource;
    }

}
