package com.oga.cqrsref.Mapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class XmlToObjet {
    public Object toObject(String xml,Class<?> T) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(T);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        return unmarshaller.unmarshal(reader);
    }

}
