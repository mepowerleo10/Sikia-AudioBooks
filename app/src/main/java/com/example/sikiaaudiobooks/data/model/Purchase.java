package com.example.sikiaaudiobooks.data.model;

import java.util.Date;

public class Purchase {
    private String userId;
    private String bookId;
    private Date date;
    private Long price;

    public Purchase(String userId, String bookId, Date date, Long price) {
        this.userId = userId;
        this.bookId = bookId;
        this.date = date;
        this.price = price;
    }

    public Purchase() {
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Long getDate() {
        return date.getTime();
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
