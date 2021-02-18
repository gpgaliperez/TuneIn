package com.TuneIn.Extra;

public class Link {
    private String id;
    private String provider;
    private String url;

    public Link(String id, String provider, String url) {
        this.id = id;
        this.provider = provider;
        this.url = url;
    }

    public String getId() {
        return id;
    }
    public String getProvider() {
        return provider;
    }
    public String getUrl() {
        return url;
    }
}
