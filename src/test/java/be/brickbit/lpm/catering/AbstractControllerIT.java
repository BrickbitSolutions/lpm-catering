package be.brickbit.lpm.catering;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import be.brickbit.lpm.Application;
import be.brickbit.lpm.catering.config.LpmUserAuthenticationConverter;
import be.brickbit.lpm.core.client.dto.UserDetailsDto;
import be.brickbit.lpm.core.client.dto.UserPrincipalDto;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public abstract class AbstractControllerIT extends AbstractIT {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private LpmUserAuthenticationConverter.LpmTokenPrincipal lpmTokenPrincipal;

    @Before
    public void setUp() throws Exception {
        UserPrincipalDto principalDto = new UserPrincipalDto(
                1L,
                "admin",
                randomString(),
                Arrays.asList("ROLE_ADMIN", "ROLE_USER")
        );

        lpmTokenPrincipal = new LpmUserAuthenticationConverter.LpmTokenPrincipal(
                principalDto.getId(),
                principalDto.getUsername()
        );

        stubFor(WireMock.get(urlEqualTo("/user/me"))
                .withHeader("Authorization", equalTo("Bearer LPM-test-token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(convertToJson(principalDto))));

        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    /**
     * Configures the test to run with the given userDetails userPrincipal. Otherwise a default
     * userDetails is used.
     *
     * @param user userDetails userPrincipal.
     */
    protected void setOauthUser(LpmUserAuthenticationConverter.LpmTokenPrincipal user) {
        this.lpmTokenPrincipal = user;
    }

    protected LpmUserAuthenticationConverter.LpmTokenPrincipal userPrincipal() {
        return this.lpmTokenPrincipal;
    }

    protected String convertToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    protected ResultActions performGet(String url) throws Exception {
        return mvc().perform(
                get(url)
                        .header("Authorization", "Bearer LPM-test-token")
                        .accept(APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions performPost(String url, Object command) throws Exception {
        return mvc().perform(
                post(url)
                        .header("Authorization", "Bearer LPM-test-token")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(convertToJson(command)))
                .andDo(print());
    }

    protected ResultActions performPut(String url, Object command) throws Exception {
        return mvc().perform(
                put(url)
                        .header("Authorization", "Bearer LPM-test-token")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(convertToJson(command)))
                .andDo(print());
    }

    protected ResultActions performDelete(String url) throws Exception {
        return mvc().perform(
                delete(url)
                        .header("Authorization", "Bearer LPM-test-token")
                        .accept(APPLICATION_JSON))
                .andDo(print());
    }

    protected void stubCore(String url, int responseStatus, Object response) throws JsonProcessingException {
        stubFor(WireMock.get(urlEqualTo(url))
                .willReturn(aResponse()
                        .withHeader("Authorization", "Bearer LPM-test-token")
                        .withStatus(responseStatus)
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(convertToJson(response))));
    }

    protected MockMvc mvc() {
        return mvc;
    }
}
