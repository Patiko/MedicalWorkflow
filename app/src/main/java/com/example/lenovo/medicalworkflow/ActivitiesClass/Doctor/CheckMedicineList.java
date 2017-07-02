package com.example.lenovo.medicalworkflow.ActivitiesClass.Doctor;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import com.example.lenovo.medicalworkflow.ActivitiesClass.AddSubmission;
import com.example.lenovo.medicalworkflow.ActivitiesClass.NFZWorker.NfzWorkerMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Patient.PatientMainScreen;
import com.example.lenovo.medicalworkflow.ActivitiesClass.Pharmacist.PharmacistMainScreen;
import com.example.lenovo.medicalworkflow.CustomAdapters.CustomAdapterMedicines;
import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.ActivitiesClass.LoginActivity;
import com.example.lenovo.medicalworkflow.R;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Lenovo on 07.05.2017.
 */

public class CheckMedicineList extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;
    ImageView img;

    private CustomAdapterMedicines customAdapterMedicines;
    Cursor cursor;
    ListView listView;
    SharedPreferences sharedPreferences;

    Button b1,b2;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_medicine_list);
        b1=(Button)findViewById(R.id.addMedicineB);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //    int Value = sharedpreferences.getInt(LoginActivity.UserId,0);
                editor.remove(LoginActivity.UsedMedicineId);
                editor.apply();
                Intent i = new Intent(CheckMedicineList.this, DisplayMedicine.class);
                startActivity(i);
            }
        });

        b2=(Button)findViewById(R.id.addSubmissionB);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CheckMedicineList.this, AddSubmission.class);
                startActivity(i);
            }
        });


        mydb = new DBHelper(this);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        Integer creator_id = sharedPreferences.getInt(LoginActivity.UserId,0);
        cursor = mydb.getAllMedicinesByCreator(creator_id);
        customAdapterMedicines = new CustomAdapterMedicines(CheckMedicineList.this, cursor, 0);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(customAdapterMedicines);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = cursor.getInt(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_ID));
                //          boolean visibility=true;
                if(id_To_Search!=0){
                    //     Bundle dataBundle = new Bundle();
                    //   dataBundle.putInt("id", id_To_Search);
                    //  dataBundle.putBoolean("doctor_visibility", visibility);
                    sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //    int Value = sharedpreferences.getInt(LoginActivity.UserId,0);
                    editor.putInt(LoginActivity.UsedMedicineId,id_To_Search);
                    editor.apply();

               /* Button editB = (Button)findViewById(R.id.button2);
                editB.setVisibility(View.INVISIBLE);
                Button deleteB = (Button)findViewById(R.id.button3);
                deleteB.setVisibility(View.INVISIBLE);*/
                    Intent intent = new Intent(getApplicationContext(), DisplayMedicine.class);
                    //        intent.putExtras(dataBundle);
                    startActivity(intent);
                    // arrayAdapter.notifyDataSetChanged();
                }
            }
        });



   /*
     //   ArrayList array_list = mydb.getAllMedicinesByCreator(creator_id);
       // String pesel = sharedpreferences.getString(LoginActivity.PeselId,"");
        //ArrayList array_list = mydb.getAllMedicinesByUser(pesel);
       // ArrayList array_list = mydb.getAllMedicines();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2+1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

               *//* Button editB = (Button)findViewById(R.id.button2);
                editB.setVisibility(View.INVISIBLE);
                Button deleteB = (Button)findViewById(R.id.button3);
                deleteB.setVisibility(View.INVISIBLE);*//*

                Intent intent = new Intent(getApplicationContext(),DisplayMedicine.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
               // arrayAdapter.notifyDataSetChanged();

            }
        });
*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.medicine_menu, menu);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String name) {
                    //  Log.d(TAG, "onQueryTextSubmit ");
                    sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    Integer creator_id = sharedPreferences.getInt(LoginActivity.UserId,0);
                    cursor=mydb.searchMedicines(name,creator_id);
                    if (cursor==null){
                        Toast.makeText(CheckMedicineList.this,"Nie znaleziono!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(CheckMedicineList.this, "Znaleziono "+cursor.getCount() + " rekordów!",Toast.LENGTH_LONG).show();
                    }
                    customAdapterMedicines.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String name) {
                    //   Log.d(TAG, "onQueryTextChange ");
                    sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    Integer creator_id = sharedPreferences.getInt(LoginActivity.UserId,0);
                    cursor=mydb.searchMedicines(name,creator_id);
                    if (cursor!=null){
                        customAdapterMedicines.swapCursor(cursor);
                    }
                    return false;
                }

            });

        }

        return true;

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


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem patients_item){
        super.onOptionsItemSelected(patients_item);

        switch(patients_item.getItemId()) {
            case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DisplayMedicine.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(patients_item);
        }
    }*/

/*    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }*/
}
