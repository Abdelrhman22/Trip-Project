package eg.com.iti.triporganizer.screens.login;

interface ILoginPresenter {
    void saveInSharedPreferences(String email, String password);
    boolean login(String email, String password);
    boolean checkIfUserLoggedInBefore(String email, String password);
}
