package com.petrushin.task3.main;

import com.petrushin.task3.domain.*;
import com.petrushin.task3.service.*;
import com.petrushin.task3.service.impl.*;
import com.petrushin.task3.service.parsers.*;
import com.petrushin.task3.service.parsers.impl.LotParserImpl;
import com.petrushin.task3.service.parsers.impl.UserParserImpl;

import java.util.List;

public class Main {

    private static final String PATH_TO_USER_JSON = "./users.json";
    private static final String PATH_TO_LOT_JSON = "./lots.json";

    public static void main(String[] args) {

        Auction auction = Auction.getInstance();

        Parser<User> userParser = new UserParserImpl();
        Parser<Lot> lotParser = new LotParserImpl();
        List<User> users = userParser.parse(PATH_TO_USER_JSON);
        List<Lot> lots = lotParser.parse(PATH_TO_LOT_JSON);

        auction.setLots(lots);
        auction.setUsers(users);

        UserService userService = new UserServiceImpl();
        LotService lotService = new LotServiceImpl(userService);
        AuctionService auctionService = new AuctionServiceImpl(lotService);
        auctionService.startTrade(auction);
    }

}
