package com.petrushin.task3.service.impl;

import com.petrushin.task3.domain.Lot;
import com.petrushin.task3.domain.User;
import com.petrushin.task3.service.printer.PrintService;
import com.petrushin.task3.service.UserService;

import java.util.Map;

public class UserServiceImpl implements UserService {

    private static final double PERCENTAGE_TO_BUY = 0.7;
    private static final double BET_STEP = 1.05;

    /**
     * This method makes bet for lot if user have
     * enough money
     *
     * @param user - user {@link User}
     * @param lot  - lot {@link Lot}
     */
    @Override
    public void makeBetForLot(User user, Lot lot) {
        Map<User, Integer> userMap = lot.getUserMap();

        int priceLot = lot.getPrice();
        int price = userMap.get(user);
        if (price == priceLot) {
            return;
        }

        int userCash = user.getCash();
        int cashToSpend = (int) (userCash * PERCENTAGE_TO_BUY);
        int lotNewPrice = (int) (priceLot * BET_STEP);
        if (lotNewPrice <= cashToSpend) {
            lot.setPrice(lotNewPrice);
            userMap.put(user, lotNewPrice);
            PrintService.print(user + " makes rise bet to "
                    + lotNewPrice + " in lot: " + lot.getId());
        }
    }
}
