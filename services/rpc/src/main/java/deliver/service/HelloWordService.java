package deliver.service;

import deliver.data.AdvancedHelloRequest;
import deliver.data.HelloWord;
import deliver.data.SimpleHelloRequest;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;


@WebService(name="Deliver", targetNamespace = "http://informatique.polytech.unice.fr/soa1/cookbook/")
public interface HelloWordService {

    @WebResult(name="simple_result")
    HelloWord simple(@WebParam(name = "simpleMessage") SimpleHelloRequest request);

    @WebResult(name="complex_result")
    HelloWord complex(@WebParam(name="complexMessage") AdvancedHelloRequest request);

}
