package api;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DataProviderGetUser_Test extends TestNGCitrusSpringSupport {

  private TestContext context;

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
  @Test(description = "Получение информации о пользователе", dataProvider = "usersProvider")
  @CitrusTest
  public void getActions(String id, String name, String surname ) {
    this.context = citrus.getCitrusContext().createTestContext();

    context.setVariable("value", "superValue");

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
                    .expression("$.data.last_name", surname))
    );
  }
}
