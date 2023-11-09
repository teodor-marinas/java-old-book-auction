package com.codecool.old_book_auction.model;

import java.util.Arrays;

public class Bid {
    private int id;
    private int bidderID;
    private int bidPrice;

    public Bid(int id, int bidderID, int bidPrice) {
        this.id = id;
        this.bidderID = bidderID;
        this.bidPrice = bidPrice;
    }

    public int getBidID() {
        return this.id;
    }

    public int getBidderID() {
        return this.bidderID;
    }

    public int getPriceBid() {
        return this.bidPrice;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", bidderID=" + bidderID +
                ", bidPrice=" + bidPrice +
                '\'' +
                '}';
    }
}

