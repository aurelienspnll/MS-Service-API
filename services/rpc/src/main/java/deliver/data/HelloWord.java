package deliver.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class HelloWord {
    private String answer;

    @XmlElement
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

}
