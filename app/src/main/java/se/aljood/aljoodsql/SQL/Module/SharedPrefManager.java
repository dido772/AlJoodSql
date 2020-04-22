package se.aljood.aljoodsql.SQL.Module;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Eyad on 2018-05-20.
 */

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    public static final String DATA_INSERT_URL = "http://192.168.1.182:8080/crude.php";
    public static final String DATA_ITEMS_URL="http://192.168.1.182:8080/items/";

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_SCREEN_LAYOUT_SIZE="screen_size";
    private static final String KEY_CUS_ID ="1";
    private static final String KEY_CUS_NAME="Alomare Livs";
    private static final String  KEY_ORD_ID="0";
    private static final String KEY_ORD_DATE="2020-08-12";
    private static final String KEY_ORD_SENDED="0";
    private static final String KEY_ORD_PACKAD="0";
    private static final String KEY_ORD_SHIPPED="0";
    private static final String KEY_ORD_CANCELLED="0";
    private static final String KEY_ORD_TOTAL="0";


    private SharedPrefManager(Context context){
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public Boolean userLogin(int id, String username, String email, String pass, Boolean remember_me){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_NAME, username);
        editor.apply();
        return true;
    }

    public String get_user_Name(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String user_name = sharedPreferences.getString(KEY_USER_NAME, null);
        return user_name;
    }

    public String get_Cus_Name(){
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    String cus_name = sharedPreferences.getString(KEY_CUS_NAME, null);
        return cus_name;
}
    public void set_Screen_Size(String screen_size_value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SCREEN_LAYOUT_SIZE,screen_size_value);
        editor.apply();
    }

    public void set_User_Name(String user_name_value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_NAME,user_name_value);
        editor.apply();
    }
    public void set_ord_id(int user_name_value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ORD_ID,user_name_value);
        editor.apply();
    }
    public void set_ord_date(String user_name_value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ORD_DATE,user_name_value);
        editor.apply();
    }
    public void set_ord_total(String user_name_value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ORD_TOTAL,user_name_value);
        editor.apply();
    }
    public void set_ord_sended(Boolean user_name_value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ORD_SENDED, String.valueOf(user_name_value));
        editor.apply();
    }
    public void set_ord_shipped(Boolean user_name_value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ORD_SHIPPED, String.valueOf(user_name_value));
        editor.apply();
    }
    public void set_ord_packad(Boolean user_name_value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ORD_PACKAD, String.valueOf(user_name_value));
        editor.apply();
    }
    public void set_cancelled(Boolean user_name_value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ORD_CANCELLED, String.valueOf(user_name_value));
        editor.apply();
    }

    public void set_Cus_Name(String cus_name_value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CUS_NAME,cus_name_value);
        editor.apply();
    }
    public String get_Cus_ID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String cus_id = sharedPreferences.getString(KEY_CUS_ID, null);
        return cus_id;
    }
    public String get_ord_ID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String ord_id = sharedPreferences.getString(KEY_ORD_ID, null);
        return ord_id;
    }
    public String get_ord_date(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String ord_id = sharedPreferences.getString(KEY_ORD_DATE, null);
        return ord_id;
    }
    public String get_ord_total(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String ord_id = sharedPreferences.getString(KEY_ORD_TOTAL, null);
        return ord_id;
    }
    public Boolean get_ord_sended(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Boolean ord_id = sharedPreferences.getBoolean(KEY_ORD_SENDED, false);
        return ord_id;
    }
    public Boolean get_ord_shipped(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Boolean ord_id = sharedPreferences.getBoolean(KEY_ORD_SHIPPED, false);
        return ord_id;
    }
    public Boolean get_ord_packad(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Boolean ord_id = sharedPreferences.getBoolean(KEY_ORD_PACKAD, false);
        return ord_id;
    }
    public Boolean get_ord_cancelled(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Boolean ord_id = sharedPreferences.getBoolean(KEY_ORD_CANCELLED, false);
        return ord_id;
    }

    public void set_Cus_ID(String cus_id_value){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CUS_ID,cus_id_value);
        editor.apply();
    }




}
