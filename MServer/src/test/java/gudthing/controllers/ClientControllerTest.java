package gudthing.controllers;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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


    //PROBLEM HERE
    @Test
    public void testIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/clients/").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("clients"));
    }

    @Test
    public void testAddNewClientForm() throws Exception {

    }

    @Test
    public void testProcessSubmit() throws Exception {

    }
}