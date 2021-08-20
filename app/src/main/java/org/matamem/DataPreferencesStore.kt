package org.matamem

import android.content.Context

class DataPreferencesStore constructor(context : Context) {
    private var context = context;
    private var prefs = context.getSharedPreferences(
        context.packageName + "preferences", Context.MODE_PRIVATE)


    public fun save(key : String, value : String){
        prefs.edit().putString(key , value).commit()
    }

    public fun save(key : String, value : Int){
        prefs.edit().putInt(key , value).commit()
    }

    public fun save(key : String, value : Boolean){
        prefs.edit().putBoolean(key, value).commit()
    }

    public fun save(key : String, value : Float){
        prefs.edit().putFloat(key, value).commit()
    }


    public fun getString(key : String , defValue : String) : String? {
        return prefs.getString(key , defValue)
    }

    public fun getBoolean(key : String , defValue : Boolean) : Boolean{
        return prefs.getBoolean(key , defValue)
    }

    public fun getInt(key : String , defValue : Int):Int{
        return prefs.getInt(key , defValue);
    }




}