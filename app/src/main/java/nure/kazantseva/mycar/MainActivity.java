package nure.kazantseva.mycar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nure.kazantseva.mycar.db.DbHelperLogin;
import nure.kazantseva.mycar.model.User;
import nure.kazantseva.mycar.utils.InputValidator;

public class MainActivity extends AppCompatActivity {
    private final AppCompatActivity activity = MainActivity.this;

    private EditText email, password;
    private InputValidator inputValidator;
    private DbHelperLogin dbHelperLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();
    }

    private void initObjects(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        inputValidator = new InputValidator(activity);
        dbHelperLogin = new DbHelperLogin(activity);
    }

    public void onClickLogIn(View view) {
        verify();
    }

    private void verify(){
        if(!inputValidator.isInputEditTextFilled(email)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(password)){
            return;
        }
        if(!inputValidator.isInputEditTextEmail(email)){
            return;
        }
        if(dbHelperLogin.checkUser(email.getText().toString()) &&
                dbHelperLogin.checkUser(email.getText().toString().trim()
                ,password.getText().toString().trim())){
            Toast.makeText(activity,"Everything is ok!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(activity,"Something went wrong!",Toast.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        email.setText(null);
        password.setText(null);
    }

    public void onClickSignUp(View view) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
}