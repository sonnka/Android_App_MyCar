package nure.kazantseva.mycar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.db.DBHelperAuto;
import nure.kazantseva.mycar.db.DBHelperOther;
import nure.kazantseva.mycar.db.DBHelperRefill;
import nure.kazantseva.mycar.db.DBHelperRepair;
import nure.kazantseva.mycar.db.DBHelperWasher;
import nure.kazantseva.mycar.model.Repair;
import nure.kazantseva.mycar.utils.InputValidator;

public class InfoRepair extends AppCompatActivity {

    int auto_id;
    int expense_id;
    String typeOfExpense;
    DBHelperAuto dbHelperAuto;
    DBHelperRefill dbHelperRefill;
    DBHelperRepair dbHelperRepair;
    DBHelperWasher dbHelperWasher;
    DBHelperOther dbHelperOther;
    InputValidator inputValidator;

    TextView text, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_repair);

        dbHelperAuto = new DBHelperAuto(this.getApplicationContext());

        Bundle arguments = getIntent().getExtras();
        if(arguments != null){
            if(arguments.containsKey("AutoId")) {
                auto_id = arguments.getInt("AutoId");
            }
            if(arguments.containsKey("ExpenseId")){
                expense_id = arguments.getInt("ExpenseId");
            }
            if(arguments.containsKey("TypeOfExpense")){
                typeOfExpense = arguments.getString("TypeOfExpense");
            }
        }else{
            Toast.makeText(this.getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getApplicationContext(),ListOfExpenses.class);
            this.finish();
            startActivity(intent);
        }

        init();
    }

    private void init() {

        dbHelperRefill = new DBHelperRefill(this.getApplicationContext());
        dbHelperRepair = new DBHelperRepair(this.getApplicationContext());
        dbHelperWasher = new DBHelperWasher(this.getApplicationContext());
        dbHelperOther = new DBHelperOther(this.getApplicationContext());
        inputValidator = new InputValidator(this);

        text = findViewById(R.id.text);
        info = findViewById(R.id.info);

        text.setText(typeOfExpense);
        getTypeOfExpense(typeOfExpense);
    }

    private void getTypeOfExpense(String name){
        switch(name){
            case "Ремонт": {
                Cursor cursor = dbHelperRepair.findById(expense_id);
                if(cursor.getCount() == 0){
                    Toast.makeText(InfoRepair.this,"No data!",Toast.LENGTH_LONG).show();
                }else{
                    Repair repair = new Repair();
                    while(cursor.moveToNext()){
                        repair.setId(cursor.getInt(0));
                        repair.setAuto_id(cursor.getInt(1));
                        repair.setDate(LocalDate.parse(cursor.getString(2)));
                        repair.setRun(cursor.getInt(3));
                        repair.setDescription(cursor.getString(4));
                        repair.setPrice(cursor.getInt(5));

                        info.setText(repair.toString());
                    }
                }

            }
            case "Заправка": {

            }
            case "Автомийка": {

            }
            default: {

            }
        }
    }
}