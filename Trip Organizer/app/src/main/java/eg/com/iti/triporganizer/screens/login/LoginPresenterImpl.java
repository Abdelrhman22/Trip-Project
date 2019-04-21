package eg.com.iti.triporganizer.screens.login;

import com.google.firebase.auth.FirebaseAuth;

import eg.com.iti.triporganizer.Network.NetworkServices.FirebaseLoginUsingEmailAndPassword;
import eg.com.iti.triporganizer.utils.SharedPreferencesManager;

public class LoginPresenterImpl implements LoginContract.LoginPresenter {

    private LoginContract.LoginView loginView;
    private FirebaseLoginUsingEmailAndPassword firebaseLoginUsingEmailAndPassword;
    private SharedPreferencesManager sharedPreferencesManager;

    public LoginPresenterImpl(LoginContract.LoginView loginView) {
        this.loginView = loginView;
        firebaseLoginUsingEmailAndPassword = new FirebaseLoginUsingEmailAndPassword(this);
        sharedPreferencesManager=new SharedPreferencesManager(this,loginView);

    }

    @Override
    public void loginWithEmailAndPassword(String userEmail, String userPassword , FirebaseAuth firebaseAuth) {
        firebaseLoginUsingEmailAndPassword.loginWithEmailAndPassword(userEmail, userPassword, firebaseAuth);
    }


    @Override
    public void notifyViewWithSuccessfulLogin() {
        loginView.respondToSuccessfulLogin();
    }

    @Override
    public void notifyViewWithFailedLogin() {
        loginView.respondToFailedLogin();
    }

    @Override
    public void notifyViewWithUnverifiedEmail() {
        loginView.respondToUnverifiedEmail();
    }

    @Override
    public boolean checkIfLoggedIn() {
        return sharedPreferencesManager.checkRememberMe();
    }

    @Override
    public void notifySharedPreferencesManagerToSetRememberMe() {
        sharedPreferencesManager.setRememberMe();
    }

    @Override
    public void removeRememberMeFromSharedPreferences() {
        sharedPreferencesManager.removeRememberMe();
    }
}
