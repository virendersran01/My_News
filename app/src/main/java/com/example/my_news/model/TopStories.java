package com.example.my_news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopStories {

    @SerializedName("results")
    @Expose
    private ArrayList<Result> results = new ArrayList<>();

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public class Multimedia {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("format")
        @Expose
        private String format;
        @SerializedName("height")
        @Expose
        private int height;
        @SerializedName("width")
        @Expose
        private int width;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("subtype")
        @Expose
        private String subtype;
        @SerializedName("caption")
        @Expose
        private String caption;
        @SerializedName("copyright")
        @Expose
        private String copyright;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }
    }

    public class Result {
        @SerializedName("section")
        @Expose
        private String section;
        @SerializedName("subsection")
        @Expose
        private String subsection;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("abstract")
        @Expose
        private String _abstract;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("thumbnail_standard")
        @Expose
        private String thumbnailStandard;
        @SerializedName("short_url")
        @Expose
        private String shortUrl;
        @SerializedName("byline")
        @Expose
        private String byline;
        @SerializedName("item_type")
        @Expose
        private String item_type;
        @SerializedName("updated_date")
        @Expose
        private String updatedDate;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("multimedia")
        @Expose
        private ArrayList<Multimedia> multimedia = new ArrayList<>();

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getSubsection() {
            return subsection;
        }

        public void setSubsection(String subsection) {
            this.subsection = subsection;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String get_abstract() {
            return _abstract;
        }

        public void set_abstract(String _abstract) {
            this._abstract = _abstract;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnailStandard() {
            return thumbnailStandard;
        }

        public void setThumbnailStandard(String thumbnailStandard) {
            this.thumbnailStandard = thumbnailStandard;
        }

        public String getShortUrl() {
            return shortUrl;
        }

        public void setShortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
        }

        public String getByline() {
            return byline;
        }

        public void setByline(String byline) {
            this.byline = byline;
        }

        public String getItem_type() {
            return item_type;
        }

        public void setItem_type(String item_type) {
            this.item_type = item_type;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public ArrayList<Multimedia> getMultimedia() {
            return multimedia;
        }
        public void setMultimedia(ArrayList<Multimedia> multimedia) {
            this.multimedia = multimedia;
        }
    }
}
