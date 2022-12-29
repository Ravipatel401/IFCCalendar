package com.approidtech.ifccalendar.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.approidtech.ifccalendar.utils.CalendarUtils.daysInMonthArray;
import static com.approidtech.ifccalendar.utils.CalendarUtils.monthYearFromDate;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.approidtech.ifccalendar.API.APIClient;
import com.approidtech.ifccalendar.API.APIInterface;
import com.approidtech.ifccalendar.API.Data;
import com.approidtech.ifccalendar.API.EventListData;
import com.approidtech.ifccalendar.API.ResponseData;
import com.approidtech.ifccalendar.R;
import com.approidtech.ifccalendar.utils.CalendarAdapter;
import com.approidtech.ifccalendar.utils.CalendarUtils;

import com.approidtech.ifccalendar.utils.EventAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.ozcanalasalvar.library.view.popup.TimePickerPopup;
import com.ozcanalasalvar.library.view.timePicker.TimePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NormalCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NormalCalendarFragment extends Fragment implements CalendarAdapter.OnItemListener,View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView ivForward,ivBack;
    private TextView monthYearText,tVResult;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private Button addEvent;
    private Dialog dialog;
    private TimePickerPopup timePickerPopup;
    private String selectedDate,selectedTime;
    private APIInterface apiInterface;
    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String myID;
    public NormalCalendarFragment() {
        // Required empty public constructor
    }

    public static NormalCalendarFragment newInstance(String param1, String param2) {
        NormalCalendarFragment fragment = new NormalCalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("HardwareIds")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_normalcalendar, container, false);

        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
        eventListView = view.findViewById(R.id.eventListView);
        tVResult = view.findViewById(R.id.tvresult);
        addEvent = view.findViewById(R.id.addevent);
        ivBack = view.findViewById(R.id.ivback);
        ivForward = view.findViewById(R.id.ivforward);
        ivBack.setOnClickListener(this::onClick);
        ivForward.setOnClickListener(this::onClick);
        addEvent.setOnClickListener(this::onClick);
        CalendarUtils.selectedDate = LocalDate.now();
        myID = Settings.Secure.getString(requireActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        setMonthView();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        return view;
    }
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        //setEventAdpater();
    }
    @Override
    public void onItemClick(int position, LocalDate date) {
        if(date != null) {
            CalendarUtils.selectedDate = date;
            selectedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            getData(date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            setMonthView();
        }
    }
    private void getData(String userDate){
        apiInterface.fetchDate(APIClient.API_KEY,userDate,myID).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.code()==200){
                    if(response.body().getSuccess()==1){
                        Data data = new Gson().fromJson(new Gson().toJson(response.body().getData()), Data.class);
                        if (data.getExtraDay() == 0) {
                            tVResult.setText(data.getResultDate());
                            SharedPreferences.Editor editor = requireActivity().getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE).edit();
                            editor.putInt("Day", data.getCurrDay());
                            editor.putInt("Month", data.getCurrMonth());
                            editor.putInt("Year", data.getCurrYear());
                            editor.apply();
                        }else{
                            tVResult.setText("Rest Day");
                        }
                        List<EventListData> listData =null;
                        listData = data.getEventData();
                        setEventAdpater(listData);

                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void setEventAdpater(List<EventListData> eventAdpater) {
        EventAdapter eventAdapter = new EventAdapter(getActivity(),0,eventAdpater,1);
        eventListView.setAdapter(eventAdapter);
    }
    private void addEventPopup() throws DateTimeParseException {
        String date;
        date =selectedDate;
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_reminder);
        dialog.setCancelable(false);
        Button btnCancel = (Button) dialog.findViewById(R.id.btncancel);
        Button btnSet = (Button) dialog.findViewById(R.id.btnset);
        TextView setTime = (TextView)dialog.findViewById(R.id.tv_settime);
        TextView setDate = (TextView)dialog.findViewById(R.id.tv_setdate);
        TextInputEditText messegeBox = (TextInputEditText)dialog.findViewById(R.id.ib_messege);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
        if (date != null) {
            setDate.setText(date);
            if (CalendarUtils.isValidFormat("dd/MM/yyyy",date)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dateTime = LocalDate.parse(selectedDate, formatter);
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                selectedDate = dateTime.format(formatter2);

            }else {
                selectedDate =date;
            }
        }else {
            selectedDate = String.valueOf(CalendarUtils.selectedDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateTime = LocalDate.parse(selectedDate, formatter);
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            selectedDate = dateTime.format(formatter2);
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(selectedDate,formatter2);
            setDate.setText(localDate.format(formatter1));
        }

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName = messegeBox.getText().toString();
                dialog.dismiss();
                addEvent(selectedDate,selectedTime,eventName);
                //getData(selectedDate);
            }
        });
        timePickerPopup = new TimePickerPopup.Builder()
                .from(getActivity())
                .offset(4)
                .textSize(17)
                .setTime(12, 30)
                .listener(new TimePickerPopup.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelected(TimePicker timePicker, int hour, int minute) {
                        selectedTime = hour+":"+minute;
                        setTime.setText(hour+":"+minute);
                    }
                })
                .build();
    }
    public void addEvent(String selectedDate, String selectedTime, String message){
        apiInterface.addEvent(APIClient.API_KEY,"1",selectedDate,selectedTime,message,myID).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.code()==200){
                    if(response.body().getSuccess()==1){
                        new AlertDialog.Builder(getContext())
                                .setTitle("Event")
                                .setMessage("Event saved successfully...")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        getData(selectedDate);
                                        dialog.dismiss();
                                    }
                                })
                                .show();

                    }
                }

            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    public void openTimePicker() {
        timePickerPopup.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivback:
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
                setMonthView();
                break;
            case R.id.ivforward:
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
                setMonthView();
                break;
            case R.id.addevent:
                addEventPopup();
                break;
            default:
                break;
        }
    }
}