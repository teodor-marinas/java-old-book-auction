package com.codecool.old_book_auction.model;

public class Book {
    private int id;
    private String title;
    private Topic topic;
    private int price;

    public Book(int id, String title, Topic topic, int price) {
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.price = price;
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Topic getTopic() {
        return topic;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", topic=" + topic +
                ", price=" + price +
                '}';
    }
}
