package Utilitys;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import domain.Faculty;

import java.util.Map;


public abstract class Validator {
    public static boolean isValidEmailAddress(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException _) {
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

    public static boolean isValidString(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }

        return true;
    }

    public static boolean isValidFaculty(String name, String code, Map<String, Faculty> facultyMap) {
        if (facultyMap.containsKey(code) || facultyMap.containsValue(name)) {
            return false;
        }

        return true;
    }


}
