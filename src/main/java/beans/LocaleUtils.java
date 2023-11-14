package beans;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleUtils {

    private static final String BUNDLE_NAME = "locale";

    public static ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }

    public static String getString(String key, Locale locale) {
        ResourceBundle bundle = getBundle(locale);
        return bundle.getString(key);
    }

    public static String getString(String key) {
        return getString(key, Locale.getDefault());
    }

}