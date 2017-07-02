package com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.CheckSubmissionList;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.SearchPatient;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.SearchPatientForSubmissionList;
import com.example.lenovo.medicalworkflow.Database.AndroidDatabaseManager;
import com.example.lenovo.medicalworkflow.ActivitiesClass.LoginActivity;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 24.05.2017.
 */

public class PharmacistMainScreen extends Activity {

    Button b1, b2,b3,b5,b6,b7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pharmacist_main_screen);

        b1=(Button)findViewById(R.id.go_to_pharmacist_detailsB);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PharmacistMainScreen.this, PharmacistUserDetails.class);
                //      i.putExtra("userIdKey",userIdFromLogin+1);
                startActivity(i);
            }
        });



        b5=(Button)findViewById(R.id.createSubmissionB);
        b5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(PharmacistMainScreen.this, SearchPatient.class);
                startActivity(i);
            }
        });



        b6=(Button)findViewById(R.id.checkSubmissionListB);
        b6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(PharmacistMainScreen.this, CheckSubmissionList.class);
                startActivity(i);
            }
        });

        b7=(Button)findViewById(R.id.checkSubmissionListByPatientB);
        b7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(PharmacistMainScreen.this, SearchPatientForSubmissionList.class);
                startActivity(i);
            }
        });




        TextView tv1 =(TextView)findViewById(R.id.mainPharmacistPanelTV);

        tv1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(PharmacistMainScreen.this,AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });

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
        //finish();
    }
}
