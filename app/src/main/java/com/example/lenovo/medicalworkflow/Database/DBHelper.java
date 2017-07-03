package com.example.lenovo.medicalworkflow.Database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Lenovo on 07.05.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName2.db";

    public static final String MEDICINE_TABLE_NAME = "MEDICINE";
    public static final String MEDICINE_COLUMN_ID = "_id";
    public static final String MEDICINE_COLUMN_NAME = "name";
    public static final String MEDICINE_COLUMN_TYPE = "type";
    public static final String MEDICINE_COLUMN_QUANTITY = "quantity";
    public static final String MEDICINE_COLUMN_REFUNDABLE = "refundable";
    public static final String MEDICINE_COLUMN_INJECTION_WAY = "injection_way";
    public static final String MEDICINE_COLUMN_CREATOR_ID = "creator_id";
    public static final String MEDICINE_COLUMN_SUBMISSION_ID = "submission_id";


    public static final String USER_TABLE_NAME="USER";
    public static final String USER_COLUMN_ID = "_id";
    public static final String USER_COLUMN_FIRST_NAME = "first_name";
    public static final String USER_COLUMN_LAST_NAME = "last_name";
    public static final String USER_COLUMN_PESEL = "pesel";
    public static final String USER_COLUMN_LOGIN = "login";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_PROFILE = "profile";
    public static final String USER_COLUMN_INSURED = "insured";

    public static final String USER_COLUMN_BIRTHDATE = "birthdate";
    public static final String USER_COLUMN_PHONENUMBER = "phoneNumber";
    public static final String USER_COLUMN_STREET = "street";
    public static final String USER_COLUMN_NRDOM = "nr_dom";
    public static final String USER_COLUMN_NRMIES = "nr_mies";
    public static final String USER_COLUMN_POST_CODE = "post_code";
    public static final String USER_COLUMN_CITY = "city";
    public static final String USER_COLUMN_NPWZ = "npwz";
    public static final String USER_COLUMN_SPECIALISATIONS = "specialisations";
    public static final String USER_COLUMN_WORK_NAME = "work_name";
    public static final String USER_COLUMN_REGISTRY_ENTRY = "registry_entry";
    public static final String USER_COLUMN_REGON = "regon";


    public static final String SUBMISSION_TABLE_NAME = "SUBMISSION";
    public static final String SUBMISSION_COLUMN_ID = "_id";
    public static final String SUBMISSION_COLUMN_DOC_NAME = "doc_name";
    public static final String SUBMISSION_COLUMN_MEDICINE_ID = "medicine_id";
    public static final String SUBMISSION_COLUMN_DOCTOR_ID = "doctor_id";
    public static final String SUBMISSION_COLUMN_USER_ID = "user_id";
    public static final String SUBMISSION_COLUMN_DOC_TYPE = "doc_type";
    public static final String SUBMISSION_COLUMN_CREATED_AT = "created_at";
    public static final String SUBMISSION_COLUMN_STATUS = "status";


    public static final String STATE_TABLE_NAME= "STATE";
    public static final String STATE_COLUMN_ID = "_id";
    public static final String STATE_COLUMN_NAME = "state_name";
    public static final String STATE_COLUMN_KEY = "state_key";
    public static final String STATE_COLUMN_PAYER_PLACE_ID = "payer_place_id";
    public static final String STATE_COLUMN_PAYER_PLACE_NAME= "payer_place_name";




    public static final String CREATE_TABLE_SUBMISSION = "CREATE TABLE " + SUBMISSION_TABLE_NAME
            + "(" + SUBMISSION_COLUMN_ID + " INTEGER PRIMARY KEY, " + SUBMISSION_COLUMN_MEDICINE_ID + " INTEGER, "+
            SUBMISSION_COLUMN_USER_ID + " INTEGER, " +SUBMISSION_COLUMN_DOC_NAME + " TEXT," + SUBMISSION_COLUMN_DOC_TYPE + "TEXT, "
            + SUBMISSION_COLUMN_CREATED_AT +" INTEGER"+
            ","+SUBMISSION_COLUMN_DOCTOR_ID + " INTEGER "+
    ")";

//+SUBMISSION_COLUMN_DOCTOR_ID + " INTEGER,"

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + MEDICINE_TABLE_NAME + "(" +
                        "_id integer primary key, name text,type text,quantity text, refundable text,injection_way text, creator_id integer, submission_id integer)"
        );

        db.execSQL(
                "CREATE TABLE "+USER_TABLE_NAME+"(_id integer primary key, first_name text,last_name text,pesel text, login text, password text,  profile text, insured text, birthdate text," +
                        "phoneNumber text, street text, nr_dom text, nr_mies text, post_code text, city text, npwz text, specialisations text, work_name text,registry_entry text, regon text," +
                        " payer_place text, loginCount integer)"
        );

        db.execSQL(
                "CREATE TABLE " + SUBMISSION_TABLE_NAME+"(_id integer primary key, user_id integer,doctor_id integer, doc_name text, doc_type text, created_at datetime, status text)"
        );

        //  db.execSQL(CREATE_TABLE_SUBMISSION);

        db.execSQL(
                "CREATE TABLE " + STATE_TABLE_NAME + "(" +
                        "_id integer primary key, state_name text,state_key text,payer_place_id text, payer_place_name text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ MEDICINE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ SUBMISSION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ STATE_TABLE_NAME);
        onCreate(db);
    }

    //STATE TABLE//

    public boolean insertState(String state_name, String state_key, String payer_place_id, String payer_place_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("state_name", state_name);
        contentValues.put("state_key", state_key);
        contentValues.put("payer_place_id", payer_place_id);
        contentValues.put("payer_place_name", payer_place_name);
        db.insert(STATE_TABLE_NAME, null, contentValues);
        return true;
    }
    public String getPayerPlace(String post_code) {
        ArrayList<String> patients_list = new ArrayList<String>();
        String post_code_shorten = post_code.substring(0, 2);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + STATE_TABLE_NAME + " where state_key like '" + post_code_shorten + "%'", null);
        //  Cursor res =db.query(USER_TABLE_NAME,null," profile=? and pesel=?",new String[]{profile_patient,pesel},null,null,null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "";
        }
        cursor.moveToFirst();
        String payerPlace = cursor.getString(cursor.getColumnIndex(DBHelper.STATE_COLUMN_PAYER_PLACE_NAME)) + " " + cursor.getString(cursor.getColumnIndex(DBHelper.STATE_COLUMN_PAYER_PLACE_ID));
        cursor.close();
        return payerPlace;
    }

/*    public void savePayer(){

        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor cursor2 = db2.rawQuery("UPDATE INTO "+USER_TABLE_NAME+ " where profile='"+profile_patient+"' and pesel like '"+post_code+"%'", null);
        //  Cursor res =db.query(USER_TABLE_NAME,null," profile=? and pesel=?",new String[]{profile_patient,pesel},null,null,null);
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor2;
    }*/


    //MEDICINES TABLE //
    public boolean insertMedicine(String name, String type, String quantity, String refundable, String injection_way,Integer creator_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("type", type);
        contentValues.put("quantity", quantity);
        contentValues.put("refundable", refundable);
        contentValues.put("injection_way", injection_way);
        contentValues.put("creator_id",creator_id );
      //  contentValues.put("submission_id",0);
        db.insert(MEDICINE_TABLE_NAME, null, contentValues);
        return true;

      /*  SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT * FROM MEDICINE WHERE name='"+name+"' AND type='"+type+"' AND quantity='"+quantity+"' AND creator_id="+creator_id ,null);
        if (cursor.getCount() < 1) // Medicine Not Exist
        {
            cursor.close();
            return 0;
        }
        cursor.moveToFirst();
        Integer medicine_id = cursor.getInt(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_ID));
        cursor.close();


        SQLiteDatabase db3 = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("medicine_id", medicine_id);
        db3.insert(SUBMISSION_TABLE_NAME, null, contentValues2);

        SQLiteDatabase db4 = this.getReadableDatabase();
        Cursor cursor2 = db4.rawQuery("SELECT * FROM SUBMISSION WHERE medicine_id="+medicine_id ,null);
        if (cursor2.getCount() < 1) // Medicine Not Exist
        {
            cursor2.close();
            return 0;
        }
        cursor2.moveToFirst();
        Integer submission_id = cursor2.getInt(cursor.getColumnIndex(DBHelper.MEDICINE_COLUMN_SUBMISSION_ID));
        cursor2.close();


        return submission_id;*/
    }

    public Integer getSubmissionIdByValues(Integer patient_id, String name, String type ){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SUBMISSION WHERE user_id ="+patient_id+" AND doc_name='"+name+"' AND doc_type='"+type+"'",null);
        if (cursor.getCount() < 1) // Submission Not Exist
        {
            cursor.close();
            return 0;
        }
        cursor.moveToFirst();
        Integer submission_id = cursor.getInt(cursor.getColumnIndex(DBHelper.SUBMISSION_COLUMN_ID));
        cursor.close();
        return submission_id;
    }




    public Cursor getMedicineData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+ MEDICINE_TABLE_NAME +" where _id="+id+"", null );
        return res;
    }


    public int numberOfRowsMedicine(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USER_TABLE_NAME);
        return numRows;
    }

    public boolean updateMedicine(Integer id, String name, String type, String quantity, String refundable, String injection_way, Integer creator_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("type", type);
        contentValues.put("quantity", quantity);
        contentValues.put("refundable", refundable);
        contentValues.put("injection_way", injection_way);
        contentValues.put("creator_id",creator_id);
      //  contentValues.put("submission_id",submission_id);
        db.update(MEDICINE_TABLE_NAME, contentValues, "_id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public boolean updateMedicineBySubmission(Integer id, Integer submission_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("submission_id",submission_id);
        //  contentValues.put("submission_id",submission_id);
        db.update(MEDICINE_TABLE_NAME, contentValues, "_id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }


    public Integer deleteMedicine(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
         return db.delete(MEDICINE_TABLE_NAME, "_id = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllMedicinesByUser() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+ MEDICINE_TABLE_NAME + " WHERE _id = ", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
           // if(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)).equals("")==false)
            array_list.add(res.getString(res.getColumnIndex(MEDICINE_COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }



    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd.MM.yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    //SUBMISSION TABLE //
    public boolean insertSubmission( Integer user_id ,Integer doctor_id, String doc_name, String doc_type ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
     //   contentValues.put(SUBMISSION_COLUMN_MEDICINE_ID, medicine_id);
        contentValues.put(SUBMISSION_COLUMN_USER_ID, user_id);
        contentValues.put(SUBMISSION_COLUMN_DOCTOR_ID, doctor_id);
        contentValues.put(SUBMISSION_COLUMN_DOC_NAME, doc_name);
        contentValues.put(SUBMISSION_COLUMN_DOC_TYPE, doc_type);
        contentValues.put(SUBMISSION_COLUMN_CREATED_AT, getDateTime());
        contentValues.put(SUBMISSION_COLUMN_STATUS, "NOWA");
        db.insert(SUBMISSION_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateSubmission(Integer id,Integer user_id, Integer doctor_id, String name, String doc_type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SUBMISSION_COLUMN_USER_ID, user_id);
        //contentValues.put(SUBMISSION_COLUMN_MEDICINE_ID, medicine_id);
        contentValues.put(SUBMISSION_COLUMN_DOCTOR_ID, doctor_id);
        contentValues.put(SUBMISSION_COLUMN_DOC_NAME, name);
        contentValues.put(SUBMISSION_COLUMN_DOC_TYPE, doc_type);
        db.update(SUBMISSION_TABLE_NAME, contentValues, SUBMISSION_COLUMN_ID +" = ? ", new String[] { String.valueOf(id) } );
        return true;
    }

    public boolean updateSubmissionStatus(Integer id,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SUBMISSION_COLUMN_STATUS, status);
        db.update(SUBMISSION_TABLE_NAME, contentValues, SUBMISSION_COLUMN_ID +" = ? ", new String[] { String.valueOf(id) } );
        return true;
    }

    public Cursor getSubmissionData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+ SUBMISSION_TABLE_NAME +" where "+SUBMISSION_COLUMN_ID+"="+id+"", null );
        return res;
    }

    public Cursor getAllSubmissionData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM SUBMISSION s, USER u, MEDICINE m WHERE s.user_id=u._id and s.medicine_id=m._id and s.doctor_id=u._id AND "+"s."+SUBMISSION_COLUMN_ID+"="+id+"", null );
        return res;
    }

    public Cursor getPatientDetailsBySubmission(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM SUBMISSION s join USER u on s.user_id=u._id WHERE "+"s."+SUBMISSION_COLUMN_ID+"="+id+"", null );
        return res;
    }
    public Cursor getDoctorDetailsBySubmission(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM SUBMISSION s join USER u on s.doctor_id=u._id WHERE "+"s."+SUBMISSION_COLUMN_ID+"="+id+"", null );
        return res;
    }

    public Cursor getAllMedicinesBySubmission(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query(MEDICINE_TABLE_NAME,null," submission_id=?",new String[]{Integer.toString(id)},null,null,null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Cursor getMedicinesDetailsByDoctorUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+MEDICINE_TABLE_NAME+" WHERE "+MEDICINE_COLUMN_CREATOR_ID+"="+id+"", null );
        if (res == null) {
            return null;
        } else if (!res.moveToFirst()) {
            res.close();
            return null;
        }

        return res;
    }

    public Cursor getAllMedicinesByCreator(Integer creator_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query(MEDICINE_TABLE_NAME,null," creator_id=? AND submission_id is null",new String[]{Integer.toString(creator_id)},null,null,null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }


    public int numberOfRowsSubmission(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USER_TABLE_NAME);
        return numRows;
    }



    public Integer deleteSubmission(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SUBMISSION_TABLE_NAME, "_id = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList<Integer> getAllMedicinesByUser(Integer creator_id) {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+ MEDICINE_TABLE_NAME + " WHERE creator_id = "+creator_id+" AND submission_id is null", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            array_list.add(res.getInt(res.getColumnIndex(MEDICINE_COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllSubmissionDetails(int id) {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+ MEDICINE_TABLE_NAME + " m, "
                + USER_TABLE_NAME +" u, " + SUBMISSION_TABLE_NAME + " s, WHERE u."
                +SUBMISSION_COLUMN_ID + " ='"+id+"' AND u."+USER_COLUMN_ID+" = s."
                + SUBMISSION_COLUMN_USER_ID + " AND m."+MEDICINE_COLUMN_ID+" = s."
                +SUBMISSION_COLUMN_MEDICINE_ID + " AND u."+USER_COLUMN_ID+" = s."
                +SUBMISSION_COLUMN_DOCTOR_ID, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            // if(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)).equals("")==false)
            array_list.add(res.getString(res.getColumnIndex(SUBMISSION_COLUMN_DOC_NAME)));
            res.moveToNext();
        }
        return array_list;
    }



    public Cursor getAllSubmissionByType(String doc_type) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query(SUBMISSION_TABLE_NAME,null," doc_type=? ",new String[]{doc_type},null,null,null);
        cursor.moveToFirst();
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Cursor getAllSubmissionByTypeAndPatient(String doc_type, Integer patient_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query(SUBMISSION_TABLE_NAME,null," doc_type=? AND user_id=?",new String[]{doc_type, Integer.toString(patient_id)},null,null,null);
        cursor.moveToFirst();
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }


    //USER TABLE //
    public boolean insertUser(String first_name, String last_name, String pesel, String login, String password, String profile, String insured,
                              String birthdate, String phoneNumber,String street, String nr_dom, String nr_mies, String post_code, String city,
                              String npwz, String specialisations, String work_name, String registry_entry, String regon) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", first_name);
        contentValues.put("last_name", last_name);
        contentValues.put("pesel", pesel);
        contentValues.put("login", login);
        contentValues.put("password", password);
        contentValues.put("profile", profile);
        contentValues.put("insured", insured);
        contentValues.put("birthdate", "");
        contentValues.put("phoneNumber", "");
        contentValues.put("street", "");
        contentValues.put("nr_dom", "");
        contentValues.put("nr_mies", "");
        contentValues.put("post_code", "");
        contentValues.put("city", "");
        contentValues.put("npwz", "");
        contentValues.put("specialisations", "");
        contentValues.put("work_name", "");
        contentValues.put("registry_entry", "");
        contentValues.put("regon", "");
        contentValues.put("payer_place","");
        contentValues.put("loginCount",0);

        db.insert(USER_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateUserLoginCount(String login, Integer loginCount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("loginCount",loginCount);
        db.update(USER_TABLE_NAME, contentValues, "login = ? ", new String[] { login } );
        return true;
    }

    public Integer getUserLoginCount(String login){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query("USER",null," login=?",new String[]{login},null,null,null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return 0;
        }
        cursor.moveToFirst();
        Integer loginCount= cursor.getInt(cursor.getColumnIndex("loginCount"));
        cursor.close();
        return loginCount;
    }


    public boolean isUserTableEmpty(){
        boolean empty=false;
        SQLiteDatabase db = this.getReadableDatabase();
        //SQLiteDatabase dbW = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM "+USER_TABLE_NAME, null);
        if (cur != null) {
            cur.moveToFirst();                       // Always one row returned.
            if (cur.getInt (0) == 0) {               // Zero count means empty table.
                empty=true;
            }
        }
        return empty;
    }

    public boolean insertDummyPacjentUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", "Jan1");
        contentValues.put("last_name", "Kowalski");
        contentValues.put("pesel", "62091505132");
        contentValues.put("login", "p");
        contentValues.put("password", "p");
        contentValues.put("profile", "Pacjent");
        contentValues.put("insured", "234/1992");
        contentValues.put("birthdate", "15.09.1962");
        contentValues.put("phoneNumber", "");
        contentValues.put("street", "Odkryta");
        contentValues.put("nr_dom", "41");
        contentValues.put("nr_mies", "12");
        contentValues.put("post_code", "03-134");
        contentValues.put("city", "Piaseczno");
        contentValues.put("npwz", "");
        contentValues.put("specialisations", "");
        contentValues.put("work_name", "");
        contentValues.put("registry_entry", "");
        contentValues.put("regon", "");
        contentValues.put("payer_place", "");
        db.insert(USER_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertDummyLekarzUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", "Piotr");
        contentValues.put("last_name", "Nowak");
        contentValues.put("pesel", "22222222222");
        contentValues.put("login", "l");
        contentValues.put("password", "l");
        contentValues.put("profile", "Lekarz");
        contentValues.put("insured", "");
        contentValues.put("birthdate", "");
        contentValues.put("phoneNumber", "221111123");
        contentValues.put("street", "Litewska");
        contentValues.put("nr_dom", "18");
        contentValues.put("nr_mies", "");
        contentValues.put("post_code", "00-950");
        contentValues.put("city", "Warszawa");
        contentValues.put("npwz", "7724513");
        contentValues.put("specialisations", "neurologia, radiologia");
        contentValues.put("work_name", "Praktyka lekarska Jan Nowak");
        contentValues.put("registry_entry", "000000046985-01");
        contentValues.put("regon", "123456789");
        contentValues.put("payer_place", "");
        db.insert(USER_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertDummyPracownikNFZUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", "Bartosz1");
        contentValues.put("last_name", "Nowak1");
        contentValues.put("pesel", "33333333333");
        contentValues.put("login", "n");
        contentValues.put("password", "n");
        contentValues.put("profile", "Pracownik NFZ");
        contentValues.put("insured", "");
        contentValues.put("birthdate", "");
        contentValues.put("phoneNumber", "");
        contentValues.put("street", "");
        contentValues.put("nr_dom", "");
        contentValues.put("nr_mies", "");
        contentValues.put("post_code", "");
        contentValues.put("city", "");
        contentValues.put("npwz", "");
        contentValues.put("specialisations", "");
        contentValues.put("work_name", "");
        contentValues.put("registry_entry", "");
        contentValues.put("regon", "");
        contentValues.put("payer_place", "");
        db.insert(USER_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertDummyFarmaceutaUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", "Katarzyna");
        contentValues.put("last_name", "Słowik1");
        contentValues.put("pesel", "44444444444");
        contentValues.put("login", "f");
        contentValues.put("password", "f");
        contentValues.put("profile", "Farmaceuta");
        contentValues.put("insured", "");
        contentValues.put("birthdate", "");
        contentValues.put("phoneNumber", "");
        contentValues.put("street", "");
        contentValues.put("nr_dom", "");
        contentValues.put("nr_mies", "");
        contentValues.put("post_code", "");
        contentValues.put("city", "");
        contentValues.put("npwz", "");
        contentValues.put("specialisations", "");
        contentValues.put("work_name", "");
        contentValues.put("registry_entry", "");
        contentValues.put("regon", "");
        contentValues.put("payer_place", "");
        db.insert(USER_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertDummyMedicine(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "Enarenal");
        contentValues.put("type", "Tabletki");
        contentValues.put("quantity", 30);
        contentValues.put("refundable", "Tak");
        contentValues.put("injection_way", "Do połknięcia");
        contentValues.put("creator_id",2);
        contentValues.put("submission_id",1);
        db.insert(MEDICINE_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertDummySubmission(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", 1);
        contentValues.put("doctor_id", 2);
        contentValues.put("doc_name", "Recepta1");
        contentValues.put("doc_type", "Recepta standardowa");
        contentValues.put("created_at", getDateTime());
        contentValues.put("status","NOWA");
        db.insert(SUBMISSION_TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean insertDummySubmission2(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", 1);
        contentValues.put("doctor_id", 2);
        contentValues.put("doc_name", "Recepta2");
        contentValues.put("doc_type", "Skierowanie na leczenie uzdrowiskowe");
        contentValues.put("created_at", getDateTime());
        contentValues.put("status","NOWA");
        db.insert(SUBMISSION_TABLE_NAME, null, contentValues);
        return true;
    }




    public Cursor getUserData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+USER_TABLE_NAME+" where _id="+id+"", null );
        return res;
    }


    public Cursor getUserFirstAndLastName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "SELECT * FROM "+USER_TABLE_NAME+" where _id="+id+"", null );
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }



    public int numberOfRowsUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USER_TABLE_NAME);
        return numRows;
    }

    public boolean updateUser(Integer id, String first_name, String last_name, String pesel, String login, String password, String profile, String insured, String birthdate,
                              String phoneNumber, String street, String nr_dom, String nr_mies, String post_code, String city,
                              String npwz, String specialisations, String work_name, String registry_entry, String regon, String payer_place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", first_name);
        contentValues.put("last_name", last_name);
        contentValues.put("pesel", pesel);
        contentValues.put("login", login);
        contentValues.put("password", password);
        contentValues.put("profile", profile);
        contentValues.put("insured", insured);
        contentValues.put("birthdate", birthdate);
        contentValues.put("phoneNumber", phoneNumber);
        contentValues.put("street", street);
        contentValues.put("nr_dom", nr_dom);
        contentValues.put("nr_mies", nr_mies);
        contentValues.put("post_code", post_code);
        contentValues.put("city", city);
        contentValues.put("npwz", npwz);
        contentValues.put("specialisations", specialisations);
        contentValues.put("work_name", work_name);
        contentValues.put("registry_entry", registry_entry);
        contentValues.put("regon", regon);
        contentValues.put("payer_place",payer_place);
        db.update(USER_TABLE_NAME, contentValues, "_id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteUser(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_TABLE_NAME, "_id = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllUsers() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+USER_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            // if(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)).equals("")==false)
            array_list.add(res.getString(res.getColumnIndex(USER_COLUMN_LOGIN)));
            res.moveToNext();
        }
        return array_list;
    }

    public Cursor getAllPatients() {
        ArrayList<String> patients_list = new ArrayList<String>();
        String profile_patient="Pacjent";
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query(USER_TABLE_NAME,null," profile=?",new String[]{profile_patient},null,null,null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }


 /*   public ArrayList<String> getAllPatients() {
        ArrayList<String> patients_list = new ArrayList<String>();
        String profile_patient="Pacjent";
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =db.query(USER_TABLE_NAME,null," profile=? ",new String[]{profile_patient},null,null,null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            // if(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)).equals("")==false)
            patients_list.add(res.getString(res.getColumnIndex(USER_COLUMN_PESEL)));
            res.moveToNext();
        }
        return patients_list;
    }*/

    public Cursor searchPatients(String pesel) {
        ArrayList<String> patients_list = new ArrayList<String>();
        String profile_patient="Pacjent";
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+USER_TABLE_NAME+ " where profile='"+profile_patient+"' and pesel like '"+pesel+"%'", null);
      //  Cursor res =db.query(USER_TABLE_NAME,null," profile=? and pesel=?",new String[]{profile_patient,pesel},null,null,null);


        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Cursor searchMedicines(String name, Integer creator_id) {
        ArrayList<String> patients_list = new ArrayList<String>();
        String profile_patient="Pacjent";
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+MEDICINE_TABLE_NAME+ " where creator_id='"+creator_id+"' and name like '"+name+"%'", null);
        //  Cursor res =db.query(USER_TABLE_NAME,null," profile=? and pesel=?",new String[]{profile_patient,pesel},null,null,null);


        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }


//    public ArrayList<String> searchPatients(String pesel) {
//        ArrayList<String> patients_list = new ArrayList<String>();
//        String profile_patient="Pacjent";
//        //hp = new HashMap();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM user where profile='"+profile_patient+"' and pesel like '"+pesel+"%'", null);
//        //  Cursor res =db.query(USER_TABLE_NAME,null," profile=? and pesel=?",new String[]{profile_patient,pesel},null,null,null);
//        res.moveToFirst();
//
//        while(!res.isAfterLast()){
//            // if(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)).equals("")==false)
//            patients_list.add(res.getString(res.getColumnIndex(USER_COLUMN_PESEL)));
//            res.moveToNext();
//        }
//        return patients_list;
//    }

    public String getSinglePasswordUsingLogin(String login){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query("USER",null," login=?",new String[]{login},null,null,null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("password"));
        cursor.close();
        return password;
    }

    public boolean getExistLogin(String login){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query("USER",null," login=?",new String[]{login},null,null,null);

        if(cursor.getCount()>0) // Login Exists
        {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean getExistPesel(String pesel){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query("USER",null," pesel=?",new String[]{pesel},null,null,null);

        if(cursor.getCount()>0) // Pesel Exists
        {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

/*    public boolean getExistPesel(String login){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query("USER",null," login=?",new String[]{login},null,null,null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return true;
        }
        cursor.moveToFirst();
        String pesel= cursor.getString(cursor.getColumnIndex("pesel"));
        cursor.close();

        Cursor cursor2 =db.query("USER",null," pesel=?",new String[]{pesel},null,null,null);
        if(cursor2.getCount()>0) // Login Exists
        {
            cursor2.close();
            return true;
        } else {
            cursor2.close();
            return false;
        }
    }*/


    public int getUserId(String login){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query("USER",null," login=?",new String[]{login},null,null,null);
        if (cursor.moveToFirst())
            return cursor.getInt(0);
        else
            return -1; // not found

    }


    public String getPeselId(String login){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query("USER",null," login=?",new String[]{login},null,null,null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String pesel= cursor.getString(cursor.getColumnIndex("pesel"));
        cursor.close();
        return pesel;
    }

    public String getProfileId(String login){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query("USER",null," login=?",new String[]{login},null,null,null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String profile= cursor.getString(cursor.getColumnIndex("profile"));
        cursor.close();
        return profile;
    }


    public boolean getSpecificProfile(String login,String loggedProfile){
       //String loggedProfile = "Lekarz";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query("USER",null," login=? and profile=? ",new String[]{login, loggedProfile},null,null,null);

        if(cursor.getCount()>0) // Login with profile exists
        {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }



//////////////////////////////////////////////////


    public ArrayList<Cursor> getManagerData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}





