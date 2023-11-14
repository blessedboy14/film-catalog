package beans;

import org.mindrot.jbcrypt.BCrypt;

public class MethodsClass {
    public static String chooseErrorMessage(RESULT result) {
        return switch (result) {
            case INCORRECT_EMAIL -> "Email is incorrect";
            case INCORRECT_PHONE -> "Phone is incorrect";
            case EMAIL_USED -> "Email is in use, choose another";
            case PHONE_USED -> "Phone is in use, choose another";
            case PROPERTIES_ERROR -> "Shit happens, db.properties file don't want to be read";
            case EMAIL_NOT_USED -> "This email isn't registered yet";
            case PASS_INCORRECT -> "Your pass in incorrect";
            case ADD_FILM_PARAMETER_ERROR -> "Film parameters incorrect";
            case FILM_EXIST -> "This film is already exist";
            default -> "Weird message";
        };
    }
}
