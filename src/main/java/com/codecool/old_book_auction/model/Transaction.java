package com.codecool.old_book_auction.model;

public class Transaction {
    private int id;
    private int time;
    private int bidID;
    private int bidderID;

    public Transaction(int id, int time, int bidID, int bidderID) {
        this.id = id;
        this.time = time;
        this.bidID = bidID;
        this.bidderID = bidderID;
    }

    public int getTransactionID() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public int getBidID() {
        return bidID;
    }

    public int getBidderID() {
        return bidderID;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", time=" + time +
                ", bidID=" + bidID +
                ", bidderID=" + bidderID +
                '}';
    }
}


