package eg.com.iti.triporganizer.screens.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.internal.ILocationSourceDelegate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.screens.home.HomeActivity;
import eg.com.iti.triporganizer.screens.register.RegistrationActivity;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    TextView signupTextView;
    Button loginButton;
    SignInButton signInButton;
    EditText emailEditText;
    EditText passwordEditText;
    ProgressBar progressBar;
    LoginPresenter presenter;
    SignInWithFirebase signInWithFirebase;
    boolean loginResult;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "LOGIN";
    private static final int RC_SIGN_IN = 1;
    FirebaseAuth mAuth;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        signupTextView = findViewById(R.id.signupButton);
        loginButton = findViewById(R.id.loginButton);
        emailEditText = findViewById(R.id.emailInputEditText);
        passwordEditText = findViewById(R.id.passwordInputEditText);
        progressBar = findViewById(R.id.progressBar);
        signInButton = findViewById(R.id.sign_in_button);

        mAuth = FirebaseAuth.getInstance();

        //Not A Member Yet? action, to redirect user to sign up page.
        signupTextView.setOnClickListener((v) -> {
            Intent outgoingIntentToRegisteration = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(outgoingIntentToRegisteration);

        });

        loginButton.setOnClickListener((v) -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            Log.i("msg", "Entered Login");
            loginResult = presenter.login(email, password);


        });
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setOnClickListener((v) -> {

            switch (v.getId()) {
                case R.id.sign_in_button:
                    signIn();
                    break;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.signInWithGoogle(account);
                loginDoneSuccessfully();
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                loginFailed();
            }
        }
    }

    @Override
    public void loginDoneSuccessfully() {
        Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFailed() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showValidationErrorMessage() {
        Toast.makeText(this, "Wrong Username or Password!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void enterEmailMessage() {
        Toast.makeText(this, "Please Enter your email!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void enterPasswordMessage() {
        Toast.makeText(this, "Please Enter your password!", Toast.LENGTH_SHORT).show();
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


}