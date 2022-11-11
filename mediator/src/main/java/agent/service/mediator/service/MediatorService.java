package agent.service.mediator.service;

import agent.service.mediator.data.Mediator;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MediatorService {
    public static Mediator mediator = new Mediator(new HashMap<>());

    public void registerNewAgent(String uri, Integer amountOfJobs){
        mediator.addAgent(amountOfJobs,uri);
    }
}
