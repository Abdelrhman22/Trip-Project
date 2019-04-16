package eg.com.iti.triporganizer.screens.login;

interface ILoginView {
    void loginDoneSuccessfully();
    void loginFailed();
    void showValidationErrorMessage();
    void enterEmailMessage();
    void enterPasswordMessage();
    void showProgressBar();
    void hideProgressBar();
}
