package com.example.lenovo.medicalworkflow.ActivitiesClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.DoctorMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker.NfzWorkerMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist.PharmacistMainScreen;
import com.example.lenovo.medicalworkflow.CustomAdapters.CustomAdapterSubmissions;
import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.R;

import java.util.ArrayList;

/**
 * Created by Lenovo on 15.06.2017.
 */



public class CheckSubmissionList extends Activity implements AdapterView.OnItemSelectedListener {

    private DBHelper mydb;
    ListView listView, listView2;
    Spinner spinnerDocType;
    ArrayList array_list;
    Cursor cursor;
    SharedPreferences sharedPreferences;
    CustomAdapterSubmissions customAdapterSubmissions;

    TextView patientTV, userFirstName, userLastName;
    TextView statusTV;
    Button editStatusPopUp;
    Button openStatusPopUp;
    Button acceptSubmission, rejectSubmission;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_submission_list);

        patientTV = (TextView) findViewById(R.id.patientTV);
        userFirstName = (TextView) findViewById(R.id.firstNameTV);
        userLastName = (TextView) findViewById(R.id.lastNameTV);
        patientTV.setVisibility(View.GONE);
        userFirstName.setVisibility(View.GONE);
        userLastName.setVisibility(View.GONE);
        statusTV =(TextView) findViewById(R.id.txtStatus);
      //  editStatusPopUp = (Button)findViewById(R.id.editSubmissionStatus);






        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES,Context.MODE_PRIVATE);
        //int ValueUserId = sharedPreferences.getInt(LoginActivity.UsedMedicineId,0);
        int ValuePatientId = sharedPreferences.getInt(LoginActivity.UsedUserId,0);
        int ValueDoctorId = sharedPreferences.getInt(LoginActivity.UserId,0);

        spinnerDocType = (Spinner) findViewById(R.id.submissionTypeSpinner);
        mydb = new DBHelper(this);
        ArrayList array_list,array_list2;




        ArrayAdapter<CharSequence> adapter_doc_type = ArrayAdapter.createFromResource(this.getApplicationContext(),
            R.array.submission_type_array, android.R.layout.simple_spinner_item);
    //adapter_profil.setDropDownViewResourc(android.R.layout.simple_spinner_dropdown_item);
        spinnerDocType.setAdapter(adapter_doc_type);
      //  spinnerDocType.setSelection(adapter_doc_type.getPosition(submissionTypeDefault));
    //    spinnerDocType.setSelection(getIndex(spinnerDocType,submissionTypeDefault));
        spinnerDocType.setOnItemSelectedListener(this);

        String docType = (String) spinnerDocType.getSelectedItem().toString();

        cursor = (Cursor) mydb.getAllSubmissionByType(docType);
        customAdapterSubmissions = new CustomAdapterSubmissions(CheckSubmissionList.this, cursor, 0);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(customAdapterSubmissions);
        customAdapterSubmissions.notifyDataSetChanged();


  /*      array_list = mydb.getAllSubmissionByType(docType);
        //  array_list = mydb.getAllSubmissionByType(submissionType);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

                int id_To_Search = cursor.getInt(cursor.getColumnIndex(DBHelper.SUBMISSION_COLUMN_ID));
                //          boolean visibility=true;
                if(id_To_Search!=0){
                    //     Bundle dataBundle = new Bundle();
                    //   dataBundle.putInt("id", id_To_Search);
                    //  dataBundle.putBoolean("doctor_visibility", visibility);
                    sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //    int Value = sharedpreferences.getInt(LoginActivity.UserId,0);
                    editor.putInt(LoginActivity.UsedSubmissionId,id_To_Search);
                    editor.apply();

               /* Button editB = (Button)findViewById(R.id.button2);
                editB.setVisibility(View.INVISIBLE);
                Button deleteB = (Button)findViewById(R.id.button3);
                deleteB.setVisibility(View.INVISIBLE);*/
                    Intent intent = new Intent(getApplicationContext(), DisplaySubmission.class);
                    //        intent.putExtras(dataBundle);
                    startActivity(intent);
                    // arrayAdapter.notifyDataSetChanged();
                }



            }
        });


/*

        openStatusPopUp = (Button) findViewById(R.id.editSubmissionStatus);
        openStatusPopUp.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View a){
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popUpView = layoutInflater.inflate(R.layout.edit_status_popup,null);
                final PopupWindow popupWindow = new PopupWindow(
                        popUpView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                acceptSubmission = (Button)popUpView.findViewById(R.id.acceptButton);
                acceptSubmission.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        popupWindow.dismiss();
                    }});
                rejectSubmission = (Button)popUpView.findViewById(R.id.rejectButton);
                rejectSubmission.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        popupWindow.dismiss();
                    }});

                popupWindow.showAsDropDown(openStatusPopUp, 50, -30);
            }
        });
*/


    }

    public void updateStatus(){

    }


    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        spinnerDocType.setSelection(position);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES,Context.MODE_PRIVATE);
        int ValueDoctorId = sharedPreferences.getInt(LoginActivity.UserId,0);
        int ValuePatientId = sharedPreferences.getInt(LoginActivity.UsedUserId,0);
        String docType = (String) spinnerDocType.getSelectedItem().toString();

        cursor = (Cursor) mydb.getAllSubmissionByType(docType);
        customAdapterSubmissions = new CustomAdapterSubmissions(CheckSubmissionList.this, cursor, 0);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(customAdapterSubmissions);
        customAdapterSubmissions.notifyDataSetChanged();


      /*  array_list = mydb.getAllSubmissionByType(docType);
        //  array_list = mydb.getAllSubmissionByType(submissionType);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
