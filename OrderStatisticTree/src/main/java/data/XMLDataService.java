package data;

import domain.VideoStore;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLDataService implements DataService {
    public VideoStore loadData(String resource) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(VideoStore.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            File xmlFile = new File(resource);
            return (VideoStore) unmarshaller.unmarshal(xmlFile);
        }
        catch (JAXBException e) {
            System.out.println("No such file!");
            return null;
        }

    }
}
