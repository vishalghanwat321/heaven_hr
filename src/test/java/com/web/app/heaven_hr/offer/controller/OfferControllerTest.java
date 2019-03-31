package com.web.app.heaven_hr.offer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.app.heaven_hr.HeavenHrApplication;
import com.web.app.heaven_hr.config.security.WebSecurityConfigTest;
import com.web.app.heaven_hr.offer.dto.OfferDto;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote This is integration test class for Offer.
 * @since 2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {HeavenHrApplication.class, WebSecurityConfigTest.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @Before
    public void setUp() {
        this.objectMapper = new ObjectMapper();
    }

    // data-offer.sql file will load data to h2
    // Below test case will be verified w.r.t. in memory Data calling h2 using JPA repositories
    @SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data-offer.sql")})
    @Test
    @WithUserDetails("heavenhr")

    public void testOfferController_1_ById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/offer/{offerId}", 1872306377836480L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1872306377836480L)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jobTitle", Matchers.is("First Job")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.receivedApplications", Matchers.is(3)));
    }

    // Verify the list of all offers with expected count 2
    @Test
    @WithUserDetails("heavenhr")
    public void testOfferController_2_ByListAll() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/offer"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    // // Verify the list of all offers with pagination with expected count 2
    @Test
    @WithUserDetails("heavenhr")
    public void testOfferController_3_ByListAllWithPagination() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/offer?page={page}&page_size={page_size}", 0, 5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(2)));
    }

    // Create new Offer
    @Test
    @WithUserDetails("heavenhr")
    public void testOfferController_4_CreateOffer() throws Exception {

        //Create new DTO
        OfferDto offerDto = new OfferDto();
        offerDto.setJobTitle("Third Job");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/offer")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(offerDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    // this is to verify where new record which was added in previous testCase is saved successfully or not
    // New total record count should be 3.
    @Test
    @WithUserDetails("heavenhr")
    public void testOfferController_5_PostCreatingNewOffer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/offer"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }
}
