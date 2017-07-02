package com.example.lenovo.medicalworkflow.ActivitiesClass.Registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medicalworkflow.Database.DBHelper;
import com.example.lenovo.medicalworkflow.LoginActivity;
import com.example.lenovo.medicalworkflow.R;

/**
 * Created by Lenovo on 07.05.2017.
 */

public class RegisterUser extends Activity {

    private DBHelper mydb ;
    Button b1,b2,b3;

    Integer id_To_Update=0;
    TextView first_name;
    TextView last_name;
    TextView pesel;
    TextView login;
    TextView password;
   // TextView profil;
    Spinner spinner_profil;
    String profil;
    TextView insured;
    TextView insuredTV;

    TextView birthdate;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        first_name = (TextView) findViewById(R.id.firstNameET);
        last_name = (TextView) findViewById(R.id.lastNameET);
        pesel = (TextView)findViewById(R.id.peselET);
        login = (TextView)findViewById(R.id.loginET);
        password = (TextView)findViewById(R.id.passwordET);
        //profil = (TextView)findViewById(R.id.profilET);
        spinner_profil = (Spinner) findViewById(R.id.profilSpin);
        insured = (TextView)findViewById(R.id.insuredET);
        insuredTV = (TextView)findViewById(R.id.insuredTV);
        //birthdate="";



        mydb=new DBHelper(this);

        ArrayAdapter<CharSequence> adapter_profil = ArrayAdapter.createFromResource(this.getApplicationContext(),
                R.array.profil_array,android.R.layout.simple_spinner_item);

        //adapter_profil.setDropDownViewResourc(android.R.layout.simple_spinner_dropdown_item);
        spinner_profil.setAdapter(adapter_profil);
        spinner_profil.setOnItemSelectedListener(new Listener_Of_Selecting_Profil_Spinner());
/*        if(Listener_Of_Selecting_Profil_Spinner.profilType.equals("Pacjent")){
            insured.setVisibility(View.VISIBLE);
            insuredTV.setVisibility(View.VISIBLE);

        }else {
            insured.setVisibility(View.INVISIBLE);
            insuredTV.setVisibility(View.INVISIBLE);
        }*/
/*        b1=(Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterUser.this, DisplayMedicine.class);
                startActivity(i);
            }
        });*/

    }

    public static class Listener_Of_Selecting_Profil_Spinner implements AdapterView.OnItemSelectedListener
    {
        static String profilType;
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)
        {
            // By using this you can get the position of patients_item which you
            // have selected from the dropdown

            profilType = (parent.getItemAtPosition(pos)).toString();

        }
        public void onNothingSelected(AdapterView<?> parent)
        {
            // Do nothing.
        }
    };

    public void runInsertUser(View view) {
        boolean existLogin=mydb.getExistLogin(login.getText().toString());
        boolean existPesel=mydb.getExistPesel(pesel.getText().toString());
        profil =Listener_Of_Selecting_Profil_Spinner.profilType;
        if (!first_name.getText().toString().equals("") && !last_name.getText().toString().equals("") && !pesel.getText().toString().equals("") && pesel.getText().toString().trim()
                .length() == 11 && !login.getText().toString().equals("") && !password.getText().toString().equals("")) {
            if(!existLogin)
            {
                if(!existPesel){
                    if (mydb.insertUser(first_name.getText().toString(),last_name.getText().toString(),pesel.getText().toString(),login.getText().toString(),
                            password.getText().toString(), profil,insured.getText().toString(),"","", "", "","","","","","","","","")) {


                        Toast.makeText(getApplicationContext(), "Użytkownik utworzony",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Użytkownik nie został utworzony",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Użytkownik o podanym nr pesel istnieje",
                            Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(getApplicationContext(), "Użytkownik o podanym loginie istnieje",
                        Toast.LENGTH_LONG).show();
            }

        } else if(first_name.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Proszę podać Imię",
                    Toast.LENGTH_LONG).show();
        } else if(last_name.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Proszę podać Nazwisko",
                    Toast.LENGTH_LONG).show();
        } else if(pesel.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Proszę podać PESEL",
                    Toast.LENGTH_LONG).show();
        }else if(!pesel.getText().toString().equals("") && pesel.getText().toString().trim()
                .length() != 11) {
            Toast.makeText(getApplicationContext(), "Proszę podać 11-cyfrowy PESEL",
                    Toast.LENGTH_LONG).show();
        } else if(login.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Proszę podać login",
                    Toast.LENGTH_LONG).show();
        } else if(password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Proszę podać hasło",
                    Toast.LENGTH_LONG).show();
        }


    }


    public void runDeleteUser(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            mydb.deleteUser(id_To_Update);
            Toast.makeText(getApplicationContext(), "Użytkownik usunięty", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);

        }
    }

}
