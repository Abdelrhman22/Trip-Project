package eg.com.iti.triporganizer.screens.register;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import eg.com.iti.triporganizer.R;
import eg.com.iti.triporganizer.model.UserDTO;
import eg.com.iti.triporganizer.utils.NetworkUtilities;
import eg.com.iti.triporganizer.utils.UserDataValidation;

public class RegistrationActivity extends AppCompatActivity implements RegistrationContract.RegistrationView {

    //-----------------------components---------------------------
    private TextInputLayout nameWrapper;
    private TextInputLayout emailWrapper;
    private TextInputLayout passwordWrapper;
    private TextInputLayout passwordConfirmationWrapper;
    private TextInputLayout phoneNumWrapper;
    private ProgressBar progressBar;
    private Button signUpButton;
    //-------------------------values------------------------------
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userPasswordConfirmation;
    private String userPhoneNum;

    //-------------------------------------------------------------
    RegistrationContract.RegistrationPresenter registrationPresenter;
    UserDTO regisetringUser;
    UserDataValidation userDataValidation;
    boolean check;


    public RegistrationActivity() {
        this.userDataValidation = new UserDataValidation();
        registrationPresenter = new RegistrationPresenterImpl(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initializeComponents();
        addingListeners();
    }

    private void initializeComponents() {
        nameWrapper = findViewById(R.id.nameTextInputLayout);
        emailWrapper = findViewById(R.id.emailWrapper);
        passwordWrapper = findViewById(R.id.passwordWrapper);
        passwordConfirmationWrapper = findViewById(R.id.confirmPasswordTextInputLayout);
        phoneNumWrapper = findViewById(R.id.phoneTextInputLayout);
        signUpButton = findViewById(R.id.signUpButton);
        progressBar = findViewById(R.id.progress);
    }


    private void addingListeners() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(NetworkUtilities.isOnline(RegistrationActivity.this))
                        register();
                else
                    Toast.makeText(RegistrationActivity.this, "Check your internet connection please", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private boolean getValues() {
        boolean done = true;
        if (nameWrapper.getEditText() != null) {
            userName = nameWrapper.getEditText().getText().toString();
            nameWrapper.setError(null);
            if (userName.trim().length() == 0) {
                nameWrapper.setError("Name is required");
                done = false;
            } else
                done = true;
        }

        if (emailWrapper.getEditText() != null) {
            userEmail = emailWrapper.getEditText().getText().toString();
            emailWrapper.setError(null);
            if (userEmail.trim().length() == 0) {
                emailWrapper.setError("Email is required");
                done = false;
            } else {
                check = userDataValidation.validateEmail(userEmail);
                if (!check) {
                    emailWrapper.setError("Enter Valid Email");
                    done = false;
                } else
                    done = true;

            }
        }

        if (passwordWrapper.getEditText() != null) {
            userPassword = passwordWrapper.getEditText().getText().toString();
            passwordWrapper.setError(null);
            if (userPassword.length() == 0) {
                passwordWrapper.setError("Password is required");
                done = false;
            } else {
                check = userDataValidation.validatePassword(userPassword);
                if (!check) {
                    passwordWrapper.setError("Password should be of at least 6 characters");
                    done = false;
                } else
                    done = true;
            }
        }
        if (passwordConfirmationWrapper.getEditText() != null) {
            userPasswordConfirmation = passwordConfirmationWrapper.getEditText().getText().toString();
            passwordConfirmationWrapper.setError(null);
            if (userPasswordConfirmation.length() == 0) {
                passwordConfirmationWrapper.setError("Password confirmation is required");
                done = false;
            } else {
                check = userDataValidation.validatePasswordConfirmation(userPasswordConfirmation, userPassword);
                if (!check) {
                    passwordConfirmationWrapper.setError("Password confirmation doesn't match password");
                    done = false;
                } else
                    done = true;
            }
        }
        if (phoneNumWrapper.getEditText() != null) {
            userPhoneNum = phoneNumWrapper.getEditText().getText().toString();
            phoneNumWrapper.setError(null);
            if (userPhoneNum.trim().length() == 0) {
                phoneNumWrapper.setError("Phone number is required");
                done = false;
            } else {
                check = userDataValidation.validatePhoneNum(userPhoneNum);
                if (!check) {
                    phoneNumWrapper.setError("Enter valid mobile number");
                    done = false;
                } else
                    done = true;
            }
        }
        return done;
    }


    @Override
    public void respondToSuccessfulRegistration() {
        hideProgress();
        Toast.makeText(this, "Registered Successfully,Please verify your email", Toast.LENGTH_SHORT).show();
        cleanFields();
    }


    @Override
    public void respondToFailedRegistration(String errorMsg) {
        hideProgress();
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();

    }
    private void register(){
        boolean dataInsertedCorrectly = getValues();
        if (dataInsertedCorrectly) {
            showProgress();
            regisetringUser = new UserDTO(userName, userEmail, userPassword, userPhoneNum);
            registrationPresenter.registerUser(regisetringUser);
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private void cleanFields() {
        if (nameWrapper.getEditText() != null)
            nameWrapper.getEditText().setText("");
        if (emailWrapper.getEditText() != null)
            emailWrapper.getEditText().setText("");
        if (passwordWrapper.getEditText() != null)
            passwordWrapper.getEditText().setText("");
        if (passwordConfirmationWrapper.getEditText() != null)
            passwordConfirmationWrapper.getEditText().setText("");
        if (phoneNumWrapper.getEditText() != null)
            phoneNumWrapper.getEditText().setText("");

    }

}
