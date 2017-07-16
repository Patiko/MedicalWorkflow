package com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.ActivitiesClass.LoginActivity;
import com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker.NfzWorkerMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist.PharmacistMainScreen;
import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 15.07.2017.
 */

public class DisplayDevice extends Activity {

    private DBHelper mydb ;
    TextView DeviceId;
    TextView DeviceQuantity;
    TextView DeviceName;
    TextView DeviceInstructions;
    Button b1, b2, b3;
    int id_To_Update = 0;
    LinearLayout deviceInstructionsLayout;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_device);
        DeviceId =   (TextView)  findViewById(R.id.editDeviceId);
        DeviceQuantity =   (TextView)  findViewById(R.id.edit_device_quantity);
        DeviceName =   (TextView)  findViewById(R.id.edit_device_name);
        DeviceInstructions =   (TextView)  findViewById(R.id.edit_device_instructions);
        deviceInstructionsLayout = (LinearLayout) findViewById(R.id.deviceInstructionsLayout);

        mydb = new DBHelper(this);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES,Context.MODE_PRIVATE);
        int ValueDeviceId = sharedPreferences.getInt(LoginActivity.UsedMedicineId,0);
        Button b1 = (Button)findViewById(R.id.button1);
        Button b2 = (Button)findViewById(R.id.button2);
        Button b3 = (Button)findViewById(R.id.button3);

        if(ValueDeviceId>0){
            //means this is the view part not the add contact part.
/*                if(mydb.getMedicineData(Value).equals(null)||mydb.getMedicineData(Value).equals("")){
                    Value=Value+1;

                }*/
            Cursor rs = mydb.getDeviceData(ValueDeviceId);
            if(rs!=null && rs.moveToFirst()){
                id_To_Update = ValueDeviceId;

                String d_id = rs.getString(rs.getColumnIndex(DBHelper.MEDICINE_COLUMN_DEVICE_ID));
                String d_quantity= rs.getString(rs.getColumnIndex(DBHelper.MEDICINE_COLUMN_DEVICE_QUANTITY));
                String d_name = rs.getString(rs.getColumnIndex(DBHelper.MEDICINE_COLUMN_DEVICE_NAME));
                String d_instructions = rs.getString(rs.getColumnIndex(DBHelper.MEDICINE_COLUMN_DEVICE_INSTRUCTIONS));



                if (!rs.isClosed())  {
                    rs.close();
                }

                b1.setVisibility(View.GONE);
                b2.setVisibility(View.VISIBLE);
                b3.setVisibility(View.VISIBLE);



                DeviceId.setText((CharSequence)d_id);
                DeviceId.setFocusable(false);
                DeviceId.setClickable(false);

                DeviceQuantity.setText((CharSequence)d_quantity);
                DeviceQuantity.setFocusable(false);
                DeviceQuantity.setClickable(false);

                DeviceName.setText((CharSequence)d_name);
                DeviceName.setFocusable(false);
                DeviceName.setClickable(false);

                DeviceInstructions.setText((CharSequence)d_instructions);
                DeviceInstructions.setFocusable(false);
                DeviceInstructions.setClickable(false);


            }}
        if(sharedPreferences.getInt(LoginActivity.UsedSubmissionId,0)!=0){
            b2.setVisibility(View.GONE);
            b3.setVisibility(View.GONE);
        }


    }


    public void insertDevice(View view) {


        if (!DeviceId.getText().toString().equals("") && !DeviceQuantity.getText().toString().equals("") && !DeviceName.getText().toString().equals("")
                 //  && !DeviceInstructions.getText().toString().equals("")
                ) {
            //    sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            Integer creator_id = sharedPreferences.getInt(LoginActivity.UserId,0);
            if (mydb.insertDevice(creator_id,DeviceId.getText().toString(), DeviceQuantity.getText().toString(),
                    DeviceName.getText().toString(), DeviceInstructions.getText().toString())) {

                Toast.makeText(getApplicationContext(), "Wyrób medyczny został dodany",
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "Wyrób medyczny niedodany",
                        Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(getApplicationContext(), CheckDeviceList.class);
            startActivity(intent);


        } else if (DeviceId.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Proszę podać liczbę porządkową wyrobu medycznego",
                    Toast.LENGTH_LONG).show();
        } else if(DeviceQuantity.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Proszę podać liczbę sztuk",
                    Toast.LENGTH_LONG).show();
        } else if(DeviceName.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Proszę podać nazwę wyrobu medycznego",
                    Toast.LENGTH_LONG).show();
        } /*else if(DeviceInstructions.getText().toString().equals("")){
            deviceInstructionsLayout.setVisibility(View.GONE);
        }*/
    }





    public void updateDevice(View view) {


        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES,Context.MODE_PRIVATE);
        final int ValueDeviceId = sharedPreferences.getInt(LoginActivity.UsedMedicineId,0);
        if(ValueDeviceId>0){
            Button b=(Button)findViewById(R.id.button2);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!DeviceId.getText().toString().equals("") && !DeviceQuantity.getText().toString().equals("") && !DeviceName.getText().toString().equals("")
                           //        && !DeviceInstructions.getText().toString().equals("")
                            ) {
                        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                        Integer creator_id = sharedPreferences.getInt(LoginActivity.UserId,0);

                        if(mydb.updateDevice(ValueDeviceId,creator_id,DeviceId.getText().toString(),
                                DeviceQuantity.getText().toString(), DeviceName.getText().toString(),
                                DeviceInstructions.getText().toString())){
                            Toast.makeText(getApplicationContext(), "Wyrób medyczny został zmieniony", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),CheckDeviceList.class);
                            startActivity(intent);

                        } else{
                            Toast.makeText(getApplicationContext(), "Wyrób medyczny pozostał niezmieniony", Toast.LENGTH_SHORT).show();
                        }

                    }else if (DeviceId.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Proszę podać liczbę porządkową wyrobu medycznego",
                                Toast.LENGTH_LONG).show();
                    } else if(DeviceQuantity.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Proszę podać liczbę sztuk",
                                Toast.LENGTH_LONG).show();
                    } else if(DeviceName.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Proszę podać nazwę wyrobu medycznego",
                                Toast.LENGTH_LONG).show();
                    }

                }
            });
            b.setVisibility(View.VISIBLE);
            DeviceId.setEnabled(true);
            DeviceId.setFocusableInTouchMode(true);
            DeviceId.setClickable(true);

            DeviceQuantity.setEnabled(true);
            DeviceQuantity.setFocusableInTouchMode(true);
            DeviceQuantity.setClickable(true);

            DeviceName.setEnabled(true);
            DeviceName.setFocusableInTouchMode(true);
            DeviceName.setClickable(true);

            DeviceInstructions.setEnabled(true);
            DeviceInstructions.setFocusableInTouchMode(true);
            DeviceInstructions.setClickable(true);

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
