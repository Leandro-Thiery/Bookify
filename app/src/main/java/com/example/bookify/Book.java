package com.example.bookify;

import java.io.Serializable;

public class Book implements Serializable {
    String title;
    String description;
    String category;
    String cover_url;
    String book_id;
    String author;
    String pdf_url;

    public Book (){

    }

    public Book(String title, String description, String category, String cover_url, String book_id, String author, String pdf_url) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.cover_url = cover_url;
        this.book_id = book_id;
        this.author = author;
        this.pdf_url = pdf_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }
}