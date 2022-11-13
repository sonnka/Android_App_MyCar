package nure.kazantseva.mycar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.db.DBHelperAuto;

public class MainPage extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    DBHelperAuto dbHelperAuto;
    private static int auto_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        bottomNavigationView = findViewById(R.id.bottomMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.expenses);
    }

    public static int getAuto_id() {
        return auto_id;
    }

    public static void setAuto_id(int autoIdNew) {
        auto_id = autoIdNew;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        getExtra();
        switch (item.getItemId()) {
            case R.id.expenses:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,
                       new ListOfExpenses()).commit();
                return true;

            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,
                        new AboutMe()).commit();
                return true;

            case R.id.statistic:
                getSupportFragmentManager().beginTransaction().replace(R.id.Fragment,
                        new Statistic()).commit();
                return true;
        }
        return false;
    }

    private void getExtra(){
        dbHelperAuto = new DBHelperAuto(this);

        Bundle arguments = getIntent().getExtras();
        if(arguments != null){
            if(arguments.containsKey("email")) {
                String email = arguments.getString("email");
                setAuto_id(dbHelperAuto.findByEmail(email));
            }
            if(arguments.containsKey("id")){
                setAuto_id(arguments.getInt("id"));
            }
        }else{
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,LogIn.class);
            startActivity(intent);
        }
    }
}