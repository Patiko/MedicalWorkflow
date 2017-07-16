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
 * Created by Lenovo on 14.06.2017.
 */

public class CustomAdapterDevices extends CursorAdapter {
    TextView txtDeviceId;
    TextView txtDeviceQuantity;
    TextView txtDeviceName;
    TextView txtDeviceInstructions;

    private LayoutInflater mInflater;

    public CustomAdapterDevices(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View   view    =    mInflater.inflate(R.layout.device_id, parent, false);
        CustomAdapterDevices.ViewHolder holder  =   new CustomAdapterDevices.ViewHolder();
        //   holder.txtId    =   (TextView)  view.findViewById(R.id.txtId);
        holder.txtDeviceId    =   (TextView)  view.findViewById(R.id.editDeviceId);
        holder.txtDeviceQuantity   =   (TextView)  view.findViewById(R.id.edit_device_quantity);
        holder.txtDeviceName   =   (TextView)  view.findViewById(R.id.edit_device_name);
        holder.txtDeviceInstructions   =   (TextView)  view.findViewById(R.id.edit_device_instructions);
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

        CustomAdapterDevices.ViewHolder holder  =   (CustomAdapterDevices.ViewHolder)    view.getTag();
        // holder.txtId.setText(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_ID)));
        holder.txtDeviceId.setText(cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_DEVICE_ID)));
        holder.txtDeviceQuantity.setText(cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_DEVICE_QUANTITY)));
        holder.txtDeviceName.setText(cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_DEVICE_NAME)));
        holder.txtDeviceInstructions.setText(cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_DEVICE_INSTRUCTIONS)));

    }

    static class ViewHolder {
        //   TextView txtId;
        TextView txtDeviceId;
        TextView txtDeviceQuantity;
        TextView txtDeviceName;
        TextView txtDeviceInstructions;
    }
}
