package nure.kazantseva.mycar.activity.statistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.activity.MainPage;
import nure.kazantseva.mycar.db.DBHelper;

public class Statistic4 extends AppCompatActivity {

    DBHelper dbHelper;
    int auto_id;
    String email;
    List<Integer> ids;
    List<Double> sum;
    List<String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic4);

       init();
       getData();
       buildPieChart1();
        buildPieChart2();


    }

    private void init(){
        dbHelper = new DBHelper(this);
        auto_id = MainPage.getAuto_id();
        email = MainPage.getEmail();

        ids = new ArrayList<>();
        sum= new ArrayList<>();
        name = new ArrayList<>();
    }

    private void getData(){
        Cursor cursor = dbHelper.getStatistics4(email);

        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data!",Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                name.add(cursor.getString(0));
                ids.add(cursor.getInt(1));
                sum.add(cursor.getDouble(2));
                Log.i("Shooooooow", name.get(name.size()-1) + " "
                        + ids.get(name.size()-1) + " " + sum.get(name.size()-1));
            }
        }
    }

    private void buildPieChart1(){
        PieChart pieChart = findViewById(R.id.pieChart1);

        ArrayList<PieEntry> expenses = new ArrayList<>();

        String nameCar = "";
        for(int i = 0; i < ids.size()/2; i++){

            Cursor cursor = dbHelper.findAutoById(ids.get(i));
            if(cursor.getCount() == 0){
                Toast.makeText(this,"No data!",Toast.LENGTH_LONG).show();
            }else{
                while(cursor.moveToNext()){
                    nameCar = cursor.getString(2) + " " +cursor.getString(3);
                }
            }

            expenses.add(new PieEntry(sum.get(i).floatValue(), nameCar));

        }

        PieDataSet pieDataSet = new PieDataSet(expenses, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(20f);

        PieData pieData = new PieData(pieDataSet);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(16f);
        legend.setYOffset(20f);
        legend.setDrawInside(false);

        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Витрати на заправку");
        pieChart.setCenterTextSize(17f);
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setCenterTextColor(Color.parseColor("#222222"));
        pieChart.animate();
    }

    private void buildPieChart2(){
        PieChart pieChart = findViewById(R.id.pieChart2);

        ArrayList<PieEntry> expenses = new ArrayList<>();

        String nameCar = "";
        for(int i = 2; i < ids.size(); i++){

            Cursor cursor = dbHelper.findAutoById(ids.get(i));
            if(cursor.getCount() == 0){
                Toast.makeText(this,"No data!",Toast.LENGTH_LONG).show();
            }else{
                while(cursor.moveToNext()){
                    nameCar = cursor.getString(2) + " " +cursor.getString(3);
                }
            }

            expenses.add(new PieEntry(sum.get(i).floatValue(), nameCar));

        }

        PieDataSet pieDataSet = new PieDataSet(expenses, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(20f);

        PieData pieData = new PieData(pieDataSet);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(16f);
        legend.setYOffset(20f);
        legend.setDrawInside(false);

        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Витрати на ремонт");
        pieChart.setCenterTextSize(17f);
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setCenterTextColor(Color.parseColor("#222222"));
        pieChart.animate();
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