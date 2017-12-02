package com.petrushin.task3.service.printer;

public abstract class PrintService {

    private static PrintService instance;

    public static void init(PrintService printService){
        if(instance==null){
            instance = printService;
        }
    }

    public abstract void printMessage(String str);

    public static void print(String str){
        instance.printMessage(str);
    }

}
