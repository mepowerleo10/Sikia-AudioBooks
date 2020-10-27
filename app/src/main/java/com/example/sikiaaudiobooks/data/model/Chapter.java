package com.example.sikiaaudiobooks.data.model;

public class Chapter {
    private Long id;
    private Long size;
    private String title;

    public Chapter(Long id, Long size, String title) {
        this.id = id;
        this.size = size;
        this.title = title;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    private Double length;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
