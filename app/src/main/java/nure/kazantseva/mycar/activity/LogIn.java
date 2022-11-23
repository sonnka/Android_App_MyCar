package nure.kazantseva.mycar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.db.DBHelper;
import nure.kazantseva.mycar.utils.InputValidator;

public class LogIn extends AppCompatActivity {
    private final AppCompatActivity activity = LogIn.this;

    private EditText email, password;
    private InputValidator inputValidator;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initObjects();
    }

    private void initObjects(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        inputValidator = new InputValidator(activity);
        dbHelper = new DBHelper(activity);

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
        if(dbHelper.checkUser(email.getText().toString().trim()) &&
                dbHelper.checkUser(email.getText().toString().trim()
                ,password.getText().toString().trim())){
            checkAvailableData(email.getText().toString().trim());
        }else{
            Toast.makeText(activity,"Something went wrong!",Toast.LENGTH_LONG).show();
        }
    }

    private void checkAvailableData(String email) {
        if(dbHelper.checkByEmail(email) > 0){
            Intent intent = new Intent(this.getApplicationContext(), MainPage.class);
            intent.putExtra("email",email);
            startActivity(intent);
            this.finish();

        }else{
            Intent intent = new Intent(this.getApplicationContext(),CreateAuto.class);
            intent.putExtra("email",email);
            startActivity(intent);
            this.finish();
        }
    }

    private void emptyInputEditText() {
        email.setText(null);
        password.setText(null);
    }

    public void onClickSignUp(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        this.finish();
    }
}