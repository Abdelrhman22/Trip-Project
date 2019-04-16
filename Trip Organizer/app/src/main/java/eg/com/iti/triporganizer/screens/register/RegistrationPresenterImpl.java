package eg.com.iti.triporganizer.screens.register;

import eg.com.iti.triporganizer.Network.NetworkServices.FirebaseAuthenticationUsingEmailAndPassword;
import eg.com.iti.triporganizer.model.UserDTO;
import eg.com.iti.triporganizer.model.helpers.firebase_helpers.FireBaseHelper;

public class RegistrationPresenterImpl implements RegistrationContract.RegistrationPresenter{
    private RegistrationContract.RegistrationView registrationView;
    private FireBaseHelper fireBaseHelper;
    private FirebaseAuthenticationUsingEmailAndPassword firebaseAuthenticationUsingEmailAndPassword;

    public RegistrationPresenterImpl(RegistrationContract.RegistrationView registrationView) {
        this.registrationView=registrationView;
        fireBaseHelper = new FireBaseHelper();
        firebaseAuthenticationUsingEmailAndPassword=new FirebaseAuthenticationUsingEmailAndPassword(this);
    }

    @Override
    public void registerUser(UserDTO userDTO) {
        firebaseAuthenticationUsingEmailAndPassword.registerUser(userDTO);
    }

    @Override
    public void notifyViewWithSuccessfulRegistration() {
        registrationView.respondToSuccessfulRegistration();
    }

    @Override
    public void notifyViewWithFailedRegistration(String errorMsg) {
        registrationView.respondToFailedRegistration(errorMsg);

    }
}
