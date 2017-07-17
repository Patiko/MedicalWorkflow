package com.example.lenovo.medicalworkflow.ActivitiesClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.DoctorMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.DoctorUserDetails;
import com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker.NfzWorkerMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker.NfzWorkerUserDetails;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientUserDetails;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist.PharmacistMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist.PharmacistUserDetails;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Registration.RegisterUser;
import com.example.lenovo.medicalworkflow.Database.AndroidDatabaseManager;
import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.R;

public class LoginActivity extends Activity {

    Button b1,b2;
    EditText ed1,ed2;


    TextView tx1,tx2,registerLink;
    int counter = 3;
    private DBHelper mydb ;

    public static final String doctor ="Lekarz";
    public static final String patient ="Pacjent";
    public static final String nfzWorker ="Pracownik NFZ";
    public static final String pharmacist ="Farmaceuta";

    public static final String nowaStatus="NOWA";
    public static final String doctorCheckStatus="SPRAWDZANA PRZEZ LEKARZA";
    public static final String doctorAcceptedStatus="ZAAKCEPTOWANA PRZEZ LEKARZA";
    public static final String doctorRejectedStatus="ODRZUCONA PRZEZ LEKARZA";
    public static final String pharmacistCheckStatus="SPRAWDZANA PRZEZ FARMACEUTE";
    public static final String pharmacistAcceptedStatus="ZAAKCEPTOWANA PRZEZ FARMACEUTE";
    public static final String pharmacistRejectedStatus="ODRZUCONA PRZEZ FARMACEUTE";
    public static final String successStatus="WYDANO";
    public static final String expiredStatus="WYGAŚNIĘTA";


    public static final String MyPREFERENCES = "MyPrefLoggedUser" ;
    public static final String UserId = "idKey";
    public static final String LoginId = "loginKey";
    public static final String PeselId = "peselKey";
    public static final String UsedUserId = "usedUserKey";
    public static final String LoggedProfileId = "loggedProfileKey";
    public static final String UsedMedicineId = "usedMedicineKey";
    public static final String UsedSubmissionId = "usedSubmissionKey";
    public static final String UsedRemedyTypeId = "usedRemedyTypeKey";

    public static final String remedyTypeMedicine="Lek";
    public static final String remedyTypeDevice="Wyrob medyczny";

    public static final String submissionTypeZlecenie="Zlecenie na wyroby medyczne";
    public static final String submissionTypeRStand="Recepta standardowa";
    public static final String submissionTypeRFarm="Recepta farmaceutyczna";



    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mydb = new DBHelper(this);

        b1 = (Button)findViewById(R.id.button1);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);

        b2 = (Button)findViewById(R.id.button2);
        tx1 = (TextView)findViewById(R.id.textView3);
        tx1.setVisibility(View.GONE);
        tx2 = (TextView)findViewById(R.id.textView2);
        tx2.setVisibility(View.GONE);

        registerLink = (TextView)findViewById(R.id.registerLink);
        registerLink.setTypeface(null, Typeface.BOLD_ITALIC);
        registerLink.setPaintFlags(registerLink.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);


        TextView tv =(TextView)findViewById(R.id.appTitle);

        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(LoginActivity.this,AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });

        insertDummyData();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(ed1.getText().toString().equals("admin") &&
                        ed2.getText().toString().equals("admin"))*/

                String login = ed1.getText().toString();
                String password =ed2.getText().toString();
                String storedPassword=mydb.getSinglePasswordUsingLogin(login);


                if(!login.equals("")&& !password.equals("")){
                    if(password.equals(storedPassword)){
                        int userId = mydb.getUserId(login);
                        String peselId= mydb.getPeselId(login);
                        String profileId = mydb.getProfileId(login);
                        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                        Integer firstUsage=0;
                        Boolean areYouLekarz=mydb.getSpecificProfile(login,doctor);
                        Boolean areYouPatient=mydb.getSpecificProfile(login,patient);
                        Boolean areYouNFZWorker=mydb.getSpecificProfile(login,nfzWorker);
                        Boolean areYouPharmacist=mydb.getSpecificProfile(login,pharmacist);
                        Integer loginCount = mydb.getUserLoginCount(login);

                        if(areYouLekarz){
                            Toast.makeText(getApplicationContext(),
                                    "Zalogowano jako lekarz...",Toast.LENGTH_LONG).show();
                         //   Intent i = new Intent(LoginActivity.this, DoctorMainScreen.class);

                            if(loginCount==0){
                                loginCount++;
                                mydb.updateUserLoginCount(login,loginCount);
                                Intent i = new Intent(LoginActivity.this, DoctorUserDetails.class);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.putInt(UserId,userId);
                                editor.putString(LoginId,login);
                                editor.putString(PeselId,peselId);
                                editor.putString(LoggedProfileId, profileId);
                                editor.apply();
                                startActivity(i);
                                Toast.makeText(getApplicationContext(),
                                        "Pierwsze logowanie - Uzupełnij dane personalne.",Toast.LENGTH_LONG).show();
                            }else {
                                Intent i = new Intent(LoginActivity.this, DoctorMainScreen.class);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.putInt(UserId,userId);
                                editor.putString(LoginId,login);
                                editor.putString(PeselId,peselId);
                                editor.putString(LoggedProfileId, profileId);
                                editor.apply();
                                startActivity(i);
                            }

                        }else if(areYouPatient){
                            Toast.makeText(getApplicationContext(),
                                    "Zalogowano jako pacjent...",Toast.LENGTH_LONG).show();
                            if(loginCount==0) {
                                loginCount++;
                                mydb.updateUserLoginCount(login, loginCount);
                                Intent i = new Intent(LoginActivity.this, PatientUserDetails.class);
                                // i.putExtra("userIdKey", userId);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.putInt(UserId,userId);
                                editor.putString(LoginId,login);
                                editor.putString(PeselId,peselId);
                                editor.putString(LoggedProfileId, profileId);
                                editor.apply();
                                startActivity(i);
                                Toast.makeText(getApplicationContext(),
                                        "Pierwsze logowanie - Uzupełnij dane personalne.",Toast.LENGTH_LONG).show();
                            }else {
                                Intent i = new Intent(LoginActivity.this, PatientMainScreen.class);
                                // i.putExtra("userIdKey", userId);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.putInt(UserId,userId);
                                editor.putString(LoginId,login);
                                editor.putString(PeselId,peselId);
                                editor.putString(LoggedProfileId, profileId);
                                editor.apply();
                                startActivity(i);
                            }


                        } else if(areYouNFZWorker){
                            Toast.makeText(getApplicationContext(),
                                    "Zalogowano jako pracownik NFZ...",Toast.LENGTH_LONG).show();
                            if(loginCount==0) {
                                loginCount++;
                                mydb.updateUserLoginCount(login, loginCount);
                                Intent i = new Intent(LoginActivity.this, NfzWorkerUserDetails.class);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.putInt(UserId,userId);
                                editor.putString(LoginId,login);
                                editor.putString(PeselId,peselId);
                                editor.putString(LoggedProfileId, profileId);
                                editor.apply();
                                startActivity(i);
                                Toast.makeText(getApplicationContext(),
                                        "Pierwsze logowanie - Uzupełnij dane personalne.",Toast.LENGTH_LONG).show();
                            }else{
                                Intent i = new Intent(LoginActivity.this, NfzWorkerMainScreen.class);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.putInt(UserId,userId);
                                editor.putString(LoginId,login);
                                editor.putString(PeselId,peselId);
                                editor.putString(LoggedProfileId, profileId);
                                editor.apply();
                                startActivity(i);

                            }
                         //   Intent i = new Intent(LoginActivity.this, NfzWorkerMainScreen.class);


                        } else if(areYouPharmacist){
                            Toast.makeText(getApplicationContext(),
                                    "Zalogowano jako farmaceuta...",Toast.LENGTH_LONG).show();
                            if(loginCount==0) {
                                loginCount++;
                                mydb.updateUserLoginCount(login, loginCount);
                         //   Intent i = new Intent(LoginActivity.this, PharmacistMainScreen.class);
                            Intent i = new Intent(LoginActivity.this, PharmacistUserDetails.class);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.clear();
                            editor.putInt(UserId,userId);
                            editor.putString(LoginId,login);
                            editor.putString(PeselId,peselId);
                            editor.putString(LoggedProfileId, profileId);
                            editor.apply();
                            startActivity(i);
                                Toast.makeText(getApplicationContext(),
                                        "Pierwsze logowanie - Uzupełnij dane personalne.",Toast.LENGTH_LONG).show();
                            }else{
                                Intent i = new Intent(LoginActivity.this, PharmacistMainScreen.class);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.putInt(UserId,userId);
                                editor.putString(LoginId,login);
                                editor.putString(PeselId,peselId);
                                editor.putString(LoggedProfileId, profileId);
                                editor.apply();
                                startActivity(i);
                            }
                        }


                        ed1.setText("");
                        ed2.setText("");


                    }else{
                        Toast.makeText(getApplicationContext(), "Niepoprawny login lub hasło",Toast.LENGTH_LONG).show();
                        ed1.setText("");
                        ed2.setText("");
                        tx1.setVisibility(View.VISIBLE);
                        tx2.setVisibility(View.VISIBLE);
                        tx1.setTextColor(Color.RED);
                        tx2.setTextColor(Color.RED);
                        //ffff1135
                        counter--;
                        tx1.setText(Integer.toString(counter));
                        if (counter == 0) {
                            b1.setEnabled(false);
                        }
                    }
                }else
                {
                    Toast.makeText(getApplicationContext(), "Proszę podać login i hasło",Toast.LENGTH_LONG).show();
                }

            }
        });

        registerLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(LoginActivity.this, RegisterUser.class);
                startActivity(i);
            }
        });



/*        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/


    }

    public void insertDummyData(){
        if(mydb.isUserTableEmpty()){
            mydb.insertDummyPacjentUser();
            mydb.insertDummyLekarzUser();
            mydb.insertDummyPracownikNFZUser();
            mydb.insertDummyFarmaceutaUser();
            mydb.insertDummyMedicine();
            mydb.insertDummyDevice();
            mydb.insertDummySubmission();
            mydb.insertDummySubmission2();

            mydb.insertState("zachodniopomorskie","70","16","Zachodniopomorski Oddział NFZ");
            mydb.insertState("zachodniopomorskie","71","16","Zachodniopomorski Oddział NFZ");
            mydb.insertState("zachodniopomorskie","72","16","Zachodniopomorski Oddział NFZ");
            mydb.insertState("zachodniopomorskie","73","16","Zachodniopomorski Oddział NFZ");
            mydb.insertState("zachodniopomorskie","74","16","Zachodniopomorski Oddział NFZ");

            mydb.insertState("wielkopolskie","60","60","Wielkopolski Oddział NFZ");
            mydb.insertState("wielkopolskie","61","15","Wielkopolski Oddział NFZ");
            mydb.insertState("wielkopolskie","62","15","Wielkopolski Oddział NFZ");
            mydb.insertState("wielkopolskie","63","15","Wielkopolski Oddział NFZ");
            mydb.insertState("wielkopolskie","64","15","Wielkopolski Oddział NFZ");

            mydb.insertState("warmińsko-mazurskie","10","14","Warmińsko-Mazurski Oddział NFZ");
            mydb.insertState("warmińsko-mazurskie","11","14","Warmińsko-Mazurski Oddział NFZ");
            mydb.insertState("warmińsko-mazurskie","12","14","Warmińsko-Mazurski Oddział NFZ");
            mydb.insertState("warmińsko-mazurskie","13","14","Warmińsko-Mazurski Oddział NFZ");
            mydb.insertState("warmińsko-mazurskie","14","14","Warmińsko-Mazurski Oddział NFZ");
            mydb.insertState("warmińsko-mazurskie","19","14","Warmińsko-Mazurski Oddział NFZ");

            mydb.insertState("świętokrzyskie","25","13","Świętokrzyski Oddział NFZ");
            mydb.insertState("świętokrzyskie","27","13","Świętokrzyski Oddział NFZ");
            mydb.insertState("świętokrzyskie","28","13","Świętokrzyski Oddział NFZ");
            mydb.insertState("świętokrzyskie","29","13","Świętokrzyski Oddział NFZ");

            mydb.insertState("śląskie","40","12","Śląski Oddział NFZ");
            mydb.insertState("śląskie","41","12","Śląski Oddział NFZ");
            mydb.insertState("śląskie","42","12","Śląski Oddział NFZ");
            mydb.insertState("śląskie","43","12","Śląski Oddział NFZ");
            mydb.insertState("śląskie","44","12","Śląski Oddział NFZ");

            mydb.insertState("pomorskie","75","11","Pomorski Oddział NFZ");
            mydb.insertState("pomorskie","76","11","Pomorski Oddział NFZ");
            mydb.insertState("pomorskie","77","11","Pomorski Oddział NFZ");
            mydb.insertState("pomorskie","78","11","Pomorski Oddział NFZ");
            mydb.insertState("pomorskie","79","11","Pomorski Oddział NFZ");
            mydb.insertState("pomorskie","80","11","Pomorski Oddział NFZ");
            mydb.insertState("pomorskie","81","11","Pomorski Oddział NFZ");
            mydb.insertState("pomorskie","82","11","Pomorski Oddział NFZ");
            mydb.insertState("pomorskie","83","11","Pomorski Oddział NFZ");
            mydb.insertState("pomorskie","84","11","Pomorski Oddział NFZ");

            mydb.insertState("podlaskie","15","10","Podlaski Oddział NFZ");
            mydb.insertState("podlaskie","16","10","Podlaski Oddział NFZ");
            mydb.insertState("podlaskie","17","10","Podlaski Oddział NFZ");
            mydb.insertState("podlaskie","18","10","Podlaski Oddział NFZ");
            mydb.insertState("podlaskie","19","10","Podlaski Oddział NFZ");

            mydb.insertState("podkarpackie","35","09","Podkarpacki Oddział NFZ");
            mydb.insertState("podkarpackie","36","09","Podkarpacki Oddział NFZ");
            mydb.insertState("podkarpackie","37","09","Podkarpacki Oddział NFZ");
            mydb.insertState("podkarpackie","38","09","Podkarpacki Oddział NFZ");
            mydb.insertState("podkarpackie","39","09","Podkarpacki Oddział NFZ");

            mydb.insertState("opolskie","45","08","Opolski Oddział NFZ");
            mydb.insertState("opolskie","46","08","Opolski Oddział NFZ");
            mydb.insertState("opolskie","47","08","Opolski Oddział NFZ");
            mydb.insertState("opolskie","48","08","Opolski Oddział NFZ");
            mydb.insertState("opolskie","49","08","Opolski Oddział NFZ");

            mydb.insertState("mazowieckie","00","07","Mazowiecki Oddział NFZ");
            mydb.insertState("mazowieckie","01","07","Mazowiecki Oddział NFZ");
            mydb.insertState("mazowieckie","02","07","Mazowiecki Oddział NFZ");
            mydb.insertState("mazowieckie","03","07","Mazowiecki Oddział NFZ");
            mydb.insertState("mazowieckie","04","07","Mazowiecki Oddział NFZ");
            mydb.insertState("mazowieckie","05","07","Mazowiecki Oddział NFZ");
            mydb.insertState("mazowieckie","06","07","Mazowiecki Oddział NFZ");
            mydb.insertState("mazowieckie","07","07","Mazowiecki Oddział NFZ");
            mydb.insertState("mazowieckie","08","07","Mazowiecki Oddział NFZ");
            mydb.insertState("mazowieckie","09","07","Mazowiecki Oddział NFZ");
            mydb.insertState("mazowieckie","26","07","Mazowiecki Oddział NFZ");

            mydb.insertState("małopolski","30","06","Małopolski Oddział NFZ");
            mydb.insertState("małopolski","31","06","Małopolski Oddział NFZ");
            mydb.insertState("małopolski","32","06","Małopolski Oddział NFZ");
            mydb.insertState("małopolski","33","06","Małopolski Oddział NFZ");
            mydb.insertState("małopolski","34","06","Małopolski Oddział NFZ");

            mydb.insertState("łódzkie","90","05","Łódzki Oddział NFZ");
            mydb.insertState("łódzkie","91","05","Łódzki Oddział NFZ");
            mydb.insertState("łódzkie","92","05","Łódzki Oddział NFZ");
            mydb.insertState("łódzkie","93","05","Łódzki Oddział NFZ");
            mydb.insertState("łódzkie","94","05","Łódzki Oddział NFZ");
            mydb.insertState("łódzkie","95","05","Łódzki Oddział NFZ");
            mydb.insertState("łódzkie","96","05","Łódzki Oddział NFZ");
            mydb.insertState("łódzkie","97","05","Łódzki Oddział NFZ");
            mydb.insertState("łódzkie","98","05","Łódzki Oddział NFZ");
            mydb.insertState("łódzkie","99","05","Łódzki Oddział NFZ");

            mydb.insertState("lubuskie","65","04","Mazowiecki Oddział NFZ");
            mydb.insertState("lubuskie","66","04","Mazowiecki Oddział NFZ");
            mydb.insertState("lubuskie","67","04","Mazowiecki Oddział NFZ");
            mydb.insertState("lubuskie","68","04","Mazowiecki Oddział NFZ");
            mydb.insertState("lubuskie","69","04","Mazowiecki Oddział NFZ");

            mydb.insertState("lubelskie","20","03","Lubelski Oddział NFZ");
            mydb.insertState("lubelskie","21","03","Lubelski Oddział NFZ");
            mydb.insertState("lubelskie","22","03","Lubelski Oddział NFZ");
            mydb.insertState("lubelskie","23","03","Lubelski Oddział NFZ");
            mydb.insertState("lubelskie","24","03","Lubelski Oddział NFZ");

            mydb.insertState("kujawsko-pomorski","85","02","Kujawsko-Pomorski Oddział NFZ");
            mydb.insertState("kujawsko-pomorski","86","02","Kujawsko-Pomorski Oddział NFZ");
            mydb.insertState("kujawsko-pomorski","87","02","Kujawsko-Pomorski Oddział NFZ");
            mydb.insertState("kujawsko-pomorski","88","02","Kujawsko-Pomorski Oddział NFZ");
            mydb.insertState("kujawsko-pomorski","89","02","Kujawsko-Pomorski Oddział NFZ");

            mydb.insertState("dolnośląskie","50","01","Dolnośląski Oddział NFZ");
            mydb.insertState("dolnośląskie","51","01","Dolnośląski Oddział NFZ");
            mydb.insertState("dolnośląskie","52","01","Dolnośląski Oddział NFZ");
            mydb.insertState("dolnośląskie","53","01","Dolnośląski Oddział NFZ");
            mydb.insertState("dolnośląskie","54","01","Dolnośląski Oddział NFZ");
            mydb.insertState("dolnośląskie","55","01","Dolnośląski Oddział NFZ");
            mydb.insertState("dolnośląskie","56","01","Dolnośląski Oddział NFZ");
            mydb.insertState("dolnośląskie","57","01","Dolnośląski Oddział NFZ");
            mydb.insertState("dolnośląskie","58","01","Dolnośląski Oddział NFZ");
            mydb.insertState("dolnośląskie","59","01","Dolnośląski Oddział NFZ");
        }

    }
}
