package gudthing.models;

import gudthing.models.InstructionModels.Health;
import gudthing.models.InstructionModels.Metric;
import gudthing.properties.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Ben on 21/04/2016.
 */
public class QueryHandler {


    private static RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

    private static ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 3000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }


    public static boolean healthHandLer(ClientWithInstruction clientWithInstruction){
        String url = URLService.urlEncoder(clientWithInstruction);
        url+= InstructionType.HEALTH.mapping();

        String ip = clientWithInstruction.getIpAddress();
        String type = clientWithInstruction.getInstructionType().toString();


        boolean connected = URLService.testResponse(url);
        SessionSuccessOperations.incrementCounter();

        if(connected){
            Health health = restTemplate.getForObject(url, Health.class);
            if(health != null && health.getStatus().equals("UP")){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    };

    public static void infoHandler(ClientWithInstruction clientWithInstruction){


    };

    public static String metricsHandler(ClientWithInstruction clientWithInstruction){
        String url = URLService.urlEncoder(clientWithInstruction);
        url+= InstructionType.METRICS.mapping();
        boolean connected = URLService.testResponse(url);


        if(connected){
            Metric metrics = restTemplate.getForObject(url, Metric.class);
            System.out.println(metrics);
            SessionSuccessOperations.incrementCounter();
            return metrics.toString();
        }else {
            String temp = "Unable to retrieve information";
            SessionExceptionOperation.incrementCounter();
            return temp;
        }

    };

    public static String queryHandler(ClientWithInstruction clientWithInstruction){
        String url = URLService.urlEncoder(clientWithInstruction);
        url+= InstructionType.QUERY_MYSQL.mapping();
        url += "/sqlDatabase";

        try {

            Message instruction = clientWithInstruction.getMessage();
            Message response = restTemplate.postForObject(url, instruction, Message.class);

            if(!checkForException(response.getClientResponse(), clientWithInstruction.getInstructionType().toString(), clientWithInstruction.getIpAddress())){
                SessionSuccessOperations.incrementCounter();
            }else{
                SessionExceptionOperation.incrementCounter();
            };
            return response.getClientResponse();
        }catch (Exception e){
            System.out.println("EXCEPTION");
            String temp ="EXCEPTION: " + e.getStackTrace()[0];
            SessionFailureOperation.incrementCounter();
            return temp;
        }

    };

    public static String queryMongoHandler(ClientWithInstruction clientWithInstruction) {
        String url = URLService.urlEncoder(clientWithInstruction);
        url+= InstructionType.QUERY_MONGO.mapping();
        url += "/nosqlDatabase";

        try{
            Message instruction = clientWithInstruction.getMessage();
            Message response = restTemplate.postForObject(url, instruction, Message.class);

            if(!checkForException(response.getClientResponse(), clientWithInstruction.getInstructionType().toString(), clientWithInstruction.getIpAddress())){
                SessionSuccessOperations.incrementCounter();
            }else{
                SessionExceptionOperation.incrementCounter();
            };
            return response.getClientResponse();
        }catch (Exception e){
            System.out.println("EXCEPTION");
            String temp = "EXCEPTION: " + e.getStackTrace()[0];
            SessionFailureOperation.incrementCounter();
            return temp;
        }
    };

    public static String shutdownHandler(ClientWithInstruction clientWithInstruction) {
        String url = URLService.urlEncoder(clientWithInstruction);
        url += InstructionType.SHUTDOWN.mapping();

        try {
            System.out.println(url);
            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
            HttpStatus status = response.getStatusCode();
            System.out.println(status);

            if (status == HttpStatus.OK) {
                String temp = "The client has been shut down successfully.";
                SessionSuccessOperations.incrementCounter();
                return "The client has been shut down successfully.";
            } else {
                String temp = "There was an error: '" + status.toString() + "'. Could not shut client down";
                SessionExceptionOperation.incrementCounter();
                return temp;
            }
        } catch (Exception e) {
            String temp = "There was an error. Probable cause is that system is unable to connect to client, please try run a HEALTH command. ";
            SessionFailureOperation.incrementCounter();
            return temp;
        }

    }

    private static boolean checkForException(String response, String type, String targetMachineIP) {
        if(response != null && (response.toLowerCase().contains("exception") || response.toLowerCase().contains("error"))){
            return true;
        }
        return false;
    }


};