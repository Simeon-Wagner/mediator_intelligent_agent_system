package agent.service.mediator.controller;

import agent.service.mediator.service.MediatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("mediator")
public class MediatorController {

    public static final int ROUNDS = 100;
    public static final double CAN_ACCEPT = 0.5;
    private final MediatorService mediatorService;
    @Autowired
    public MediatorController(MediatorService mediatorService) {
        this.mediatorService = mediatorService;
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestParam("uri") String uri,
                           @RequestParam("amountJobs") int amountJobs,
                           HttpServletResponse response){
        mediatorService.registerNewAgent(uri,amountJobs);

        try{
            response.getOutputStream().print(ROUNDS+";"+CAN_ACCEPT);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
