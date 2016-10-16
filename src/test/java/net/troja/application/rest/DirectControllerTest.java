package net.troja.application.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.nio.file.Paths;

import net.troja.application.DataLoader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DirectControllerTest {
    @Autowired
    private DataLoader dataLoader;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        dataLoader.load(Paths.get("src/test/resources/demodata.txt"));
    }

    @Test
    public void match() {
        final int source = 3;
        final int destination = 6;

        final ResponseEntity<DirectResponse> responseEntity = restTemplate.getForEntity("/api/direct?dep_sid=" + source + "&arr_sid=" + destination, DirectResponse.class);

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        final DirectResponse response = responseEntity.getBody();
        assertThat(response.isDirect(), equalTo(true));
        assertThat(response.getSource(), equalTo(source));
        assertThat(response.getDestionation(), equalTo(destination));
    }

    @Test
    public void miss() {
        final int source = 0;
        final int destination = 5;

        final ResponseEntity<DirectResponse> responseEntity = restTemplate.getForEntity("/api/direct?dep_sid=" + source + "&arr_sid=" + destination, DirectResponse.class);

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        final DirectResponse response = responseEntity.getBody();
        assertThat(response.isDirect(), equalTo(false));
        assertThat(response.getSource(), equalTo(source));
        assertThat(response.getDestionation(), equalTo(destination));
    }
}
