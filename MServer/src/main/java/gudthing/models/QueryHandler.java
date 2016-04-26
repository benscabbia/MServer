package gudthing.models;

import gudthing.models.InstructionModels.Health;
import gudthing.models.InstructionModels.Metric;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ben on 21/04/2016.
 */
public class QueryHandler {


    private static RestTemplate restTemplate = new RestTemplate();


    public static boolean healthHandLer(ClientWithInstruction clientWithInstruction){
        String url = urlEncoder(clientWithInstruction, InstructionType.HEALTH);

        boolean connected = testResponse(url);

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
        String url = urlEncoder(clientWithInstruction, InstructionType.METRICS);
        boolean connected = testResponse(url);

        if(connected){
            Metric metrics = restTemplate.getForObject(url, Metric.class);
            System.out.println(metrics);
            return metrics.toString();
        }

        return "Unable to retrieve information";
    };

    public static String queryHandler(ClientWithInstruction clientWithInstruction){
        String url = urlEncoder(clientWithInstruction, InstructionType.QUERY);
        url += "/sqlDatabase";

        try {
            System.out.println(url);
            System.out.println("WHat we going to send");
            System.out.println("To: " + url + ", send: " + clientWithInstruction.getMessage().toString());

            Message instruction = clientWithInstruction.getMessage();

            Message response = restTemplate.postForObject(url, instruction, Message.class);

//            ResponseEntity<Message> response = restTemplate.postForEntity(url, clientWithInstruction.getMessage(), Message.class);


//            HttpStatus status = response.getStatusCode();
//            System.out.println(status);
//            System.out.println(response.toString());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            return response.getClientResponse();
        }catch (Exception e){
            System.out.println("EXCEPTION");
            return "EXCEPTION";
        }

    };

    public static String shutdownHandler(ClientWithInstruction clientWithInstruction){
        String url = urlEncoder(clientWithInstruction, InstructionType.SHUTDOWN);

            try{
                System.out.println(url);
                ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
                HttpStatus status = response.getStatusCode();
                System.out.println(status);

                if(status == HttpStatus.OK ){
                    return "The client has been shut down successfully.";
                }else{
                    return "There was an error: '"+ status.toString() + "'. Could not shut client down";
                }
            }catch (Exception e){
                return "There was an error. Probable cause is that system is unable to connect to client, please try run a HEALTH command";
            }

    };

    private static String urlEncoder(ClientWithInstruction clientWithInstruction, InstructionType instructionType){

        final String IPADDRESS_PATTERN =
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";



        //run test to see ip address is in valid form
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        String ipAddress = clientWithInstruction.getIpAddress();
        Matcher matcher = pattern.matcher(ipAddress);

        String portNumber = clientWithInstruction.getPortNumber();

        if(matcher.matches()){
            try{
                int port = Integer.parseInt(portNumber);

                //valid port number
                if(port<65536){

                    String url = "http://" + ipAddress + ":" + port + instructionType.mapping();
                    return url;

                }else{
                    System.out.println("Port invalid");
                    return "0000";
                }


            } catch (NumberFormatException e){
                throw new NumberFormatException();
            }


        }else{
            System.out.println("INVALID IP");
            return "0.0.0.0";
        }

    }

    private static boolean testResponse(String url){
        RestTemplate restTemplate = new RestTemplate();

        try{
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if(HttpStatus.OK == response.getStatusCode()){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            System.out.println("EXCEPTION: The RestTemplate is unable to connect with the client ");
            return false;
        }

    }
}

/*
* RestTemplate restTemplate = new RestTemplate();
//            String url = firstClient.getIpAddress() + ":" + firstClient.getPortNumber();
//
//            String workingUrl = "http://192.168.1.151:8080";
//            System.out.println(url);
//            Message message = restTemplate.getForObject(workingUrl, Message.class);
//
//            System.out.println("#####################################TOSTRING##############################");
//            System.out.println(message.toString());
//
//            model.addAttribute("message", message);
* */
