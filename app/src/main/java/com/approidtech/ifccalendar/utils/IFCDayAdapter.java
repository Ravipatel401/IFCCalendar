package com.approidtech.ifccalendar.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.approidtech.ifccalendar.R;

import java.util.ArrayList;


public class IFCDayAdapter extends RecyclerView.Adapter<IFCDayAdapter.ViewHolder> {
    private static final String TAG = "IFCAdapter";

    int selectedPosition=-1;
    ArrayList<Integer> mylist;
    int selectedDay;
    private final OnItemListener onItemListener;
    private Context context;
    public IFCDayAdapter(ArrayList<Integer> dataSet, OnItemListener onItemListener, Context context) {
        mylist = dataSet;
        this.onItemListener = onItemListener;
        this.context =context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Log.d(TAG, ">Element " + getAdapterPosition() + " clicked.");
                }
            });
            textView = (TextView) v.findViewById(R.id.cellDayText);
        }
        public TextView getTextView() {
            return textView;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.calendar_cell, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        int day = pref.getInt("Day", 12);

        if ((day-1)==holder.getAdapterPosition()){
            holder.textView.setBackgroundColor(Color.GREEN);

        }else {
            if (selectedPosition == holder.getAdapterPosition()) {
                holder.textView.setBackgroundColor(Color.BLACK);
            } else {
                holder.textView.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=holder.getAdapterPosition();
                selectedDay = selectedPosition +1;
                onItemListener.onItemClick(selectedDay);
                notifyDataSetChanged();
            }
        });
        holder.getTextView().setText(String.valueOf(position + 1));

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
    public interface OnItemListener {
        void onItemClick(int selectedDay);
    }
}