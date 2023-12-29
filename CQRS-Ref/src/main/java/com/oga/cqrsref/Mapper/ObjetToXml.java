package com.oga.cqrsref.Mapper;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
public class ObjetToXml {

    public String toXml(Object obj) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Object.class);
        Marshaller marshaller =jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        StringWriter stringWriter =new StringWriter();
        marshaller.marshal(obj,stringWriter);
        return stringWriter.toString();
    }
}
