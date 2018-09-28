package deliver.data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class AdvancedHelloRequest extends SimpleHelloRequest {

    private String nom;

    @XmlElement(required = true)
    public String getNom() { return nom; }
    public void setNom(String zone) { this.nom = nom; }

    @Override
    public String toString() {
        return super.toString() + "\n Nom: "+ nom;
    }

}