package tabdulin.demo.rainyhills;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static tabdulin.demo.rainyhills.RainyHillsController.SURFACE_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RainyHillsControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private long postForVolume(int... surface) {
        return postForResponse(surface).getBody().longValue();
    }

    private HttpStatus postForStatus(int... surface) {
        return postForResponse(surface).getStatusCode();
    }

    private ResponseEntity<Long> postForResponse(int[] surface) {
        return restTemplate.postForEntity(SURFACE_ENDPOINT, surface, Long.class);
    }


    @Test
    void rainOverSingleHole() {
        assertEquals(1, postForVolume(1, 0, 2));
        assertEquals(1, restTemplate.postForEntity(SURFACE_ENDPOINT, new double[] {1.9, 0, 2.9}, Long.class)
                .getBody().longValue());

    }

    @Test
    void rainOverEmptySurface() {
        assertEquals(0, postForVolume());
    }

    @Test
    void rainOverIncorrectSurfaceInput() {
        assertEquals(HttpStatus.BAD_REQUEST, restTemplate.postForEntity(SURFACE_ENDPOINT, "{\"a\": \"1\"}", String.class)
                .getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, restTemplate.postForEntity(SURFACE_ENDPOINT, "incorrect input", String.class)
                .getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, restTemplate.postForEntity(SURFACE_ENDPOINT, 1, String.class)
                .getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, restTemplate.postForEntity(SURFACE_ENDPOINT, "[\"a\", \"1\"]" , String.class)
                .getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, restTemplate.postForEntity(SURFACE_ENDPOINT, "[\"1\", \"1\"]" , String.class)
                .getStatusCode());
    }

    @Test
    void rainOverLargeSurface() {
        int[] surface = new int[10_000_000];
        for (int i = 0; i < surface.length; i++) {
            surface[i] = i;
        }

        assertEquals(0, postForVolume(surface));
    }
}