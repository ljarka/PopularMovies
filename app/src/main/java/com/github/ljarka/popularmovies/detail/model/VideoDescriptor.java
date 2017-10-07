package com.github.ljarka.popularmovies.detail.model;

import com.google.gson.annotations.SerializedName;

public class VideoDescriptor {
    private String id;
    @SerializedName("iso_639_1")
    private String languageCode;
    @SerializedName("iso_3166_1")
    private String countryCode;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    public String getId() {
        return id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
