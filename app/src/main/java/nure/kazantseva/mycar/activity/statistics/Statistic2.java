package nure.kazantseva.mycar.activity.statistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.activity.MainPage;
import nure.kazantseva.mycar.db.DBHelper;

public class Statistic2 extends AppCompatActivity {

    DBHelper dbHelper;
    int auto_id;
    String email;
    ArrayList<String> types;
    List<Float> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic2);
        init();

        getData();

        buildBarChart();
    }

    private void init(){
        dbHelper = new DBHelper(this);
        auto_id = MainPage.getAuto_id();
        email = MainPage.getEmail();
        types = new ArrayList<>();
        values = new ArrayList<>();
    }

    private void getData(){
        Cursor cursor = dbHelper.getStatistic2(auto_id, String.valueOf(LocalDate.now().getYear()));

        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data!",Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                types.add(cursor.getString(1));
                values.add((float) cursor.getDouble(2));
            }
        }
    }

    private void buildBarChart(){
        BarChart barChart = findViewById(R.id.barChart);

        ArrayList<BarEntry> expenses = new ArrayList<>();

        int i = 0;
        while(i < values.size()){
            expenses.add(new BarEntry((float) i, values.get(i)));
            i++;
        }

        BarDataSet barDataSet = new BarDataSet(expenses, "Витрати на паливо (грн)");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.RED);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);


        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);

        barChart.setAutoScaleMinMaxEnabled(true);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setTextSize(18f);
        xAxis.setTextColor(Color.parseColor("#FF018786"));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return types.get((int) value); // xVal is a string array
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        barChart.setExtraBottomOffset(55);
        barChart.getLegend().setEnabled(false);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(2000);

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