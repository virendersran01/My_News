package com.example.my_news.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Byline {

    @SerializedName("original")
    private Object original;

    @SerializedName("person")
    private List<Object> person;

    @SerializedName("organization")
    private Object organization;

    public void setOriginal(Object original) {
        this.original = original;
    }

    public Object getOriginal() {
        return original;
    }

    public void setPerson(List<Object> person) {
        this.person = person;
    }

    public List<Object> getPerson() {
        return person;
    }

    public void setOrganization(Object organization) {
        this.organization = organization;
    }

    public Object getOrganization() {
        return organization;
    }

    @Override
    public String toString() {
        return
                "Byline{" +
                        "original = '" + original + '\'' +
                        ",person = '" + person + '\'' +
                        ",organization = '" + organization + '\'' +
                        "}";
    }
}