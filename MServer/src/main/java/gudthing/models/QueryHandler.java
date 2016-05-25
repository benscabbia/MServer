package gudthing.models;

import gudthing.models.InstructionModels.Health;
import gudthing.models.InstructionModels.Metric;
import gudthing.properties.SessionExceptionOperation;
import gudthing.properties.SessionFailureOperation;
import gudthing.properties.SessionSuccessOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Ben on 21/04/2016.
 */
public class QueryHandler {


    private static RestTemplate restTemplate = new RestTemplate();


    public static boolean healthHandLer(ClientWithInstruction clientWithInstruction){
        String url = URLService.urlEncoder(clientWithInstruction);
        url+= InstructionType.HEALTH.mapping();

        boolean connected = URLService.testResponse(url);

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
            return metrics.toString();
        }

        return "Unable to retrieve information";
    };

    public static String queryHandler(ClientWithInstruction clientWithInstruction){
        String url = URLService.urlEncoder(clientWithInstruction);
        url+= InstructionType.QUERY_MYSQL.mapping();
        url += "/sqlDatabase";

        try {

            Message instruction = clientWithInstruction.getMessage();
            Message response = restTemplate.postForObject(url, instruction, Message.class);
            SessionSuccessOperations.incrementCounter();

            checkForException(response.getClientResponse());
            return response.getClientResponse();
        }catch (Exception e){
            System.out.println("EXCEPTION");
            SessionFailureOperation.incrementCounter();
            return "EXCEPTION: " + e.getStackTrace()[0];
        }

    }



    ;

    public static String queryMongoHandler(ClientWithInstruction clientWithInstruction) {
        String url = URLService.urlEncoder(clientWithInstruction);
        url+= InstructionType.QUERY_MONGO.mapping();
        url += "/nosqlDatabase";

        try{
            Message instruction = clientWithInstruction.getMessage();
            Message response = restTemplate.postForObject(url, instruction, Message.class);
            SessionSuccessOperations.incrementCounter();
            checkForException(response.getClientResponse());
            return response.getClientResponse();
        }catch (Exception e){
            System.out.println("EXCEPTION");
            SessionFailureOperation.incrementCounter();
            return "EXCEPTION: " + e.getStackTrace()[0];
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
                SessionSuccessOperations.incrementCounter();
                return "The client has been shut down successfully.";
            } else {
                SessionFailureOperation.incrementCounter();
                return "There was an error: '" + status.toString() + "'. Could not shut client down";
            }
        } catch (Exception e) {
            SessionFailureOperation.incrementCounter();
            return "There was an error. Probable cause is that system is unable to connect to client, please try run a HEALTH command";
        }

    }

    private static void checkForException(String response) {
        if(response.toLowerCase().contains("exception") || response.toLowerCase().contains("error")){
            SessionExceptionOperation.incrementCounter();
        }
    }
};