package edu.snhu.yaroslavameleshkevich.eventsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SessionManager is a utility class for managing user sessions.
 */
public class SessionManager {

    private static final String PREF_NAME = "EventsAppSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_EMAIL = "email";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    /**
     * A constructor for the SessionManager class.
     *
     * @param context a reference to the application context
     */
    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Create a login session.
     *
     * @param email the email of the user
     */
    public void createLoginSession(String email) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    /**
     * Get the email of the currently logged in user.
     *
     * @return a string containing the email of the user
     */
    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    /**
     * Check if the user is logged in.
     *
     * @return true if the user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /**
     * Clear the session details.
     */
    public void logout() {
        editor.clear();
        editor.apply();
    }

}
