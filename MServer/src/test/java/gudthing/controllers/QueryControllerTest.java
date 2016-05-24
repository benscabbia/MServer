package gudthing.controllers;

/**
 * Created by Ben on 12/04/2016.
 * Deprecated
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = MockServletContext.class)
//@WebAppConfiguration
//@ComponentScan(basePackages = "gudthing.controllers")
public class QueryControllerTest {
//    private MockMvc mvc;
//
//    @Before
//    public void setUp() throws Exception {
//        mvc = MockMvcBuilders.standaloneSetup(new QueryController()).build();
//    }
//
//    @Test
//    // GET /query
//    public void testQueryIndex() throws Exception {
//        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/query/").accept(MediaType.ALL))
//                .andExpect(status().isOk())
//                .andExpect(view().name("QueryController/queryIndex"))
//                .andExpect(model().attributeExists("wrapper"))
//                .andReturn();
//
//        @SuppressWarnings("unchecked")
//        ClientWithSelectionListWrapper wrapper = (ClientWithSelectionListWrapper) result.getModelAndView().getModel().get("wrapper");
//        assertEquals("There should be 4 clients", wrapper.getClientList().size(), 4);
//    }
//
//    @Test
//    // GET /query
//    // POST /query/submitQuery
//    public void testProcessSubmit() throws Exception {
//        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/query/").accept(MediaType.ALL))
//                .andExpect(status().isOk())
//                .andExpect(view().name("QueryController/queryIndex"))
//                .andExpect(model().attributeExists("wrapper"))
//                .andReturn();
//
//        @SuppressWarnings("unchecked")
//        ClientWithSelectionListWrapper beforeWrapper = (ClientWithSelectionListWrapper) result.getModelAndView().getModel().get("wrapper");
//        List<ClientWithSelection> beforeClients = beforeWrapper.getClientList();
//        assertEquals("There should be 4 clients", beforeClients.size(), 4);
//    }
}