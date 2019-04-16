package eg.com.iti.triporganizer.utils;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//to validate user registration data against regex
public class UserDataValidation {


    public boolean validateEmail(String userEmail) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(userEmail);
        return matcher.matches();
    }

    public boolean validatePassword(String userPassword) {
        return userPassword.length() >= 6;
    }


    public boolean validatePasswordConfirmation(String userPasswordConfirmation, String userPassword) {
        return userPasswordConfirmation.equals(userPassword);
    }


    public boolean validatePhoneNum(String userPhoneNum) {
        Pattern pattern = Pattern.compile("(01)[0-9]{9}");
        Matcher matcher = pattern.matcher(userPhoneNum);
        return matcher.matches();
    }
}


