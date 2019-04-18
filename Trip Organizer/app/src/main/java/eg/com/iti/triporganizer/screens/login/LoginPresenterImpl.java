package eg.com.iti.triporganizer.screens.login;

import com.google.firebase.auth.FirebaseAuth;

import eg.com.iti.triporganizer.Network.NetworkServices.FirebaseLoginUsingEmailAndPassword;

public class LoginPresenterImpl implements LoginContract.LoginPresenter {

    LoginContract.LoginView loginView;
    FirebaseLoginUsingEmailAndPassword firebaseLoginUsingEmailAndPassword;

    public LoginPresenterImpl(LoginContract.LoginView loginView) {
        this.loginView = loginView;
        firebaseLoginUsingEmailAndPassword = new FirebaseLoginUsingEmailAndPassword(this);

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
}
