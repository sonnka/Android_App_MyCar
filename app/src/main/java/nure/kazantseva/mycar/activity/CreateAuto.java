package nure.kazantseva.mycar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.db.DBHelperAuto;
import nure.kazantseva.mycar.db.DbHelperUser;
import nure.kazantseva.mycar.model.Auto;
import nure.kazantseva.mycar.model.User;
import nure.kazantseva.mycar.utils.InputValidator;

public class CreateAuto extends AppCompatActivity {
    private final AppCompatActivity activity = CreateAuto.this;

    private EditText brand, model, year, fuel, run;
    private InputValidator inputValidator;
    private DBHelperAuto dbHelperAuto;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_auto);

        Bundle arguments = getIntent().getExtras();
        if(arguments != null){
            email = arguments.getString("email").toString();
        }else{
            Toast.makeText(activity,"Error",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getApplicationContext(),MainActivity.class);
            this.finish();
            startActivity(intent);
        }
        init();
    }

    public void init(){
        brand = findViewById(R.id.brandInput);
        model = findViewById(R.id.modelInput);
        year = findViewById(R.id.yearInput);
        fuel = findViewById(R.id.fuelInput);
        run = findViewById(R.id.runInput);

        inputValidator = new InputValidator(activity);
        dbHelperAuto = new DBHelperAuto(activity);
    }

    public void onClickNext(View view) {
        verify();
    }

    private void verify() {
        if(!inputValidator.isInputEditTextFilled(brand)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(model)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(year)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(fuel)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(run)){
            return;
        }
        Auto auto = new Auto();
        auto.setUser_email(email);
        auto.setBrand(brand.getText().toString().trim());
        auto.setModel(model.getText().toString().trim());
        auto.setYear(Integer.parseInt(year.getText().toString()));
        auto.setFuel(fuel.getText().toString().trim());
        auto.setRun(Long.parseLong(run.getText().toString()));

        dbHelperAuto.addAuto(auto);
        Toast.makeText(this.getApplicationContext(),"New auto created!",Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(this, MainActivity.class);
//            this.finish();
//            startActivity(intent);
    }
}