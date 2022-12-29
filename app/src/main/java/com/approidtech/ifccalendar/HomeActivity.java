package com.approidtech.ifccalendar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ozcanalasalvar.library.utils.DateUtils;
import com.ozcanalasalvar.library.view.datePicker.DatePicker;
import com.ozcanalasalvar.library.view.popup.DatePickerPopup;
import com.ozcanalasalvar.library.view.popup.TimePickerPopup;
import com.ozcanalasalvar.library.view.timePicker.TimePicker;


public class HomeActivity extends AppCompatActivity {

    private ImageView imgHourHand,imgDayHand,imgMonthHand,ivMenu;
    private Button btConvert,btReminder;
    private TextView tVResult, tVSetDate,tvSetTime;
    private DatePickerPopup datePickerPopup;
    private TimePickerPopup timePickerPopup;
    private String userDate,userTime;
    String myID;
    @SuppressLint("HardwareIds")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){e.printStackTrace();}

        imgHourHand = (ImageView) findViewById(R.id.img_hour_hand);
        imgDayHand = (ImageView)findViewById(R.id.img_day_hand);
        imgMonthHand = (ImageView)findViewById(R.id.img_month_hand);
        tVResult = (TextView) findViewById(R.id.tvresult);
        tVSetDate = (TextView)findViewById(R.id.tv_setdate);
        tvSetTime = (TextView)findViewById(R.id.tv_settime);
        ivMenu = (ImageView) findViewById(R.id.iv_menu);
        btConvert = (Button) findViewById(R.id.btnconvert);
        btReminder = (Button)findViewById(R.id.btnreminders);

        myID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        registerForContextMenu(ivMenu);

        btConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Please select Date/Time.",Toast.LENGTH_SHORT).show();

            }
        });

        datePickerPopup = new DatePickerPopup.Builder()
                .from(HomeActivity.this)
                .offset(4)
                .pickerMode(DatePicker.MONTH_ON_FIRST)
                .textSize(19)
                .endDate(DateUtils.getTimeMiles(2050, 10, 25))
                .currentDate(DateUtils.getCurrentTime())
                .startDate(DateUtils.getTimeMiles(1995, 0, 1))
                .listener(new DatePickerPopup.OnDateSelectListener() {
                    @Override
                    public void onDateSelected(DatePicker dp, long date, int day, int month, int year) {
                        tVSetDate.setText("" + day + "-" + (month + 1) + "-" + year);
                        userDate = "" + (month + 1) + "/" + day + "/" + year;
                    }
                })
                .build();

        timePickerPopup = new TimePickerPopup.Builder()
                .from(this)
                .offset(4)
                .textSize(19)
                .setTime(12, 00)
                .listener(new TimePickerPopup.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelected(TimePicker timePicker, int hour, int minute) {
                        tvSetTime.setText(""+hour+":"+minute);
                        userTime = hour+"."+minute;
                    }
                })
                .build();
        tVSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(view);
            }
        });
        tvSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker(view);
            }
        });
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivMenu.performLongClick();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // you can set menu header with title icon etc
        menu.setHeaderTitle("Options");
        // add menu items
        menu.add(0, v.getId(), 0, "Reminder");
        menu.add(0, v.getId(), 0, "Info");

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Reminder") {
            Toast.makeText(getApplicationContext(),"Reminder Clicked", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle() == "Info") {
            Toast.makeText(getApplicationContext(),"Info Clicked", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
    private void rotateDialer(double getDays,double getMonth){
        double setHours = Double.parseDouble(userTime);
        double hours = 360/24 * setHours +180;

        double setDay = getDays;
        double days = (12.857*setDay-90)-6.428;

        double setYear = getMonth;
        double years = setYear-90;

        if(getMonth>360){
            restDayPopup();
        }

        RotateAnimation hour = new RotateAnimation(-180, (float) hours, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        hour.setDuration(1200);
        hour.setInterpolator(new LinearInterpolator());
        hour.setFillAfter(true);
        imgHourHand.startAnimation(hour);

        RotateAnimation day = new RotateAnimation(-90, (float) days, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        day.setDuration(1200);
        day.setInterpolator(new LinearInterpolator());
        day.setFillAfter(true);
        imgDayHand.startAnimation(day);

        RotateAnimation month = new RotateAnimation(-90, (float) years, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        month.setDuration(1200);
        month.setInterpolator(new LinearInterpolator());
        month.setFillAfter(true);
        imgMonthHand.startAnimation(month);

    }

    public void restDayPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Entered date is rest day of the year.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void openDatePicker(View view) {
        datePickerPopup.show();
    }
    public void openTimePicker(View view) {
        timePickerPopup.show();
    }
}
