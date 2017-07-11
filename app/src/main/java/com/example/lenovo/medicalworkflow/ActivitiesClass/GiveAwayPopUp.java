package com.example.lenovo.medicalworkflow.ActivitiesClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 11.07.2017.
 */

public class GiveAwayPopUp extends Activity {
    Button giveAwayBtn;
    SharedPreferences sharedPreferences;
    private DBHelper mydb;
    String accepted="ZAAKCEPTOWANA";
    String rejected="ODRZUCONA";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.give_away_popup);
        giveAwayBtn= (Button) findViewById(R.id.giveAwayButton);
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
    public void setGiveAwayStatus(View view){

        int ValueSubmissionId = sharedPreferences.getInt(LoginActivity.UsedSubmissionId,0);
        //  mydb.updateSubmissionStatus(ValueSubmissionId,accepted);
        Cursor rs = mydb.getSubmissionData(ValueSubmissionId);
        if(rs!=null && rs.moveToFirst()){

            String doc_nam = rs.getString(rs.getColumnIndex(DBHelper.SUBMISSION_COLUMN_DOC_NAME));
            String statu = rs.getString(rs.getColumnIndex(DBHelper.SUBMISSION_COLUMN_STATUS));
            mydb.updateSubmissionStatus(ValueSubmissionId,LoginActivity.successStatus);
            Toast.makeText(getApplicationContext(), "Wniosek "+doc_nam+" został wydany!",
                    Toast.LENGTH_LONG).show();


            if (!rs.isClosed())  {
                rs.close();
            }
        }

        finish();
    }



}