package com.oga.cqrsref.Mapper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.crypto.dsig.XMLObject;
import java.io.StringWriter;

public class JsonToXml {
    public static String toXml(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

        XMLObject xmlObject = gson.fromJson(jsonObject, XMLObject.class);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XMLObject.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(xmlObject, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
