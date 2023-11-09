package com.codecool.old_book_auction;


import com.codecool.old_book_auction.model.*;

import java.util.ArrayList;
import java.util.Random;

public class App {
    public static void main(String[] args) {
        final int bookCount = 20;
        final int minPrice = 20;
        final int maxPrice = 100;

        final int bidderCount = 10;
        final int minimumCapital = 50;
        final int maximumCapital = 500;
        final Random random = new Random();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Bidder> bidders = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();
        int totalStartingPricesOfAllBooks = 0;
        int totalStartingPricesOfSoldBooks = 0;
        int totalPaidMoney = 0;
        int numberOfBooksSold = 0;


        Topic[] topics = Topic.values();
        final String[] titles = {"Ancient Enigmas", "Cosmic Explorations", "Chemical Wonders", "Epic History", "Medical Breakthroughs",
                "Quantum Realm", "Lost Civilizations", "Stellar Journeys", "The Elements", "Time Chronicles", "Healing Miracles",
                "Physics Revealed", "Hidden Treasures", "Celestial Wonders", "Chemistry Unmasked", "Historical Footprints",
                "Medical Marvels", "The Physics Book", "Archaeological Mysteries", "Starry Nights"};

        for (int i = 1; i <= bookCount; i++) {
            Book newBook = new Book(i, titles[i - 1], Topic.values()[random.nextInt(topics.length)], random.nextInt(minPrice, maxPrice));
            books.add(newBook);
            totalStartingPricesOfAllBooks += newBook.getPrice();
        }
        for (int i = 1; i <= bidderCount; i++) {
            Topic favoriteTopic = Topic.values()[random.nextInt(topics.length)];
            Bidder newBidder = new Bidder(i, random.nextInt(minimumCapital, maximumCapital), favoriteTopic, interestedTopics(favoriteTopic));
            bidders.add(newBidder);
        }


        for (int i = 0; i < books.size(); i++) {
            ArrayList<Bidder> interestedBidders = new ArrayList<>();
            System.out.println("\n\n\n\n\nBook #" + books.get(i).getID() +
                    " with the title " + books.get(i).getTitle() + " on the topic of " + books.get(i).getTopic() +
                    " and the starting price of $" + books.get(i).getPrice() + ".");
            for (Bidder bidder : bidders) {
                if (bidder.interested(books.get(i)) && bidder.canBid(bidder.getThresholdPrice(books.get(i).getTopic()), books.get(i).getPrice())) {
                    interestedBidders.add(bidder);
                }
            }
            if (interestedBidders.size() == 0) {
                System.out.println("There are no interested bidders.\n");
            } else {
                printInterestedBidders(interestedBidders, books.get(i));
                int round = 0;
                System.out.println("\nBidding Round " + round);
                Bidder currentHighestBidder = interestedBidders.get(random.nextInt(interestedBidders.size()));
                Bid currentHighestBid = new Bid(1, currentHighestBidder.getBidderId(), books.get(i).getPrice());
                System.out.println(currentHighestBidder);
                System.out.println(currentHighestBid);
                Boolean bookSold = false;
                if (interestedBidders.size() == 1) {
                    bookSold = true;
                }
                int bidsPerRound = 0;
                System.out.println();
                while (!bookSold) {
                    round++;
                    System.out.println("Bidding Round " + round);
                    for (Bidder bidder : interestedBidders) {
                        Bid potentialBid = bidder.getBid(books.get(i), currentHighestBid);
                        if ((bidder.canBid(potentialBid.getPriceBid(), currentHighestBid.getPriceBid())
                                && bidder.getBidderId() != currentHighestBid.getBidderID())
                                && (potentialBid.getPriceBid() > currentHighestBid.getPriceBid()
                                && bidder.getThresholdPrice(books.get(i).getTopic()) > currentHighestBid.getPriceBid())) {
                            currentHighestBid = potentialBid;
                            bidsPerRound++;
                            System.out.println("succesfully.");
                            System.out.println(currentHighestBid);
                        } else {
                            System.out.println("but won't do it because of his $" + bidder.getThresholdPrice(books.get(i).getTopic()) + " threshold or his id.");
                        }
                    }
                    if (bidsPerRound > 0) {
                        bidsPerRound = 0;
                    } else {
                        bookSold = true;
                        break;
                    }
                    System.out.println();
                }
                System.out.println();
                Bidder winner = null;
                for (Bidder bidder : bidders) {
                    if (bidder.getBidderId() == currentHighestBid.getBidderID()) {
                        winner = bidder;
                    }
                }

                System.out.println("The book was sold to Bidder #" + currentHighestBid.getBidderID() + " for $" + currentHighestBid.getPriceBid() + ".");
                totalStartingPricesOfSoldBooks += books.get(i).getPrice();
                totalPaidMoney += currentHighestBid.getPriceBid();
                System.out.println("Bidder before: " + winner);
                numberOfBooksSold++;
                assert winner != null;
                winner.buyBook(books.get(i), currentHighestBid, numberOfBooksSold, transactions);
                System.out.println();
            }
        }
        System.out.println("\n\n\n\nThe full list of transactions is:\n");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.println("\n\nThere were " + numberOfBooksSold + " books sold out of " + bookCount + ". ");
        System.out.println("The total initial value of all the " + bookCount + " books was $" + totalStartingPricesOfAllBooks + ". ");
        System.out.println("The total initial value of the " + numberOfBooksSold + " books was $" + totalStartingPricesOfSoldBooks + ". ");
        System.out.println("The auctioneers received a total of $" + totalPaidMoney + " for the " + numberOfBooksSold + " sold books. ");

    }


    private static Topic[] interestedTopics(Topic favTopic) {
        Random random = new Random();
        Topic interested1 = Topic.values()[random.nextInt(Topic.values().length)];
        Topic interested2 = Topic.values()[random.nextInt(Topic.values().length)];
        while (interested1 == favTopic) {
            interested1 = Topic.values()[random.nextInt(Topic.values().length)];
        }
        while (interested2 == interested1 || interested2 == favTopic) {
            interested2 = Topic.values()[random.nextInt(Topic.values().length)];
        }
        return new Topic[]{interested1, interested2};
    }

    private static void printInterestedBidders(ArrayList<Bidder> interestedBidders, Book book) {
        System.out.println("There are " + interestedBidders.size() + " interested bidders:");
        for (Bidder interestedBidder : interestedBidders) {
            System.out.println(interestedBidder.getName() + " // Favourite Topic: " + interestedBidder.getFavourite() + " // Current Capital: $" + interestedBidder.getCapital() + " // Threshold for this book: $" + interestedBidder.getThresholdPrice(book.getTopic()));
        }
    }
}


