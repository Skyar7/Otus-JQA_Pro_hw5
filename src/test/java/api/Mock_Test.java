package api;

//import com.consol.citrus.annotations.CitrusTest;
//import com.consol.citrus.context.TestContext;
//import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
//import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
//import org.springframework.http.HttpStatus;
//import org.testng.annotations.Test;
//
//import static com.consol.citrus.http.actions.HttpActionBuilder.http;
//
//public class Mock_Test extends TestNGCitrusSpringSupport {
//
//
//  public TestContext context;
//
//  @Test(description = "МОК", enabled = true)
//  @CitrusTest
//  public void getTestActions() {
//    this.context = citrus.getCitrusContext().createTestContext();
//
//    run(http()
//            .client("restClientMock")
//            .send()
//            .get("users/" + context.getVariable("userId"))
//            .fork(true)
//    );
//
//    run(http()
//            .server("restServerMock")
//            .receive()
//            .get());
//
//    run(http()
//            .server("restServerMock")
//            .send()
//            .response()
//            .message()
//            .type("application/json")
//            .body("{\n" +
//                    "    \"data\": {\n" +
//                    "        \"id\": ${userId},\n" +
//                    "        \"email\": \"janet.weaver@reqres.in\",\n" +
//                    "        \"first_name\": \"Janet\",\n" +
//                    "        \"last_name\": \"Weaver\",\n" +
//                    "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
//                    "    },\n" +
//                    "    \"support\": {\n" +
//                    "        \"url\": \"https://reqres.in/#support-heading\",\n" +
//                    "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
//                    "    }\n" +
//                    "}"));
//
//    run(http().client("restClientMock")
//            .receive()
//            .response(HttpStatus.OK)
//            .message()
//            .type("application/json")
//            .body(new ObjectMappingPayloadBuilder(getJsonData(), "objectMapper"))
//    );
//  }
//
//  public User getJsonData() {
//    User user = new User();
//
//    Data data = new Data();
//    data.setId(Integer.valueOf(context.getVariable("userId")));
//    data.setEmail("janet.weaver@reqres.in");
//    data.setFirstName("Janet");
//    data.setLastName("Weaver");
//    data.setAvatar("https://reqres.in/img/faces/2-image.jpg");
//    user.setData(data);
//
//    Support support = new Support();
//    support.setUrl("https://reqres.in/#support-heading");
//    support.setText("To keep ReqRes free, contributions towards server costs are appreciated!");
//
//    user.setSupport(support);
//
//    return user;
//  }
//}