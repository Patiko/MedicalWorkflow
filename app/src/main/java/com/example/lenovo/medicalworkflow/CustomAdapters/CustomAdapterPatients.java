package com.example.lenovo.medicalworkflow.CustomAdapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 22.05.2017.
 */

public class CustomAdapterPatients  extends CursorAdapter {
    TextView txtId,txtFirstName,txtLastName, txtPesel;
    private LayoutInflater mInflater;

    public CustomAdapterPatients(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View   view    =    mInflater.inflate(R.layout.patients_item, parent, false);
        ViewHolder holder  =   new ViewHolder();
     //   holder.txtId    =   (TextView)  view.findViewById(R.id.txtId);
        holder.txtFirstName    =   (TextView)  view.findViewById(R.id.txtFirstName);
        holder.txtLastName   =   (TextView)  view.findViewById(R.id.txtLastName);
        holder.txtPesel   =   (TextView)  view.findViewById(R.id.txtPesel);
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
        holder.txtFirstName.setText(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_FIRST_NAME)));
        holder.txtLastName.setText(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_LAST_NAME)));
        holder.txtPesel.setText("Pesel: "+cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_PESEL)));

    }

    static class ViewHolder {
     //   TextView txtId;
        TextView txtFirstName;
        TextView txtLastName;
        TextView txtPesel;
    }
}
