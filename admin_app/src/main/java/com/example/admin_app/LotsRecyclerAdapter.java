package com.example.admin_app;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.rxjava3.annotations.NonNull;


public class LotsRecyclerAdapter extends RecyclerView.Adapter<LotsRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<LotsItem> lotsItemArrayList;
    DatabaseReference databaseReference;

    public LotsRecyclerAdapter(Context context, ArrayList<LotsItem> lotsItemArrayList) {
        this.context = context;
        this.lotsItemArrayList = lotsItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.lot_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LotsItem lots = lotsItemArrayList.get(position);

        holder.textName.setText("Name: " + lots.getLotName());
        holder.textPrice.setText("Price: " + lots.getLotPrice());

        holder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogUpdate viewDialogUpdate = new ViewDialogUpdate();
                viewDialogUpdate.showDialog(context, lots.getLotID(), lots.getLotName(), lots.getLotPrice());
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogConfirmDelete viewDialogConfirmDelete = new ViewDialogConfirmDelete();
                viewDialogConfirmDelete.showDialog(context, lots.getLotID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return lotsItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textPrice;

        Button buttonDelete;
        Button buttonUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);

            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
        }
    }

    public class ViewDialogUpdate {
        public void showDialog(Context context, String idLot, String name, String price) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_lot);

            EditText textName = dialog.findViewById(R.id.textName);
            EditText textPrice = dialog.findViewById(R.id.textPrice);

            textName.setText(name);
            textPrice.setText(price);


            Button buttonUpdate = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonUpdate.setText("UPDATE");

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String newName = textName.getText().toString();
                    String newPrice = textPrice.getText().toString();

                    if (name.isEmpty() || price.isEmpty() ) {
                        Toast.makeText(context, "All fields must be completed!", Toast.LENGTH_SHORT).show();
                    } else {

                        if (newName.equals(name) && newPrice.equals(price)) {
                            Toast.makeText(context, "Nothing changed!", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.child("LOTS").child(idLot).setValue(new LotsItem(idLot, newName, newPrice));
                            Toast.makeText(context, "Lot Updated!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }


                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }


    public class ViewDialogConfirmDelete {
        public void showDialog(Context context, String idLot) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_dialog_confirm_delete);

            Button buttonDelete = dialog.findViewById(R.id.buttonDelete);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    databaseReference.child("LOTS").child(idLot).removeValue();
                    Toast.makeText(context, "User Deleted successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }
}


