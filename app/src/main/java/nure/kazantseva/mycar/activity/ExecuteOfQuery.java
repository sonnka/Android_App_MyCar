package nure.kazantseva.mycar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.adapters.QueryAdapter;
import nure.kazantseva.mycar.db.DBHelper;

public class ExecuteOfQuery extends AppCompatActivity {

    EditText query;
    Button button;
    RecyclerView recyclerView;
    DBHelper dbHelper;
    ArrayList<String> text;
    QueryAdapter queryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_of_query);

        init();
    }

    private void init(){
        text = new ArrayList<>();

        dbHelper = new DBHelper(this);

        recyclerView = findViewById(R.id.recycle_view);
        query = findViewById(R.id.query);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAllData();
            }
        });
    }

    @SuppressLint("Range")
    private void displayAllData(){
        clearRecycleView();
        Cursor cursor = dbHelper.executeQuery(query.getText().toString().trim());
        fillData(cursor);
        setAdapterOnRecycleView();
    }

    private void fillData(Cursor cursor) {
        if (cursor.equals(null) || cursor.getCount() == 0) {
            Toast.makeText(this, "No data!", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                StringBuilder str = new StringBuilder("");
                for(int i = 0; i < cursor.getColumnCount(); i++) {
                    str.append(cursor.getString(i));
                    str.append(" ");
                }
                text.add(str.toString());
            }
        }
    }

    private void clearRecycleView(){
        text = new ArrayList<>();
    }

    private void setAdapterOnRecycleView() {
        queryAdapter = new QueryAdapter(this,text);
        recyclerView.setAdapter(queryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}