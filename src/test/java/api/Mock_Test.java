package api;

import api.pojo.MockCourseDTO;
import api.pojo.MockUserDTO;
import api.pojo.MockUserScoreDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Mock_Test {
  private static final WireMockServer wireMockServer = new WireMockServer();

  @BeforeClass
  public static void startWireMock() {
    wireMockServer.start();
    configureFor(wireMockServer.port());
  }

  @AfterClass
  public static void stopWireMock() {
    wireMockServer.stop();
  }

  @Test
  public void wiremockTest() throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();

    // Создание заглушек.

    MockUserScoreDTO score = new MockUserScoreDTO("Test user", 78);
    MockCourseDTO course1 = new MockCourseDTO("QA java", 15000);
    MockCourseDTO course2 = new MockCourseDTO("Java", 12000);
    MockUserDTO user = new MockUserDTO("Test user", "QA", "test@test.test", 23);

    stubFor(get(urlPathEqualTo("/user/get/1"))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(objectMapper.writeValueAsString(score))
                    .withStatus(200)));

    stubFor(get(urlPathEqualTo("/cource/get/all"))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(objectMapper.writeValueAsString(Arrays.asList(course1, course2)))
                    .withStatus(200)));

    stubFor(get(urlPathEqualTo("/user/get/all"))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody(objectMapper.writeValueAsString(Arrays.asList(user)))
                    .withStatus(200)));

    // Проверка работы заглушек

    CloseableHttpClient httpClient = HttpClients.createDefault();

    HttpGet request = new HttpGet(String.format("%s/cource/get/all", "http://localhost:8080"));
    HttpResponse httpResponse = httpClient.execute(request);
    String responseString = convertResponseToString(httpResponse);
    System.out.println("С чем сравниваем: " + objectMapper.writeValueAsString(Arrays.asList(course1, course2)));
    System.out.println("Реальный ответ: " + responseString);
  }

  private String convertResponseToString(HttpResponse response) throws IOException {
    InputStream responseStream = response.getEntity().getContent();
    Scanner scanner = new Scanner(responseStream, "UTF-8");
    String responseString = scanner.useDelimiter("\\Z").next();
    scanner.close();
    return responseString;
  }
}