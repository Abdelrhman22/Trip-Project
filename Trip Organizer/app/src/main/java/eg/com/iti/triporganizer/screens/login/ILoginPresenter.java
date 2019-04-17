package eg.com.iti.triporganizer.screens.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

interface ILoginPresenter {
    void saveInSharedPreferences(String email, String password);
    boolean login(String email, String password);
    void signInWithGoogle(GoogleSignInAccount account);
    boolean checkIfUserLoggedInBefore(String email, String password);
}
