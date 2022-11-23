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

public class AddExistedCar extends AppCompatActivity {

    private String email;
    private EditText code;
    private InputValidator inputValidator;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_existed_car);

        readExtra();
        init();
    }

    private void readExtra(){
        Bundle arguments = getIntent().getExtras();
        if(arguments != null){
            email = arguments.getString("email").toString();
        }else{
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getApplicationContext(), LogIn.class);
            this.finish();
            startActivity(intent);
        }
    }

    private void init(){
        code = findViewById(R.id.uniqueCode);

        inputValidator = new InputValidator(this);
        dbHelper = new DBHelper(this);
    }

    public void onClickNext(View view) {
        String input = code.getText().toString();
        if(inputValidator.checkInputCode(input)){
            int autoId = dbHelper.searchByCode(input.trim());
            if(autoId != 0){
                Intent intent = new Intent(this, MainPage.class);
                intent.putExtra("id",autoId);
                startActivity(intent);
                this.finish();
            }else{
                Toast.makeText(this.getApplicationContext(),"No car with such code!",
                        Toast.LENGTH_LONG).show();
            }
        }else{
            code.setText("");
            Toast.makeText(this.getApplicationContext(),"Invalid code!",
                    Toast.LENGTH_LONG).show();
        }
    }


    public void back(View view) {
        Intent intent = new Intent(this, CreateAuto.class);
        startActivity(intent);
        this.finish();
    }
}