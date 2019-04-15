package eg.com.iti.triporganizer.model;

import android.util.Patterns;

// Arafa added UserDTO which contains user Details in Register Activity .

public class UserDTO
{
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;

    public UserDTO( String name, String email, String password, String phone )
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
    public UserDTO(String id, String name, String email, String password, String phone ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }


    public boolean isPasswordLengthGreaterThan5() {
        return getPassword().length() > 5;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
