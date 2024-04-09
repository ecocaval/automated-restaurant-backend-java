package com.automated.restaurant.automatedRestaurant.core.utils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailUtils {

    public static boolean stringIsAValidEmail(String email) {
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }
}
