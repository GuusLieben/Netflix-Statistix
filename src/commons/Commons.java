package com.netflix.commons;

import java.awt.*;
import java.io.*;
import java.security.*;
import java.text.*;
import java.time.*;
import java.util.logging.*;

import static javax.xml.bind.DatatypeConverter.*;

public class Commons {

    // Instantiate a logger for the entire project
    public static final Logger logger = Logger.getLogger("Netflix");

    static {
        try {
            String dateTime =
                    String.format("d.%s-t.%s", LocalDate.now().toString(), LocalTime.now().toString()).replace(":", "-");
            dateTime.replace(" ", ".");
            FileHandler handler = new FileHandler("logs/log-" + dateTime + ".log", true);
            Commons.logger.addHandler(handler);
        } catch (IOException e) {
            Commons.exception(e);
        }
    }

    public static String percentage(double num) {
        return new DecimalFormat("#.0").format(num);
    }

    // Exception handle
    public static void exception(Exception ex) {
        String stack = "";
        for (StackTraceElement el : ex.getStackTrace()) stack += el.toString() + "\n";
        // Print the suspected cause
        logger.severe(String.format("%s\nSuspected cause : %s\n%s", ex.getMessage(), ex.getCause(), stack));
    }

    public static String hashSHA256(String password) {
        try {
            // Set digest to use SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Set digest to convert given password
            md.update(password.getBytes());

            // Return it as a string
            return printHexBinary(md.digest()).toLowerCase();
        } catch (NoSuchAlgorithmException ex) {
            // In case SHA-256 is somehow not found
            Commons.exception(ex);
        }
        return null;
    }

    public static void clearPane(Container con) {
        // Clears a container
        con.removeAll();
        con.repaint();
        con.revalidate();
    }
}
