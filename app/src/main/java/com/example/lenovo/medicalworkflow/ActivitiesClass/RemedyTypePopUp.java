package com.example.lenovo.medicalworkflow.ActivitiesClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.CheckDeviceList;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.CheckMedicineList;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.DoctorUserDetails;
import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 14.07.2017.
 */

public class RemedyTypePopUp extends Activity {
    Button chooseMedicineBtn, chooseDeviceBtn;
    SharedPreferences sharedPreferences;
    private DBHelper mydb;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remedy_type_popup);
        chooseMedicineBtn= (Button) findViewById(R.id.chooseMedicine);
        chooseMedicineBtn= (Button) findViewById(R.id.chooseDevice);
        mydb = new DBHelper(this);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width =dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*0.7),(int)(height*0.4));

    }

    public void chooseMedicine(View view){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LoginActivity.UsedRemedyTypeId,LoginActivity.remedyTypeMedicine);
        editor.apply();

        Intent i = new Intent(RemedyTypePopUp.this, CheckMedicineList.class);
        startActivity(i);

        finish();
    }

    public void chooseDevice(View view){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LoginActivity.UsedRemedyTypeId,LoginActivity.remedyTypeDevice);
        editor.apply();

        Intent i = new Intent(RemedyTypePopUp.this, CheckDeviceList.class);
        startActivity(i);

        finish();
    }

}
