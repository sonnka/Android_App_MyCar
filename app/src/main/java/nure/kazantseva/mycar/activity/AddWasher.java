package nure.kazantseva.mycar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.db.DBHelperWasher;
import nure.kazantseva.mycar.model.Repair;
import nure.kazantseva.mycar.model.Washer;
import nure.kazantseva.mycar.utils.InputValidator;

public class AddWasher extends AppCompatActivity {

    EditText date, price;
    Button nextButton;
    int auto_id;
    int expense_id = 0;
    DatePickerDialog datePickerDialog;
    InputValidator inputValidator;
    DBHelperWasher dbHelperWasher;
    Washer washer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_washer);

        getExtra();
        init();

        if(expense_id != 0){
            edit();
        }
    }

    private void getExtra(){
        Bundle arguments = getIntent().getExtras();
        if(arguments != null){
            auto_id = arguments.getInt("id");
            if(arguments.containsKey("ExpenseId")){
                expense_id = arguments.getInt("ExpenseId");
            }
        }else{
            Toast.makeText(this.getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getApplicationContext(),ListOfExpenses.class);
            this.finish();
            startActivity(intent);
        }
    }

    private void init(){
        date = findViewById(R.id.date);
        date.setInputType(0);
        price = findViewById(R.id.price);
        nextButton = findViewById(R.id.nextButton);
        nextButton.setText("Додати запис");

        washer = new Washer();
        inputValidator = new InputValidator(this.getApplicationContext());
        dbHelperWasher = new DBHelperWasher(this.getApplicationContext());
    }

    private void edit(){
        nextButton.setText("Оновити запис");
        Cursor cursor = dbHelperWasher.findById(expense_id);
        if(cursor.getCount() == 0){
            Toast.makeText(AddWasher.this,"No data!",Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                washer.setId(cursor.getInt(0));
                washer.setAuto_id(cursor.getInt(1));
                washer.setDate(LocalDate.parse(cursor.getString(2)));
                washer.setPrice(cursor.getDouble(3));
            }
        }

        date.setText(inputValidator.convertStringToDateString(washer.getDate().toString().trim()));
        price.setText(String.valueOf(washer.getPrice()));
    }

    @SuppressLint("SetTextI18n")
    public void onDate(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(AddWasher.this,
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
        if(!inputValidator.isInputEditTextFilled(price)){
            return;
        }
        if(inputValidator.validateDate(date.getText().toString().trim()) && auto_id != 0){
            if(!inputValidator.convertToLocalDate(date.getText().toString().trim()).equals(null)){
                washer.setAuto_id(auto_id);
                washer.setDate(inputValidator.convertToLocalDate
                        (date.getText().toString().trim()));
                washer.setPrice(Double.parseDouble(price.getText().toString().trim()));

                if (expense_id != 0) {
                    dbHelperWasher.updateWasher(washer);
                } else {
                    dbHelperWasher.addWasher(washer);
                }
                Toast.makeText(this.getApplicationContext(),"New washer created!",Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra("id",auto_id);
        startActivity(intent);
        this.finish();
    }
}