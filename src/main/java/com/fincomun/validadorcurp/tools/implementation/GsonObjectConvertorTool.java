package com.fincomun.validadorcurp.tools.implementation;


import com.fincomun.validadorcurp.tools.definition.IObjectConvertor;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

@Validated
@Component("GsonObjectConvertorImp")
public class GsonObjectConvertorTool implements IObjectConvertor {

    private final Gson gson;

    @Autowired
    public GsonObjectConvertorTool(@Qualifier("simpleGson") Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T> HashMap<String, Object> objectToMapConverter(T object) {
        return gson.fromJson(gson.toJson(object), HashMap.class);
    }

    @Override
    public <T> HashMap<String, String> objectToMapConverterSS(T object) {
        HashMap<String , Object> mapTobject = objectToMapConverter(object);
        Map<String, String> stringMap = new HashMap<>();
        mapTobject.forEach((k,v) ->  stringMap.put(k, String.valueOf(v)));
        return gson.fromJson(gson.toJson(stringMap), HashMap.class);
    }

    @Override
    public <T> String objectToJsonConverter(T object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T mapToObjectConverter(Map<String, Object> map, Class<T> classTo) {
        return gson.fromJson(gson.toJson(map), classTo);

    }

    @Override
    public <T> T jsonToObjectConverter(String json, Class<T> classTo) {
        return  gson.fromJson(json, classTo);
    }

    @Override
    public HashMap<String, Object> jsonToMap(String json) {
        return jsonToObjectConverter(json, HashMap.class);
    }

}
