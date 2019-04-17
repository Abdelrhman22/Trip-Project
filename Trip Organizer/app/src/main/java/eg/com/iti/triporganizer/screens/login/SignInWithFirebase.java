package eg.com.iti.triporganizer.screens.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import eg.com.iti.triporganizer.screens.register.RegistrationActivity;


public class SignInWithFirebase {

    FirebaseAuth mAuth;
    static final String TAG = "LOGIN";
    ILoginView loginView;
    GoogleSignInAccount googleSignInAccount;


    public SignInWithFirebase(ILoginView loginView) {
        this.loginView = loginView;
        mAuth = FirebaseAuth.getInstance();
    }

    //Sign-in with email & password
    public void signIn(String emailStr, String passwordStr) {

        mAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener((Activity) loginView, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("msg", "2abl el task is successful");
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginView.loginDoneSuccessfully();

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            loginView.loginFailed();
                        }
                    }
                });
    }

    //Sign-in with Google Account
    void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        googleSignInAccount = acct;
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) loginView, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }



}
