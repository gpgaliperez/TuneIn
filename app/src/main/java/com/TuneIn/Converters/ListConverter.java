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
    public String fromIntegerList(List<Integer> integerList) {
        if (integerList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        String json = gson.toJson(integerList, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Integer> toIntegerList(String integerString) {
        if (integerString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        List<Integer> artistasSeguidosList = gson.fromJson(integerString, type);
        return artistasSeguidosList;
    }



}





