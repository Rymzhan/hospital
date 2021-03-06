package com.example.diploma_hospital.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diploma_hospital.R;
import com.example.diploma_hospital.model.NoteView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<NoteView> mList;
    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date, name, number;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            date = v.findViewById(R.id.tvDate);
            name = v.findViewById(R.id.tvUser);
            number = v.findViewById(R.id.tvNum);
            cardView = v.findViewById(R.id.dateCV);
        }
    }

    public ListAdapter(List<NoteView> plist, Context pContext) {
        this.mList = plist;
        this.mContext = pContext;
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = this.mLayoutInflater.inflate(R.layout.my_row, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //set data
        NoteView weightData = mList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date currentDate = new Date();
            Date checkDate = sdf.parse(weightData.getDate());
            if (currentDate.compareTo(checkDate) < 0) {
                holder.cardView.setBackgroundColor(Color.GREEN);
                holder.date.setText(weightData.getDate());
            } else if (currentDate.compareTo(checkDate) > 0) {
                holder.cardView.setBackgroundColor(Color.LTGRAY);
                holder.date.setText(weightData.getDate());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(weightData.getGuestName().length()>7){
            holder.name.setTextSize(18);
        }
        if(weightData.getGuestName().length()>20){
            holder.name.setTextSize(16);
        }


        holder.name.setText(weightData.getGuestName());

        if(weightData.getDate().equals("1")){
            holder.cardView.setBackgroundColor(Color.GREEN);
            holder.date.setText("Ваш анализ готов");
        }else if(weightData.getDate().equals("0")){
            holder.cardView.setBackgroundColor(Color.YELLOW);
            holder.date.setText("Находится в обработке");
        }
        else {
            holder.date.setText(weightData.getDate());
        }
        if (weightData.getNum().equals(" ")) {
            holder.number.setVisibility(View.INVISIBLE);
        } else {
            holder.number.setVisibility(View.VISIBLE);
            holder.number.setText(weightData.getNum());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
