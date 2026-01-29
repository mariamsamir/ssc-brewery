package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CustomerControllerIT extends BaseIT {

    @Test
    void willReturnCustomersForAdmin() throws Exception {
        mockMvc.perform(get("/customers").with(httpBasic("admin", "admin"))
        ).andExpect(status().isOk());
    }

    @Test
    void willReturnCustomersForCustomer() throws Exception {
        mockMvc.perform(get("/customers").with(httpBasic("scott", "tiger"))
        ).andExpect(status().isOk());
    }

    @Test
    void willReturnForbiddenWhenGetCustomersForUser() throws Exception {
        mockMvc.perform(get("/customers").with(httpBasic("user", "user"))
        ).andExpect(status().isForbidden());
    }

    @Test
    void willReturnUnAuthorizedWhenGetCustomersWithNoAuth() throws Exception {
        mockMvc.perform(get("/customers")
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void willCreateNewCustomerForAdmin() throws Exception {
        mockMvc.perform(post("/customers/new")
                        .param("customerName", "Foo Customer2")
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void willReturnForbiddenWhenCreateNewCustomerForUser() throws Exception {
        mockMvc.perform(post("/customers/new")
                        .param("customerName", "Foo Customer2")
                        .with(httpBasic("user", "user")))
                .andExpect(status().isForbidden());
    }

    @Test
    void willReturnUnAuthorizedWhenCreateNewCustomerWithNoAuth() throws Exception {
        mockMvc.perform(post("/customers/new")
                        .param("customerName", "Foo Customer2"))
                .andExpect(status().isUnauthorized());
    }
}
