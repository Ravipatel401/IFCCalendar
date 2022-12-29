package com.approidtech.ifccalendar.utils;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.approidtech.ifccalendar.API.EventListData;
import com.approidtech.ifccalendar.R;

import java.util.List;

public class EventAdapter extends ArrayAdapter<EventListData> {

    int i;
    public EventAdapter(@NonNull Context context, int resource, @NonNull List<EventListData> objects, int i) {
        super(context, resource, objects);
        this.i = i;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        EventListData event = getItem(position);

        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        TextView eventDate,eventTime,eventMessage;

        eventDate= view.findViewById(R.id.eventcellDate);
        eventTime =view.findViewById(R.id.eventcellTime);
        eventMessage = view.findViewById(R.id.eventcellMessege);


        if (i == 1) {
            eventDate.setText(event.getIs365Date());
        }else {
            eventDate.setText(event.getIs364Date());
        }
        //eventDate.setText(event.getIs365Date());
        eventTime.setText(event.getIsTime());
        eventMessage.setText(event.getMessage());
        return view;
    }
}
