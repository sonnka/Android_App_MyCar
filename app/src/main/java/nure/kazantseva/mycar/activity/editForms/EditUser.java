package nure.kazantseva.mycar.activity.editForms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.activity.LogIn;
import nure.kazantseva.mycar.activity.MainPage;
import nure.kazantseva.mycar.db.DBHelper;
import nure.kazantseva.mycar.model.Auto;
import nure.kazantseva.mycar.model.User;
import nure.kazantseva.mycar.utils.InputValidator;

public class EditUser extends AppCompatActivity {

    EditText name, email, password;
    DBHelper dbHelper;
    InputValidator inputValidator;
    String Email;
    int auto_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        init();
    }

    public void init(){
        Email = MainPage.getEmail();
        auto_id = MainPage.getAuto_id();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        dbHelper = new DBHelper(this);
        inputValidator = new InputValidator(this);

        User user = userData();

        name.setText(user.getName());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
    }


    private User userData(){
        Cursor cursor = dbHelper.findUserByEmail(Email);
        User user = new User();
        if(cursor.getCount() == 0){
        }else{
            while(cursor.moveToNext()){
                user.setName(cursor.getString(0));
                user.setEmail(cursor.getString(1));
                user.setPassword(cursor.getString(2));
            }
        }
        return user;
    }

    public void onClick(View view) {
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
        if(email.getText().toString().trim().equals(Email)
                ||!dbHelper.checkUser(email.getText().toString().trim())){

            User user = new User();
            user.setName(name.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());

            dbHelper.updateUser(user,Email);
            Toast.makeText(this.getApplicationContext(),"User is updated!",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("email",email.getText().toString());
            intent.putExtra("id",auto_id);
            startActivity(intent);
            this.finish();
        }else{
            Toast.makeText(this.getApplicationContext(),"User with such email is already exist!"
                    ,Toast.LENGTH_LONG).show();
        }
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra("email",Email);
        intent.putExtra("id",auto_id);
        startActivity(intent);
        this.finish();
    }
}