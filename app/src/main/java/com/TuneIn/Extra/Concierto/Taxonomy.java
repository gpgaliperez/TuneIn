package com.TuneIn.Extra.Concierto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Taxonomy {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parent_id")
    @Expose
    private Object parentId;
    @SerializedName("document_source")
    @Expose
    private DocumentSource documentSource;
    @SerializedName("rank")
    @Expose
    private Integer rank;

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

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public DocumentSource getDocumentSource() {
        return documentSource;
    }

    public void setDocumentSource(DocumentSource documentSource) {
        this.documentSource = documentSource;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
