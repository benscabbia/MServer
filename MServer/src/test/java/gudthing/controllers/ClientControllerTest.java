package gudthing.controllers;

import gudthing.models.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by Ben on 04/04/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
@ComponentScan(basePackages = "gudthing.controllers")
public class ClientControllerTest {

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new ClientController()).build();

    }

    @Test
    public void testIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/clients/").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("clients"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    public void testAddNewClientForm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/clients/create/").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("createClient"))
                .andExpect(model().attributeExists("client"));
    }

    @Test
    public void testProcessSubmit() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/clients/create", 0).param("clientID", "5").param("ipAddress", "111.11.11.111").param("description", "test case") )
                .andExpect(redirectedUrl("/clients"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/clients/").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("clients"))
                .andReturn();

        @SuppressWarnings("unchecked")
        List<Client> clients = (List<Client>) result.getModelAndView().getModel().get("clients");
        assertEquals("There should be 5 clients", clients.size(), 5);

//        int clientID = clients.get(clients.size()- (clients.size()-4)).getClientID();
//        for(Client client : clients){
//            System.out.println(client.getClientID() + ", " + client.getIpAddress() + ", " + client.getDescription() );
//        }

        //assertEquals("ID should be 5", 5, clientID);


//      mvc.perform(get("/cookbook/recipe/{id}/delete", recipeId))
//               .andExpect(redirectedUrl("/cookbook/?message=You+successfully+deleted+Test+Recipe%21"));
//      result = this.mockMvc.perform(get("/cookbook/?message=You+successfully+deleted+Test+Recipe%21"))
//               .andExpect(status().isOk())
//               .andExpect(model().attributeExists("recipes"))
//               .andReturn();
//      @SuppressWarnings("unchecked")
//      List<Recipe> recipes2 = (List<Recipe>) result.getModelAndView().getModel().get("recipes");
//      assertEquals("there should be two recipes", recipes2.size(), 2);

    }
}

//this.clientID = clientID;
//        this.ipAddress = ipAddress;
//        this.description = description;