package rpc;


import javax.jws.WebService;

@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/Uberoo/cookbook/",
        portName          = "ETAComputerPort",
        serviceName       = "ETAComputerService",
        endpointInterface = "rpc.ETAComputationService")
public class ETAComputationImpl implements ETAComputationService {
}
