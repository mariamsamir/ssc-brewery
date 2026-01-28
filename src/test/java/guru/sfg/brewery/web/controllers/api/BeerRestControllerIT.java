package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by jt on 6/12/20.
 */
@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    @WithMockUser("admin")
    @Test
    void willReturnBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer"))
                .andExpect(status().isOk());
    }

    @Test
    void willReturnBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beer/3c68bf88-8802-4a65-a573-44b9eafa7f15"))
                .andExpect(status().isOk());
    }

    @Test
    void willReturnBeerByUpc() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0083783375213"))
                .andExpect(status().isOk());
    }

    @Test
    void willDeleteBeerWihHttpBasicAuth() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/3c68bf88-8802-4a65-a573-44b9eafa7f15").
                        with(httpBasic("admin","admin")))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void willReturnUnAuthWhenDeleteBeerWithoutBasicAuth() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/3c68bf88-8802-4a65-a573-44b9eafa7f15"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @Disabled
    void willDeleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/3c68bf88-8802-4a65-a573-44b9eafa7f15").
                        header("Api-Key", "admin")
                        .header("Api-Secret", "admin"))
                .andExpect(status().isOk());

    }

    @Test
    @Disabled
    void willReturnUnAuthWhenDeleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/3c68bf88-8802-4a65-a573-44b9eafa7f15").
                        header("Api-Key", "admin")
                        .header("Api-Secret", "adminfalse"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @Disabled
    void willDeleteBeerWithAuthParam() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/3c68bf88-8802-4a65-a573-44b9eafa7f15").
                        param("Api-Key", "admin")
                        .header("Api-Secret", "admin"))
                .andExpect(status().isOk());

    }

    @Test
    @Disabled
    void willReturnUnAuthWhenDeleteBeerWithAuthParam() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/3c68bf88-8802-4a65-a573-44b9eafa7f15").
                        param("Api-Key", "admin")
                        .header("Api-Secret", "adminfalse"))
                .andExpect(status().isUnauthorized());

    }
}