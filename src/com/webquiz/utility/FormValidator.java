package com.webquiz.utility;

public final class FormValidator {

    public static boolean validateEmptyField(String formField) {
        if (formField.equals(""))
            return false;
        else
            return true;
    }

    public static boolean validatePassword(String password) {
        if (password.equals(""))
            return false;
        if (password.length() < 8)
            return false;
        if (!password.matches(".*[0-9].*"))
            return false;
        if (!password.matches(".*[a-z].*"))
            return false;
        if (!password.matches(".*[A-Z].*"))
            return false;
    
        return true;
    }

}
