package nure.kazantseva.mycar.adapters;

import android.content.Context;
import android.content.Intent;
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
import nure.kazantseva.mycar.activity.Info;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> text,date,price, layout;
    ArrayList<Integer> expense_id;
    Integer auto_id;


    public ExpensesAdapter(Context context,ArrayList<Integer> expense_id,
                           Integer auto_id,ArrayList<String> layout, ArrayList<String> text
            , ArrayList<String> date, ArrayList<String> price) {
        this.expense_id = expense_id;
        this.auto_id = auto_id;
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
        holder.expense_id.setText(String.valueOf(expense_id.get(position)));
        holder.layout.setBackgroundColor(Color.parseColor(layout.get(position)));
        holder.text.setText(String.valueOf(text.get(position)));
        holder.date.setText(String.valueOf(date.get(position)));
        holder.price.setText(String.valueOf(price.get(position)));

        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt = v.findViewById(R.id.expense_id);
                TextView txt2 = v.findViewById(R.id.text);
                Integer expenseId = Integer.parseInt(txt.getText().toString());
                Intent intent = new Intent(context, Info.class);
                intent.putExtra("AutoId",auto_id);
                intent.putExtra("ExpenseId",expenseId);
                intent.putExtra("TypeOfExpense",txt2.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text,date,price, expense_id;
        ConstraintLayout layout;
        Integer auto_id;
        public View view;

        public MyViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            expense_id = itemView.findViewById(R.id.expense_id);
            layout = itemView.findViewById(R.id.layout);
            text = itemView.findViewById(R.id.text);
            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.price);
            auto_id = ExpensesAdapter.this.auto_id;

        }

        public View getView() {
            return view;
        }
    }
}
