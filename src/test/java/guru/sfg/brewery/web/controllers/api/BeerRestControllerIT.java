package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by jt on 6/12/20.
 */
@SpringBootTest
public class BeerRestControllerIT extends BaseIT {


    @Autowired
    BeerRepository beerRepository;

//    @Autowired
//    BeerOrderRepository beerOrderRepository;


    @WithMockUser("admin")
    @Test
    void willReturnBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer"))
                .andExpect(status().isOk());
    }

    @Test
    void willReturnUnAuthWithFindBeerByIdWithNoAuth() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(get("/api/v1/beer/" + beer.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void willReturnBeerByUpcForAdmin() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0083783375213")
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk());
    }

    @Test
    void willReturnBeerByUpcForUser() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0083783375213")
                        .with(httpBasic("user", "user")))
                .andExpect(status().isOk());
    }

    @Test
    void willReturnBeerByUpcForCustomer() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0083783375213")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());
    }

    @Test
    void willReturnUnAuthorizedBeerByUpcWithNoAuth() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0083783375213"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeerForAdmin() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(get("/api/v1/beer/" +  beer.getId())
                .with(httpBasic("admin", "admin"))
        ).andExpect(status().isOk());
    }

    @Test
    void findBeerForUser() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(get("/api/v1/beer/" +  beer.getId())
                .with(httpBasic("user", "user"))
        ).andExpect(status().isOk());
    }

    @Test
    void findBeerForCustomer() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(get("/api/v1/beer/" +  beer.getId())
                .with(httpBasic("scott", "tiger"))
        ).andExpect(status().isOk());
    }

    @Test
    void willReturnBeer() throws Exception {

        mockMvc.perform(get("/beers").param("beerName", "")
        ).andExpect(status().isOk());
    }

    @DisplayName("Delete Tests")
    @Nested
    class DeleteTests {
        public Beer beerToDelete() {
            Random rand = new Random();

            return beerRepository.saveAndFlush(Beer.builder()
                    .beerName("Delete Me Beer")
                    .beerStyle(BeerStyleEnum.IPA)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(String.valueOf(rand.nextInt(99999999)))
                    .build());
        }

        @Test
        void willDeleteBeerWihHttpBasicAuthForAdminRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId()).
                            with(httpBasic("admin", "admin")))
                    .andExpect(status().is2xxSuccessful());

        }

        @Test
        void willForbiddenDeleteBeerWihHttpBasicAuthForNonAdminRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId()).
                            with(httpBasic("user", "user")))
                    .andExpect(status().isForbidden());

        }

        @Test
        void willReturnUnAuthWhenDeleteBeerWithoutBasicAuth() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId()))
                    .andExpect(status().isUnauthorized());

        }

        @Test
        @Disabled
        void willDeleteBeer() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId()).
                            header("Api-Key", "admin")
                            .header("Api-Secret", "admin"))
                    .andExpect(status().isOk());

        }

        @Test
        @Disabled
        void willReturnUnAuthWhenDeleteBeer() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId()).
                            header("Api-Key", "admin")
                            .header("Api-Secret", "adminfalse"))
                    .andExpect(status().isUnauthorized());

        }

        @Test
        @Disabled
        void willDeleteBeerWithAuthParam() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId()).
                            param("Api-Key", "admin")
                            .header("Api-Secret", "admin"))
                    .andExpect(status().isOk());

        }

        @Test
        @Disabled
        void willReturnUnAuthWhenDeleteBeerWithAuthParam() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId()).
                            param("Api-Key", "admin")
                            .header("Api-Secret", "adminfalse"))
                    .andExpect(status().isUnauthorized());

        }
    }


}