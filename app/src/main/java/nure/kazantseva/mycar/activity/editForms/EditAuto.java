package nure.kazantseva.mycar.activity.editForms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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
import nure.kazantseva.mycar.activity.AddExistedCar;
import nure.kazantseva.mycar.activity.CreateAuto;
import nure.kazantseva.mycar.activity.MainPage;
import nure.kazantseva.mycar.db.DBHelper;
import nure.kazantseva.mycar.model.Auto;
import nure.kazantseva.mycar.model.UserAuto;
import nure.kazantseva.mycar.utils.InputValidator;

public class EditAuto extends AppCompatActivity {

    private final AppCompatActivity activity = EditAuto.this;

    private EditText brand, model, year, fuel, run;
    private InputValidator inputValidator;
    private DBHelper dbHelper;
    private String email;
    int auto_id;
    private Spinner spinner;

    private String[] type_of_fuel = new String[]{"Газ","Бензин","Дизель"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_auto);
        init();
    }

    public void init(){
        brand = findViewById(R.id.brandInput);
        model = findViewById(R.id.modelInput);
        year = findViewById(R.id.yearInput);
        run = findViewById(R.id.runInput);
        fuel = new EditText(activity);

        email = MainPage.getEmail();
        auto_id = MainPage.getAuto_id();

        inputValidator = new InputValidator(activity);
        dbHelper = new DBHelper(activity);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter(this
                , R.layout.layout_spinner2_item, type_of_fuel);

        adapter.setDropDownViewResource(R.layout.layout_spinner2_item);

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

        fillData();
    }

    public void fillData(){
        Auto auto = autoData();
        brand.setText(auto.getBrand());
        model.setText(auto.getModel());
        year.setText(String.valueOf(auto.getYear()));
        run.setText(String.valueOf(auto.getRun()));
        switch (auto.getFuel()){
            case "Газ": spinner.setSelection(0); break;
            case "Бензин": spinner.setSelection(1); break;
            case "Дизель": spinner.setSelection(2); break;
        }

    }

    private Auto autoData(){
        Cursor cursor = dbHelper.findAutoById(auto_id);
        Auto auto = new Auto();
        if(cursor.getCount() == 0){
        }else{
            while(cursor.moveToNext()){
                auto.setUniqueCode(cursor.getString(1));
                auto.setBrand(cursor.getString(2));
                auto.setModel(cursor.getString(3));
                auto.setYear(cursor.getInt(4));
                auto.setFuel(cursor.getString(5));
                auto.setRun(cursor.getLong(6));
            }
        }
        return auto;
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
            auto.setId(auto_id);
            auto.setBrand(brand.getText().toString().trim());
            auto.setModel(model.getText().toString().trim());
            auto.setYear(Integer.parseInt(year.getText().toString()));
            auto.setFuel(fuel.getText().toString().trim());
            auto.setRun(Long.parseLong(run.getText().toString()));

            dbHelper.updateAuto(auto);

            Toast.makeText(this.getApplicationContext(), "Auto is updated!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("id", auto.getId());
            intent.putExtra("email", email);
            startActivity(intent);
            this.finish();
        }else{
            Toast.makeText(this.getApplicationContext(),"Invalid data",Toast.LENGTH_LONG).show();
        }
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra("email",email);
        intent.putExtra("id",auto_id);
        startActivity(intent);
        this.finish();
    }

}