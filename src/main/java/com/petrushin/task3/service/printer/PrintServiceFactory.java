package com.petrushin.task3.service.printer;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class PrintServiceFactory {

    private static final Logger LOGGER = LogManager.getLogger(PrintServiceFactory.class);

    private static final String PRINT_FILE_PATH = "print.file.path";
    private static final String CONSOLE = "CONSOLE";
    private static final String FILE = "FILE";
    private static final String PROPERTIES_PATH = "config";
    private PrintService printService;

    public PrintService initPrintService(String string) {
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_PATH);

        if (CONSOLE.equalsIgnoreCase(string)) {
            printService = new ConsolePrintService();
        } else if (FILE.equalsIgnoreCase(string)) {
            try {
                String filePath = bundle.getString(PRINT_FILE_PATH);
                OutputStream outputStream = new FileOutputStream(filePath);
                printService = new FilePrintService(outputStream);

            } catch (FileNotFoundException e) {
                LOGGER.error("Exception at initPrintService method: {}");
            }
        } else {
            throw new NoSuchElementException();
        }
        PrintService.init(printService);
        return printService;
    }
}
