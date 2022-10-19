package nure.kazantseva.mycar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nure.kazantseva.mycar.db.DbHelperLogin;
import nure.kazantseva.mycar.model.User;
import nure.kazantseva.mycar.utils.InputValidator;

public class Register extends AppCompatActivity {

    EditText name, email, password;
    DbHelperLogin dbHelperLogin;
    InputValidator inputValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    public void init(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        dbHelperLogin = new DbHelperLogin(this.getApplicationContext());
        inputValidator = new InputValidator(this.getApplicationContext());
    }
    public void onClickCreateAccount(View view) {
        verify();
    }

    private void verify() {
        if(!inputValidator.isInputEditTextFilled(email)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(password)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(name)){
            return;
        }
        if(!inputValidator.isInputEditTextEmail(email)){
            return;
        }
        if(!dbHelperLogin.checkUser(email.getText().toString().trim())){
            User user = new User();
            user.setName(name.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());

            dbHelperLogin.addUser(user);
            Toast.makeText(this.getApplicationContext(),"New account created!",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,MainActivity.class);
            this.finish();
            startActivity(intent);
        }else{
            Toast.makeText(this.getApplicationContext(),"User with such email is already exist!",Toast.LENGTH_LONG).show();
        }
    }
}