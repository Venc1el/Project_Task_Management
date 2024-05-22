package com.example.uts_pm2;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.uts_pm2.data.UserData;
import com.google.gson.Gson;

public class PrefsManager {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_IS_FIRST_LAUNCH = "is_first_launch";
    private static final String KEY_USER_DATA = "user_data";
    private static final String USER_ID_KEY = "user_id";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public PrefsManager(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public void setLogin(boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setFirstLaunch(boolean isFirstLaunch){
        editor.putBoolean(KEY_IS_FIRST_LAUNCH, isFirstLaunch);
        editor.apply();
    }

    public boolean isFirstLaunch(){
        return sharedPreferences.getBoolean(KEY_IS_FIRST_LAUNCH, true);
    }

    public void setUserData(UserData userData){
        String userDataJson = gson.toJson(userData);
        editor.putString(KEY_USER_DATA, userDataJson);
        editor.putInt(USER_ID_KEY, userData.getUserId());
        editor.apply();
    }

    public UserData getUserData() {
        String userDataJson = sharedPreferences.getString(KEY_USER_DATA, null);
        if (userDataJson != null) {
            return gson.fromJson(userDataJson, UserData.class);
        }
        return null;
    }

    public int getUserId() {
        return sharedPreferences.getInt(USER_ID_KEY, -1);
    }

    public void clear(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER_DATA);
        editor.remove(USER_ID_KEY);
        editor.remove(KEY_IS_LOGGED_IN);
        editor.remove(PREF_NAME);
        editor.apply();
    }
}
