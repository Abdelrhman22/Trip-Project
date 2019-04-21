package eg.com.iti.triporganizer.screens.home;

import eg.com.iti.triporganizer.Network.NetworkServices.FirebaseLogout;
import eg.com.iti.triporganizer.utils.SharedPreferencesManager;

public class HomePresenterImpl implements HomeContract.HomePresenter {
    HomeContract.HomeView homeView;
    FirebaseLogout firebaseLogout;
    SharedPreferencesManager sharedPreferencesManager;
    public HomePresenterImpl(HomeContract.HomeView homeView) {
        this.homeView=homeView;
        firebaseLogout=new FirebaseLogout(this);
    }

    @Override
    public void signOut() {
        firebaseLogout.signOut();
    }

    @Override
    public void notifyViewWithSuccessfulSignOut() {
        homeView.respondToSuccessfulSignOut();
    }

}
