package nure.kazantseva.mycar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.db.DBHelperAuto;
import nure.kazantseva.mycar.db.DBHelperRefill;
import nure.kazantseva.mycar.model.Auto;
import nure.kazantseva.mycar.model.Refill;
import nure.kazantseva.mycar.utils.InputValidator;

public class AddRefill extends AppCompatActivity {

    EditText date,run,beforeRefill, addFuel, price,station;
    int auto_id;
    DatePickerDialog datePickerDialog;
    InputValidator inputValidator;
    DBHelperRefill dbHelperRefill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_refill);

        Bundle arguments = getIntent().getExtras();
        if(arguments != null){
            auto_id = arguments.getInt("id");
        }else{
            Toast.makeText(this.getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getApplicationContext(),ListOfExpenses.class);
            this.finish();
            startActivity(intent);
        }
        init();
    }

    public void init(){
        date = (EditText) findViewById(R.id.date);
        date.setInputType(0);
        run = (EditText) findViewById(R.id.run);
        beforeRefill = (EditText) findViewById(R.id.beforeRefill);
        addFuel = (EditText) findViewById(R.id.addFuel);
        price = (EditText) findViewById(R.id.price);
        station = (EditText) findViewById(R.id.station);

        inputValidator = new InputValidator(this.getApplicationContext());
        dbHelperRefill = new DBHelperRefill(this.getApplicationContext());
        dbHelperRefill.deleteRefill(0);
    }

    public void onDate(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        datePickerDialog = new DatePickerDialog(AddRefill.this,
                (view1, year, monthOfYear, dayOfMonth) -> date.setText(dayOfMonth + "/"
                        + (monthOfYear + 1) + "/" + year), mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void onClickAdd(View view) {
        verify();
    }

    private void verify() {
        if(!inputValidator.isInputEditTextFilled(date)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(run)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(beforeRefill)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(addFuel)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(price)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(station)){
            return;
        }
        if(inputValidator.validateDate(date.getText().toString().trim()) && auto_id != 0){
            if(!inputValidator.convertToLocalDate(date.getText().toString().trim()).equals(null)){
                Refill refill = new Refill();
                refill.setAuto_id(auto_id);
                refill.setDate(inputValidator.convertToLocalDate
                        (date.getText().toString().trim()));
                refill.setRun(Long.parseLong
                        (run.getText().toString().trim()));
                refill.setBeforeRefill(Double.parseDouble
                        (beforeRefill.getText().toString().trim()));
                refill.setAddFuel(Double.parseDouble(addFuel.getText().toString().trim()));
                refill.setPrice(Integer.parseInt(price.getText().toString().trim()));
                refill.setStation(station.getText().toString().trim());

                dbHelperRefill.addRefill(refill);
                Toast.makeText(this.getApplicationContext(),"New refill created!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, ListOfExpenses.class);
                intent.putExtra("id",auto_id);
                this.finish();
                startActivity(intent);
            }else{
                Toast.makeText(this.getApplicationContext(),"Not convert date!",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this.getApplicationContext(),"Not valid date!",Toast.LENGTH_LONG).show();
        }

    }

    public void back(View view) {
        this.finish();
    }
}