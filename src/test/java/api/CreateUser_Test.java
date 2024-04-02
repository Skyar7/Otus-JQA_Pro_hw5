package api;

import api.pojo.CreateUserRequestDTO;
import api.pojo.CreateUserResponseDTO;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;

import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class CreateUser_Test extends TestNGCitrusSupport {

  String name = "Bill";
  String job = "Cleaner";

  @Test(description = "Создание пользователе")
  @CitrusTest
  public void getTestActions() {

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
    createUserResponse.setCreatedAt("unknown");
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