package eg.com.iti.triporganizer.screens.login;

public interface ILoginView {
    void loginDoneSuccessfully();
    void loginFailed();
    void showValidationErrorMessage();
    void enterEmailMessage();
    void enterPasswordMessage();
}
