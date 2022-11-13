package nure.kazantseva.mycar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.db.DBHelperAuto;
import nure.kazantseva.mycar.db.DBHelperOther;
import nure.kazantseva.mycar.db.DBHelperRefill;
import nure.kazantseva.mycar.db.DBHelperRepair;
import nure.kazantseva.mycar.db.DBHelperWasher;
import nure.kazantseva.mycar.model.Other;
import nure.kazantseva.mycar.model.Refill;
import nure.kazantseva.mycar.model.Repair;
import nure.kazantseva.mycar.model.Washer;
import nure.kazantseva.mycar.utils.InputValidator;

public class Info extends AppCompatActivity {

    int auto_id;
    int expense_id;
    String typeOfExpense;
    DBHelperAuto dbHelperAuto;
    DBHelperRefill dbHelperRefill;
    DBHelperRepair dbHelperRepair;
    DBHelperWasher dbHelperWasher;
    DBHelperOther dbHelperOther;
    InputValidator inputValidator;
    ImageButton edit, delete;

    TextView text, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        getExtra();
        init();
    }

    private void getExtra(){
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
    }

    private void init() {
        dbHelperRefill = new DBHelperRefill(this.getApplicationContext());
        dbHelperRepair = new DBHelperRepair(this.getApplicationContext());
        dbHelperWasher = new DBHelperWasher(this.getApplicationContext());
        dbHelperOther = new DBHelperOther(this.getApplicationContext());
        inputValidator = new InputValidator(this);

        text = findViewById(R.id.text);
        info = findViewById(R.id.info);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);

        text.setText(typeOfExpense);
        getTypeOfExpense(typeOfExpense);

    }

    private void getTypeOfExpense(String name){
        switch(name){
            case "Ремонт": getRepair();break;
            case "Заправка": getRefill(); break;
            case "Автомийка": getWasher(); break;
            default: getOther(); break;
        }
    }

    private void getRepair(){
        Cursor cursor = dbHelperRepair.findById(expense_id);
        if(cursor.getCount() == 0){
            Toast.makeText(Info.this,"No data!",Toast.LENGTH_LONG).show();
        }else{
            Repair repair = new Repair();
            while(cursor.moveToNext()){
                repair.setId(cursor.getInt(0));
                repair.setAuto_id(cursor.getInt(1));
                repair.setDate(LocalDate.parse(cursor.getString(2)));
                repair.setRun(cursor.getInt(3));
                repair.setDescription(cursor.getString(4));
                repair.setPrice(cursor.getDouble(5));

                info.setText(repair.toString());
            }
        }
    }

    private void getRefill(){
        Cursor cursor = dbHelperRefill.findById(expense_id);
        if(cursor.getCount() == 0){
            Toast.makeText(Info.this,"No data!",Toast.LENGTH_LONG).show();
        }else{
            Refill refill = new Refill();
            while(cursor.moveToNext()){
                refill.setId(cursor.getInt(0));
                refill.setAuto_id(cursor.getInt(1));
                refill.setDate(LocalDate.parse(cursor.getString(2)));
                refill.setRun(cursor.getInt(3));
                refill.setBeforeRefill(cursor.getDouble(4));
                refill.setAddFuel(cursor.getDouble(5));
                refill.setPrice(cursor.getDouble(6));
                refill.setStation(cursor.getString(7));
                info.setText(refill.toString());
            }
        }
    }

    private void getWasher(){
        Cursor cursor = dbHelperWasher.findById(expense_id);
        if(cursor.getCount() == 0){
            Toast.makeText(Info.this,"No data!",Toast.LENGTH_LONG).show();
        }else{
            Washer washer = new Washer();
            while(cursor.moveToNext()){
                washer.setId(cursor.getInt(0));
                washer.setAuto_id(cursor.getInt(1));
                washer.setDate(LocalDate.parse(cursor.getString(2)));
                washer.setPrice(cursor.getDouble(3));
                info.setText(washer.toString());
            }
        }
    }

    private void getOther(){
        Cursor cursor = dbHelperOther.findById(expense_id);
        if(cursor.getCount() == 0){
            Toast.makeText(Info.this,"No data!",Toast.LENGTH_LONG).show();
        }else{
            Other other = new Other();
            while(cursor.moveToNext()){
                other.setId(cursor.getInt(0));
                other.setAuto_id(cursor.getInt(1));
                other.setDate(LocalDate.parse(cursor.getString(2)));
                other.setDescription(cursor.getString(3));
                other.setPrice(cursor.getDouble(4));

                info.setText(other.toString());
            }
        }
    }

    public void editExpense(View view) {
        switch(typeOfExpense){
            case "Ремонт": {
                Intent intent = new Intent(this, AddRepair.class);
                intent.putExtra("id",auto_id);
                intent.putExtra("ExpenseId",expense_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                this.finish();
                startActivity(intent);
                break;
            }
            case "Заправка": {
                Intent intent = new Intent(this, AddRefill.class);
                intent.putExtra("id",auto_id);
                intent.putExtra("ExpenseId",expense_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                this.finish();
                startActivity(intent);
                break;
            }
            case "Автомийка": {
                Intent intent = new Intent(this, AddWasher.class);
                intent.putExtra("id",auto_id);
                intent.putExtra("ExpenseId",expense_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                this.finish();
                startActivity(intent);
                break;
            }
            default: {
                Intent intent = new Intent(this, AddOther.class);
                intent.putExtra("id",auto_id);
                intent.putExtra("ExpenseId",expense_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                this.finish();
                startActivity(intent);
                break;
            }
        }
    }

    public void deleteExpense(View view) {
        switch(typeOfExpense){
            case "Ремонт":{
                dbHelperRepair.deleteRepair(expense_id);
                Intent intent = new Intent(this, ListOfExpenses.class);
                intent.putExtra("id", auto_id);
                this.finish();
                startActivity(intent);
                break;
            }
            case "Заправка": {
                dbHelperRefill.deleteRefill(expense_id);
                Intent intent = new Intent(this, ListOfExpenses.class);
                intent.putExtra("id", auto_id);
                this.finish();
                startActivity(intent);
                break;
            }
            case "Автомийка": {
                dbHelperWasher.deleteWasher(expense_id);
                Intent intent = new Intent(this, ListOfExpenses.class);
                intent.putExtra("id", auto_id);
                this.finish();
                startActivity(intent);
                break;
            }
            default:  {
                dbHelperOther.deleteOther(expense_id);
                Intent intent = new Intent(this, ListOfExpenses.class);
                intent.putExtra("id", auto_id);
                this.finish();
                startActivity(intent);
                break;
            }
        }
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra("id",auto_id);
        startActivity(intent);
        this.finish();
    }
}