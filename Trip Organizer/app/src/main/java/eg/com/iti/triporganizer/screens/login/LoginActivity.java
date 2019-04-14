package eg.com.iti.triporganizer.screens.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.screens.register.RegistrationActivity;

public class LoginActivity extends AppCompatActivity {

    TextView signupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupTextView = findViewById(R.id.signupButton);

        //Not A Member Yet? action, to redirect user to sign up page.
        signupTextView.setOnClickListener((v)->{
            Intent outgoingIntentToRegisteration = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(outgoingIntentToRegisteration);

        });



    }
}
