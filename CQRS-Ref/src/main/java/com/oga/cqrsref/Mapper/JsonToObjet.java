package com.oga.cqrsref.Mapper;

import com.google.gson.Gson;

public class JsonToObjet {

    public Object toObject(String json,Class<?>T)
    {
        Gson gson =new Gson();
        return  gson.fromJson(json,T);
    }
}
