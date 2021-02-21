package com.TuneIn.Entidades;
import com.TuneIn.Extra.Concierto.Announcements;
import com.TuneIn.Extra.Concierto.Performer;
import com.TuneIn.Extra.Concierto.Stats_;
import com.TuneIn.Extra.Concierto.Taxonomy_;
import com.TuneIn.Extra.Concierto.Venue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Concierto {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("datetime_utc")
    @Expose
    private String datetimeUtc;
    @SerializedName("venue")
    @Expose
    private Venue venue;
    @SerializedName("datetime_tbd")
    @Expose
    private Boolean datetimeTbd;
    @SerializedName("performers")
    @Expose
    private List<Performer> performers = null;
    @SerializedName("is_open")
    @Expose
    private Boolean isOpen;
    @SerializedName("links")
    @Expose
    private List<Object> links = null;
    @SerializedName("datetime_local")
    @Expose
    private String datetimeLocal;
    @SerializedName("time_tbd")
    @Expose
    private Boolean timeTbd;
    @SerializedName("short_title")
    @Expose
    private String shortTitle;
    @SerializedName("visible_until_utc")
    @Expose
    private String visibleUntilUtc;
    @SerializedName("stats")
    @Expose
    private Stats_ stats;
    @SerializedName("taxonomies")
    @Expose
    private List<Taxonomy_> taxonomies = null;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("announce_date")
    @Expose
    private String announceDate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("date_tbd")
    @Expose
    private Boolean dateTbd;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("access_method")
    @Expose
    private Object accessMethod;
    @SerializedName("event_promotion")
    @Expose
    private Object eventPromotion;
    @SerializedName("announcements")
    @Expose
    private Announcements announcements;
    @SerializedName("conditional")
    @Expose
    private Boolean conditional;
    @SerializedName("enddatetime_utc")
    @Expose
    private Object enddatetimeUtc;
    @SerializedName("themes")
    @Expose
    private List<Object> themes = null;
    @SerializedName("domain_information")
    @Expose
    private List<Object> domainInformation = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatetimeUtc() {
        return datetimeUtc;
    }

    public void setDatetimeUtc(String datetimeUtc) {
        this.datetimeUtc = datetimeUtc;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Boolean getDatetimeTbd() {
        return datetimeTbd;
    }

    public void setDatetimeTbd(Boolean datetimeTbd) {
        this.datetimeTbd = datetimeTbd;
    }

    public List<Performer> getPerformers() {
        return performers;
    }

    public void setPerformers(List<Performer> performers) {
        this.performers = performers;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public List<Object> getLinks() {
        return links;
    }

    public void setLinks(List<Object> links) {
        this.links = links;
    }

    public String getDatetimeLocal() {
        return datetimeLocal;
    }

    public void setDatetimeLocal(String datetimeLocal) {
        this.datetimeLocal = datetimeLocal;
    }

    public Boolean getTimeTbd() {
        return timeTbd;
    }

    public void setTimeTbd(Boolean timeTbd) {
        this.timeTbd = timeTbd;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getVisibleUntilUtc() {
        return visibleUntilUtc;
    }

    public void setVisibleUntilUtc(String visibleUntilUtc) {
        this.visibleUntilUtc = visibleUntilUtc;
    }

    public Stats_ getStats() {
        return stats;
    }

    public void setStats(Stats_ stats) {
        this.stats = stats;
    }

    public List<Taxonomy_> getTaxonomies() {
        return taxonomies;
    }

    public void setTaxonomies(List<Taxonomy_> taxonomies) {
        this.taxonomies = taxonomies;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getAnnounceDate() {
        return announceDate;
    }

    public void setAnnounceDate(String announceDate) {
        this.announceDate = announceDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDateTbd() {
        return dateTbd;
    }

    public void setDateTbd(Boolean dateTbd) {
        this.dateTbd = dateTbd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getAccessMethod() {
        return accessMethod;
    }

    public void setAccessMethod(Object accessMethod) {
        this.accessMethod = accessMethod;
    }

    public Object getEventPromotion() {
        return eventPromotion;
    }

    public void setEventPromotion(Object eventPromotion) {
        this.eventPromotion = eventPromotion;
    }

    public Announcements getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Announcements announcements) {
        this.announcements = announcements;
    }

    public Boolean getConditional() {
        return conditional;
    }

    public void setConditional(Boolean conditional) {
        this.conditional = conditional;
    }

    public Object getEnddatetimeUtc() {
        return enddatetimeUtc;
    }

    public void setEnddatetimeUtc(Object enddatetimeUtc) {
        this.enddatetimeUtc = enddatetimeUtc;
    }

    public List<Object> getThemes() {
        return themes;
    }

    public void setThemes(List<Object> themes) {
        this.themes = themes;
    }

    public List<Object> getDomainInformation() {
        return domainInformation;
    }

    public void setDomainInformation(List<Object> domainInformation) {
        this.domainInformation = domainInformation;
    }
}