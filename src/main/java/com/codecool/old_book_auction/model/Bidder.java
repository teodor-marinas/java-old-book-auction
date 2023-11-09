package com.codecool.old_book_auction.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Bidder {
    private final List<Book> books = new ArrayList<Book>();
    private final Topic favourite;
    private final Topic[] interested;

    private int capital;

    private int id;
    private String name;


    public Bidder(int id, int capital, Topic favourite, Topic[] interested) {
        this.id = id;
        this.capital = capital;
        this.favourite = favourite;
        this.interested = interested;
        this.name = "Bidder #" + id;
    }

    public boolean interested(Book book) {
        for (Topic topic : this.interested) {
            if (topic.equals(book.getTopic())) {
                return true;
            }
        }
        return false;
    }

    public boolean canBid(int potentialBidPrice, int currentHighestBidPrice) {
        if (currentHighestBidPrice > potentialBidPrice) {
            return false;
        }
        return true;
    }

    public Bid getBid(Book book, Bid currentBid) {

        int bidPrice = currentBid.getPriceBid() + getBidPrice(book.getPrice(), getThresholdPrice(book.getTopic()));
        System.out.println("Bidder #" + this.id + " wants to bid $" + bidPrice);
//        System.out.println(currentBid.getPriceBid() + " + " + getBidPrice(book.getPrice(), getThresholdPrice(book.getTopic())) + " = " + bidPrice);
        return new Bid(currentBid.getBidID() + 1, this.id, bidPrice);
    }

    private static int getBidPrice(int currentPrice, int threshold) {
//        System.out.println(" ( " + threshold + " - " + currentPrice + " ) / 2 = " + (threshold-currentPrice)/2);
        return (threshold - currentPrice) / 2;
    }

    public int getThresholdPrice(Topic topic) {
        if (topic == this.favourite) {
            return (int) (capital / 2);
        }
        return (int) (capital / 4);
    }

    public void buyBook(Book book, Bid winningBid, int transactionId, ArrayList<Transaction> transactions) {
        Book updatedPriceBook = new Book(book.getID(), book.getTitle(), book.getTopic(), winningBid.getPriceBid());
        books.add(updatedPriceBook);
        this.capital = this.capital - winningBid.getPriceBid();

        Transaction transaction = new Transaction(transactionId, (int) new Date().getTime(), winningBid.getBidID(), winningBid.getBidderID());
        transactions.add(transaction);
        System.out.println(transaction);
        System.out.println(this.getName() + " has $" + this.capital + " left.");
        System.out.println("Bidder after: " + this);
    }

    public int getBidderId() {
        return this.id;
    }

    public int getCapital() {
        return this.capital;
    }

    public String getName() {
        return name;
    }

    public Topic getFavourite() {
        return favourite;
    }

    @Override
    public String toString() {
        return "Bidder{" +
                "books=" + books +
                ", favourite=" + favourite +
                ", interested=" + Arrays.toString(interested) +
                ", capital=" + capital +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
