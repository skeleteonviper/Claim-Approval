package org.example.workers;


import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import org.example.services.ReassignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class ReassignTaskWorker implements JobHandler {

    private static final Logger logger = Logger.getLogger(ReassignTaskWorker.class.getName());

    @Autowired
    private ReassignService reassignService;

    @Override
    public void handle(JobClient client, ActivatedJob job){
        try {
//        reterving the current processor from the variables
            Map<String, Object> variables=job.getVariablesAsMap();
            String currentProcessor=variables.get("Processor").toString();
            String action=variables.get("action").toString();
            logger.info("Current Processor: "+currentProcessor);
            logger.info("action: "+action);

//            check if the action is "Reassign"
            if("Reassign".equals(action)){
                //logic to determine the new processor
                String newProcessor=reassignService.determineNewProcessor(currentProcessor);
                logger.info("New Processor: "+newProcessor);

                //update the variables with the new processor
                variables.put("Processor",newProcessor);

                //completing the job and updating the variables
                client.newCompleteCommand(job.getKey())
                        .variables(variables).send().join();
                logger.info("Job completed successfully with new processor: "+ newProcessor);
            }else{
                //if action is not reassgin , just complete the job
                client.newCompleteCommand(job.getKey())
                        .send()
                        .join();
                logger.info("job completed without reassignment");
            }
        } catch (Exception e) {
            logger.severe("Failed to reassign task: "+ e.getMessage());
        }
    }
}
