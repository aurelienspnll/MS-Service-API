package deliver.data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class SimpleHelloRequest {

    private String message;

    @XmlElement(name = "id", required = true)
    public String getMessage() { return message; }
    public void setMessage(String identifier) { this.message = identifier; }

    @Override
    public String toString() {
        return "Message:\n" + message;
    }

}