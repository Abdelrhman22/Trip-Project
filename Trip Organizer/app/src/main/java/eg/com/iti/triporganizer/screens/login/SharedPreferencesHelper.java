package eg.com.iti.triporganizer.screens.login;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static ILoginView loginView;
    private static Context context;
    static SharedPreferences userLoginSettings;

    public SharedPreferencesHelper(ILoginView loginView, Context context) {
        this.loginView = loginView;
        this.context = (Context) loginView;
    }

    public static void saveInSharedPreferences(String email, String password) {
        userLoginSettings = context.getSharedPreferences("UserInfo", context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = userLoginSettings.edit();
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.commit();
    }

    public static String getUserEmail(Context context) {
        return context.getSharedPreferences("UserInfo", context.MODE_PRIVATE).getString("Email", "");
    }


    public static boolean checkIfUserLoggedInBefore() {

        String retrievedEmail = context.getSharedPreferences("UserInfo", context.MODE_PRIVATE).getString("Email", "false");
        String retrievedPassword = context.getSharedPreferences("UserInfo", context.MODE_PRIVATE).getString("Password", "false");

        if (retrievedEmail.length() == 0 || retrievedPassword.length() == 0) {
            return false;
        } else {
            return true;
        }
    }


}

