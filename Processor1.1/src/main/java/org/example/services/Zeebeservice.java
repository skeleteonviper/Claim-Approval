package org.example.services;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Zeebeservice {
    @Autowired
    private ZeebeClient zeebeClient;

    public void startWorkflowInstance(String bpmnProcessId, Map<String, Object> variables) {
        zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(bpmnProcessId)
                .latestVersion()
                .variables(variables)
//                .withResult()
                .send()
                .join();
    }
    public ZeebeClient getZeebeClient() {
        return zeebeClient;
    }
}
