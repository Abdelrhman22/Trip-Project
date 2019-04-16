package eg.com.iti.triporganizer.screens.register;

import eg.com.iti.triporganizer.model.UserDTO;

public interface RegistrationContract {
    interface RegistrationView{
        void respondToSuccessfulRegistration();
        void respondToFailedRegistration(String errorMsg);
        void showProgress();
        void hideProgress();
    }
    interface RegistrationPresenter{
        void registerUser(UserDTO userDTO);
        void notifyViewWithSuccessfulRegistration();
        void notifyViewWithFailedRegistration(String errorMsg);
    }
}
