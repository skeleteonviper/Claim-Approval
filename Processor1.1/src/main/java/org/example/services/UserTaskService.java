package org.example.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class UserTaskService {
    public void assignUser(ArrayList<String> processorArray, Map<String, Object> variables) {
        String assignes= null;
//        ArrayList<String> assignes=new ArrayList<>();

//        mapping the processor values to the email addresses
        for(String processor : processorArray){
            switch (processor){
                case "1":
                    assignes="kipirib699@paxnw.com";
                    break;
                case "2":
                    assignes="fekil71966@avzong.com";
                    break;
                case "3":
                    assignes="rimimo5545@digopm.com";
                    break;
                case "4":
                    assignes="tatow48422@chysir.com";
                    break;
                case "5":
                    assignes="daseja5558@chysir.com";
                    break;
            }
        }
//        here we set the processorVariable with the mapped email address
        variables.put("AssignedUsers", assignes);
    }
}
