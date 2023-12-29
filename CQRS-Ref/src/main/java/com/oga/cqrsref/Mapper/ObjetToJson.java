package com.oga.cqrsref.Mapper;
import com.google.gson.Gson;

public class ObjetToJson {
    public String toJson(Object obj){
        Gson gson =new Gson();
        return gson.toJson(obj);

    }

}
