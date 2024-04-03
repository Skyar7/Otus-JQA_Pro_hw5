package api.helpers;

import api.features.MarshallerPojoToXML;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static com.consol.citrus.ws.actions.SoapActionBuilder.soap;

public class SoapHelper_Test extends TestNGCitrusSpringSupport {

  @Test(description = "Преобразование чисел", enabled = true)
  @CitrusTest
  public void numberConversionTest() {
    MarshallerPojoToXML<Class<NumberToDollars>> ptxReq = new MarshallerPojoToXML<>();
    MarshallerPojoToXML<Class<NumberToDollarsResponse>> ptxRes = new MarshallerPojoToXML<>();

    run(soap()
            .client("soapClientNumbConvServ")
            .send()
            .message()
            .body(ptxReq.convert(NumberToDollars.class, getNumberToDollarsRequest(),
                    "http://www.dataaccess.com/webservicesserver/", "NumberToDollars")));

    run(soap()
            .client("soapClientNumbConvServ")
            .receive()
            .message()
            .body(ptxRes.convert(NumberToDollarsResponse.class, getNumberToDollarsResponse(),
                    "http://www.dataaccess.com/webservicesserver/", "NumberToDollarsResponse")));
  }

  private NumberToDollars getNumberToDollarsRequest() {
    NumberToDollars numberToDollars = new NumberToDollars();
    numberToDollars.setDNum(new BigDecimal("15"));
    return numberToDollars;
  }

  private NumberToDollarsResponse getNumberToDollarsResponse() {
    NumberToDollarsResponse numberToDollarsResponse = new NumberToDollarsResponse();
    numberToDollarsResponse.setNumberToDollarsResult("fifteen dollars");
    return numberToDollarsResponse;
  }
}