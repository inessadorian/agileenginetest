package com.agileengine;

import java.util.List;
import java.util.Set;

public class SearchModel {

    private String id;
    private Set<String> classNames;
    private String href;
    private String title;
    private String rel;
    private String textButton;

    public SearchModel(String id, Set<String> classNames, String href, String title, String rel, String textButton) {
        this.id = id;
        this.classNames = classNames;
        this.href = href;
        this.title = title;
        this.rel = rel;
        this.textButton = textButton;
    }

    public Set<String> getClassNames() {
        return classNames;
    }

    public void setClassNames(Set<String> classNames) {
        this.classNames = classNames;
    }

    public String getTextButton() {
        return textButton;
    }

    public void setTextButton(String textButton) {
        this.textButton = textButton;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }


    @Override
    public String toString() {
        return "SearchModel{" +
                "id='" + id + '\'' +
                ", classNames=" + classNames +
                ", href='" + href + '\'' +
                ", title='" + title + '\'' +
                ", rel='" + rel + '\'' +
                ", textButton='" + textButton + '\'' +
                '}';
    }
}
