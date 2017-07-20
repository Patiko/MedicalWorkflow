package com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker.NfzWorkerMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist.PharmacistMainScreen;
import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.ActivitiesClass.LoginActivity;
import com.example.lenovo.medicalworkflow.R;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by Lenovo on 07.05.2017.
 */

public class DisplayMedicine extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;
    ImageView deleteIcon;

    TextView name ;
    TextView type;
    TextView dosage;
    TextView quantity;
    RadioButton refundablee;
    TextView refundable;
    RadioGroup radioRefundGr;
    RadioButton radio1Refund;
    RadioButton radio2Refund;
    SharedPreferences sharedPreferences;
    Button checkRefundBtn;
    ListView excelRecords;



    TextView injection_way;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_medicine);
        name = (TextView) findViewById(R.id.editTextName);
        type = (TextView) findViewById(R.id.editTextType);
        quantity = (TextView) findViewById(R.id.editTextQuantity);
        //refundable = (TextView) findViewById(R.id.textView4);
        dosage = (TextView) findViewById(R.id.editTextDosage);
        checkRefundBtn = (Button) findViewById(R.id.checkRefundBtn);

       // excelRecords = (ListView) findViewById(R.id.excelRecords);


        injection_way = (TextView) findViewById(R.id.editTextInjectionWay);

        /*
        int radioId = radioRefundGr.getCheckedRadioButtonId();
        refundable=(RadioButton)findViewById(radioId);*/

        radioRefundGr =(RadioGroup) findViewById(R.id.radio_refundable) ;
        radio1Refund=(RadioButton) findViewById(R.id.radio_refund_yes);
        radio2Refund=(RadioButton) findViewById(R.id.radio_refund_no);
        refundable = radio2Refund;
        radioRefundGr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                //String refundable="";
//                Toast.makeText(getApplicationContext(), "DUPA2 "+checkedId,
//                        Toast.LENGTH_LONG).show();
                if(checkedId == radio1Refund.getId()){
                    refundable = radio1Refund;
                }
                if(checkedId == radio2Refund.getId()){
                    refundable = radio2Refund;
                }
            }
        });



        mydb = new DBHelper(this);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES,Context.MODE_PRIVATE);
        int Value = sharedPreferences.getInt(LoginActivity.UsedMedicineId,0);
        Button b = (Button)findViewById(R.id.button1);
        Button editB = (Button)findViewById(R.id.button2);
        Button deleteB = (Button)findViewById(R.id.button3);

        if(Value>0){
                //means this is the view part not the add contact part.
/*                if(mydb.getMedicineData(Value).equals(null)||mydb.getMedicineData(Value).equals("")){
                    Value=Value+1;

                }*/
            Cursor rs = mydb.getMedicineData(Value);
                if(rs!=null && rs.moveToFirst()){
                id_To_Update = Value;
                //rs.moveToFirst();
              //  if(!rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME)).equals("-1")) {

                    String nam = rs.getString(rs.getColumnIndex(DBHelper.MEDICINE_COLUMN_NAME));
                    String typ = rs.getString(rs.getColumnIndex(DBHelper.MEDICINE_COLUMN_TYPE));
                    String dosag = rs.getString(rs.getColumnIndex(DBHelper.MEDICINE_COLUMN_DOSAGE));
                    String quant = rs.getString(rs.getColumnIndex(DBHelper.MEDICINE_COLUMN_QUANTITY));
                    String refund = rs.getString(rs.getColumnIndex(DBHelper.MEDICINE_COLUMN_REFUNDABLE));
                    String inject = rs.getString(rs.getColumnIndex(DBHelper.MEDICINE_COLUMN_INJECTION_WAY));



                if (!rs.isClosed())  {
                    rs.close();
                }

                b.setVisibility(View.GONE);
                editB.setVisibility(View.VISIBLE);
                deleteB.setVisibility(View.VISIBLE);



                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                type.setText((CharSequence)typ);
                type.setFocusable(false);
                type.setClickable(false);

                    dosage.setText((CharSequence)dosag);
                    dosage.setFocusable(false);
                    dosage.setClickable(false);

                quantity.setText((CharSequence)quant);
                quantity.setFocusable(false);
                quantity.setClickable(false);


                    if(refund.equals("Tak")){
                        radio1Refund.setChecked(true);
                    }
                    if(refund.equals("Nie")){
                        radio2Refund.setChecked(true);

                    }
                    radio1Refund.setFocusable(false);
                    radio1Refund.setClickable(false);
                    radio2Refund.setFocusable(false);
                    radio2Refund.setClickable(false);

                injection_way.setText((CharSequence)inject);
                injection_way.setFocusable(false);
                injection_way.setClickable(false);




            }}
        if(sharedPreferences.getInt(LoginActivity.UsedSubmissionId,0)!=0){
            editB.setVisibility(View.GONE);
            deleteB.setVisibility(View.GONE);
        }
    /*    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            saveUrl("zalacznik-do-obwieszczenia-1.xls","http://www.mz.gov.pl/wp-content/uploads/2017/04/zalacznik-do-obwieszczenia-1.xls");
            Toast.makeText(getApplicationContext(), "Plik pobrany",
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

   /*     new Thread(new Runnable(){
            @Override
            public void run() {
                // Do network action in this function

            }
        }).start();*/




    }

    public void checkRefund(View view){
        try {

            AssetManager am =getAssets();
            //InputStream is=am.open("zalacznik-do-obwieszczenia-nowy.xls");
            InputStream is=am.open("zalacznik-do-obwieszczenia-1.xls");
            String xxNames="";
            String subStringNames="";
            String xxDosages="";
            String subStringDosages="";
            String xxTypes="";
            String subStringTypes="";
            List<String> medicineNameListExcel= new ArrayList<String>();
            List<String> medicineTypeListExcel= new ArrayList<String>();
            List<String> medicineDosageListExcel= new ArrayList<String>();
            List<String> medicineQuantityListExcel= new ArrayList<String>();

            List<Integer> indexNameList= new ArrayList<Integer>();

            Workbook wb = Workbook.getWorkbook(is);
            Sheet s=wb.getSheet(0);
            int row = s.getRows();
            int col = s.getColumns();

/*
                 for (int i=3; i<row; i++){
                //for(int c=0; c<col; c++){
                    Cell z = s.getCell(2,i);
                    xx =xx+z.getContents();
                     xx=xx+"\n";
                }
              //  }
            display(xx);*/

      ///////////// MEDICINE NAME LIST ///////////////
            for (int i=3; i<row; i++){
                //for(int c=0; c<col; c++){
                    Cell zNames = s.getCell(2,i);
                xxNames="";
                xxNames =xxNames+zNames.getContents();
                int iendNames=xxNames.indexOf(",");
                if(iendNames!= -1){
                    subStringNames="";
                    subStringNames = xxNames.substring(0,iendNames);
                    medicineNameListExcel.add(subStringNames);
                }
            }
            if(searchIfExists(name.getText().toString(),medicineNameListExcel)){
                Toast.makeText(getApplicationContext(), "Lek jest refundowany!",
                        Toast.LENGTH_SHORT).show();
                //  radio1Refund.setClickable(false);
                // radio2Refund.setClickable(false);
            }else {
                Toast.makeText(getApplicationContext(), "Lek nie jest refundowany!",
                        Toast.LENGTH_SHORT).show();

            }
            indexNameList = getIndexListEnteredNames(name.getText().toString(),medicineNameListExcel);  //ZWROC LISTE INDEXow GDZIE WYSTEPUJE PODANY NAME
            String textToDisplay="";
            if(!indexNameList.isEmpty()){
                for(int h=0; h<indexNameList.size(); h++){
                    textToDisplay=textToDisplay+Integer.toString(indexNameList.get(h));
                    textToDisplay=textToDisplay+"\n";
                }
                TextView xxx=(TextView) findViewById(R.id.textView0);
                xxx.setText(textToDisplay);
            }
         //   displayTextView(medicineNameListExcel);

          /////////////// MEDICINE TYPE LIST /////////////////
            for (int i=3; i<row; i++){
                //for(int c=0; c<col; c++){
                Cell zTypes = s.getCell(2,i);
                xxTypes="";
                xxTypes =xxTypes+zTypes.getContents();
                // int iendDosages=xxDosages.indexOf(",", xxDosages.indexOf(",")+1); // INDEX OF SECOND OCCURENCE OF COMMA
                // int iendDosages=xxDosages.indexOf(xxDosages.substring(s.lastIndexOf(':') + 1);
                //if(iendDosages!= -1){
                subStringTypes="";

                int lastIndexType = xxTypes.lastIndexOf(",");
                int prevIndexType = xxTypes.lastIndexOf(",", lastIndexType - 1);

                if(prevIndexType!=-1 && lastIndexType!=-1){
                    subStringTypes = xxTypes.substring(prevIndexType +1, lastIndexType).trim();
                    //subStringTypes = xxTypes.substring(typeStart, typeEnd);
                    medicineTypeListExcel.add(subStringTypes);
                }
            }
            if(searchIfExists(type.getText().toString(),medicineTypeListExcel)){
                Toast.makeText(getApplicationContext(), "Lek jest refundowany! POSTAC",
                        Toast.LENGTH_SHORT).show();
                //  radio1Refund.setClickable(false);
                // radio2Refund.setClickable(false);
            }else {
                Toast.makeText(getApplicationContext(), "Lek nie jest refundowany! POSTAC",
                        Toast.LENGTH_SHORT).show();

            }
               displayTextView(medicineTypeListExcel);

            ///////////// MEDICINE DOSAGE LIST /////////////////
            for (int i=3; i<row; i++){
                //for(int c=0; c<col; c++){
                Cell zDosages = s.getCell(2,i);
                xxDosages="";
                xxDosages =xxDosages+zDosages.getContents();
               // int iendDosages=xxDosages.indexOf(",", xxDosages.indexOf(",")+1); // INDEX OF SECOND OCCURENCE OF COMMA
                int iendDosages=xxDosages.lastIndexOf(',');

                if(iendDosages!= -1){
                    subStringDosages="";
                    subStringDosages = xxDosages.substring(xxDosages.lastIndexOf(',')+2).trim();
                    medicineDosageListExcel.add(subStringDosages);
                }
            }
            if(searchIfExists(dosage.getText().toString(),medicineDosageListExcel)){
                Toast.makeText(getApplicationContext(), "Lek jest refundowany! DAWKA",
                        Toast.LENGTH_SHORT).show();
                //  radio1Refund.setClickable(false);
                // radio2Refund.setClickable(false);
            }else {
                Toast.makeText(getApplicationContext(), "Lek nie jest refundowany! DAWKA",
                        Toast.LENGTH_SHORT).show();

            }
         //   displayTextView(medicineDosageListExcel);

        }
        catch (Exception e){
        }
    }
    public List<Integer> getIndexListEnteredNames(String name, List<String> lista){

        List<Integer> indexList= new ArrayList<Integer>();
        for(int b=0; b<lista.size(); b++){
            if(name.equals(lista.get(b))){
                indexList.add(b);
            }
        }
        return indexList;
    }


    public Boolean searchIfExists(String name, List<String> lista){
        Boolean refundFlag=false;
        int index;
        for(int a=0; a<lista.size(); a++){
            if(name.equals(lista.get(a))){
                index=a;
                refundFlag=true;
                break;
            }else refundFlag=false;
        }
        return refundFlag;
    }

        public void displayList(List<String> arrayList){
            ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, arrayList);

           ListView excelRecords = (ListView)findViewById(R.id.excelRecords);
            excelRecords.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

    }

    public void displayTextView(List<String> arrayList){
        String finalStr="";
        for(int j=0; j<arrayList.size();j++){
            finalStr=finalStr+arrayList.get(j);
            finalStr=finalStr+"\n";

        }
        TextView x = (TextView)findViewById(R.id.textView00);
        x.setText(finalStr);
    }
/*    public void display(String value){
        TextView x = (TextView)findViewById(R.id.textView0);
        x.setText(value);
    }*/



    public void saveUrl(final String filename, final String urlString)
            throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;

        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }


/*    public void radioRefundClicked(View view) {


       RadioGroup radioRefundGr = (RadioGroup) findViewById(R.id.radio_refundable);
        radio1Refund = (RadioButton) findViewById(R.id.radio_refund_yes);
        radio2Refund = (RadioButton) findViewById(R.id.radio_refund_no);

        int radioId = radioRefundGr.getCheckedRadioButtonId();
        refundable=(RadioButton)findViewById(radioId);
       // refundable = radioRefundBtn.getText().toString();


       boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_refund_yes:
                if (checked)
                    refundable=radio1Refund.getId();
                break;
            case R.id.radio_refund_no:
                if (checked)
                    refundable.setText("no");
                break;

    }*/

    public void insertMedicine(View view) {
            if (!name.getText().toString().equals("") && !type.getText().toString().equals("") && !dosage.getText().toString().equals("") && !quantity.getText().toString().equals("")
           //       && !refundable.getText().toString().equals("")
                    ) {
            //    sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                Integer creator_id = sharedPreferences.getInt(LoginActivity.UserId,0);
                if (mydb.insertMedicine(name.getText().toString(), type.getText().toString(), dosage.getText().toString(),
                        quantity.getText().toString(), refundable.getText().toString(),
                        injection_way.getText().toString(),creator_id)) {

                    Toast.makeText(getApplicationContext(), "Lek został dodany",
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Lek niedodany",
                            Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getApplicationContext(), CheckMedicineList.class);
                startActivity(intent);


            } else if (name.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Proszę podać nazwę leku",
                        Toast.LENGTH_LONG).show();
            } else if(type.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Proszę podać postać leku np. tabl.",
                        Toast.LENGTH_LONG).show();
            }else if (dosage.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Proszę podać dawke leku np. 50 mg",
                        Toast.LENGTH_LONG).show();
            } else if(quantity.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Proszę podać zawartość opakowania np. 30 szt.",
                        Toast.LENGTH_LONG).show();
            }
    }



    public void refundYesClicked(View v) {
        checkRefundBtn.setVisibility(View.VISIBLE);
    }

    public void refundNoClicked(View v) {
        checkRefundBtn.setVisibility(View.GONE);
    }



    public void updateMedicine(View view) {


        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES,Context.MODE_PRIVATE);
        final int Value = sharedPreferences.getInt(LoginActivity.UsedMedicineId,0);
        if(Value>0){
            Button b=(Button)findViewById(R.id.button2);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!name.getText().toString().equals("") && !type.getText().toString().equals("") && !dosage.getText().toString().equals("") && !quantity.getText().toString().equals("")
                 //           && !refundable.getText().toString().equals("")
                            ) {
                        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                        Integer creator_id = sharedPreferences.getInt(LoginActivity.UserId,0);

                        if(mydb.updateMedicine(Value,name.getText().toString(),
                                type.getText().toString(), dosage.getText().toString(), quantity.getText().toString(),
                                refundable.getText().toString(), injection_way.getText().toString(),creator_id)){
                            Toast.makeText(getApplicationContext(), "Lek został zmieniony", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),CheckMedicineList.class);
                            startActivity(intent);

                        } else{
                            Toast.makeText(getApplicationContext(), "Lek pozostał niezmieniony", Toast.LENGTH_SHORT).show();
                        }

                    }else if (name.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Proszę podać nazwę leku",
                                Toast.LENGTH_LONG).show();
                    } else if(type.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Proszę podać postać leku np. tabl.",
                                Toast.LENGTH_LONG).show();
                    }else if (dosage.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Proszę podać dawke leku np. 50 mg",
                                Toast.LENGTH_LONG).show();
                    } else if(quantity.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Proszę podać zawartość opakowania np. 30 szt.",
                                Toast.LENGTH_LONG).show();
                    }

                }
            });
            b.setVisibility(View.VISIBLE);
            name.setEnabled(true);
            name.setFocusableInTouchMode(true);
            name.setClickable(true);

            type.setEnabled(true);
            type.setFocusableInTouchMode(true);
            type.setClickable(true);

            dosage.setEnabled(true);
            dosage.setFocusableInTouchMode(true);
            dosage.setClickable(true);

            quantity.setEnabled(true);
            quantity.setFocusableInTouchMode(true);
            quantity.setClickable(true);


            radio1Refund.setFocusable(true);
            radio1Refund.setClickable(true);
            radio2Refund.setFocusable(true);
            radio2Refund.setClickable(true);


            injection_way.setEnabled(true);
            injection_way.setFocusableInTouchMode(true);
            injection_way.setClickable(true);
            Toast.makeText(getApplicationContext(), "Jesteś w trybie edycji",
                    Toast.LENGTH_LONG).show();


        }
    }

    public void runDelete(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            mydb.deleteMedicine(id_To_Update);
                Toast.makeText(getApplicationContext(), "Lek usunięty", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),CheckMedicineList.class);
                startActivity(intent);


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
