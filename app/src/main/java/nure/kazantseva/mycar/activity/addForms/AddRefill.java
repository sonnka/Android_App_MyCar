package nure.kazantseva.mycar.activity.addForms;

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
import nure.kazantseva.mycar.activity.ListOfExpenses;
import nure.kazantseva.mycar.activity.MainPage;
import nure.kazantseva.mycar.db.DBHelper;
import nure.kazantseva.mycar.model.Refill;
import nure.kazantseva.mycar.utils.InputValidator;

public class AddRefill extends AppCompatActivity {

    EditText date,run,beforeRefill, addFuel, price,station;
    Button nextButton;
    int auto_id;
    int expense_id = 0;
    DatePickerDialog datePickerDialog;
    InputValidator inputValidator;
    DBHelper dbHelper;
    Refill refill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_refill);

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
            Intent intent = new Intent(this.getApplicationContext(), ListOfExpenses.class);
            this.finish();
            startActivity(intent);
        }
    }

    public void init(){
        date = findViewById(R.id.date);
        date.setInputType(0);
        run = findViewById(R.id.run);
        beforeRefill = findViewById(R.id.beforeRefill);
        addFuel = findViewById(R.id.addFuel);
        price = findViewById(R.id.price);
        station = findViewById(R.id.station);
        nextButton = findViewById(R.id.nextButton);
        nextButton.setText("Додати запис");

        refill = new Refill();
        inputValidator = new InputValidator(this.getApplicationContext());
        dbHelper = new DBHelper(this.getApplicationContext());
    }

    private void edit(){
        nextButton.setText("Оновити запис");
        Cursor cursor = dbHelper.findRefillById(expense_id);
        if(cursor.getCount() == 0){
            Toast.makeText(AddRefill.this,"No data!",Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                refill.setId(cursor.getInt(0));
                refill.setAuto_id(cursor.getInt(1));
                refill.setDate(LocalDate.parse(cursor.getString(4)));
                refill.setRun(cursor.getInt(5));
                refill.setBeforeRefill(cursor.getDouble(6));
                refill.setAddFuel(cursor.getDouble(7));
                refill.setPrice(cursor.getDouble(8));
                refill.setStation(cursor.getString(9));
            }
        }

        date.setText(inputValidator.convertStringToDateString(refill.getDate().toString().trim()));
        run.setText(String.valueOf(refill.getRun()));
        beforeRefill.setText(String.valueOf(refill.getBeforeRefill()));
        addFuel.setText(String.valueOf(refill.getAddFuel()));
        price.setText(String.valueOf(refill.getPrice()));
        station.setText(refill.getStation());
    }

    @SuppressLint("SetTextI18n")
    public void onDate(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
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
                refill.setAuto_id(auto_id);
                refill.setDate(inputValidator.convertToLocalDate
                        (date.getText().toString().trim()));
                refill.setRun(Long.parseLong
                        (run.getText().toString().trim()));
                refill.setBeforeRefill(Double.parseDouble
                        (beforeRefill.getText().toString().trim()));
                refill.setAddFuel(Double.parseDouble(addFuel.getText().toString().trim()));
                refill.setPrice(Double.parseDouble(price.getText().toString().trim()));
                refill.setStation(station.getText().toString().trim());

                if (expense_id != 0) {
                    dbHelper.updateRefill(refill);
                } else {
                    dbHelper.addRefill(refill);
                }
                Toast.makeText(this.getApplicationContext(),"New refill created!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainPage.class);
                intent.putExtra("id",auto_id);
                startActivity(intent);
                this.finish();
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