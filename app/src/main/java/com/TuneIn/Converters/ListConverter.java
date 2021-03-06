package com.TuneIn.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

//  TODO https://mobikul.com/insert-custom-list-and-get-that-list-in-room-database-using-typeconverter/
public class ListConverter implements Serializable {

    @TypeConverter
    public static String fromIntegerList(List<String> integerList) {
        if (integerList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        String json = gson.toJson(integerList, type);
        return json;
    }

    @TypeConverter // note this annotation
    public  static List<String> toIntegerList(String integerString) {
        if (integerString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> artistasSeguidosList = gson.fromJson(integerString, type);
        return artistasSeguidosList;
    }



}





