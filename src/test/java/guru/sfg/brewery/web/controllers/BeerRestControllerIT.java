package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by jt on 6/12/20.
 */
@WebMvcTest
public class BeerRestControllerIT extends BaseIT {

//    @WithMockUser("spring")
    @Test
    void willReturnBeers() throws Exception{
        mockMvc.perform(get("/api/v1/beer"))
                .andExpect(status().isOk());
    }

    @Test
    void willReturnBeerById() throws Exception{
        mockMvc.perform(get("/api/v1/beer/3c68bf88-8802-4a65-a573-44b9eafa7f15"))
                .andExpect(status().isOk());
    }

    @Test
    void willReturnBeerByUpc() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0083783375213"))
                .andExpect(status().isOk());
    }

}