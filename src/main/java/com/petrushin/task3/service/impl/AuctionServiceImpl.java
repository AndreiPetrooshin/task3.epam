package com.petrushin.task3.service.impl;


import com.petrushin.task3.domain.*;
import com.petrushin.task3.service.AuctionService;
import com.petrushin.task3.service.LotService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AuctionServiceImpl implements AuctionService {

    private final static Logger LOGGER = LogManager.getLogger(AuctionServiceImpl.class);

    private LotService lotService;

    public AuctionServiceImpl(LotService lotService) {
        this.lotService = lotService;
    }


    /**
     * This method creates two Executors to start
     * LotRunnable {@link LotRunnable} and
     * LotTerminate {@link LotTerminate} threads.
     *
     * @param auction - object with users data and lots data.
     */
    @Override
    public void startTrade(Auction auction) {
        LOGGER.info("The auction is starts!");

        List<Lot> lots = auction.getLots();
        List<User> users = auction.getUsers();

        LOGGER.info("Number of users:{}, number of lots:{}",
                users.size(), lots.size());

        int lotsSize = lots.size();
        ExecutorService lotsStartExecutor = Executors.newFixedThreadPool(lotsSize);
        ExecutorService lotsTerminateExecutor = Executors.newFixedThreadPool(lotsSize);

        for (Lot lot : lots) {
            registrationUsersForLot(users, lot);

            LotRunnable lotRunnable = new LotRunnable(lot);
            lotsStartExecutor.submit(lotRunnable);

            LotTerminate lotTerminate = new LotTerminate(lot);
            lotsTerminateExecutor.submit(lotTerminate);
        }
        lotsStartExecutor.shutdown();
        lotsTerminateExecutor.shutdown();
    }


    /**
     * This method registers Users in trades to the LOT
     *
     * @param users - List of users
     * @param lot   - {@link Lot} the lot object to which
     *              users are registered.
     */
    private void registrationUsersForLot(List<User> users, Lot lot) {
        for (User user : users) {
            int cash = user.getCash();
            int price = lot.getPrice();
            if (cash >= price) {
                lot.addUser(user);
            }
        }
    }



    /**
     * Private inner class to RUN the tradeLot method
     * in {@link LotServiceImpl} class
     */
    private class LotRunnable implements Runnable {

        private Lot lot;

        LotRunnable(Lot lot) {
            this.lot = lot;
        }

        @Override
        public void run() {
            lot.setState(State.TRADING);
            lotService.tradeLot(lot);
        }
    }



    /**
     * Private inner class to start thread which
     * terminates lot by it lifeTime
     */
    private class LotTerminate implements Runnable {

        private Lot lot;

        LotTerminate(Lot lot) {
            this.lot = lot;
        }

        @Override
        public void run() {
            try {
                int lifeTime = lot.getLifeTime();
                TimeUnit.SECONDS.sleep(lifeTime);
            } catch (InterruptedException e) {
                LOGGER.error("Error waiting for the end of the bidding lot : {}", lot.getId());
            } finally {
                lot.setState(State.TERMINATED);
            }
        }
    }
}
