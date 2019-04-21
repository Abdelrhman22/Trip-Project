package eg.com.iti.triporganizer.screens.login;

import com.google.firebase.auth.FirebaseAuth;

public interface LoginContract {

    interface LoginPresenter {
        void loginWithEmailAndPassword(String userEmail, String userPassword, FirebaseAuth firebaseAuth);

        void notifyViewWithSuccessfulLogin();

        void notifyViewWithFailedLogin();

        void notifyViewWithUnverifiedEmail();

        boolean checkIfLoggedIn();

        void notifySharedPreferencesManagerToSetRememberMe();

        void removeRememberMeFromSharedPreferences();

    }
    interface LoginView {

        void respondToSuccessfulLogin();

        void respondToFailedLogin();

        void respondToUnverifiedEmail();
    }
}
