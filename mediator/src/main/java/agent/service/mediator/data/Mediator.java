package agent.service.mediator.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

@Getter
@Setter
@AllArgsConstructor
public class Mediator {
    HashMap<Integer, Agent> session;
    Integer [] contract;

    public Mediator(HashMap<Integer, Agent> session) {
        this.session = session;
    }

    public void addAgent(Integer amountOfJobs, String uri){
        if(session.get(amountOfJobs)== null){
            session.put(amountOfJobs,new Agent(uri));
        }
        else{
            Agent a = new Agent(uri);
            Agent b = session.get(amountOfJobs);
            startSession(a,b,amountOfJobs);
        }


    }

    private void startSession(Agent a, Agent b, int amountOfJobs) {


        contract = IntStream.range(0,amountOfJobs).boxed().toArray(Integer [] :: new);
        for(int i = 0; i< 100; i++){
            Integer [] proposal = mutate_contract(contract);
            boolean aAnswer = a.propose(proposal, contract);
            boolean bAnswer = b.propose(proposal, contract);
            if(aAnswer && bAnswer){
                this.contract = proposal;
                System.out.println("Round -> "+ i);
                Arrays.stream(contract).forEach(System.out::println);
            }
        }
    }

    public Integer [] mutate_contract(Integer [] contract){
        Integer [] proposal = Arrays.copyOf(contract, contract.length);
        double mutational_constant = 0.1;
        int index = 0;
        for (int i = 0; i < proposal.length; i++) {
            if(Math.random() <= mutational_constant){
                do {
                    index = (int) (Math.random() * contract.length);
                }while(index == i);
                Integer temp = proposal[i];
                proposal[i]= proposal[index];
                proposal[index]= temp;
            }
        }
        return proposal;
    }



}
