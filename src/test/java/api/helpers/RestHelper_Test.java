package api.helpers;

import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

import api.pojo.CreateUserRequestDTO;
import api.pojo.CreateUserResponseDTO;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;

import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RestHelper_Test extends TestNGCitrusSupport {

  @Test(description = "Получение информации о пользователях", dataProvider = "usersProvider")
  @CitrusTest
  public void getUsersTest(String id, String name, String lastname) {

    run(http()
            .client("restClientReqres")
            .send()
            .get("users/" + id));

    run(http()
            .client("restClientReqres")
            .receive()
            .response(HttpStatus.OK)
            .message()
            .type(MessageType.JSON)
            .validate(jsonPath()
                    .expression("$.data.id", id)
                    .expression("$.data.first_name", name)
                    .expression("$.data.last_name", lastname))
    );
  }

  @DataProvider(name = "usersProvider")
  public Object[][] getProvider() {
    return new Object[][]{
      new Object[]{"1","George", "Bluth"},
      new Object[]{"2","Janet", "Weaver"},
      new Object[]{"3","Emma", "Wong"},
      new Object[]{"4","Eve", "Holt"},
      new Object[]{"5","Charles", "Morris"},
      new Object[]{"6","Tracey", "Ramos"},
      new Object[]{"7","Michael", "Lawson"},
      new Object[]{"8","Lindsay", "Ferguson"},
      new Object[]{"9","Tobias", "Funke"},
      new Object[]{"10","Byron", "Fields"},
      new Object[]{"11","George", "Edwards"},
      new Object[]{"12","Rachel", "Howell"},
    };
  }

  @Test(description = "Создание пользователя")
  @CitrusTest
  public void createUserTest() {
    String name = "Bill";
    String job = "Cleaner";

    run(http()
            .client("restClientReqres")
            .send()
            .post("users")
            .message()
            .type("application/json")
            .body(new ObjectMappingPayloadBuilder(getRequestData(name, job),"objectMapper"))
    );

    run(http()
            .client("restClientReqres")
            .receive()
            .response(HttpStatus.CREATED)
            .message()
            .type("application/json")
            .body(new ObjectMappingPayloadBuilder(getResponseData(name, job), "objectMapper"))
    );
  }

  private CreateUserResponseDTO getResponseData(String name, String job) {
    CreateUserResponseDTO createUserResponse = new CreateUserResponseDTO();
    createUserResponse.setName(name);
    createUserResponse.setJob(job);
    createUserResponse.setId("@isNumber()@");
    createUserResponse.setCreatedAt("@ignore()@");

    return createUserResponse;
  }

  private CreateUserRequestDTO getRequestData(String name, String job) {
    CreateUserRequestDTO createUserRequest = new CreateUserRequestDTO();
    createUserRequest.setName(name);
    createUserRequest.setJob(job);

    return createUserRequest;
  }
}