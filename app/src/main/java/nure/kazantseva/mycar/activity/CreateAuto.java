package nure.kazantseva.mycar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Random;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.db.DBHelper;
import nure.kazantseva.mycar.model.Auto;
import nure.kazantseva.mycar.model.UserAuto;
import nure.kazantseva.mycar.utils.InputValidator;

public class CreateAuto extends AppCompatActivity {
    private final AppCompatActivity activity = CreateAuto.this;

    private EditText brand, model, year, fuel, run;
    private InputValidator inputValidator;
    private DBHelper dbHelper;
    private String email;
    private Spinner spinner;

    private String[] type_of_fuel = new String[]{"Газ","Бензин","Дизель"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_auto);

        Bundle arguments = getIntent().getExtras();
        if(arguments != null){
            email = arguments.getString("email");
        }else{
            Toast.makeText(activity,"Error",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getApplicationContext(), LogIn.class);
            this.finish();
            startActivity(intent);
        }
        init();
    }

    public void init(){
        brand = findViewById(R.id.brandInput);
        model = findViewById(R.id.modelInput);
        year = findViewById(R.id.yearInput);
        run = findViewById(R.id.runInput);
        fuel = new EditText(activity);

        inputValidator = new InputValidator(activity);
        dbHelper = new DBHelper(activity);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter(this
                , R.layout.layout_spinner_item, type_of_fuel);

        adapter.setDropDownViewResource(R.layout.layout_spinner_item);

        spinner.setAdapter(adapter);


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = (String)parent.getItemAtPosition(position);
                fuel.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
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
        if(Integer.parseInt(year.getText().toString()) <= LocalDate.now().getYear()
                && Integer.parseInt(year.getText().toString()) >= 1900
                && Long.parseLong(run.getText().toString()) >= 0) {
            Auto auto = new Auto();
            auto.setUniqueCode(generateUniqueCode());
            auto.setBrand(brand.getText().toString().trim());
            auto.setModel(model.getText().toString().trim());
            auto.setYear(Integer.parseInt(year.getText().toString()));
            auto.setFuel(fuel.getText().toString().trim());
            auto.setRun(Long.parseLong(run.getText().toString()));

            dbHelper.addAuto(auto);

            auto.setId(dbHelper.searchByCode(auto.getUniqueCode()));

            UserAuto userAuto = new UserAuto(email, auto.getId());
            dbHelper.addUserAuto(userAuto);

            Toast.makeText(this.getApplicationContext(),"New auto created!",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("id",auto.getId());
            intent.putExtra("email",email);
            startActivity(intent);
            this.finish();
        }else{
            Toast.makeText(this.getApplicationContext(),"Invalid data",Toast.LENGTH_LONG).show();
        }
    }

    private String generateUniqueCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String uniqueCode = String.format("%06d", number);
        if(dbHelper.checkUniqueCode(uniqueCode)){
            generateUniqueCode();
        }
        return uniqueCode;
    }

    public void hasAlreadyCar(View view) {
        Intent intent = new Intent(this, AddExistedCar.class);
        intent.putExtra("email",email);
        startActivity(intent);
        this.finish();
    }
}