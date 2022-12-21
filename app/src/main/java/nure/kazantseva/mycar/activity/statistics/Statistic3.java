package nure.kazantseva.mycar.activity.statistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.activity.MainPage;
import nure.kazantseva.mycar.db.DBHelper;

public class Statistic3 extends AppCompatActivity {

    DBHelper dbHelper;
    int auto_id;
    String email;
    double priceLiter = 0;
    double fuel_consumption = 0;
    TextView fuelConsum, price, result;
    EditText destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic3);

        init();
        getData();
        getConsumption();
    }

    private void init(){
        dbHelper = new DBHelper(this);
        auto_id = MainPage.getAuto_id();
        email = MainPage.getEmail();

        fuelConsum = findViewById(R.id.fuelConsum);
        price = findViewById(R.id.price);
        result = findViewById(R.id.result);
        destination = findViewById(R.id.destination);
    }

    private void getData(){
        Cursor cursor = dbHelper.getStatistic3(auto_id);
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data!",Toast.LENGTH_LONG).show();
        }else{
            cursor.moveToFirst();
            priceLiter = cursor.getDouble(0);
        }
        price.setText(priceLiter + " грн");
    }

    private void getConsumption(){
        Cursor consumption = dbHelper.getFuelConsumption(auto_id);
        int i = 0;
        long[] runKM = new long[2];
        double[] beforeRefill = new double[2];
        double[] addFuel = new double[2];
        if(consumption.getCount() == 0){
            Toast.makeText(this,"No data!",Toast.LENGTH_LONG).show();
        }else{
            while(consumption.moveToNext() && i < 2){
                runKM[i] = consumption.getLong(0);
                beforeRefill[i] = consumption.getDouble(1);
                addFuel[i] = consumption.getDouble(2);
                i++;
            }
        }

        DecimalFormat df = new DecimalFormat("0.00");

        if(consumption.getCount() > 1){
            fuel_consumption = ((beforeRefill[1] + addFuel[1] - beforeRefill[0])*100)
                    /(runKM[0]-runKM[1]);
            fuelConsum.setText(df.format(fuel_consumption) + " л/км");
        }
    }

    public void calculate(View view){
        DecimalFormat df = new DecimalFormat("0.00");
        double distance = Double.parseDouble(destination.getText().toString());
        result.setText(df.format(distance * fuel_consumption * priceLiter/100) + " грн");
    }

    public void back(View view){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 1) {
            //no fragments left
            finish();
        } else {
            super.onBackPressed();
        }
    }
}