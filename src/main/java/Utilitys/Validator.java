package Utilitys;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;


public abstract class Validator {
    public static boolean isValidEmailAddress(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            return false;
        }

        return true;
    }

    public static boolean isValidPhoneNumber(String number, String countryCode) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber swissNumberProto = phoneUtil.parse(number, countryCode);
            return phoneUtil.isValidNumber(swissNumberProto);
        } catch (Exception e) {
            return false;
        }
    }
}
