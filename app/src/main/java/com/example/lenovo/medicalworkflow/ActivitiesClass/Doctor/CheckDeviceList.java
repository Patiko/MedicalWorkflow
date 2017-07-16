package com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.AddSubmission;
import com.example.lenovo.medicalworkflow.ActivitiesClass.LoginActivity;
import com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker.NfzWorkerMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist.PharmacistMainScreen;
import com.example.lenovo.medicalworkflow.CustomAdapters.CustomAdapterDevices;
import com.example.lenovo.medicalworkflow.CustomAdapters.CustomAdapterMedicines;
import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 15.07.2017.
 */

public class CheckDeviceList extends Activity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;
    ImageView img;

    private CustomAdapterDevices customAdapterDevices;
    Cursor cursor;
    ListView listView;
    SharedPreferences sharedPreferences;

    Button b1,b2;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_device_list);
        b1=(Button)findViewById(R.id.addDeviceB);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //    int Value = sharedpreferences.getInt(LoginActivity.UserId,0);
                editor.remove(LoginActivity.UsedMedicineId);
                editor.apply();
                Intent i = new Intent(CheckDeviceList.this, DisplayDevice.class);
                startActivity(i);
            }
        });

        b2=(Button)findViewById(R.id.addSubmissionB);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CheckDeviceList.this, AddSubmission.class);
                startActivity(i);
            }
        });


        mydb = new DBHelper(this);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        Integer creator_id = sharedPreferences.getInt(LoginActivity.UserId,0);
        cursor = mydb.getAllDevicesByCreator(creator_id);
        customAdapterDevices = new CustomAdapterDevices(CheckDeviceList.this, cursor, 0);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(customAdapterDevices);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

                int id_To_Search = cursor.getInt(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_ID));
                //          boolean visibility=true;
                if(id_To_Search!=0){
                    //     Bundle dataBundle = new Bundle();
                    //   dataBundle.putInt("id", id_To_Search);
                    //  dataBundle.putBoolean("doctor_visibility", visibility);
                    sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //    int Value = sharedpreferences.getInt(LoginActivity.UserId,0);
                    editor.putInt(LoginActivity.UsedMedicineId,id_To_Search);
                    editor.apply();

               /* Button editB = (Button)findViewById(R.id.button2);
                editB.setVisibility(View.INVISIBLE);
                Button deleteB = (Button)findViewById(R.id.button3);
                deleteB.setVisibility(View.INVISIBLE);*/
                    Intent intent = new Intent(getApplicationContext(), DisplayDevice.class);
                    //        intent.putExtras(dataBundle);
                    startActivity(intent);
                    // arrayAdapter.notifyDataSetChanged();
                }
            }
        });
       // for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            if (listView.getAdapter().getCount()>0) {

                b1.setVisibility(View.GONE);
            }
      //  }

      /*  if(listView!=null){

        }*/



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

}
