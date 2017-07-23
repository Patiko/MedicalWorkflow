package com.example.lenovo.medicalworkflow.ActivitiesClass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.DisplayMedicine;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.DoctorMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker.NfzWorkerMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist.PharmacistMainScreen;
import com.example.lenovo.medicalworkflow.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 23.07.2017.
 */

public class DisplayRefundList extends DisplayMedicine {
    private ScaleGestureDetector mScaleGestureDetector;

    DisplayMedicine displayMedicine;
    protected void onCreate(Bundle savedInstanceState) {
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
       // setContentView(R.layout.display_refund_list);

        setContentView(R.layout.display_refund_list);
        init();
    }

    public void init() {
        displayMedicine = new DisplayMedicine();
        List<String> nameList = new ArrayList<String>();
        List<String> typeList = new ArrayList<String>();
        List<String> dosageList = new ArrayList<String>();
        List<String> quantityList = new ArrayList<String>();
        nameList= getDependingParameterList(DisplayMedicine.nameListType);
        typeList= getDependingParameterList(DisplayMedicine.typeListType);
        dosageList= getDependingParameterList(DisplayMedicine.dosageListType);
        quantityList= getDependingParameterList(DisplayMedicine.quantityListType);


        TableLayout refundTable = (TableLayout) findViewById(R.id.refundTableTL);
        TableRow headerRow = new TableRow(this);
        TextView tv0 = new TextView(this);

        tv0.setText(getResources().getString(R.string.lpHeader));
        tv0.setTextColor(Color.BLACK);
        tv0.setTypeface(null, Typeface.BOLD_ITALIC);
        tv0.setBackgroundColor(Color.GRAY);

        headerRow.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(getResources().getString(R.string.nameHeader));
        tv1.setTextColor(Color.BLACK);
        tv1.setTypeface(null, Typeface.BOLD_ITALIC);
        tv1.setBackgroundColor(Color.GRAY);
        headerRow.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(getResources().getString(R.string.typeHeader));
        tv2.setTextColor(Color.BLACK);
        tv2.setTypeface(null, Typeface.BOLD_ITALIC);
        tv2.setBackgroundColor(Color.GRAY);
        headerRow.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(getResources().getString(R.string.dosageHeader));
        tv3.setTextColor(Color.BLACK);
        tv3.setBackgroundColor(Color.GRAY);
        tv3.setTypeface(null, Typeface.BOLD_ITALIC);
        headerRow.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText(getResources().getString(R.string.quantityHeader));
        tv4.setTextColor(Color.BLACK);
        tv4.setBackgroundColor(Color.GRAY);
        tv4.setTypeface(null, Typeface.BOLD_ITALIC);
        headerRow.addView(tv4);
        refundTable.addView(headerRow);
        for (int i = 0; i < nameList.size(); i++) {
            TableRow contentRows = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(""+i);
            t1v.setTextColor(Color.BLACK);
            t1v.setBackgroundColor(Color.GRAY);

          //  t1v.setGravity(Gravity.CENTER);
            contentRows.addView(t1v);
            TextView t2v = new TextView(this);
            if(nameList.get(i).length()<=36){
                t2v.setText(" "+nameList.get(i));
            }else {
                t2v.setText(" "+nameList.get(i).substring(0,35));
            }

            t2v.setTextColor(Color.BLACK);
           // t2v.setGravity(Gravity.CENTER);
            contentRows.addView(t2v);
            TextView t3v = new TextView(this);
            if(typeList.get(i).length()<=36){
                t3v.setText(typeList.get(i));
            }else {
                t3v.setText(typeList.get(i).substring(0,35));
            }

            t3v.setTextColor(Color.BLACK);
          //  t3v.setGravity(Gravity.CENTER);
            contentRows.addView(t3v);
            TextView t4v = new TextView(this);
            if(dosageList.get(i).length()<=31){
                t4v.setText(dosageList.get(i));
            }else {
                t4v.setText(dosageList.get(i).substring(0,30));
            }

            t4v.setTextColor(Color.BLACK);
         //   t4v.setGravity(Gravity.CENTER);
            contentRows.addView(t4v);
            TextView t5v = new TextView(this);
            if(quantityList.get(i).length()<=36){
                t5v.setText(quantityList.get(i));
            }else {
                t5v.setText(quantityList.get(i).substring(0,35));
            }
            t5v.setTextColor(Color.BLACK);
         //   t5v.setGravity(Gravity.CENTER);
            contentRows.addView(t5v);
            refundTable.addView(contentRows);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void imageClick(View view){

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Wylogowano...",
                Toast.LENGTH_LONG).show();
    }

    public void homeClick(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String loggedProfile = sharedPreferences.getString(LoginActivity.LoggedProfileId,"");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(LoginActivity.UsedSubmissionId);
        editor.apply();
        switch (loggedProfile){
            case LoginActivity.doctor:
                Intent intent = new Intent(getApplicationContext(), DoctorMainScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case LoginActivity.patient:
                intent = new Intent(getApplicationContext(), PatientMainScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case LoginActivity.pharmacist:
                intent = new Intent(getApplicationContext(), PharmacistMainScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case LoginActivity.nfzWorker:
                intent = new Intent(getApplicationContext(), NfzWorkerMainScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
    public void onResume() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();
    }

    @Override
    public void onStart() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onStart();
    }
}
