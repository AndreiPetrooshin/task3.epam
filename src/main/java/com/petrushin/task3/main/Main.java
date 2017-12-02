package com.petrushin.task3.main;

import com.petrushin.task3.domain.*;
import com.petrushin.task3.service.*;
import com.petrushin.task3.service.impl.*;
import com.petrushin.task3.service.parsers.*;
import com.petrushin.task3.service.parsers.impl.LotParserImpl;
import com.petrushin.task3.service.parsers.impl.UserParserImpl;
import com.petrushin.task3.service.printer.PrintServiceFactory;
import java.util.List;
import java.util.ResourceBundle;

public class Main {

    private static final String PATH_TO_USER_JSON = "./users.json";
    private static final String PATH_TO_LOT_JSON = "./lots.json";
    private static final String PROPERTIES_PATH = "config";
    private static final String PRINTER = "printer";

    public static void main(String[] args){
        initPrinter();
        Auction auction = Auction.getInstance();
        initAuction(auction);

        UserService userService = new UserServiceImpl();
        LotService lotService = new LotServiceImpl(userService);
        AuctionService auctionService = new AuctionServiceImpl(lotService);
        auctionService.startTrade(auction);
    }

    private static void initAuction(Auction auction) {
        Parser<User> userParser = new UserParserImpl();
        List<User> users = userParser.parse(PATH_TO_USER_JSON);
        auction.setUsers(users);

        Parser<Lot> lotParser = new LotParserImpl();
        List<Lot> lots = lotParser.parse(PATH_TO_LOT_JSON);
        auction.setLots(lots);
    }

    private static void initPrinter() {
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_PATH);
        PrintServiceFactory factory = new PrintServiceFactory();
        String printer = bundle.getString(PRINTER);
        factory.initPrintService(printer);
    }

}
