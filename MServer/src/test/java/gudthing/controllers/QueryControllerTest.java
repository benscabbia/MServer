package gudthing.controllers;

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
    public void testProcessSubmit() throws Exception {
        ClientWithSelectionListWrapper wrapper = new ClientWithSelectionListWrapper();

    }
}