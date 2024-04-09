package api;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import pojo.MockCourseDTO;
import pojo.MockUserDTO;
import pojo.MockUserScoreDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Mock_Test {
  private static final String MOCK_HOST = "localhost";
  private static final int MOCK_HOST_PORT = 6666;
  private static final WireMockServer WIRE_MOCK_SERVER = new WireMockServer(MOCK_HOST_PORT);

  @BeforeClass
  public static void startWireMock() {
    WIRE_MOCK_SERVER.start();
    configureFor(MOCK_HOST, WIRE_MOCK_SERVER.port());
  }

  @AfterClass
  public static void stopWireMock() {
    WIRE_MOCK_SERVER.stop();
  }

  @Test
  public void wiremockTest() throws IOException {

    // Генерация данных.

    MockUserScoreDTO score = new MockUserScoreDTO("Test user", 78);
    MockCourseDTO course1 = new MockCourseDTO("QA java", 15000);
    MockCourseDTO course2 = new MockCourseDTO("Java", 12000);
    MockUserDTO user = new MockUserDTO("Test user", "QA", "test@test.test", 23);

    ObjectMapper objectMapper = new ObjectMapper();

    String scoreJson = objectMapper.writeValueAsString(score);
    String courseJson = objectMapper.writeValueAsString(Arrays.asList(course1, course2));
    String userJson = objectMapper.writeValueAsString(Arrays.asList(user));

    int scoreStatus = HttpStatus.SC_OK;
    int courseStatus = HttpStatus.SC_OK;
    int userStatus = HttpStatus.SC_OK;

    String scorePath = "/user/get/1";
    String coursePath = "/cource/get/all";
    String userPath = "/user/get/all";

    // Создание заглушек.

    stubFor(get(urlPathEqualTo(scorePath))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(scoreJson)
                    .withStatus(scoreStatus)));

    stubFor(get(urlPathEqualTo(coursePath))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(courseJson)
                    .withStatus(courseStatus)));

    stubFor(get(urlPathEqualTo(userPath))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(userJson)
                    .withStatus(userStatus)));

    // Проверка работы заглушек.

    mockGetMethodChecking(scorePath, scoreStatus, scoreJson);
    mockGetMethodChecking(coursePath, courseStatus, courseJson);
    mockGetMethodChecking(userPath, userStatus, userJson);
  }

  private void mockGetMethodChecking(String path, int status, String body) throws IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet request = new HttpGet("http://" + MOCK_HOST + ":" + MOCK_HOST_PORT + path);
    HttpResponse httpResponse = httpClient.execute(request);
    String responseString = convertResponseToString(httpResponse);
    Assert.assertEquals(status, httpResponse.getStatusLine().getStatusCode());
    Assert.assertEquals(body, responseString);
  }

  private String convertResponseToString(HttpResponse response) throws IOException {
    InputStream responseStream = response.getEntity().getContent();
    Scanner scanner = new Scanner(responseStream, "UTF-8");
    String responseString = scanner.useDelimiter("\\Z").next();
    scanner.close();

    return responseString;
  }
}