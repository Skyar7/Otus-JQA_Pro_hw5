package api;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class GetUser_Test extends TestNGCitrusSpringSupport {

  private TestContext context;

  @Test(description = "Получение информации о пользователе")
  @CitrusTest
  public void getActions() {
    this.context = citrus.getCitrusContext().createTestContext();

    context.setVariable("value", "superValue");

    run(http()
            .client("restClientReqres")
            .send()
            .get("users/" + context.getVariable("userId")));

    run(http()
            .client("restClientReqres")
            .receive()
            .response(HttpStatus.OK)
            .message()
            .type(MessageType.JSON));

  }
}
