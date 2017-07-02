package com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientUserDetails;
import com.example.lenovo.medicalworkflow.Database.AndroidDatabaseManager;
import com.example.lenovo.medicalworkflow.LoginActivity;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 24.05.2017.
 */

public class NfzWorkerMainScreen extends Activity {


    Button b1, b2,b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfz_worker_main_screen);

        b1=(Button)findViewById(R.id.go_to_nfz_worker_detailsB);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NfzWorkerMainScreen.this, NfzWorkerUserDetails.class);
                //      i.putExtra("userIdKey",userIdFromLogin+1);
                startActivity(i);
            }
        });

        TextView tv =(TextView)findViewById(R.id.mainNFZWorkerPanelTV);

        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(NfzWorkerMainScreen.this,AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });


   /*     b2=(Button)findViewById(R.id.checkMedicineListB);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientMainScreen.this, CheckMedicineList.class);
                startActivity(i);
            }
        });*/

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
