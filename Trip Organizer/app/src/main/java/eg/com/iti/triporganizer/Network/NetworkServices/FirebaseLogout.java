package eg.com.iti.triporganizer.Network.NetworkServices;

import com.google.firebase.auth.FirebaseAuth;

import eg.com.iti.triporganizer.screens.home.HomeContract;

public class FirebaseLogout {
    HomeContract.HomePresenter homePresenter;

    public FirebaseLogout(HomeContract.HomePresenter homePresenter) {
        this.homePresenter = homePresenter;
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        homePresenter.notifyViewWithSuccessfulSignOut();
    }
}
