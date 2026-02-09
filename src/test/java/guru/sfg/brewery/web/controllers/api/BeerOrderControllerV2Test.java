package guru.sfg.brewery.web.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.sfg.brewery.bootstrap.DefaultBreweryLoader;
import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.domain.BeerOrder;
import guru.sfg.brewery.domain.Customer;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import guru.sfg.brewery.web.model.BeerOrderDto;
import guru.sfg.brewery.web.model.BeerOrderLineDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BeerOrderControllerV2Test extends BaseIT {

    public static final String API_ROOT = "/api/v2/orders/";

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    ObjectMapper objectMapper;

    Customer stPeteCustomer;
    Customer dunedinCustomer;
    Customer keyWestCustomer;
    List<Beer> loadedBeers;

    @BeforeEach
    public void setup() {
        stPeteCustomer = customerRepository.findAllByCustomerName(DefaultBreweryLoader.ST_PETE_DISTRIBUTING).orElseThrow();
        dunedinCustomer = customerRepository.findAllByCustomerName(DefaultBreweryLoader.DUNEDIN_DISTRIBUTING).orElseThrow();
        keyWestCustomer = customerRepository.findAllByCustomerName(DefaultBreweryLoader.KEY_WEST_DISTRIBUTORS).orElseThrow();
        loadedBeers = beerRepository.findAll();
    }

    @WithUserDetails("admin")
    @Test
    void listOrderAdminAuth() throws Exception {
        mockMvc.perform(get(API_ROOT))
                .andExpect(status().isOk());
    }


    @WithUserDetails(value = DefaultBreweryLoader.STPETE_USER)
    @Test
    void listOrdersCustomerAuth() throws Exception {
        mockMvc.perform(get(API_ROOT))
                .andExpect(status().isOk());
    }

    @WithUserDetails(value = DefaultBreweryLoader.DUNEDIN_USER)
    @Test
    void listOrdersCustomerNOTAuth() throws Exception {
        mockMvc.perform(get(API_ROOT))
                .andExpect(status().isOk());
    }

    @Test
    void listOrdersNoAuth() throws Exception {
        mockMvc.perform(get(API_ROOT))
                .andExpect(status().isUnauthorized());
    }


    @Transactional
    @Test
    void getByOrderIdNotAuth() throws Exception {
        BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream().findFirst().orElseThrow();

        mockMvc.perform(get(API_ROOT + beerOrder.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Transactional
    @WithUserDetails("admin")
    @Test
    void getByOrderIdADMIN() throws Exception {
        BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream().findFirst().orElseThrow();

        mockMvc.perform(get(API_ROOT + beerOrder.getId()))
                .andExpect(status().is2xxSuccessful());
    }

    @Transactional
    @WithUserDetails(DefaultBreweryLoader.STPETE_USER)
    @Test
    void getByOrderIdCustomerAuth() throws Exception {
        BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream().findFirst().orElseThrow();

        mockMvc.perform(get(API_ROOT + beerOrder.getId()))
                .andExpect(status().is2xxSuccessful());
    }

    @Transactional
    @WithUserDetails(DefaultBreweryLoader.DUNEDIN_USER)
    @Test
    void getByOrderIdCustomerNOTAuth() throws Exception {
        BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream().findFirst().orElseThrow();

        mockMvc.perform(get(API_ROOT + beerOrder.getId()))
                .andExpect(status().isNotFound());
    }


    private BeerOrderDto buildOrderDto(Customer customer, UUID beerId) {
        List<BeerOrderLineDto> orderLines = Arrays.asList(BeerOrderLineDto.builder()
                .id(UUID.randomUUID())
                .beerId(beerId)
                .orderQuantity(5)
                .build());

        return BeerOrderDto.builder()
                .customerId(customer.getId())
                .customerRef("123")
                .orderStatusCallbackUrl("http://example.com")
                .beerOrderLines(orderLines)
                .build();
    }
}
