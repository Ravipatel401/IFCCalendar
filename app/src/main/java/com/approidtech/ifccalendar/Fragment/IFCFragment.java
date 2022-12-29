package com.approidtech.ifccalendar.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.approidtech.ifccalendar.API.APIClient;
import com.approidtech.ifccalendar.API.APIInterface;
import com.approidtech.ifccalendar.API.Data;
import com.approidtech.ifccalendar.API.EventListData;
import com.approidtech.ifccalendar.API.ResponseData;
import com.approidtech.ifccalendar.R;
import com.approidtech.ifccalendar.utils.EventAdapter;
import com.approidtech.ifccalendar.utils.IFCDayAdapter;
import com.google.gson.Gson;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IFCFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 7;
    private ArrayList<Integer> getarray = new ArrayList<Integer>();
    private TextView tvMonthYear,tvIFCResult;
    private int currentMonth,currentYear;
    private ImageView ivBack,ivForward;
    private boolean isLeapYear;
    private String IFCDay,IFCYear;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    private APIInterface apiInterface;
    private String myID;
    private ListView eventListView;
    public IFCFragment() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivback:
                setPreviousMonthYear();
                break;
            case R.id.ivforward:
                setNextMonthYear();
                break;
            default:
                break;
        }
    }

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLeapYear =false;
    }

    @SuppressLint("HardwareIds")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ifc, container, false);
        rootView.setTag(TAG);
        myID = Settings.Secure.getString(requireActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        tvMonthYear = rootView.findViewById(R.id.monthYearTV);
        ivBack = rootView.findViewById(R.id.ivback);
        ivForward = rootView.findViewById(R.id.ivforward);
        tvIFCResult = rootView.findViewById(R.id.tvIFCResult);
        eventListView = rootView.findViewById(R.id.eventListView);
        ivBack.setOnClickListener(this::onClick);
        ivForward.setOnClickListener(this::onClick);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.ifcRecyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }

        setMonthYear();
        initDataset();
        setMonthView();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        return rootView;
    }
    public void onResume() {
        super.onResume();
        SharedPreferences pref = requireActivity().getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int day=pref.getInt("Day", 12);
        int month=pref.getInt("Month", 12);
        int year=pref.getInt("Year", 2023);
        getData(month+"/"+day+"/"+year);
        currentMonth = month;
        currentYear = year;
        tvMonthYear.setText(currentMonth+"-"+currentYear);
        setMonthView();
    }
    private void setMonthYear(){
        DateFormat mm = new SimpleDateFormat("MM");
        Date month = new Date();
        currentMonth = Integer.parseInt(mm.format(month));
        DateFormat yyyy = new SimpleDateFormat("yyyy");
        Date year = new Date();
        currentYear = Integer.parseInt(yyyy.format(year));
        tvMonthYear.setText(currentMonth+"-"+currentYear);
    }
    private void setNextMonthYear(){
        if (currentMonth > 13){
            currentMonth = 1;
            currentYear = currentYear +1;
            tvMonthYear.setText(currentMonth+"-"+currentYear);
        }else {
            currentMonth = currentMonth+1;
            tvMonthYear.setText(currentMonth+"-"+currentYear);
        }
        initDataset();
    }
    private void setPreviousMonthYear(){
        if (currentMonth<=1) {
            currentMonth = 14;
            currentYear = currentYear -1;
            tvMonthYear.setText(currentMonth+"-"+currentYear);
        }else {
            currentMonth = currentMonth - 1;
            tvMonthYear.setText(currentMonth + "-" + currentYear);
        }
        initDataset();
    }
    private void setMonthView(){
       // mAdapter = new IFCDayAdapter(getarray);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setAdapter(new IFCDayAdapter(getarray, new IFCDayAdapter.OnItemListener() {
            @Override
            public void onItemClick(int selectedDay) {
                IFCDay = String.valueOf(selectedDay);
                getData(currentMonth+"/"+IFCDay+"/"+currentYear);
            }
        },getContext()));
    }
    private void getData(String userDate){
        apiInterface.fethcDateIFC(APIClient.API_KEY,userDate,myID).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.code()==200){
                    if(response.body().getSuccess()==1){
                        Data data = new Gson().fromJson(new Gson().toJson(response.body().getData()), Data.class);
                        tvIFCResult.setText(data.getResultDate());
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
        EventAdapter eventAdapter = new EventAdapter(getActivity(),0,eventAdpater,0);
        eventListView.setAdapter(eventAdapter);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }
    private void initDataset() {
        int j;
        GregorianCalendar cal = new GregorianCalendar();
        if (cal.isLeapYear(currentYear+1)){
            isLeapYear = true;
            j=3;
        }else {
            isLeapYear =false;
            j = 2;
        }
        if (currentMonth == 14) {
            getarray.clear();
            for (int i = 1; i < j; i++) {
                getarray.add(i);
            }
            setMonthView();
        }else {
            getarray.clear();
            for (int i = 1; i < 29; i++) {
                getarray.add(i);
            }
            setMonthView();
        }
    }
}