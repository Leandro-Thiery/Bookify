package com.example.bookify;

public class Library {
    private String title;
    private String lib_book_id;

    public String getTitle() {
        return title;
    }

    public String getLib_book_id() {
        return lib_book_id;
    }

    public Library(String title, String lib_book_id) {
        this.title = title;
        this.lib_book_id = lib_book_id;
    }

    public Library(){

    }
}
