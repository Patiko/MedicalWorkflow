package com.example.lenovo.medicalworkflow.CustomAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.lenovo.medicalworkflow.ActivitiesClass.DisplaySubmission;
import com.example.lenovo.medicalworkflow.ActivitiesClass.LoginActivity;
import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 23.06.2017.
 */

public class CustomAdapterSubmissions  extends CursorAdapter {
    TextView txtId,txtFirstName,txtLastName, txtPesel;
    private LayoutInflater mInflater;

    public CustomAdapterSubmissions(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View   view    =    mInflater.inflate(R.layout.submission_item, parent, false);
        ViewHolder holder  =   new ViewHolder();
        //   holder.txtId    =   (TextView)  view.findViewById(R.id.txtId);
        holder.txtName    =   (TextView)  view.findViewById(R.id.txtName);
        holder.txtCreatedAt = (TextView) view.findViewById(R.id.txtCreatedAT);
        holder.txtStatus = (TextView) view.findViewById(R.id.txtStatus);
       /* holder.txtFirstNamePat    =   (TextView)  view.findViewById(R.id.txtFirstNamePat);
        holder.txtLastNamePat   =   (TextView)  view.findViewById(R.id.txtLastNamePat);
        holder.txtPesel   =   (TextView)  view.findViewById(R.id.txtPesel);
        holder.txtFirstNameDoc    =   (TextView)  view.findViewById(R.id.txtFirstNameDoc);
        holder.txtLastNameDoc   =   (TextView)  view.findViewById(R.id.txtLastNameDoc);*/

        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //If you want to have zebra lines color effect uncomment below code
        /*if(cursor.getPosition()%2==1) {
             view.setBackgroundResource(R.drawable.item_list_backgroundcolor);
        } else {
            view.setBackgroundResource(R.drawable.item_list_backgroundcolor2);
        }*/





        ViewHolder holder  =   (ViewHolder)    view.getTag();
        // holder.txtId.setText(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_ID)));
        holder.txtName.setText(cursor.getString(cursor.getColumnIndex(DBHelper.SUBMISSION_COLUMN_DOC_NAME)));
        holder.txtCreatedAt.setText(cursor.getString(cursor.getColumnIndex(DBHelper.SUBMISSION_COLUMN_CREATED_AT)));
        String statu = cursor.getString(cursor.getColumnIndex(DBHelper.SUBMISSION_COLUMN_STATUS));
        holder.txtStatus.setText(statu);
        if((statu.equals(LoginActivity.doctorRejectedStatus) || statu.equals(LoginActivity.pharmacistRejectedStatus) || statu.equals(LoginActivity.expiredStatus) )){
            holder.txtStatus.setPaintFlags(holder.txtStatus.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.txtStatus.setPaintFlags(holder.txtStatus.getPaintFlags() &~ Paint.STRIKE_THRU_TEXT_FLAG);
        }

       /* if((statu.equals(LoginActivity.doctorRejectedStatus) || statu.equals(LoginActivity.pharmacistRejectedStatus))){
            holder.txtStatus.setPaintFlags(holder.txtStatus.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }*/

/*        holder.txtFirstNameDoc.setText(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_FIRST_NAME)));
        holder.txtLastNameDoc.setText(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_LAST_NAME)));
        holder.txtFirstNamePat.setText(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_FIRST_NAME)));
        holder.txtLastNamePat.setText(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_LAST_NAME)));
        holder.txtPesel.setText(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_PESEL)));*/

    }


    static class ViewHolder {
        //   TextView txtId;
        TextView txtName;
        TextView txtCreatedAt;
        TextView txtStatus;
        TextView txtFirstNamePat;
        TextView txtLastNamePat;
        TextView txtFirstNameDoc;
        TextView txtLastNameDoc;
        TextView txtPesel;
    }




}
