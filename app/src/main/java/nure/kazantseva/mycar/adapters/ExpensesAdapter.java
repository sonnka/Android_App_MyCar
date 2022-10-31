package nure.kazantseva.mycar.adapters;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nure.kazantseva.mycar.R;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> text,date,price;
    ArrayList<String> layout;


    public ExpensesAdapter(Context context,ArrayList<String> layout, ArrayList<String> text
            , ArrayList<String> date, ArrayList<String> price) {
        this.layout = layout;
        this.text = text;
        this.context = context;
        this.date = date;
        this.price = price;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_expenses,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.layout.setBackgroundColor(Color.parseColor(String.valueOf(layout.get(position))));
        holder.text.setText(String.valueOf(text.get(position)));
        holder.date.setText(String.valueOf(date.get(position)));
        holder.price.setText(String.valueOf(price.get(position)));
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text,date,price;
        ConstraintLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            text = itemView.findViewById(R.id.text);
            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.price);
        }
    }
}
