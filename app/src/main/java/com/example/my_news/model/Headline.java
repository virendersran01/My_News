package com.example.my_news.model;

import com.google.gson.annotations.SerializedName;

public class Headline {

    @SerializedName("sub")
    private Object sub;

    @SerializedName("content_kicker")
    private Object contentKicker;

    @SerializedName("name")
    private Object name;

    @SerializedName("main")
    private String main;

    @SerializedName("seo")
    private Object seo;

    @SerializedName("print_headline")
    private Object printHeadline;

    @SerializedName("kicker")
    private Object kicker;

    public void setSub(Object sub) {
        this.sub = sub;
    }

    public Object getSub() {
        return sub;
    }

    public void setContentKicker(Object contentKicker) {
        this.contentKicker = contentKicker;
    }

    public Object getContentKicker() {
        return contentKicker;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getName() {
        return name;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getMain() {
        return main;
    }

    public void setSeo(Object seo) {
        this.seo = seo;
    }

    public Object getSeo() {
        return seo;
    }

    public void setPrintHeadline(Object printHeadline) {
        this.printHeadline = printHeadline;
    }

    public Object getPrintHeadline() {
        return printHeadline;
    }

    public void setKicker(Object kicker) {
        this.kicker = kicker;
    }

    public Object getKicker() {
        return kicker;
    }

    @Override
    public String toString() {
        return
                "Headline{" +
                        "sub = '" + sub + '\'' +
                        ",content_kicker = '" + contentKicker + '\'' +
                        ",name = '" + name + '\'' +
                        ",main = '" + main + '\'' +
                        ",seo = '" + seo + '\'' +
                        ",print_headline = '" + printHeadline + '\'' +
                        ",kicker = '" + kicker + '\'' +
                        "}";
    }
}