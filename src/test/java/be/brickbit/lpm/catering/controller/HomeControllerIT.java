package be.brickbit.lpm.catering.controller;

import com.github.tomakehurst.wiremock.client.WireMock;

import org.junit.Test;

import be.brickbit.lpm.catering.AbstractControllerIT;
import be.brickbit.lpm.catering.fixture.UserFixture;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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

    @Test
    public void mockServerTest() throws Exception {
        UserDetailsDto user = UserFixture.mutable();

        stubFor(WireMock.get(urlEqualTo("/user/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(convertToJson(user))));

        performGet("/test")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())));

    }
}