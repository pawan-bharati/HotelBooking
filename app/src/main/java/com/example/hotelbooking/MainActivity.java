package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    //private ProgressBar progressBar;
    // private int progressStatus=0;
    //private TextView textView;
    //private Handler handler= new Handler();
    private String[] location = {"Bharatpur", "Surkhet", "Dailekh", "Kathmandu", "Bhaktapur", "lalitpur"};
    private String[] room = {"Deluxe", "AC", "Platinum"};
    private Spinner spinlocation;
    private Spinner spinroom;
    EditText etadult, etchild, etroom;
    Button btncalc;
    TextView tvdayofstay, totalsuite, tvtotal, tvVat, tvroomno, tverr;
    int no_of_room;
    private TextView simpledatepicker1, simpledatepicker2;
    private DatePicker view;
    private int year1, year2;
    private int month1, month2;
    private int day1, day2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // progressBar = findViewById(R.id.progressBar);
        //textView = findViewById(R.id.textView);
        //new Thread(new Runnable() {
        //  @Override
        // public void run() {
        //   while (progressStatus < 100) {
        //     progressStatus += 1;
        //   handler.post(new Runnable() {
        //     @Override
        //   public void run() {
        //     progressBar.setProgress(progressStatus);
        //   textView.setText(progressStatus + "/" + progressBar.getMax());
        // }
        // });
        //try {
        //   Thread.sleep(10);
        //} catch (InterruptedException e) {
        //   e.printStackTrace();
        // }
        //}
        // }
        // }).start();

        totalsuite = findViewById(R.id.totalsuite);
        tvtotal = findViewById(R.id.tvtotal);
        tvVat = findViewById(R.id.tvVat);
        tvroomno = findViewById(R.id.tvroomno);
        tverr = findViewById(R.id.tverr);
        tvdayofstay = findViewById(R.id.tvdayofstay);

        spinlocation = findViewById(R.id.spinlocation);
        spinroom = findViewById(R.id.spinroom);
        etadult = findViewById(R.id.etadult);
        etchild = findViewById(R.id.etchild);
        etroom = findViewById(R.id.etroom);
        simpledatepicker1 = findViewById(R.id.simpledatepicker1);
        simpledatepicker2 = findViewById(R.id.simpledatepicker2);
        btncalc = findViewById(R.id.btncalc);

        ArrayAdapter adapter = new ArrayAdapter<>
                (
                        this,
                        android.R.layout.simple_list_item_1,
                        location
                );
        ArrayAdapter adapter1 = new ArrayAdapter<>
                (
                        this,
                        android.R.layout.simple_list_item_1, room
                );
        spinlocation.setAdapter(adapter);
        spinroom.setAdapter(adapter1);

        simpledatepicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCalendar1();
            }

        });
        simpledatepicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCalendar2();
            }
        });
    }

    private void loadCalendar1() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = "Checked OUT ON :" + month + "/" + dayOfMonth + "/" + year;
                String year1 = String.valueOf(year);
                String month1 = (month + 1) < 10 ? "0" + (month + 1) : String.valueOf(month + 1);
                String _date = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                String _pickedDate = year + "-" + month1 + "-" + _date;
                Log.e("PickedDate: ", "Date: " + _pickedDate); //2019-02-12
                day1=dayOfMonth;
                simpledatepicker1.setText(date);
            }
        }, year, month, day);
        c.get(Calendar.YEAR);
        c.get(Calendar.MONTH);
        c.get(Calendar.MONTH);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


        datePickerDialog.show();
    }


    private void loadCalendar2() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = "Checked IN ON :" + month + "/" + dayOfMonth + "/" + year;
                String year2 = String.valueOf(year);
                String month2 = (month + 1) < 10 ? "0" + (month + 1) : String.valueOf(month + 1);
                String _date = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                String _pickedDate = year + "-" + month1 + "-" + _date;
                Log.e("PickedDate: ", "Date: " + _pickedDate); //2019-02-12
                day2 = dayOfMonth;
                simpledatepicker2.setText(date);
            }

        }, year, month, day);
        c.get(Calendar.YEAR);
        c.get(Calendar.MONTH);
        c.get(Calendar.MONTH);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();


        btncalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(simpledatepicker2.getText())) {
                    tverr.setText("Please enter Checked in Date ");
                    return;
                }

                if (TextUtils.isEmpty(etchild.getText())) {
                    tverr.setText("Please enter number of Kids ");
                    return;
                }
                if (TextUtils.isEmpty(etadult.getText())) {
                    tverr.setText("Please enter number of adults ");
                    return;
                }

                if (TextUtils.isEmpty(simpledatepicker1.getText())) {
                    tverr.setText("Please enter Checked out Date ");
                    return;
                }

                if (TextUtils.isEmpty(etroom.getText())) {
                    tverr.setText("Please enter Number of Rooms ");
                    return;
                }

                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.set(year1, month1, day1);
                cal2.set(year2, month2, day2);
                long millis1 = cal1.getTimeInMillis();
                long millis2 = cal2.getTimeInMillis();
                long diff = millis2 - millis1;
                long diffDays = (diff / (86400000));
                no_of_room = Integer.parseInt(etroom.getText().toString());
                double price;
                double Total_Price;
                double Grand_Total;


                String suite = spinroom.getSelectedItem().toString();

                if (suite == "Deluxe") {

                    price = 2000;
                    Total_Price = price * no_of_room * diffDays;
                    Grand_Total = (0.13) * Total_Price + Total_Price;

                    tvdayofstay.setText("Total Days:" + diffDays);
                    tvroomno.setText(("Number of Room(s):" + no_of_room));
                    totalsuite.setText("Room Price Per Night:" + "2000");
                    tvtotal.setText("Total:" + Total_Price);
                    tvVat.setText("Grand Total:" + Grand_Total);
                    Toast.makeText(MainActivity.this, "Total price is" + Grand_Total, Toast.LENGTH_SHORT).show();


                } else if (suite == "AC") {

                    price = 3000;
                    Total_Price = price * no_of_room * diffDays;
                    Grand_Total = (0.13) * Total_Price + Total_Price;
                    tvdayofstay.setText("Total Days:" + diffDays);
                    tvroomno.setText(("Number of Room(s):" + no_of_room));
                    totalsuite.setText("Room Price Per Night:" + "3000");
                    tvtotal.setText("Total:" + Total_Price);
                    tvVat.setText("Grand Total:" + Grand_Total);
                    Toast.makeText(MainActivity.this, "Total price is" + Grand_Total, Toast.LENGTH_SHORT).show();

                } else if (suite == "Platinum") {
                    price = 4000;
                    Total_Price = price * no_of_room * diffDays;
                    Grand_Total = (0.13) * Total_Price + Total_Price;
                    tvdayofstay.setText("Total Days:" + diffDays);
                    tvroomno.setText(("Number of Room(s):" + no_of_room));
                    totalsuite.setText("Room Price Per Night:" + "4000");
                    tvtotal.setText("Total:" + Total_Price);
                    tvVat.setText("Grand Total:" + Grand_Total);
                    Toast.makeText(MainActivity.this, "Total price is" + Grand_Total, Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

}





