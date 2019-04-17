package eg.com.iti.triporganizer.Network.NetworkServices;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import eg.com.iti.triporganizer.model.UserDTO;
import eg.com.iti.triporganizer.screens.register.RegistrationContract;

public class FirebaseAuthenticationUsingEmailAndPassword {
    private FirebaseAuth firebaseAuth;
    private RegistrationContract.RegistrationPresenter registrationPresenter;

    public FirebaseAuthenticationUsingEmailAndPassword(RegistrationContract.RegistrationPresenter registrationPresenter) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.registrationPresenter = registrationPresenter;
    }

    public void registerUser(UserDTO registeringUser) {
        //create account
        firebaseAuth.createUserWithEmailAndPassword(registeringUser.getUserEmail(), registeringUser.getUserPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        registeringUser.setUserID(user.getUid());
                        user.sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            registrationPresenter.notifyViewWithSuccessfulRegistration();
                                        } else
                                            registrationPresenter.notifyViewWithFailedRegistration(task.getException().getMessage());
                                    }
                                });
                    }
                } else
                    registrationPresenter.notifyViewWithFailedRegistration(task.getException().getMessage());
            }
        });
    }
}