package nure.kazantseva.mycar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.db.DBHelperRefill;
import nure.kazantseva.mycar.db.DBHelperRepair;
import nure.kazantseva.mycar.model.Refill;
import nure.kazantseva.mycar.model.Repair;
import nure.kazantseva.mycar.utils.InputValidator;

public class AddRepair extends AppCompatActivity {

    EditText date,run,description, price;
    int auto_id;
    DatePickerDialog datePickerDialog;
    InputValidator inputValidator;
    DBHelperRepair dbHelperRepair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repair);

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

    private void init(){
        date = (EditText) findViewById(R.id.date);
        date.setInputType(0);
        run = (EditText) findViewById(R.id.run);
        description = (EditText) findViewById(R.id.description);
        price = (EditText) findViewById(R.id.price);

        inputValidator = new InputValidator(this.getApplicationContext());
        dbHelperRepair = new DBHelperRepair(this.getApplicationContext());
    }
    public void onDate(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        datePickerDialog = new DatePickerDialog(AddRepair.this,
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
        if(!inputValidator.isInputEditTextFilled(description)){
            return;
        }
        if(!inputValidator.isInputEditTextFilled(price)){
            return;
        }
        if(inputValidator.validateDate(date.getText().toString().trim()) && auto_id != 0){
            if(!inputValidator.convertToLocalDate(date.getText().toString().trim()).equals(null)){
                Repair repair = new Repair();
                repair.setAuto_id(auto_id);
                repair.setDate(inputValidator.convertToLocalDate
                        (date.getText().toString().trim()));
                repair.setRun(Long.parseLong
                        (run.getText().toString().trim()));
                repair.setDescription(description.getText().toString().trim());
                repair.setPrice(Integer.parseInt(price.getText().toString().trim()));

                dbHelperRepair.addRepair(repair);
                Toast.makeText(this.getApplicationContext(),"New repair created!",Toast.LENGTH_LONG).show();
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