package gudthing.models.spark;

import gudthing.models.InstructionModels.Health;
import gudthing.models.InstructionType;
import gudthing.models.URLService;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Ben on 12/05/2016.
 */
public class SparkHandler {
    private static RestTemplate restTemplate = new RestTemplate();

    //WORDCOUNT, WORDSEARCH
    public static void handleRequest(ClientWithSparkInstruction clientWithSparkInstruction){

        String url = URLService.urlEncoder(clientWithSparkInstruction);
        String health = url + "/health";
        url += "/spark";
        //url+= SparkType.WORDCOUNT.mapping();

        try {

            if(isConnected(clientWithSparkInstruction)){
                ClientWithSparkInstruction response = restTemplate.postForObject(url, clientWithSparkInstruction, ClientWithSparkInstruction.class);
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                clientWithSparkInstruction.setResponse(response.getResponse());
                //return response.getResponse();
                return;
            }else{
                clientWithSparkInstruction.setResponse("Could Not connect to the client. Please check the Health of client via Query Wizard.");
                return;
            }

        }catch (Exception e){
            System.out.println("EXCEPTION");
            clientWithSparkInstruction.setResponse("EXCEPTION occured when trying to communicate with MCLient");
            return;
        }
    }

    public static boolean isConnected(ClientWithSparkInstruction clientWithInstruction) {
        String url = URLService.urlEncoder(clientWithInstruction);
        url+= InstructionType.HEALTH.mapping();


        boolean connected = URLService.testResponse(url);

        if (connected) {
            Health health = restTemplate.getForObject(url, Health.class);
            if (health != null && health.getStatus().equals("UP")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
