package com.example.lenovo.medicalworkflow.ActivitiesClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.CheckMedicineList;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.DoctorMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker.NfzWorkerMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist.PharmacistMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Registration.RegisterUser;
import com.example.lenovo.medicalworkflow.CustomAdapterMedicines;
import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.LoginActivity;
import com.example.lenovo.medicalworkflow.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lenovo on 26.06.2017.
 */

public class AddSubmission extends Activity {
    DBHelper mydb;
    SharedPreferences sharedPreferences;
    TextView docName;
    String docType;
    Spinner spinner_docType;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_submission);
        mydb = new DBHelper(this);
        docName = (TextView) findViewById(R.id.docNameET);
        spinner_docType = (Spinner) findViewById(R.id.docTypeSpin);

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES,Context.MODE_PRIVATE);
        // int ValueUserId = sharedPreferences.getInt(LoginActivity.UsedMedicineId,0);
        int ValuePatientId = sharedPreferences.getInt(LoginActivity.UsedUserId,0);              //////////DO INSERTU
        int ValueDoctorId = sharedPreferences.getInt(LoginActivity.UserId,0);                   //////////DO INSERTU
        int ValueMedicineId = sharedPreferences.getInt(LoginActivity.UsedMedicineId,0);        //////////DO INSERTU
        String ValueUserPeselId = sharedPreferences.getString(LoginActivity.PeselId,"");

        ArrayAdapter<CharSequence> adapter_profil = ArrayAdapter.createFromResource(this.getApplicationContext(),
                R.array.submission_type_array,android.R.layout.simple_spinner_item);

        spinner_docType.setAdapter(adapter_profil);
        spinner_docType.setOnItemSelectedListener(new AddSubmission.Listener_Of_Selecting_Doc_Type_Spinner());


    }

    public static class Listener_Of_Selecting_Doc_Type_Spinner implements AdapterView.OnItemSelectedListener
    {
        static String subType;
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)
        {
            subType = (parent.getItemAtPosition(pos)).toString();
        }
        public void onNothingSelected(AdapterView<?> parent)
        {
            // Do nothing.
        }
    };





    public void insertSubmission(View view){
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        int ValuePatientId = sharedPreferences.getInt(LoginActivity.UsedUserId,0);              //////////DO INSERTU
        int ValueDoctorId = sharedPreferences.getInt(LoginActivity.UserId,0);                   //////////DO INSERTU
        int ValueMedicineId = sharedPreferences.getInt(LoginActivity.UsedMedicineId,0);
        ArrayList<Integer> medIdList = new ArrayList<Integer>();

        int ValueSubmissionId;

        docType =Listener_Of_Selecting_Doc_Type_Spinner.subType;
        if(!docName.getText().toString().equals("")){
            if (mydb.insertSubmission(ValuePatientId,ValueDoctorId,docName.getText().toString(),docType)) {
                medIdList = mydb.getAllMedicinesByUser(ValueDoctorId);
                ValueSubmissionId=mydb.getSubmissionIdByValues(ValuePatientId,docName.getText().toString(),docType);


                for(int i=0; i<medIdList.size(); i++){
                    mydb.updateMedicineBySubmission(medIdList.get(i),ValueSubmissionId);
                }

                Toast.makeText(getApplicationContext(), "Wniosek został dodany",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wniosek niedodany",
                        Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(getApplicationContext(), CheckSubmissionList.class);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(), "Nazwa wniosku nie może być pusta!",
                    Toast.LENGTH_LONG).show();
        }


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
