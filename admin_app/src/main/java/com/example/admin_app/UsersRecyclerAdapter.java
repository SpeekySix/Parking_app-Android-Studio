package com.example.admin_app;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


import io.reactivex.rxjava3.annotations.NonNull;

public class UsersRecyclerAdapter extends FirebaseRecyclerAdapter<UsersItem,UsersRecyclerAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UsersRecyclerAdapter(FirebaseRecyclerOptions<UsersItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull UsersItem model) {
        holder.nameU.setText(model.getNameU());
        holder.emailU.setText(model.getEmailU());
        holder.plate.setText(model.getPlate());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView nameU, emailU, plate;

        public myViewHolder(@NonNull View itemView){
            super(itemView);

            nameU = (TextView) itemView.findViewById(R.id.textName);
            emailU = (TextView) itemView.findViewById(R.id.textEmail);
            plate = (TextView) itemView.findViewById(R.id.textPlate);
        }

    }
}
