package eg.com.iti.triporganizer.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.screens.home.HomeActivity;
import eg.com.iti.triporganizer.screens.register.RegistrationActivity;
import eg.com.iti.triporganizer.utils.UserDataValidation;


public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    Button login;
    TextInputLayout emailWrapper;
    TextInputLayout passwordWrapper;
    SignInButton googleSignIn;
    TextView signUp;
    LoginContract.LoginPresenter loginPresenter;
    //--------------------------------------------------------------
    String userEmail, userPassword;
    UserDataValidation userDataValidation;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "progress";
    boolean dataInserted;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        loginPresenter = new LoginPresenterImpl(this);

        boolean loggedIn = loginPresenter.checkIfLoggedIn();
        if (loggedIn) {
            respondToRememberUser();
        }

        initComponents();
        addingListeners();
        //google sign in
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);

    }

    private void respondToRememberUser() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    private void initComponents() {
        login = findViewById(R.id.loginButton);
        googleSignIn = findViewById(R.id.sign_in_button);
        emailWrapper = findViewById(R.id.emailWrapper);
        passwordWrapper = findViewById(R.id.passwordWrapper);
        signUp = findViewById(R.id.signup);
    }


    private void addingListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserEmailAndPassword();
                if (dataInserted)
                    loginPresenter.loginWithEmailAndPassword(userEmail, userPassword, firebaseAuth);
            }
        });

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginPresenter.signUpWithGoogle();
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegistrationScreen();
            }
        });

    }

    private void getUserEmailAndPassword() {
        if (emailWrapper.getEditText() != null) {
            dataInserted = false;
            userEmail = emailWrapper.getEditText().getText().toString();
            if (userEmail.trim().equals("")) {
                emailWrapper.setErrorEnabled(true);
                emailWrapper.setError("Email is required");
            } else {
                userDataValidation = new UserDataValidation();
                boolean validEmail = userDataValidation.validateEmail(userEmail);
                if (!validEmail){
                    emailWrapper.setErrorEnabled(true);
                    emailWrapper.setError("Enter valid email please");
                }
                else
                    dataInserted = true;
            }
        }
        if (passwordWrapper.getEditText() != null) {
            dataInserted = false;
            userPassword = passwordWrapper.getEditText().getText().toString();
            if (userPassword.trim().equals("")){
                passwordWrapper.setErrorEnabled(true);
                passwordWrapper.setError("Password is required");}
            else {
                userDataValidation = new UserDataValidation();
                boolean validPassword = userDataValidation.validatePassword(userPassword);
                if (!validPassword){
                    passwordWrapper.setErrorEnabled(true);
                    passwordWrapper.setError("Password should be of at least 6 characters");}
                else
                    dataInserted = true;
            }
        }
    }

    private void goToRegistrationScreen() {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }


    @Override
    public void respondToSuccessfulLogin() {
        loginPresenter.notifySharedPreferencesManagerToSetRememberMe();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void respondToFailedLogin() {
        Toast.makeText(this, "Please enter valid email and password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void respondToUnverifiedEmail() {
        Toast.makeText(this, "Please Verify your Email!", Toast.LENGTH_SHORT).show();
        firebaseAuth.signOut();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //google
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null)
                    firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //sign in google
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            loginPresenter.notifySharedPreferencesManagerToSetRememberMe();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Failed to sign in with google", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void updateUI() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}