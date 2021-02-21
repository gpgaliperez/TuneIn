package com.TuneIn.Extra.Concierto;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats_ {

    @SerializedName("listing_count")
    @Expose
    private Integer listingCount;
    @SerializedName("average_price")
    @Expose
    private Integer averagePrice;
    @SerializedName("lowest_price_good_deals")
    @Expose
    private Object lowestPriceGoodDeals;
    @SerializedName("lowest_price")
    @Expose
    private Integer lowestPrice;
    @SerializedName("highest_price")
    @Expose
    private Integer highestPrice;
    @SerializedName("visible_listing_count")
    @Expose
    private Integer visibleListingCount;
    @SerializedName("dq_bucket_counts")
    @Expose
    private List<Integer> dqBucketCounts = null;
    @SerializedName("median_price")
    @Expose
    private Integer medianPrice;
    @SerializedName("lowest_sg_base_price")
    @Expose
    private Integer lowestSgBasePrice;
    @SerializedName("lowest_sg_base_price_good_deals")
    @Expose
    private Object lowestSgBasePriceGoodDeals;

    public Integer getListingCount() {
        return listingCount;
    }

    public void setListingCount(Integer listingCount) {
        this.listingCount = listingCount;
    }

    public Integer getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Integer averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Object getLowestPriceGoodDeals() {
        return lowestPriceGoodDeals;
    }

    public void setLowestPriceGoodDeals(Object lowestPriceGoodDeals) {
        this.lowestPriceGoodDeals = lowestPriceGoodDeals;
    }

    public Integer getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Integer lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Integer getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Integer highestPrice) {
        this.highestPrice = highestPrice;
    }

    public Integer getVisibleListingCount() {
        return visibleListingCount;
    }

    public void setVisibleListingCount(Integer visibleListingCount) {
        this.visibleListingCount = visibleListingCount;
    }

    public List<Integer> getDqBucketCounts() {
        return dqBucketCounts;
    }

    public void setDqBucketCounts(List<Integer> dqBucketCounts) {
        this.dqBucketCounts = dqBucketCounts;
    }

    public Integer getMedianPrice() {
        return medianPrice;
    }

    public void setMedianPrice(Integer medianPrice) {
        this.medianPrice = medianPrice;
    }

    public Integer getLowestSgBasePrice() {
        return lowestSgBasePrice;
    }

    public void setLowestSgBasePrice(Integer lowestSgBasePrice) {
        this.lowestSgBasePrice = lowestSgBasePrice;
    }

    public Object getLowestSgBasePriceGoodDeals() {
        return lowestSgBasePriceGoodDeals;
    }

    public void setLowestSgBasePriceGoodDeals(Object lowestSgBasePriceGoodDeals) {
        this.lowestSgBasePriceGoodDeals = lowestSgBasePriceGoodDeals;
    }

}
