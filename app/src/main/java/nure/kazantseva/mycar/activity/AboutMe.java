package nure.kazantseva.mycar.activity;

import static android.app.Activity.RESULT_OK;

import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.activity.addForms.AddOther;
import nure.kazantseva.mycar.activity.addForms.AddRefill;
import nure.kazantseva.mycar.activity.addForms.AddRepair;
import nure.kazantseva.mycar.activity.addForms.AddWasher;
import nure.kazantseva.mycar.activity.editForms.EditAuto;
import nure.kazantseva.mycar.activity.editForms.EditUser;
import nure.kazantseva.mycar.db.DBHelper;
import nure.kazantseva.mycar.model.Auto;
import nure.kazantseva.mycar.model.User;

public class AboutMe extends Fragment {

    int auto_id;
    String email;
    DBHelper dbHelper;
    ImageButton editUser, editAuto, deleteAuto;
    TextView name, emailTV, brandModel, year, run, uniqueCode, fuel_consumption, text, logout;
    Spinner spinner;
    ImageView meImage, autoImage;
    Uri selectedImageUri;
    String  meUri, autoUri;
    int isFirst;
    private static final int PICK_IMAGE = 1;

    public AboutMe(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_about_me, container, false);
        init(v);
        return v;
    }

    public void init(View v){
        auto_id = MainPage.getAuto_id();
        email = MainPage.getEmail();

        text = new TextView(this.getContext());
        dbHelper = new DBHelper(v.getContext());

        editUser = v.findViewById(R.id.editUser);
        editAuto = v.findViewById(R.id.editAuto);
        deleteAuto = v.findViewById(R.id.deleteAuto);
        spinner = v.findViewById(R.id.spinner);
        logout = v.findViewById(R.id.Logout);

        meImage = v.findViewById(R.id.meImage);
        autoImage = v.findViewById(R.id.autoImage);

        name = v.findViewById(R.id.name);
        emailTV = v.findViewById(R.id.email);
        brandModel = v.findViewById(R.id.brandModel);
        year = v.findViewById(R.id.year);
        run = v.findViewById(R.id.run);
        uniqueCode = v.findViewById(R.id.uniqueCode);
        fuel_consumption = v.findViewById(R.id.fuel_consumption);

        showImage();

        fillData();

        clickActions();

    }

    public void showImage(){
        Cursor cursor1 = dbHelper.getImageUser(email);

        if(cursor1.getCount() == 0){
        }else{
            while(cursor1.moveToNext() ){
                if(cursor1.getString(0) != null) {
                    meImage.setImageURI(Uri.parse(cursor1.getString(0)));
                }
            }
        }

        Cursor cursor2 = dbHelper.getImageAuto(auto_id);

        if(cursor2.getCount() == 0){
        }else{
            while(cursor2.moveToNext() ){
                if(cursor2.getString(0) != null) {
                    autoImage.setImageURI(Uri.parse(cursor2.getString(0)));
                }
            }
        }
    }

    @SuppressLint("Range")
    private void fillData(){
        User user = userData();
        Auto auto = autoData();

        name.setText(user.getName());
        emailTV.setText(user.getEmail());
        brandModel.setText(auto.getBrand() + " " + auto.getModel());
        year.setText(String.valueOf(auto.getYear()) + " року");
        uniqueCode.setText(auto.getUniqueCode());

        Cursor maxRun = dbHelper.findMaxRun(auto_id);
        if(maxRun.getCount() == 0){
            Toast.makeText(this.getActivity(),"No data!",Toast.LENGTH_LONG).show();
        }else{
            while(maxRun.moveToNext()){
                run.setText(String.valueOf(maxRun.getLong(0)) + " км");
            }
        }

        Cursor consumption = dbHelper.getFuelConsumption(auto_id);
        int i = 0;
        long[] runKM = new long[2];
        double[] beforeRefill = new double[2];
        double[] addFuel = new double[2];
        if(consumption.getCount() == 0){
            Toast.makeText(this.getActivity(),"No data!",Toast.LENGTH_LONG).show();
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
            double runValue = ((beforeRefill[1] + addFuel[1] - beforeRefill[0])*100)/(runKM[0]-runKM[1]);
            fuel_consumption.setText(df.format(runValue) + " л/км");
        }


    }

    private User userData(){
        Cursor cursor = dbHelper.findUserByEmail(email);
        User user = new User();
        if(cursor.getCount() == 0){
            Toast.makeText(this.getActivity(),"No data!",Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                user.setName(cursor.getString(0));
                user.setEmail(cursor.getString(1));
                user.setPassword(cursor.getString(2));
            }
        }
        return user;
    }

    private Auto autoData(){
        Cursor cursor = dbHelper.findAutoById(auto_id);
        Auto auto = new Auto();
        if(cursor.getCount() == 0){
            Toast.makeText(this.getActivity(),"No data!",Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                auto.setUniqueCode(cursor.getString(1));
                auto.setBrand(cursor.getString(2));
                auto.setModel(cursor.getString(3));
                auto.setYear(cursor.getInt(4));
                auto.setFuel(cursor.getString(5));
                auto.setRun(cursor.getLong(6));
            }
        }
        return auto;
    }

    private void clickActions(){
        editUser.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditUser.class);
            startActivity(intent);
        });
        editAuto.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditAuto.class);
            startActivity(intent);
        });
        deleteAuto.setOnClickListener(v -> {
            dbHelper.deleteAuto(auto_id);
            if(dbHelper.checkByEmail(email) > 0){
            }else{

                Intent intent = new Intent(v.getContext(), CreateAuto.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(v -> {
           getActivity().finish();
           Intent intent = new Intent(getContext(), LogIn.class);
           startActivity(intent);
        });

        meImage.setOnClickListener(v -> {
            isFirst = 0;
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        autoImage.setOnClickListener(v -> {
            isFirst = 1;
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        if(dbHelper.countAutos(email) > 0){
            Cursor cursor = dbHelper.getAutosOfUser(email);
            List<String> autos = new ArrayList<>();
            List<Integer> autosId = new ArrayList<>();
            autos.add("Змінити авто");
            if(cursor.getCount() == 0){
                Toast.makeText(getContext(), "No autos" , Toast.LENGTH_LONG).show();
            }else{
                while(cursor.moveToNext()){
                    autos.add(cursor.getString(1)
                            + " " + cursor.getString(2));
                    autosId.add(cursor.getInt(0));
                }
                setSpinner(autos, autosId);
            }
            autos.add("Додати нове авто");
            autos.add("Додати авто по коду");
        }else{
            Intent intent = new Intent(getContext(), CreateAuto.class);
            intent.putExtra("email",email);
            startActivity(intent);
        }

    }


    private void setSpinner(List<String> autos, List<Integer> autosId){
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getContext()
                , R.layout.layout_spinner2_item, autos);

        adapter.setDropDownViewResource(R.layout.layout_spinner2_item);

        spinner.setAdapter(adapter);


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = (String)parent.getItemAtPosition(position);
                text.setText(item);
                if(position == autos.size()-1){
                    Intent intent = new Intent(getContext(), AddExistedCar.class);
                    intent.putExtra("email",email);
                    intent.putExtra("back",-1);
                    startActivity(intent);
                }else if(position == autos.size()-2){
                    Intent intent = new Intent(getContext(), CreateAuto.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                }else{
                    if(position != 0) {
                        if(auto_id != autosId.get(position-1)) {
                            Intent intent = new Intent(getContext(), MainPage.class);
                            intent.putExtra("email", email);
                            intent.putExtra("id", autosId.get(position - 1));
                            startActivity(intent);
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                text.setText("Змінити авто");
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            selectedImageUri = data.getData();
            URI uri = null;
            try {
                uri = new URI(selectedImageUri.toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if(isFirst == 0){
                meImage.setImageURI(selectedImageUri);
                meUri = uri.toString();
                dbHelper.insertImageUser(meUri, email);
            }else{
                autoImage.setImageURI(selectedImageUri);
                autoUri = uri.toString();
                dbHelper.insertImageAuto(autoUri, auto_id);
            }
            this.getContext().getContentResolver()
                    .takePersistableUriPermission(
                            selectedImageUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    );
        }
    }

}