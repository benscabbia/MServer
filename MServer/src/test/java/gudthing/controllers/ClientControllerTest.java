package gudthing.controllers;

/**
 * Created by Ben on 04/04/2016.
 * Deprecated.
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = MockServletContext.class)
//@WebAppConfiguration
//@ComponentScan(basePackages = "gudthing.controllers")
public class ClientControllerTest {

//    private MockMvc mvc;
//
//    @Before
//    public void setUp() throws Exception {
//        mvc = MockMvcBuilders.standaloneSetup(new ClientController()).build();
//
//    }
//
//    @Test
//    // GET /clients
//    public void testIndex() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get("/clients/").accept(MediaType.ALL))
//                .andExpect(status().isOk())
//                .andExpect(view().name("clients"))
//                .andExpect(model().attributeExists("clients"));
//    }
//
//    @Test
//    // GET /clients/create/
//    public void testAddNewClientForm() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get("/clients/create/").accept(MediaType.ALL))
//                .andExpect(status().isOk())
//                .andExpect(view().name("createClient"))
//                .andExpect(model().attributeExists("client"));
//    }
//
//    @Test
//    // POST /clients/create
//    public void testProcessSubmit() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.post("/clients/create", 0)
//                .param("clientID", "5")
//                .param("ipAddress", "111.11.11.111")
//                .param("description", "test case"))
//                .andExpect(redirectedUrl("/clients"));
//
//        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/clients/").accept(MediaType.ALL))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("clients"))
//                .andReturn();
//
//        @SuppressWarnings("unchecked")
//        List<Client> clients = (List<Client>) result.getModelAndView().getModel().get("clients");
//        assertEquals("There should be 5 clients", clients.size(), 5);
//    }
//
//    @Test
//    // GET  /{clientID}
//    // POST /{clientID}/update
//    public void testGetPostUpdate() throws Exception{
//
//        int clientExist = 3;
//        int clientNotExist = 5;
//
//        String updatedIpAddress = "127.0.0.1";
//        String updatedDescription = "The client has been updated!";
//
//        //required to create the list
//        mvc.perform(MockMvcRequestBuilders.get("/clients/").accept(MediaType.ALL))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("clients"));
//
//        mvc.perform(MockMvcRequestBuilders.get("/clients/{clientID}", clientExist))
//                .andExpect(status().isOk())
//                .andExpect(view().name("editClient"));
//
//        mvc.perform(MockMvcRequestBuilders.get("/clients/{clientID}", 5))
//            .andExpect(status().isNotFound());
//
//        mvc.perform(MockMvcRequestBuilders.post("/clients/{clientID}/update", clientExist)
//                .param("clientID", clientExist + "")
//                .param("ipAddress", updatedIpAddress)
//                .param("description", updatedDescription))
//                .andExpect(redirectedUrl("/clients"));
//
//        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/clients/").accept(MediaType.ALL))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("clients"))
//                .andReturn();
//
//        @SuppressWarnings("unchecked")
//        List<Client> clients = (List<Client>) result.getModelAndView().getModel().get("clients");
//        assertEquals("There should be 4 clients", clients.size(), 4);
//
//
//        Client updatedClient = null;
//
//        int i=0;
//        for(Client client : clients){
//            if(client.getClientID() == clientExist){
//                updatedClient = clients.get(i);
//                break;
//            }
//            i++;
//        }
//        if(updatedClient != null){
//            assertEquals("The Client description should be: " + " " + updatedDescription,
//                    updatedClient.getDescription(), updatedDescription);
//            assertEquals("The Client ipAddress should be: " + " " + updatedIpAddress,
//                    updatedClient.getIpAddress(), updatedIpAddress);
//        }
//    }
//
//    @Test
//    // POST /{clientID}/delete
//    public void testDelete() throws Exception{
//
//        int clientExist = 3;
//        int clientNotExist = 5;
//
//
//        //required to create the list
//        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/clients/").accept(MediaType.ALL))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("clients"))
//                .andReturn();
//
//        @SuppressWarnings("unchecked")
//        List<Client> clients = (List<Client>) result.getModelAndView().getModel().get("clients");
//        int beforeNumberOfClients = clients.size();
//        assertEquals("There should be 4 clients", beforeNumberOfClients, 4);
//
//
//        mvc.perform(MockMvcRequestBuilders.post("/clients/{clientID}/delete", clientExist))
//                .andExpect(redirectedUrl("/clients"));
//
//        result = mvc.perform(MockMvcRequestBuilders.get("/clients/").accept(MediaType.ALL)).andReturn();
//
//        @SuppressWarnings("unchecked")
//        List<Client> clientsDelete = (List<Client>) result.getModelAndView().getModel().get("clients");
//        int afterNumberOfClients = clientsDelete.size();
//        int expectedNumberOfClients = --beforeNumberOfClients;
//
//        assertEquals("Original Clients list had: " + beforeNumberOfClients + " and new list should have " + expectedNumberOfClients,
//                afterNumberOfClients, expectedNumberOfClients);
//    }
//
//    @Test
//    // POST /{clientID}/details
//    public void testViewClient() throws Exception{
//        //TODO if details page is required
//    }
}