package com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor;

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
import com.example.lenovo.medicalworkflow.ActivitiesClass.CheckSubmissionListByPatient;
import com.example.lenovo.medicalworkflow.Database.AndroidDatabaseManager;
import com.example.lenovo.medicalworkflow.LoginActivity;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 07.05.2017.
 */

public class DoctorMainScreen extends Activity {

    Button b1,b2,b3,b4,b5,b6,b7;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_main_screen);

        b1=(Button)findViewById(R.id.addMedicineB);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorMainScreen.this, DisplayMedicine.class);
                startActivity(i);
            }
        });

        b2=(Button)findViewById(R.id.checkMedicineListB);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorMainScreen.this, CheckMedicineList.class);
                startActivity(i);
            }
        });

        b3=(Button)findViewById(R.id.userSettingsDocB);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorMainScreen.this, DoctorUserDetails.class);
                startActivity(i);
            }
        });

        b4=(Button)findViewById(R.id.searchPatientB);
        b4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(DoctorMainScreen.this, SearchPatient.class);
                startActivity(i);
            }
        });

/*        if(){
            b4=(Button)findViewById(R.id.searchPatientB);
            b4.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent i = new Intent(DoctorMainScreen.this, SearchPatient.class);
                    startActivity(i);
                }
            });
        }else{
            b4=(Button)findViewById(R.id.B);
            b4.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent i = new Intent(DoctorMainScreen.this, SearchPatient.class);
                    startActivity(i);
                }
            });
        }*/

        b5=(Button)findViewById(R.id.createSubmissionB);
        b5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(DoctorMainScreen.this, SearchPatient.class);
                startActivity(i);
            }
        });



        b6=(Button)findViewById(R.id.checkSubmissionListB);
        b6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(DoctorMainScreen.this, CheckSubmissionList.class);
                startActivity(i);
            }
        });

        b7=(Button)findViewById(R.id.checkSubmissionListByPatientB);
        b7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(DoctorMainScreen.this, SearchPatientForSubmissionList.class);
                startActivity(i);
            }
        });


        TextView tv =(TextView)findViewById(R.id.textView);

        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(DoctorMainScreen.this,AndroidDatabaseManager.class);
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

    }

}
