package com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.CheckSubmissionListByPatient;
import com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker.NfzWorkerMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist.PharmacistMainScreen;
import com.example.lenovo.medicalworkflow.CustomAdapters.CustomAdapterPatients;
import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.ActivitiesClass.LoginActivity;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 30.06.2017.
 */

public class SearchPatientForSubmissionList extends AppCompatActivity {

    private CustomAdapterPatients customAdapterPatients;
    ListView listView;
    Cursor cursor;

    Button b1,b2,b3;
    EditText et1;
    String searchValue="";
    SharedPreferences sharedpreferences;

    private ListView obj;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_patient);
        mydb = new DBHelper(this);

        cursor = mydb.getAllPatients();
        customAdapterPatients = new CustomAdapterPatients(SearchPatientForSubmissionList.this, cursor, 0);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(customAdapterPatients);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int id_To_Search = cursor.getInt(cursor.getColumnIndex(DBHelper.USER_COLUMN_ID));

                if(id_To_Search!=0){

                    sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    //    int Value = sharedpreferences.getInt(LoginActivity.UserId,0);
                    editor.putInt(LoginActivity.UsedUserId,id_To_Search);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), CheckSubmissionListByPatient.class);
                    startActivity(intent);
                }

            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String pesel) {
                    //  Log.d(TAG, "onQueryTextSubmit ");
                    cursor=mydb.searchPatients(pesel);
                    if (cursor==null){
                        Toast.makeText(SearchPatientForSubmissionList.this,"Nie znaleziono!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(SearchPatientForSubmissionList.this, "Znaleziono "+cursor.getCount() + " rekordy!",Toast.LENGTH_LONG).show();
                    }
                    customAdapterPatients.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String pesel) {
                    //   Log.d(TAG, "onQueryTextChange ");
                    cursor=mydb.searchPatients(pesel);
                    if (cursor!=null){
                        customAdapterPatients.swapCursor(cursor);
                    }
                    return false;
                }

            });

        }

        return true;

    }


    /*    public ArrayList<String> searchPatientsFront(){
        ArrayList<String> patients_list = mydb.searchPatients(searchValue);
        return ;
    }*/
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
