package eg.com.iti.triporganizer.screens.login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import eg.com.iti.triporganizer.Network.NetworkServices.SignInWithFirebase;
import eg.com.iti.triporganizer.screens.home.HomeActivity;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView loginView;
    boolean loggedIn;
    private  Context context;
    SignInWithFirebase signInWithFirebaseObject;
    SharedPreferencesHelper helper;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        this.context = (Context) loginView;
        signInWithFirebaseObject = new SignInWithFirebase(loginView);
        helper = new SharedPreferencesHelper(loginView,context);
    }

    @Override
    public boolean login(String email, String password) {
        //saveInSharedPreferences(email,password);
        boolean flag = false;
        if(email.isEmpty() && password.isEmpty()) {
            loginView.showValidationErrorMessage();
            flag = false;
        }
        else if(email.isEmpty()) {
            loginView.enterEmailMessage();
            flag = false;
        }
        else if(password.isEmpty()) {
            loginView.enterPasswordMessage();
            flag = false;
        }
        else if(email!=null && password!=null) {
            signInWithFirebaseObject.signIn(email,password);
            loggedIn = checkIfUserLoggedInBefore(email,password);
            flag = true;
        }
        return flag;

    }

    @Override
    public void signInWithGoogle(GoogleSignInAccount account) {
        signInWithFirebaseObject.firebaseAuthWithGoogle(account);
    }

    @Override
    public void saveInSharedPreferences(String email, String password) {
        helper.saveInSharedPreferences(email,password);
    }


    @Override
    public boolean checkIfUserLoggedInBefore(String email, String password) {
        helper.checkIfUserLoggedInBefore();
        return true;

    }


}
