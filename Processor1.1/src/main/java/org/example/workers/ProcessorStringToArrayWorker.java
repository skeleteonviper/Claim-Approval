package org.example.workers;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProcessorStringToArrayWorker implements JobHandler {
    @Override
    public void handle(JobClient client, ActivatedJob job){

        //reterving the output from the variables
        String processor= job.getVariablesAsMap().get("Path").toString();

        //converting the comma-seperated to an array
        String[] ProcessorArray=processor.split(",");

//        updating the variables
        Map<String, Object> variables = new HashMap<>();
        variables.put("ProcessorArray", Arrays.asList(ProcessorArray));

//        job completion and updating the variables
        client.newCompleteCommand(job.getKey())
                .variables(variables)
                .send()
                .join();
    }
}
