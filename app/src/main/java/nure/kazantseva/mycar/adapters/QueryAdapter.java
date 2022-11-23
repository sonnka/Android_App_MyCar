package nure.kazantseva.mycar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nure.kazantseva.mycar.R;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> text;


    public QueryAdapter(Context context, ArrayList<String> text) {
        this.text = text;
        this.context = context;
    }

    @NonNull
    @Override
    public QueryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_query_adapter, parent, false);
        return new QueryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QueryAdapter.MyViewHolder holder, int position) {
        holder.text.setText(text.get(position));

    }

    @Override
    public int getItemCount() {
        return text.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public View view;

        public MyViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            text = itemView.findViewById(R.id.text);

        }

        public View getView() {
            return view;
        }
    }
}
