package com.petrushin.task3.service.impl;

import com.petrushin.task3.domain.Lot;
import com.petrushin.task3.domain.State;
import com.petrushin.task3.domain.User;
import com.petrushin.task3.service.LotService;
import com.petrushin.task3.service.printer.PrintService;
import com.petrushin.task3.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LotServiceImpl implements LotService {

    private static final Logger LOGGER = LogManager.getLogger(LotServiceImpl.class);
    private static final int TIME_FOR_BET = 300;

    private Lock lock;
    private UserService userService;

    public LotServiceImpl(UserService userService) {
        this.userService = userService;
        this.lock = new ReentrantLock();
    }


    /**
     * This method takes the User Set from lot and
     * while Lot State is TRADING, it calls the
     * makeBetForLot from {@link UserServiceImpl} to each one user.
     *
     * @param lot - current lot {@link Lot}.
     */
    @Override
    public void tradeLot(Lot lot) {
        while (lot.getState() == State.TRADING) {
            Set<User> users = lot.getUserSet();
            waitForBet(lot);
            for (User user : users) {
                userService.makeBetForLot(user, lot);
            }
        }
        identifyLotWinner(lot);
    }

    private void waitForBet(Lot lot) {
        try {
            TimeUnit.MILLISECONDS.sleep(TIME_FOR_BET);
        } catch (InterruptedException e) {
            LOGGER.error("Interrupt Error in the trade lot cycle, lot: {}", lot);
        }
    }


    /**
     * This method identify lot Winner by User last bet, and
     * shows it to the console.
     * But sometimes, when a User makes a bet for lot, at the
     * same time he buys another lot, and after that he have no money to
     * buy this, at this case the lot is going on to another
     * user who has money.
     *
     * @param lot - the lot to identify winner.
     */
    private void identifyLotWinner(Lot lot) {

        try {
            lock.lock();
            Map<User, Integer> usersMap = lot.getUserMap();
            int maxBet = 0;
            User winner = null;
            for (Map.Entry<User, Integer> entry : usersMap.entrySet()) {
                int bet = entry.getValue();
                User user = entry.getKey();
                int userCash = user.getCash();
                boolean solvency = (userCash - bet) > 0;
                if (bet > maxBet && solvency) {
                    winner = entry.getKey();
                    maxBet = bet;
                }
            }
            if (winner == null) {
                PrintService.print("No one bet for this lot: " + lot);
                return;
            }
            winner.setCash(winner.getCash() - maxBet);
            PrintService.print(winner + " won lot: " + lot.getId()
                                + " - price: " + maxBet);
        } finally {
            lock.unlock();
        }
    }

}
