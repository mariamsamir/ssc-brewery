package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BreweryControllerTest extends BaseIT {

    @Test
    void willGetBreweriesForCustomerRoleAuth() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("scott","tiger"))
                ).andExpect(status().isOk());
    }

    @Test
    void willGetBreweriesForAdminRoleAuth() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("admin","admin"))
        ).andExpect(status().isOk());
    }

    @Test
    void willForbidBreweriesForUserRoleAuth() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("user","user"))
        ).andExpect(status().isForbidden());
    }

    @Test
    void willReturnUnAuthBreweriesForNonBasicAuth() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
        ).andExpect(status().isUnauthorized());
    }


    @Test
    void willGetBreweriesForCustomerRoleAuthRestApi() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries").with(httpBasic("scott","tiger"))
        ).andExpect(status().isOk());
    }

    @Test
    void willGetBreweriesForAdminRoleAuthRestApi() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries").with(httpBasic("admin","admin"))
        ).andExpect(status().isOk());
    }

    @Test
    void willForbidBreweriesForNonCustomerRoleAuthRestApi() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("user","user"))
        ).andExpect(status().isForbidden());
    }

    @Test
    void willReturnUnAuthBreweriesForNonBasicAuthRestApi() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
        ).andExpect(status().isUnauthorized());
    }
}
