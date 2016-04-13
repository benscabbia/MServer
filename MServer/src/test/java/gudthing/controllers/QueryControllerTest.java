package gudthing.controllers;

import gudthing.models.ClientWithSelection;
import gudthing.models.ClientWithSelectionListWrapper;
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
 * Created by Ben on 12/04/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
@ComponentScan(basePackages = "gudthing.controllers")
public class QueryControllerTest {
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new QueryController()).build();
    }

    @Test
    // GET /query
    public void testQueryIndex() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/query/").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("QueryController/queryIndex"))
                .andExpect(model().attributeExists("wrapper"))
                .andReturn();

        @SuppressWarnings("unchecked")
        ClientWithSelectionListWrapper wrapper = (ClientWithSelectionListWrapper) result.getModelAndView().getModel().get("wrapper");
        assertEquals("There should be 4 clients", wrapper.getClientList().size(), 4);
    }

    @Test
    // GET /query
    // POST /query/submitQuery
    public void testProcessSubmit() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/query/").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("QueryController/queryIndex"))
                .andExpect(model().attributeExists("wrapper"))
                .andReturn();

        @SuppressWarnings("unchecked")
        ClientWithSelectionListWrapper beforeWrapper = (ClientWithSelectionListWrapper) result.getModelAndView().getModel().get("wrapper");
        List<ClientWithSelection> beforeClients = beforeWrapper.getClientList();
        assertEquals("There should be 4 clients", beforeClients.size(), 4);


//        //TODO need to finish logic before can add unit test below
//        mvc.perform(MockMvcRequestBuilders.post("/query/submitQuery").accept(MediaType.ALL)
//            .param("selected", "true")
//            .param("clientID", "1")
//            .param("ipAddress", "192.168.1.56")
//            .param("description", "this is a made up ip address :p"))
//
//        .andReturn();
//
//        result = mvc.perform(MockMvcRequestBuilders.get("/query/").accept(MediaType.ALL))
//                .andExpect(status().isOk())
//                .andExpect(view().name("QueryController/queryIndex"))
//                .andExpect(model().attributeExists("wrapper"))
//                .andReturn();
//
//        @SuppressWarnings("unchecked")
//        ClientWithSelectionListWrapper afterWrapper = (ClientWithSelectionListWrapper) result.getModelAndView().getModel().get("wrapper");
//        List<ClientWithSelection> afterClients = afterWrapper.getClientList();
//        assertEquals("There should be 4 clients", afterClients.size(), 4);
//
//        System.out.println("##########################################");
//        for(ClientWithSelection c : afterClients){
//            System.out.println(c.getSelected() + " " + c.getClientID()
//            + c.getIpAddress() + " " + c.getDescription());
//            System.out.println("-----------------------------");
//        }


    }
}