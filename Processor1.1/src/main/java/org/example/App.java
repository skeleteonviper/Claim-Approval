package org.example;


import io.camunda.zeebe.client.ZeebeClient;
import org.example.services.Zeebeservice;
import org.example.workers.*;
//    import org.example.workers.StoreDecisionOutputWorker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class App  implements CommandLineRunner {
    @Autowired
    private Zeebeservice zeebeservice;


    @Autowired
    public ProcessorStringToArrayWorker processorStringToArrayWorker;

    @Autowired
    public ConvertStringToArrayWorker convertStringToArrayWorker;

    @Autowired
    public AssignUserTaskWorker assignUserTaskWorker;

    @Autowired
    public ReassignTaskWorker reassignTaskWorker;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        // Start a workflow instance with your specific BPMN process ID
        try{
            Map<String, Object> variables = new HashMap<>();
            variables.put("exampleKey", "exampleValue");
            zeebeservice.startWorkflowInstance("Process_phoxxls", variables);
            System.out.println("The workflow instance has been started successfully");
        }catch(Exception e){
            System.out.println("Falied to start workflow instance"+e.getMessage());
        }

        ZeebeClient client = zeebeservice.getZeebeClient();
        client.newWorker()
                .jobType("Processor-string-to-array")
                .handler(processorStringToArrayWorker)
                .open();

        client.newWorker()
                .jobType("convert-string-to-array")
                .handler(convertStringToArrayWorker)
                .open();

        client.newWorker()
                .jobType("assign-user-task")
                .handler(assignUserTaskWorker)
                .open();

        client.newWorker()
                .jobType("reassign-task")
                .handler(reassignTaskWorker)
                .open();

    }
}