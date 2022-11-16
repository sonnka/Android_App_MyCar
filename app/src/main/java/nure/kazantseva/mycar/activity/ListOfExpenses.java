package nure.kazantseva.mycar.activity;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.activity.addForms.AddOther;
import nure.kazantseva.mycar.activity.addForms.AddRefill;
import nure.kazantseva.mycar.activity.addForms.AddRepair;
import nure.kazantseva.mycar.activity.addForms.AddWasher;
import nure.kazantseva.mycar.adapters.ExpensesAdapter;
import nure.kazantseva.mycar.db.DBHelper;

public class ListOfExpenses extends Fragment {

    FloatingActionButton fab, fab1, fab2, fab3, fab4;
    TabLayout tabLayout;
    SearchView searchView;
    RecyclerView recyclerView;
    Boolean isFABOpen = false;
    int auto_id;
    DBHelper dbHelper;
    ExpensesAdapter expensesAdapter;
    ArrayList<String> date, price, text, layout;
    ArrayList<Integer> expense_id;


    public ListOfExpenses(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_list_of_expenses, container, false);
        init(v);
        return v;
    }

    public void init(View v){
        auto_id = MainPage.getAuto_id();
        text = new ArrayList<>();
        date = new ArrayList<>();
        price = new ArrayList<>();
        layout = new ArrayList<>();
        expense_id = new ArrayList<>();

        dbHelper = new DBHelper(this.getActivity());

        recyclerView = v.findViewById(R.id.recycle_view);
        tabLayout = v.findViewById(R.id.tabs);
        searchView = v.findViewById(R.id.search);

        fab = v.findViewById(R.id.add_button);
        fab1 = v.findViewById(R.id.other_button);
        fab2 = v.findViewById(R.id.washer_button);
        fab3 = v.findViewById(R.id.refill_button);
        fab4 = v.findViewById(R.id.repair_button);
        fab.setOnClickListener(view -> {
            if(!isFABOpen){
                showFABMenu();
            }else{
                closeFABMenu();
            }
        });


        fab1.setOnClickListener(view ->{
            Intent intent = new Intent(this.getActivity(), AddOther.class);
            intent.putExtra("id",auto_id);
            startActivity(intent);
        });
        fab2.setOnClickListener(view ->{
            Intent intent = new Intent(this.getActivity(), AddWasher.class);
            intent.putExtra("id",auto_id);
            startActivity(intent);
        });
        fab3.setOnClickListener(view ->{
            Intent intent = new Intent(this.getActivity(), AddRefill.class);
            intent.putExtra("id",auto_id);
            startActivity(intent);
        });
        fab4.setOnClickListener(view ->{
            Intent intent = new Intent(this.getActivity(), AddRepair.class);
            intent.putExtra("id",auto_id);
            startActivity(intent);
        });



//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterDate(newText);
//                return false;
//            }
//        });
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                clearRecycleView();
//                displayAllData();
//                setAdapterOnRecycleView();
//                return false;
//            }
//        });

        displayAllData();
        setAdapterOnRecycleView();

        selectingTabs();

    }

//    private void filterDate(String searchText){
//        Cursor cursor = dbHelper.searchByDate(auto_id, searchText);
//        clearRecycleView();
//        fillData(cursor);
//        setAdapterOnRecycleView();
//    }

    private void selectingTabs() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0: {
                        clearRecycleView();
                        displayAllData();
                        setAdapterOnRecycleView();
                        break;
                    }
                    case 1: {
                        clearRecycleView();
                        displayRepair();
                        setAdapterOnRecycleView();
                        break;
                    }
                    case 2: {
                        clearRecycleView();
                        displayRefill();
                        setAdapterOnRecycleView();
                        break;
                    }
                    case 3: {
                        clearRecycleView();
                        displayWasher();
                        setAdapterOnRecycleView();
                        break;
                    }
                    case 4: {
                        clearRecycleView();
                        displayOther();
                        setAdapterOnRecycleView();
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void clearRecycleView(){
        text = new ArrayList<>();
        date = new ArrayList<>();
        price = new ArrayList<>();
        layout = new ArrayList<>();
        expense_id = new ArrayList<>();
    }

    private void setAdapterOnRecycleView() {
        expensesAdapter = new ExpensesAdapter(this.getActivity(),expense_id,auto_id,
                layout,text,date,price);
        recyclerView.setAdapter(expensesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
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

    @SuppressLint("Range")
    private void displayAllData(){
        Cursor cursor = dbHelper.readAllDataByAutoId(auto_id);
        fillData(cursor);
    }

    private void fillData(Cursor cursor){
        if(cursor.getCount() == 0){
            Toast.makeText(this.getActivity(),"No data!",Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                expense_id.add(cursor.getInt(0));
                layout.add(cursor.getString(1));
                text.add(cursor.getString(2));
                date.add(cursor.getString(3));
                price.add(cursor.getString(4));
            }
        }
    }

    private void displayRepair(){
        Cursor cursor = dbHelper.readCertainDataByAutoId(auto_id, "repair");
        fillData(cursor);
    }

    private void displayRefill(){
        Cursor cursor = dbHelper.readCertainDataByAutoId(auto_id, "refill");
        fillData(cursor);
    }

    private void displayWasher(){
        Cursor cursor = dbHelper.readCertainDataByAutoId(auto_id, "washer");
        fillData(cursor);
    }

    private void displayOther(){
        Cursor cursor = dbHelper.readCertainDataByAutoId(auto_id, "other");
        fillData(cursor);
    }

}