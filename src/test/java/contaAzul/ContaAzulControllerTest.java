package test.java.contaAzul;

import contaAzul.ContaAzulController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by fabiano on 05/11/18.
 */
public class ContaAzulControllerTest extends ApplicationTest {

    private MockMvc mockMvc;

    @Autowired
    private ContaAzulController contaAzulController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(contaAzulController).build();
    }

    @Test
    public void testListAll() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rest/bankslips")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testListUniqueNotExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/rest/bankslips/1000")).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testListUniqueExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/rest/bankslips/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCancelExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/rest/bankslips/5"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testCancelExistAndCancelAgain() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/rest/bankslips/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/rest/bankslips/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCancelNotExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/rest/bankslips/1000"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testPaymentExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/bankslips/3/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"payment_date\":\"2018-11-04\"}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testPaymentNotExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/bankslips/1000/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"payment_date\":\"2018-11-04\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testPaymentOnPaidAccount() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/bankslips/2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"payment_date\":\"2018-11-04\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testPaymentAndPaidAgain() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/bankslips/4/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"payment_date\":\"2018-11-04\"}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/bankslips/4/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"payment_date\":\"2018-11-04\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testInsert() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/bankslips")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"due_date\":\"2018-11-04\",\"total_in_cents\":23400,\"customer\":\"Cliente vai pagar\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
