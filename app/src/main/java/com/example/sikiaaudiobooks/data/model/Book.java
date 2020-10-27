package com.example.sikiaaudiobooks.data.model;

import java.util.List;

public class Book {
    private Double length, rating;
    private Long size;
    private Long noChapters;
    private Long price;
    private String id;
    private String narrator;
    private String genre;
    private String language;
    private String author;
    private String coverPath;
    private String title;
    private List<Chapter> chapters;
    private String tempCoverPath;

    public Book(Double length, Double rating, Long size, Long noChapters, Long price, String id,
                String narrator, String genre, String language, String author, String coverPath,
                List<Chapter> chapters, String title) {
        this.length = length;
        this.rating = rating;
        this.size = size;
        this.noChapters = noChapters;
        this.price = price;
        this.id = id;
        this.narrator = narrator;
        this.genre = genre;
        this.language = language;
        this.author = author;
        this.coverPath = coverPath;
        this.chapters = chapters;
        this.title = title;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getNoChapters() {
        return noChapters;
    }

    public void setNoChapters(Long noChapters) {
        this.noChapters = noChapters;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getNarrator() {
        return narrator;
    }

    public void setNarrator(String narrator) {
        this.narrator = narrator;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}

