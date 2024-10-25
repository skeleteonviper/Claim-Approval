package org.example.workers;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import org.example.services.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class AssignUserTaskWorker implements JobHandler {

    @Autowired
    private UserTaskService userTaskService;

    @Override
    public void handle(JobClient client, ActivatedJob job){
        try {
            Map<String, Object> variables = job.getVariablesAsMap();
//            String[] processorArray=(String[]) variables.get("ProcessorArray");
            ArrayList<String> processorArray=(ArrayList<String>) variables.get("ProcessorArray");
//            executing the user task assignment
            userTaskService.assignUser(processorArray,variables);

//            complete the job
            client.newCompleteCommand(job.getKey()).variables(variables).send().join();
        }catch(Exception e){
            client.newFailCommand(job.getKey())
                    .retries(job.getRetries()-1)
                    .errorMessage(e.getMessage())
                    .send()
                    .join();
        }
    }
}
