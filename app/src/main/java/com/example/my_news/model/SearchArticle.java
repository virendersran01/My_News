package com.example.my_news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchArticle {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("copyright")
    @Expose
    private String copyright;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public class Multimedia {

        @SerializedName("caption")
        @Expose
        public String caption;

        @SerializedName("credit")
        @Expose
        public String credit;

        @SerializedName("crop_name")
        @Expose
        public String cropName;

        @SerializedName("height")
        @Expose
        public int height;

        @SerializedName("rank")
        @Expose
        public int rank;

        @SerializedName("subtype")
        @Expose
        public String subtype;

        @SerializedName("type")
        @Expose
        public String type;

        @SerializedName("url")
        @Expose
        public String url;

        @SerializedName("width")
        @Expose
        public int width;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getCropName() {
            return cropName;
        }

        public void setCropName(String cropName) {
            this.cropName = cropName;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }

    public class Response {
        @SerializedName("documents")
        @Expose
        private ArrayList<Docs> docs = null;

        public ArrayList<Docs> getDocs() {
            return docs;
        }

        public void setDocs(ArrayList<Docs> docs) {
            this.docs = docs;
        }
    }

    public class Docs {

        @SerializedName("web-url")
        @Expose
        private String webUrl;
        @SerializedName("snippet")
        @Expose
        private String snippet;
        @SerializedName("multimedia")
        @Expose
        private ArrayList<Multimedia> multimedia = null;
        @SerializedName("pud_date")
        @Expose
        private String pubDate;
        @SerializedName("document_type")
        @Expose
        private String documentType;
        @SerializedName("news_desk")
        @Expose
        private String newsDesk;
        @SerializedName("type_of_material")
        @Expose
        private String typeOfMaterial;


        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public String getSnippet() {
            return snippet;
        }

        public void setSnippet(String snippet) {
            this.snippet = snippet;
        }

        public ArrayList<Multimedia> getMultimedia() {
            return multimedia;
        }

        public void setMultimedia(ArrayList<Multimedia> multimedia) {
            this.multimedia = multimedia;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getDocumentType() {
            return documentType;
        }

        public void setDocumentType(String documentType) {
            this.documentType = documentType;
        }

        public String getNewsDesk() {
            return newsDesk;
        }

        public void setNewsDesk(String newsDesk) {
            this.newsDesk = newsDesk;
        }

        public String getTypeOfMaterial() {
            return typeOfMaterial;
        }

        public void setTypeOfMaterial(String typeOfMaterial) {
            this.typeOfMaterial = typeOfMaterial;
        }
    }
}
