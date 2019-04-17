package eg.com.iti.triporganizer.screens.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.internal.ILocationSourceDelegate;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.screens.home.HomeActivity;
import eg.com.iti.triporganizer.screens.register.RegistrationActivity;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    TextView signupTextView;
    Button loginButton;
    EditText emailEditText;
    EditText passwordEditText;
    ProgressBar progressBar;
    LoginPresenter presenter;

    boolean loginResult;

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


    }

    @Override
    public void loginDoneSuccessfully() {
        Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

        @Override
        public void loginFailed () {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void showValidationErrorMessage () {
            Toast.makeText(this, "Wrong Username or Password!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void enterEmailMessage () {
            Toast.makeText(this, "Please Enter yor email!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void enterPasswordMessage () {
            Toast.makeText(this, "Please Enter your password!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void showProgressBar () {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void hideProgressBar () {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
