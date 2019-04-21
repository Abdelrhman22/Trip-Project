package eg.com.iti.triporganizer.screens.home;

public interface HomeContract {
    interface HomeView {
        void respondToSuccessfulSignOut();
    }

    interface HomePresenter {
        void signOut();

        void notifyViewWithSuccessfulSignOut();
    }
}
