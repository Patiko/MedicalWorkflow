package com.example.lenovo.medicalworkflow;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.lenovo.medicalworkflow.Database.DBHelper;

/**
 * Created by Lenovo on 14.06.2017.
 */

public class CustomAdapterMedicines extends CursorAdapter {
    TextView txtId,txtFirstName,txtLastName, txtPesel;
    private LayoutInflater mInflater;

    public CustomAdapterMedicines(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View   view    =    mInflater.inflate(R.layout.medicines_item, parent, false);
        CustomAdapterMedicines.ViewHolder holder  =   new CustomAdapterMedicines.ViewHolder();
        //   holder.txtId    =   (TextView)  view.findViewById(R.id.txtId);
        holder.txtName    =   (TextView)  view.findViewById(R.id.txtName);
        holder.txtType   =   (TextView)  view.findViewById(R.id.txtType);
        holder.txtRefundable   =   (TextView)  view.findViewById(R.id.txtRefundable);
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
        holder.txtName.setText("Nazwa leku: "+cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_NAME)));
        holder.txtType.setText("Typ: "+cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_TYPE)));
        holder.txtRefundable.setText("R: "+cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_REFUNDABLE)));

    }

    static class ViewHolder {
        //   TextView txtId;
        TextView txtName;
        TextView txtType;
        TextView txtRefundable;
    }
}
