package be.brickbit.lpm.infrastructure;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.brickbit.lpm.core.client.dto.UserPrincipalDto;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomDecimal;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomInt;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;
import static org.assertj.core.api.Assertions.assertThat;

public class OAuthPrincipalExtractorTest {

    private OAuthPrincipalExtractor extractor;

    @Before
    public void setUp() throws Exception {
        extractor = new OAuthPrincipalExtractor();
    }

    @Test
    public void extractsPrincipalId() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final Long id = randomLong();
        principalDetails.put("id", id);

        UserPrincipalDto dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getId()).isEqualTo(id);
    }

    @Test
    public void extractsPrincipalUsername() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final String username = randomString();
        principalDetails.put("username", username);

        UserPrincipalDto dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getUsername()).isEqualTo(username);
    }

    @Test
    public void extractsPrincipalAge() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final Long age = randomLong();
        principalDetails.put("age", age);

        UserPrincipalDto dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getAge()).isEqualTo(age);
    }

    @Test
    public void extractsPrincipalSeatNumber() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final Integer seatNumber = randomInt();
        principalDetails.put("seatNumber", seatNumber);

        UserPrincipalDto dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getSeatNumber()).isEqualTo(seatNumber);
    }

    @Test
    public void extractsPrincipalWallet() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final BigDecimal wallet = randomDecimal();
        principalDetails.put("wallet", wallet);

        UserPrincipalDto dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getWallet()).isEqualTo(wallet);
    }

    @Test
    public void extractsPrincipalMood() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final String mood = randomString();
        principalDetails.put("mood", mood);

        UserPrincipalDto dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getMood()).isEqualTo(mood);
    }

    @Test
    public void extractsPrincipalFirstName() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final String firstName = randomString();
        principalDetails.put("firstName", firstName);

        UserPrincipalDto dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getFirstName()).isEqualTo(firstName);
    }

    @Test
    public void extractsPrincipalLastName() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final String lastName = randomString();
        principalDetails.put("lastName", lastName);

        UserPrincipalDto dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getLastName()).isEqualTo(lastName);
    }

    @Test
    public void extractsPrincipalEmail() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final String email = randomString();
        principalDetails.put("email", email);

        UserPrincipalDto dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getEmail()).isEqualTo(email);
    }

    @Test
    public void extractsPrincipalAuthorities() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final List<String> authorities = Lists.newArrayList(randomString(), randomString());
        principalDetails.put("authorities", authorities);

        UserPrincipalDto dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getAuthorities()).isEqualTo(authorities);
    }
}