package org.example.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class ReassignService {
    private static final List<String> processors= Arrays.asList(
            "kipirib699@paxnw.com",
            "fekil71966@avzong.com",
            "rimimo5545@digopm.com",
            "tatow48422@chysir.com",
            "daseja5558@chysir.com"
    );
    public String determineNewProcessor(String currentProcessor){
        Random random=new Random();
        String newProcessor;
        do{
            newProcessor=processors.get(random.nextInt(processors.size()));
        }while (newProcessor.equals(currentProcessor));
        return newProcessor;
    }
}
