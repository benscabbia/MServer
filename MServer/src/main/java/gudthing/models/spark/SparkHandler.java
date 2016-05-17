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
    public static String wordcountHandLer(ClientWithSparkInstruction clientWithSparkInstruction){

        String url = URLService.urlEncoder(clientWithSparkInstruction);
        String health = url + "/health";
        url += "/spark";
        url+= SparkType.WORDCOUNT.mapping();

        try {
            //EXCEPTION GOT THROWN FIX IT, i think mclient cant accept this post, check ts method
            //TODO should check if we can connect to client, if NOT, then skip this and write could not connect.
            //TODO not working for second client, returning empty string check why its not returning string "could not connect..."

            if(URLService.testResponse(health)){
                ClientWithSparkInstruction response = restTemplate.postForObject(url, clientWithSparkInstruction, ClientWithSparkInstruction.class);
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                return response.getResponse();
            }else{
                return "Could Not connect to the client. Please check the Health of client via Query Wizard.";
            }

        }catch (Exception e){
            System.out.println("EXCEPTION");
            return "EXCEPTION";
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
