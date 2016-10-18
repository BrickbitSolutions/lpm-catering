package be.brickbit.lpm.infrastructure;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.brickbit.lpm.core.client.dto.UserPrincipalDto;

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
    public void extractsPrincipalMood() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final String mood = randomString();
        principalDetails.put("mood", mood);

        UserPrincipalDto dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getMood()).isEqualTo(mood);
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