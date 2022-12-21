package nure.kazantseva.mycar.activity.statistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.activity.MainPage;
import nure.kazantseva.mycar.db.DBHelper;

public class Statistic1 extends AppCompatActivity {

    DBHelper dbHelper;
    int auto_id;
    String email;
    List<String> types;
    List<Float> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic1);

        init();

        getData();

        buildPieChart();
    }

    private void init(){
        dbHelper = new DBHelper(this);
        auto_id = MainPage.getAuto_id();
        email = MainPage.getEmail();
        types = new ArrayList<>();
        values = new ArrayList<>();
    }

    private void getData(){
        Cursor cursor = dbHelper.getStatistic1(auto_id);

        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data!",Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                types.add(cursor.getString(0));
                values.add((float) cursor.getDouble(1));
            }
        }
    }

    private void buildPieChart(){
        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> expenses = new ArrayList<>();


        for(int i = 0; i < types.size(); i++){
            expenses.add(new PieEntry(values.get(i), types.get(i)));
        }

        PieDataSet pieDataSet = new PieDataSet(expenses, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(20f);

        PieData pieData = new PieData(pieDataSet);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(17f);
        legend.setYOffset(20f);
        legend.setDrawInside(false);

        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Витрати\n(грн)");
        pieChart.setCenterTextSize(20f);
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