package eg.com.iti.triporganizer.screens.login;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private ILoginView loginView;
    private Context context;
    SharedPreferences userLoginSettings;

    public SharedPreferencesHelper (ILoginView loginView, Context context) {
        this.loginView = loginView;
        this.context = (Context)loginView;
    }

    public void saveInSharedPreferences(String email, String password) {
        userLoginSettings = context.getSharedPreferences("UserInfo", context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = userLoginSettings.edit();
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.commit();
    }


    public boolean checkIfUserLoggedInBefore(String email, String password) {
        String savedUsername = context.getSharedPreferences("UserInfo", context.MODE_PRIVATE).getString("Username", "false");
        String savedPassword = context.getSharedPreferences("UserInfo", context.MODE_PRIVATE).getString("Password", "false");
//        while(savedUsername.equals(email) && savedPassword.equals(password))
//        {
//            loggedIn = true;
//        }
        return true;
    }


}
