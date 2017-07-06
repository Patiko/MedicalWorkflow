package com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.DoctorMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker.NfzWorkerMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientMainScreen;
import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.ActivitiesClass.LoginActivity;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 24.05.2017.
 */

public class PharmacistUserDetails extends Activity {


    Button b1, b2,b3;
    EditText e1,e2;

    private DBHelper mydb ;

    Integer id_To_Update=0;
    TextView first_name;
    TextView last_name;
    TextView pesel;
    TextView login;
    TextView password;
    Spinner spinner_profil;
    TextView profil;
    TextView insured;

    TextView birthdate;
    TextView phoneNumber;
    TextView street;
    TextView nr_dom;
    TextView nr_mies;
    TextView post_code;
    TextView city;
    TextView workDataTitle;
    TextView addressTitle;

    TextView npwz;
    TextView specialisations;
    TextView work_name;
    TextView registry_entry;
    TextView regon;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pharmacist_user_details);
        first_name=(TextView)findViewById(R.id.firstNameET);
        last_name=(TextView)findViewById(R.id.lastNameET);
        pesel=(TextView)findViewById(R.id.peselET);
        login=(TextView)findViewById(R.id.loginET);
        password=(TextView)findViewById(R.id.passwordET);
        profil=(TextView)findViewById(R.id.profilContentTV);
        insured = (TextView)findViewById(R.id.insuredET);

        birthdate=(TextView)findViewById(R.id.birthdateET);
        phoneNumber=(TextView)findViewById(R.id.phoneNumberET);
        street=(TextView)findViewById(R.id.streetET);
        nr_dom=(TextView)findViewById(R.id.nrDomET);
        nr_mies=(TextView)findViewById(R.id.nrMiesET);
        post_code=(TextView)findViewById(R.id.postCodeET);
        city=(TextView)findViewById(R.id.cityET);

        addressTitle=(TextView)findViewById(R.id.addressTV);
        addressTitle.setTypeface(null, Typeface.BOLD);
        workDataTitle=(TextView)findViewById(R.id.workDataTV);
        workDataTitle.setTypeface(null, Typeface.BOLD);

        npwz=(TextView)findViewById(R.id.npwzET);
        specialisations=(TextView)findViewById(R.id.specialisationsET);
        work_name=(TextView)findViewById(R.id.workNameET);
        registry_entry=(TextView)findViewById(R.id.registryEntryET);
        regon=(TextView)findViewById(R.id.regonET);


        mydb = new DBHelper(this);
        sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        // Bundle extras = getIntent().getExtras();
    /*    if(extras !=null) {
            int Value = extras.getInt("userIdKey");*/
        int Value = sharedpreferences.getInt(LoginActivity.UserId,0);
       // Toast.makeText(getApplicationContext(), "USER ID " + Value, Toast.LENGTH_LONG).show();
        if (Value > 0) {
            //means this is the view part not the add contact part.
/*                if(mydb.getMedicineData(Value).equals(null)||mydb.getMedicineData(Value).equals("")){
                    Value=Value+1;

                }*/
            Cursor rs = mydb.getUserData(Value);
            if (rs != null && rs.moveToFirst()) {
                id_To_Update = Value;
                //rs.moveToFirst();
                //  if(!rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME)).equals("-1")) {

                String first_nam = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_FIRST_NAME));
                String last_nam = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_LAST_NAME));
                String pese = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_PESEL));
                String logi = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_LOGIN));
                String pass = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_PASSWORD));
                String prof = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_PROFILE));
                String insur = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_INSURED));

                String birthdat = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_BIRTHDATE));
                String phoneNum = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_PHONENUMBER));
                String stree = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_STREET));
                String nr_do = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_NRDOM));
                String nr_mie = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_NRMIES));
                String post_cod = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_POST_CODE));
                String cit = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_CITY));

                String npw = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_NPWZ));
                String specialisatio = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_SPECIALISATIONS));
                String work_nam = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_WORK_NAME));
                String registry_ent = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_REGISTRY_ENTRY));
                String rego = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_REGON));


                if (!rs.isClosed()) {
                    rs.close();
                }

                first_name.setText((CharSequence) first_nam);
                  /*  first_name.setFocusable(false);
                    first_name.setClickable(false);*/

                last_name.setText((CharSequence) last_nam);
                 /*   last_name.setFocusable(false);
                    last_name.setClickable(false);*/

                pesel.setText((CharSequence) pese);
                pesel.setFocusable(false);
                pesel.setClickable(false);

                login.setText((CharSequence) logi);
                login.setFocusable(false);
                login.setClickable(false);

                password.setText((CharSequence) pass);
                password.setFocusable(false);
                password.setClickable(false);

                profil.setText((CharSequence)prof);
                    /*profil.setFocusable(false);
                    profil.setClickable(false);*/

                insured.setText((CharSequence) insur);

                birthdate.setText((CharSequence)birthdat);
                phoneNumber.setText((CharSequence)phoneNum);
                street.setText((CharSequence)stree);
                nr_dom.setText((CharSequence)nr_do);
                nr_mies.setText((CharSequence)nr_mie);
                post_code.setText((CharSequence)post_cod);
                city.setText((CharSequence)cit);

                npwz.setText((CharSequence)npw);
                specialisations.setText((CharSequence)specialisatio);
                work_name.setText((CharSequence)work_nam);
                registry_entry.setText((CharSequence)registry_ent);
                regon.setText((CharSequence)rego);

/*                    b1 = (Button) findViewById(R.id.button1);
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PatientUserDetails.this, PatientMainScreen.class);
                            startActivity(i);
                        }
                    });*/
            }
        }

    }

    public void runUpdateUser(View view) {
        /*Bundle extras = getIntent().getExtras();
        if(extras !=null) {*/
        // int userId = extras.getInt("userIdKey");
        if(mydb.updateUser(id_To_Update,first_name.getText().toString(),last_name.getText().toString(),pesel.getText().toString(),login.getText().toString(),
                password.getText().toString(), profil.getText().toString(),insured.getText().toString(),
                birthdate.getText().toString(),phoneNumber.getText().toString(), street.getText().toString(),
                nr_dom.getText().toString(), nr_mies.getText().toString(), post_code.getText().toString(), city.getText().toString(),
                npwz.getText().toString(), specialisations.getText().toString(), work_name.getText().toString(), registry_entry.getText().toString(), regon.getText().toString(),"")){
            Toast.makeText(getApplicationContext(), "Użytkownik został edytowany", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),PharmacistMainScreen.class);
            // intent.putExtra("useIdKey",userId);
            startActivity(intent);

        } else{
            Toast.makeText(getApplicationContext(), "Użytkownik nie zmieniony", Toast.LENGTH_LONG).show();
        }
     /*       Button b=(Button)findViewById(R.id.button1);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/
        // }
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
