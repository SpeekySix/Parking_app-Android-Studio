package com.example.parking_app;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import io.reactivex.rxjava3.annotations.NonNull;

public class LotsRecyclerAdapter extends FirebaseRecyclerAdapter<LotsItem, LotsRecyclerAdapter.myViewHolder> {

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public LotsRecyclerAdapter(@NonNull FirebaseRecyclerOptions<LotsItem> options) {
        super(options);
    }

    Context context;
    private int currentPosition = RecyclerView.NO_POSITION;



    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, LotsItem model) {
        holder.lotName.setText(model.getLotName());
        holder.lotPrice.setText(model.getLotPrice());
        currentPosition = holder.getAdapterPosition();



    }



    //dialog box
    public void showDialog(int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        // Customize the dialog as needed
        // ...

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }




    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lot_item,parent, false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView lotName, lotPrice;

        public myViewHolder(@NonNull View itemView){
            super(itemView);

            lotName = (TextView)itemView.findViewById(R.id.textName);
            lotPrice = (TextView)itemView.findViewById(R.id.textPrice);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    listener.onItemClicked(position);
                }
            }
        }
    }
}
