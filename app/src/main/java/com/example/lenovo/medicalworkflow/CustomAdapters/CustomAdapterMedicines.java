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

public class CustomAdapterMedicines extends CursorAdapter {

    private LayoutInflater mInflater;

    public CustomAdapterMedicines(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View   view    =    mInflater.inflate(R.layout.medicines_item, parent, false);
        CustomAdapterMedicines.ViewHolder holder  =   new CustomAdapterMedicines.ViewHolder();

        holder.txtName    =   (TextView)  view.findViewById(R.id.txtName);
        holder.txtType   =   (TextView)  view.findViewById(R.id.txtType);
        holder.txtDosage   =   (TextView)  view.findViewById(R.id.txtDosage);
        holder.txtQuantity   =   (TextView)  view.findViewById(R.id.txtQuantity);
        holder.txtInjection   =   (TextView)  view.findViewById(R.id.injectionTV);
        holder.txtRefundable   =   (TextView)  view.findViewById(R.id.refundableTV);
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

        CustomAdapterMedicines.ViewHolder holder  =   (CustomAdapterMedicines.ViewHolder)    view.getTag();
        // holder.txtId.setText(cursor.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_ID)));
        holder.txtName.setText(cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_NAME)));
        holder.txtType.setText(cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_TYPE)));
        holder.txtDosage.setText(cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_DOSAGE)));
        holder.txtQuantity.setText(cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_QUANTITY)));
        holder.txtRefundable.setText(cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_REFUNDABLE)));
        holder.txtInjection.setText(cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_INJECTION_WAY)));

    }

    static class ViewHolder {
        //   TextView txtId;
        TextView txtName;
        TextView txtType;
        TextView txtDosage;
        TextView txtQuantity;
        TextView txtRefundable;
        TextView txtInjection;

    }
}
