package com.example.lenovo.medicalworkflow.ActivitiesClass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 03.07.2017.
 */

public class StatusPopUp extends Activity {
    Button acceptBtn, rejectBtn;
    SharedPreferences sharedPreferences;
    private DBHelper mydb;
    String accepted="ZAAKCEPTOWANA";
    String rejected="ODRZUCONA";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_status_popup);
        acceptBtn= (Button) findViewById(R.id.acceptButton);
        rejectBtn= (Button) findViewById(R.id.rejectButton);
        mydb = new DBHelper(this);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);




        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width =dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*0.7),(int)(height*0.4));

      /*  WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount=0.0f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        back_dim_layout.setVisibility(View.VISIBLE);*/


        /*      acceptBtn.setOnClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

                int id_To_Search = cursor.getInt(cursor.getColumnIndex(DBHelper.SUBMISSION_COLUMN_ID));

                    Intent intent = new Intent(getApplicationContext(), DisplaySubmission.class);
                    //        intent.putExtras(dataBundle);
                    startActivity(intent);
                    // arrayAdapter.notifyDataSetChanged();
                }
            }
        });*/

    }
    public void setAcceptStatus(View view){

        int ValueSubmissionId = sharedPreferences.getInt(LoginActivity.UsedSubmissionId,0);
      //  mydb.updateSubmissionStatus(ValueSubmissionId,accepted);
        Cursor rs = mydb.getSubmissionData(ValueSubmissionId);
        if(rs!=null && rs.moveToFirst()){

            String doc_nam = rs.getString(rs.getColumnIndex(DBHelper.SUBMISSION_COLUMN_DOC_NAME));
            String statu = rs.getString(rs.getColumnIndex(DBHelper.SUBMISSION_COLUMN_STATUS));

            if(sharedPreferences.getString(LoginActivity.LoggedProfileId,"").equals(LoginActivity.doctor) &&
                    statu.equals(LoginActivity.doctorCheckStatus)){
                mydb.updateSubmissionStatus(ValueSubmissionId,LoginActivity.doctorAcceptedStatus);
                Toast.makeText(getApplicationContext(), "Wniosek "+doc_nam+" został zaakceptowany!",
                        Toast.LENGTH_LONG).show();
            }else if(sharedPreferences.getString(LoginActivity.LoggedProfileId,"").equals(LoginActivity.pharmacist) &&
                    statu.equals(LoginActivity.pharmacistCheckStatus)){
                mydb.updateSubmissionStatus(ValueSubmissionId,LoginActivity.pharmacistAcceptedStatus);
                Toast.makeText(getApplicationContext(), "Wniosek "+doc_nam+" został zaakceptowany!",
                        Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), "Brak odpowiednich uprawnień !!!",
                        Toast.LENGTH_LONG).show();
            }
            if (!rs.isClosed())  {
                rs.close();
            }

        }
        finish();
    }

    public void setDeclineStatus(View view){

        int ValueSubmissionId = sharedPreferences.getInt(LoginActivity.UsedSubmissionId,0);
      //  mydb.updateSubmissionStatus(ValueSubmissionId,rejected);
        Cursor rs=mydb.getAllSubmissionData(ValueSubmissionId);
        if(rs!=null && rs.moveToFirst()){

            String doc_nam = rs.getString(rs.getColumnIndex(DBHelper.SUBMISSION_COLUMN_DOC_NAME));
            String statu = rs.getString(rs.getColumnIndex(DBHelper.SUBMISSION_COLUMN_STATUS));

            if(sharedPreferences.getString(LoginActivity.LoggedProfileId,"").equals(LoginActivity.doctor) &&
                    statu.equals(LoginActivity.doctorCheckStatus)){
                mydb.updateSubmissionStatus(ValueSubmissionId,LoginActivity.doctorRejectedStatus);
                Toast.makeText(getApplicationContext(), "Wniosek "+doc_nam+" został odrzucony!",
                        Toast.LENGTH_LONG).show();
            }else if(sharedPreferences.getString(LoginActivity.LoggedProfileId,"").equals(LoginActivity.pharmacist) &&
                    statu.equals(LoginActivity.pharmacistCheckStatus)){
                mydb.updateSubmissionStatus(ValueSubmissionId,LoginActivity.pharmacistRejectedStatus);
                Toast.makeText(getApplicationContext(), "Wniosek "+doc_nam+" został odrzucony!",
                        Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), "Brak odpowiednich uprawnień !!!",
                        Toast.LENGTH_LONG).show();
            }

            if (!rs.isClosed())  {
                rs.close();
            }

        }
        finish();
    }

}