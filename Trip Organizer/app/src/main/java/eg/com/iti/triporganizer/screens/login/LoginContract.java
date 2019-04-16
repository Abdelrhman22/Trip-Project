package eg.com.iti.triporganizer.screens.login;

interface LoginContract {

    interface ILoginView {
        void loginDoneSuccessfully();
        void loginFailed();
        void showValidationErrorMessage();
        void enterEmailMessage();
        void enterPasswordMessage();
        void showProgressBar();
        void hideProgressBar();
    }

    interface ILoginPresenter {
        void saveInSharedPreferences(String email, String password);
        boolean login(String email, String password);
        boolean checkIfUserLoggedInBefore(String email, String password);
    }


}
