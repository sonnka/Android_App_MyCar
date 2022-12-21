package nure.kazantseva.mycar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nure.kazantseva.mycar.R;
import nure.kazantseva.mycar.activity.statistics.Statistic1;
import nure.kazantseva.mycar.activity.statistics.Statistic2;
import nure.kazantseva.mycar.activity.statistics.Statistic3;
import nure.kazantseva.mycar.activity.statistics.Statistic4;

public class Statistic extends Fragment {

    AppCompatButton button1, button2, button3, button4, button5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_statistic, container, false);
        init(v);
        return v;
    }

    public void init(View v){
        button1 = v.findViewById(R.id.button1);
        button2 = v.findViewById(R.id.button2);
        button3 = v.findViewById(R.id.button3);
        button4 = v.findViewById(R.id.button4);
        button5 = v.findViewById(R.id.button5);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Statistic1.class);
                startActivity(intent);
              //  getActivity().finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Statistic2.class);
                startActivity(intent);
              //  getActivity().finish();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Statistic3.class);
                startActivity(intent);
               // getActivity().finish();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Statistic4.class);
                startActivity(intent);
               // getActivity().finish();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ExecuteOfQuery.class);
                startActivity(intent);
               // getActivity().finish();
            }
        });
    }
}