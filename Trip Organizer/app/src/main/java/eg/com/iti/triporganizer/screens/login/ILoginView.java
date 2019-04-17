package eg.com.iti.triporganizer.screens.login;

import com.google.firebase.auth.FirebaseAuth;

public interface ILoginView {
    void loginDoneSuccessfully();
    void loginFailed();
    void showValidationErrorMessage();
    void enterEmailMessage();
    void enterPasswordMessage();
    void checkEmailVerification(FirebaseAuth mAuth);
}
