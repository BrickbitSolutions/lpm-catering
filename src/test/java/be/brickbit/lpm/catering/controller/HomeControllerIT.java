package be.brickbit.lpm.catering.controller;

import org.junit.Test;

import be.brickbit.lpm.catering.AbstractControllerIT;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HomeControllerIT extends AbstractControllerIT {
    @Test
    public void testHomeUrl() throws Exception {
        mvc().perform(get("/").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("UP")));
    }
}