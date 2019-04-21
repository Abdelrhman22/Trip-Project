package eg.com.iti.triporganizer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import eg.com.iti.triporganizer.screens.login.LoginContract;

import static android.content.Context.MODE_PRIVATE;

// SharedPreferences
// I will use this class to check Splash screen for the first time , check if user is login before
public class SharedPreferencesManager
{
    private SharedPreferences sharedPreferences;
    private LoginContract.LoginPresenter loginPresenter;
    private Context context;


    public SharedPreferencesManager(LoginContract.LoginPresenter loginPresenter, LoginContract.LoginView loginView) {
        this.loginPresenter = loginPresenter;
        this.context = (Context)loginView;
    }


    public boolean checkRememberMe() {
        sharedPreferences = context.getSharedPreferences("userData", MODE_PRIVATE);
        return sharedPreferences.getBoolean("RememberMe", false);
    }

    public void setRememberMe(){
        sharedPreferences = context.getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("RememberMe", true);
        editor.apply();
    }

    public void removeRememberMe() {
        sharedPreferences = context.getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("RememberMe", false);
        editor.apply();
    }
}
