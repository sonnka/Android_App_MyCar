package nure.kazantseva.mycar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.adapters.ExpensesAdapter;
import nure.kazantseva.mycar.db.DBHelperAuto;
import nure.kazantseva.mycar.db.DBHelperRefill;

public class ListOfExpenses extends AppCompatActivity {

    private RecyclerView recyclerView;
    FloatingActionButton fab, fab1, fab2, fab3, fab4;
    Boolean isFABOpen = false;
    int auto_id;
    DBHelperAuto dbHelperAuto;
    DBHelperRefill dbHelperRefill;
    ExpensesAdapter expensesAdapter;
    ArrayList<String> date, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_expenses);

        dbHelperAuto = new DBHelperAuto(this.getApplicationContext());

        Bundle arguments = getIntent().getExtras();
        if(arguments != null){
            if(arguments.containsKey("email")) {
                String email = arguments.getString("email");
                auto_id = dbHelperAuto.findByEmail(email);
            }
            if(arguments.containsKey("id")){
                auto_id = arguments.getInt("id");
            }
        }else{
            Toast.makeText(this.getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getApplicationContext(),ListOfExpenses.class);
            this.finish();
            startActivity(intent);
        }

        init();
    }

    private void init(){

        date = new ArrayList<>();
        price = new ArrayList<>();

        dbHelperRefill = new DBHelperRefill(this.getApplicationContext());

        recyclerView = findViewById(R.id.recycle_view);
        fab = (FloatingActionButton) findViewById(R.id.add_button);
        fab1 = (FloatingActionButton) findViewById(R.id.other_button);
        fab2 = (FloatingActionButton) findViewById(R.id.washer_button);
        fab3 = (FloatingActionButton) findViewById(R.id.refill_button);
        fab4 = (FloatingActionButton) findViewById(R.id.repair_button);
        fab.setOnClickListener(view -> {
            if(!isFABOpen){
                showFABMenu();
            }else{
                closeFABMenu();
            }
        });

        fab1.setOnClickListener(view ->{
            Intent intent = new Intent(ListOfExpenses.this,AddOther.class);
            intent.putExtra("id",auto_id);
            startActivity(intent);
        });
        fab2.setOnClickListener(view ->{
            Intent intent = new Intent(ListOfExpenses.this,AddWatcher.class);
            intent.putExtra("id",auto_id);
            startActivity(intent);
        });
        fab3.setOnClickListener(view ->{
            Intent intent = new Intent(ListOfExpenses.this,AddRefill.class);
            intent.putExtra("id",auto_id);
            startActivity(intent);
        });
        fab4.setOnClickListener(view ->{
            Intent intent = new Intent(ListOfExpenses.this,AddRepair.class);
            intent.putExtra("id",auto_id);
            startActivity(intent);
        });

        displayData();

        expensesAdapter = new ExpensesAdapter(this.getApplicationContext(),date,price);
        recyclerView.setAdapter(expensesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListOfExpenses.this));
    }

    private void showFABMenu(){
        isFABOpen=true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        fab4.animate().translationY(-getResources().getDimension(R.dimen.standard_205));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
        fab4.animate().translationY(0);
    }

    private void displayData(){
        Cursor cursor = dbHelperRefill.readAllDateByAutoId(auto_id);
        if(cursor.getCount() == 0){
            Toast.makeText(ListOfExpenses.this,"No data!",Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                date.add(cursor.getString(2));
                price.add(cursor.getString(6));
            }
        }
    }

}