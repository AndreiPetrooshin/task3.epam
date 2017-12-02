package com.petrushin.task3.service.printer;

import java.io.IOException;
import java.io.OutputStream;

public class FilePrintService extends PrintService {

    private OutputStream stream;

    public FilePrintService(OutputStream stream) {
        this.stream = stream;
    }

    @Override
    public void printMessage(String str) {
        try {
            str = str.concat("\n");
            byte[] byteArray = str.getBytes();
            stream.write(byteArray);
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
