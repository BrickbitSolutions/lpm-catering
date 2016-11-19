package be.brickbit.lpm.catering.controller;

import org.junit.Test;

import be.brickbit.lpm.catering.AbstractControllerIT;
import be.brickbit.lpm.catering.fixture.UserFixture;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClearanceControllerTest extends AbstractControllerIT {
    @Test
    public void returnsClearanceLevelsOver21() throws Exception {
        stubCore("/user/" + userPrincipal().getId(), 200, UserFixture.mutable(31));

        performGet("/user/clearance")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clearance", hasSize(4)))
                .andExpect(jsonPath("$.clearance", containsInAnyOrder("ANY", "PLUS_16", "PLUS_18",
                        "PLUS_21")));
    }


    @Test
    public void returnsClearanceLevelsUpTo21() throws Exception {
        stubCore("/user/" + userPrincipal().getId(), 200, UserFixture.mutable(21));

        performGet("/user/clearance")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clearance", hasSize(4)))
                .andExpect(jsonPath("$.clearance", containsInAnyOrder("ANY", "PLUS_16", "PLUS_18",
                        "PLUS_21")));
    }

    @Test
    public void returnsClearanceLevelsUpTo16() throws Exception {
        stubCore("/user/" + userPrincipal().getId(), 200, UserFixture.mutable(16));

        performGet("/user/clearance")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clearance", hasSize(2)))
                .andExpect(jsonPath("$.clearance", containsInAnyOrder("ANY", "PLUS_16")));
    }

    @Test
    public void returnsClearanceLevelsUnder16() throws Exception {
        stubCore("/user/" + userPrincipal().getId(), 200, UserFixture.mutable(15));

        performGet("/user/clearance")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clearance", hasSize(1)))
                .andExpect(jsonPath("$.clearance", containsInAnyOrder("ANY")));
    }

    @Test
    public void returnsClearanceLevelsUnder16WithParam() throws Exception {
        stubCore("/user/" + userPrincipal().getId(), 200, UserFixture.mutable(15));

        performGet("/user/clearance?userId=" + userPrincipal().getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clearance", hasSize(1)))
                .andExpect(jsonPath("$.clearance", containsInAnyOrder("ANY")));
    }

}