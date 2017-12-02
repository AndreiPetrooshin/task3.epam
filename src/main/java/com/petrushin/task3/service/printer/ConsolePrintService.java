package com.petrushin.task3.service.printer;

public class ConsolePrintService extends PrintService{

    @Override
    public void printMessage(String str) {
        System.out.println(str);
    }
}
