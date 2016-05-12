package gudthing.models;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ben on 12/05/2016.
 */
public class URLService {
    public static String urlEncoder(Client clientWithInstruction){
        return encode(clientWithInstruction);
    }
//
//    public static String urlEncoder(Client clientWithInstruction, InstructionType instructionType){
//        return encode(clientWithInstruction, instructionType.mapping());
//    }

    private static String encode(Client clientWithInstruction) {
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

                    String url = "http://" + ipAddress + ":" + port;
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

    public static boolean testResponse(String url){
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
