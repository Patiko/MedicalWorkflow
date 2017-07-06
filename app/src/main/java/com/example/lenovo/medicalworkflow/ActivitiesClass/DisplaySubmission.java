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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.DisplayMedicine;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor.DoctorMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker.NfzWorkerMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist.PharmacistMainScreen;
import com.example.lenovo.medicalworkflow.CustomAdapters.CustomAdapterMedicines;
import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.R;



/**
 * Created by Lenovo on 17.06.2017.
 */

public class DisplaySubmission extends Activity {

    private DBHelper mydb ;

    TextView doc_name;
    TextView doc_type;
    TextView doc_id;
    TextView createdAt;
    TextView patient;
    TextView patientPayer;
    TextView patientInsuredId;
    TextView doctor;
    TextView doctorPlace;
    TextView status;
    ListView medicineListView;
    CustomAdapterMedicines customAdapterMedicines;

    ImageView editStatusPopUp;
    ImageView openStatusPopUp;
    Button acceptSubmission, rejectSubmission;

    Cursor rs, rs2, rs3,rs4;


    SharedPreferences sharedPreferences;


    TextView injection_way;
    int id_To_Update, id_To_Update2, id_To_Update3 = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_submission);
        doc_name = (TextView) findViewById(R.id.docNameTV);
        doc_type = (TextView) findViewById(R.id.docTypeTV);
        createdAt = (TextView) findViewById(R.id.createdDateTV);
        doc_id = (TextView) findViewById(R.id.docIdTV);
        patient = (TextView) findViewById(R.id.patientTV);
        patientPayer = (TextView) findViewById(R.id.patientPayerTV);
        patientInsuredId = (TextView) findViewById(R.id.insuredTV);
        doctor = (TextView) findViewById(R.id.doctorTV);
        doctorPlace = (TextView) findViewById(R.id.doctorPlaceTV);
        status = (TextView) findViewById(R.id.submissionStatus);
        medicineListView = (ListView) findViewById(R.id.medicinesListView);

        mydb = new DBHelper(this);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES,Context.MODE_PRIVATE);
       // int ValueUserId = sharedPreferences.getInt(LoginActivity.UsedMedicineId,0);
        int ValuePatientId = sharedPreferences.getInt(LoginActivity.UsedUserId,0);              //////////DO INSERTU
        int ValueDoctorId = sharedPreferences.getInt(LoginActivity.UserId,0);                   //////////DO INSERTU
        int ValueMedicineId = sharedPreferences.getInt(LoginActivity.UsedMedicineId,0);        //////////DO INSERTU
        String ValueUserPeselId = sharedPreferences.getString(LoginActivity.PeselId,"");

        int ValueSubmissionId = sharedPreferences.getInt(LoginActivity.UsedSubmissionId,0);

        if(ValueSubmissionId>0){
            //means this is the view part not the add contact part.
/*                if(mydb.getMedicineData(Value).equals(null)||mydb.getMedicineData(Value).equals("")){
                    Value=Value+1;

                }*/
            Cursor rs = mydb.getPatientDetailsBySubmission(ValueSubmissionId);
            if(rs!=null && rs.moveToFirst()){
               // id_To_Update = ValuePatientId;

                String first_nam = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_FIRST_NAME));
                String last_nam = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_LAST_NAME));
                String pese = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_PESEL));
                String insur = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_INSURED));
                String birthdat = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_BIRTHDATE));

                String stree = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_STREET));
                String nr_do = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_NRDOM));
                String nr_mie = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_NRMIES));
                String post_cod = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_POST_CODE));
                String cit = rs.getString(rs.getColumnIndex(DBHelper.USER_COLUMN_CITY));

                Integer age=0;
                String dataWystawienia = mydb.getDateTime();
                if(dataWystawienia!=null && birthdat!=null) {

                    String strYearWyst = dataWystawienia.substring(6, 10);
                    int yearWyst = Integer.parseInt(strYearWyst);
                    String strMonthWyst = dataWystawienia.substring(3, 5);
                    int monthWyst = Integer.parseInt(strMonthWyst);
                    String strDayWyst = dataWystawienia.substring(0, 2);
                    int dayWyst = Integer.parseInt(strDayWyst);
                    // Toast.makeText(getApplicationContext(), "DayWys="+dayWyst+" MontWys="+monthWyst+" YearWys="+yearWyst+"age="+age,  Toast.LENGTH_LONG).show();

                    String strYearBirth = birthdat.substring(6, 10);
                    int yearBirth = Integer.parseInt(strYearBirth);
                    String strMonthBirth = birthdat.substring(3, 5);
                    int monthBirth = Integer.parseInt(strMonthBirth);
                    String strDayBirth = birthdat.substring(0, 2);
                    int dayBirth = Integer.parseInt(strDayBirth);

                    age = yearWyst - yearBirth;
                    if (monthWyst < monthBirth) {
                        age--;
                    } else if (monthWyst == monthBirth && dayWyst < dayBirth) {
                        age++;
                    }
                }
                String payerResult = mydb.getPayerPlace(post_cod);
                if(!stree.equals("")){stree="\n\tAdres\n\t\t"+stree;}
                if(!nr_mie.equals("")){nr_mie=" lok. "+nr_mie;}
                if(!post_cod.equals("")){post_cod="\n\t\t"+post_cod;}


                String patientDetails= " Pacjent\n\t\t"+first_nam+" "+last_nam+"\n\t\tPESEL: "+pese+"\n\tData urodzenia: "+birthdat +" r."+ "\n\tWiek w dniu wystawienia: "+age+" lat"
                      +stree+" "+nr_do+nr_mie+post_cod+" "+cit;
                String insuredDetails = " Dokument uprawnień: "+insur;
                String payerDetails=" Płatnik:\n\t"+ payerResult;

                if (!rs.isClosed())  {
                    rs.close();
                }
                patient.setText((CharSequence)patientDetails);
                patientInsuredId.setText((CharSequence)insuredDetails);
                patientPayer.setText((CharSequence)payerDetails);
               // type.setText((CharSequence)typ);
            }

            Cursor rs2 = mydb.getDoctorDetailsBySubmission(ValueSubmissionId);
            if(rs2!=null && rs2.moveToFirst()){
              //  id_To_Update2 = ValueDoctorId;

                String first_nam = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_FIRST_NAME));
                String last_nam = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_LAST_NAME));
                String profi = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_PROFILE));
                String npw = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_NPWZ));
                String specialis = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_SPECIALISATIONS));


                String work_nam = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_WORK_NAME));
                String registry_entr = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_REGISTRY_ENTRY));
                String rego = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_REGON));
                String stree = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_STREET));
                String nr_do = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_NRDOM));
                String nr_mie = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_NRMIES));
                String post_cod = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_POST_CODE));
                String cit = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_CITY));
                String phone_num = rs2.getString(rs2.getColumnIndex(DBHelper.USER_COLUMN_PHONENUMBER));

                if(!npw.equals("")) { npw="\n\t\tNPWZ "+npw; }
                if(!specialis.equals("")) { specialis="\n\tSpecjalizacje:\n\t\t"+specialis; }
                if(!registry_entr.equals("")) { registry_entr="\n\t\tWpis OIL "+registry_entr; }
                if(!rego.equals("")){ rego="\n\t\tREGON "+rego; }
                if(!stree.equals("")){ stree="\n\tAdres:\n\t\t"+stree; }
                if(!nr_do.equals("")){ nr_do=" "+nr_do; }
                if(!nr_mie.equals("")){ nr_mie=" lok. "+nr_mie; }
                if(!post_cod.equals("")){ post_cod="\n\t\t"+post_cod;}
                if(!phone_num.equals("")){ phone_num="\n\tTel. "+phone_num;}


                String doctorDetails= " Wystawca dokumentu \n\t\t"+profi+" "+first_nam+" "+last_nam+npw+specialis;
                String doctorPlaceDetails=" Miejsce wystawienia: "+"\n\t\t"+ work_nam+registry_entr+rego+stree+nr_do+nr_mie+post_cod+" "+cit+phone_num;


                if (!rs2.isClosed())  {
                    rs2.close();
                }
                doctor.setText((CharSequence)doctorDetails);
                doctorPlace.setText((CharSequence)doctorPlaceDetails);

                // type.setText((CharSequence)typ);
            }



         //   rs3 = mydb.getAllMedicinesByCreator(ValueDoctorId);
            rs3 = mydb.getAllMedicinesBySubmission(ValueSubmissionId);
            customAdapterMedicines = new CustomAdapterMedicines(DisplaySubmission.this, rs3, 0);
            medicineListView = (ListView) findViewById(R.id.medicinesListView);
            medicineListView.setAdapter(customAdapterMedicines);

            medicineListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                    int id_To_Search = rs3.getInt(rs3.getColumnIndex(DBHelper.MEDICINE_COLUMN_ID));
                    if(id_To_Search!=0){

                        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(LoginActivity.UsedMedicineId,id_To_Search);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), DisplayMedicine.class);
                        startActivity(intent);
             /*           Button editB = (Button)findViewById(R.id.button2);
                        editB.setVisibility(View.GONE);
                        Button deleteB = (Button)findViewById(R.id.button3);
                        deleteB.setVisibility(View.GONE);*/
                    }
                }
            });


            Cursor rs4 = mydb.getSubmissionData(ValueSubmissionId);
            if(rs4!=null && rs4.moveToFirst()){
                //  id_To_Update2 = ValueDoctorId;

                String doc_nam = rs4.getString(rs4.getColumnIndex(DBHelper.SUBMISSION_COLUMN_DOC_NAME));
                String doc_typ = rs4.getString(rs4.getColumnIndex(DBHelper.SUBMISSION_COLUMN_DOC_TYPE));
                String createA = rs4.getString(rs4.getColumnIndex(DBHelper.SUBMISSION_COLUMN_CREATED_AT));
                String doc_i = rs4.getString(rs4.getColumnIndex(DBHelper.SUBMISSION_COLUMN_ID));
                String statu = rs4.getString(rs4.getColumnIndex(DBHelper.SUBMISSION_COLUMN_STATUS));

                if (!rs4.isClosed())  {
                    rs4.close();
                }
                doc_name.setText("Nazwa: "+ (CharSequence)doc_nam);
                doc_type.setText("Typ: "+(CharSequence)doc_typ);
                createdAt.setText("Data wystawienia: "+(CharSequence)createA);
                doc_id.setText("ID: "+(CharSequence)doc_i);
                status.setText((CharSequence)statu);


            }
        }

        openStatusPopUp = (ImageView) findViewById(R.id.editSubmissionStatus);
        openStatusPopUp.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View a){
                startActivity(new Intent(DisplaySubmission.this,StatusPopUp.class));
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES,Context.MODE_PRIVATE);
        String loggedProfile = sharedPreferences.getString(LoginActivity.LoggedProfileId,"");
        switch (loggedProfile){
            case LoginActivity.doctor:
                openStatusPopUp = (ImageView) findViewById(R.id.editSubmissionStatus);
                openStatusPopUp.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View a){
                        startActivity(new Intent(DisplaySubmission.this,StatusPopUp.class));
                    }
                });
                break;
            case LoginActivity.patient:
                openStatusPopUp = (ImageView) findViewById(R.id.editSubmissionStatus);
                openStatusPopUp.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View a){
                        Toast.makeText(getApplicationContext(), "Brak odpowiednich uprawnień!",
                                Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case LoginActivity.pharmacist:
                openStatusPopUp = (ImageView) findViewById(R.id.editSubmissionStatus);
                openStatusPopUp.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View a){
                        startActivity(new Intent(DisplaySubmission.this,StatusPopUp.class));
                    }
                });
                break;
            case LoginActivity.nfzWorker:
                openStatusPopUp = (ImageView) findViewById(R.id.editSubmissionStatus);
                openStatusPopUp.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View a){
                        Toast.makeText(getApplicationContext(), "Brak odpowiednich uprawnień!",
                                Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }



    }
    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        finish();
        Intent i = new Intent(this,DisplaySubmission.class);
        startActivity(i);
      //  startActivity(getIntent());

    }
    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        int ValueSubmissionId = sharedPreferences.getInt(LoginActivity.UsedSubmissionId, 0);
        Cursor rs4 = mydb.getSubmissionData(ValueSubmissionId);
        if (rs4 != null && rs4.moveToFirst()) {
            //  id_To_Update2 = ValueDoctorId;

            String doc_nam = rs4.getString(rs4.getColumnIndex(DBHelper.SUBMISSION_COLUMN_DOC_NAME));
            String doc_typ = rs4.getString(rs4.getColumnIndex(DBHelper.SUBMISSION_COLUMN_DOC_TYPE));
            String createA = rs4.getString(rs4.getColumnIndex(DBHelper.SUBMISSION_COLUMN_CREATED_AT));
            String doc_i = rs4.getString(rs4.getColumnIndex(DBHelper.SUBMISSION_COLUMN_ID));
            String statu = rs4.getString(rs4.getColumnIndex(DBHelper.SUBMISSION_COLUMN_STATUS));

            if (!rs4.isClosed()) {
                rs4.close();
            }
            doc_name.setText("Nazwa: " + (CharSequence) doc_nam);
            doc_type.setText("Typ: " + (CharSequence) doc_typ);
            createdAt.setText("Data wystawienia: " + (CharSequence) createA);
            doc_id.setText("ID: " + (CharSequence) doc_i);
            status.setText((CharSequence) statu);
        }
    }

/*    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        refreshData();
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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(LoginActivity.UsedSubmissionId);
        editor.apply();
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
