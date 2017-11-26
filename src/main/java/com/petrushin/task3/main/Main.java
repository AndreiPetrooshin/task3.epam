package com.petrushin.task3.main;

import com.petrushin.task3.domain.*;
import com.petrushin.task3.service.*;
import com.petrushin.task3.service.impl.*;
import com.petrushin.task3.service.parsers.*;
import java.util.List;

public class Main {

    private static final String PATH_TO_USER_JSON = "./src/main/resources/users.json";
    private static final String PATH_TO_LOT_JSON = "./src/main/resources/lots.json";

    public static void main(String[] args) {

        Auction auction = Auction.getInstance();

        List<User> users = new UserParser().parse(PATH_TO_USER_JSON);
        List<Lot> lots = new LotParser().parse(PATH_TO_LOT_JSON);

        auction.setLots(lots);
        auction.setUsers(users);

        UserService userService = new UserServiceImpl();
        LotService lotService = new LotServiceImpl(userService);
        AuctionService auctionService = new AuctionServiceImpl(lotService);
        auctionService.startTrade(auction);
    }

}
