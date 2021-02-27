package com.TuneIn.Extra.Concierto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentSource {

    @SerializedName("source_type")
    @Expose
    private String sourceType;
    @SerializedName("generation_type")
    @Expose
    private String generationType;

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getGenerationType() {
        return generationType;
    }

    public void setGenerationType(String generationType) {
        this.generationType = generationType;
    }

}
