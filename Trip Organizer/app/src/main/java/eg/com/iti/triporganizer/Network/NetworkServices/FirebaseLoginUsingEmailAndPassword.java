package eg.com.iti.triporganizer.Network.NetworkServices;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import eg.com.iti.triporganizer.screens.login.LoginContract;

public class FirebaseLoginUsingEmailAndPassword {
    LoginContract.LoginPresenter loginPresenter;



    public FirebaseLoginUsingEmailAndPassword(LoginContract.LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    public void loginWithEmailAndPassword(String userEmail, String userPassword , FirebaseAuth firebaseAuth) {
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        boolean emailVerified = user.isEmailVerified();
                        if (emailVerified)
                            loginPresenter.notifyViewWithSuccessfulLogin();
                        else
                            loginPresenter.notifyViewWithUnverifiedEmail();
                    }

                } else
                    loginPresenter.notifyViewWithFailedLogin();
            }
        });
    }

}
