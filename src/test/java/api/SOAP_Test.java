package api;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.testng.annotations.Test;

public class SOAP_Test extends TestNGCitrusSpringSupport {
  public TestContext context;

  @Test(description = "Получение информации о пользователе", enabled = true)
  @CitrusTest
  public void getTestActions() {
    this.context = citrus.getCitrusContext().createTestContext();

  }
}
