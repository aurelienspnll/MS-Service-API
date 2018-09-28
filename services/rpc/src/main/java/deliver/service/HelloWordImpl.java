package deliver.service;


import deliver.data.AdvancedHelloRequest;
import deliver.data.HelloWord;
import deliver.data.SimpleHelloRequest;

import javax.jws.WebService;

@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/cookbook/",
        portName          = "ExternalDeliverPort",
        serviceName       = "ExternalDeliverService",
        endpointInterface = "deliver.service.HelloWordService")
public class HelloWordImpl implements HelloWordService{

    @Override
    public HelloWord simple(SimpleHelloRequest request) {
        return buildResponse(request.getMessage(), "inconnu");
    }

    @Override
    public HelloWord complex(AdvancedHelloRequest request) {
        return buildResponse(request.getMessage(), request.getNom());
    }


    private HelloWord buildResponse(String message, String nom) {
        HelloWord result = new HelloWord();
        result.setAnswer(message);
        return result;
    }
}

