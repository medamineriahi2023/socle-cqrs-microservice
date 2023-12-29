package com.oga.cqrsref.Mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

public class XmlToJson {

    public String toJson(String xml) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(XMLObject.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StreamSource streamSource = new StreamSource(new StringReader(xml));
        XMLObject xmlObject = unmarshaller.unmarshal(streamSource, XMLObject.class).getValue();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(xmlObject);
    }
}
