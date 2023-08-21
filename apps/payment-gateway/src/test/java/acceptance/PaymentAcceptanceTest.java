package acceptance;

import com.example.paymentgateway.PaymentGatewayApplication;
import com.example.paymentgateway.adaptor.responses.Links;
import com.example.paymentgateway.adaptor.responses.payment.PaymentResponse;
import com.example.paymentgateway.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentGatewayApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentAcceptanceTest {

    @LocalServerPort
    private int port;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private PaymentRequest paymentRequest;
    private ResponseEntity<HttpStatus> response;


    private ResponseEntity<PaymentResponse> entityResponse;

    @Test
    public void shouldCreatePayment() {
        givenAPaymentRequest();
        givenAdaptorRespondsWithAuthorised();
        whenISendThePaymentRequestToTheEndpoint();
        thenStatusIsOk();
    }

    private void givenAdaptorRespondsWithAuthorised() {
        givenAPaymentResponse();
        Mockito.when(restTemplate.postForEntity(eq("http://localhost:8090/payments/authorisation"), any(), eq(PaymentResponse.class))).thenReturn(entityResponse);
    }

    private void givenAPaymentResponse() {
        Map<String, URL> links = new HashMap<String, URL>() {{
            try {
                put("Cancel", new URL("https://try.access.cko-bank-simulator.com/payments/transaction/cancel/" + "token"));
                put("Settle", new URL("https://try.access.cko-bank-simulator.com/payments/transaction/settle/" + "token"));
                put("Refund", new URL("https://try.access.cko-bank-simulator.com/payments/transaction/refund/" + "token"));
                put("Events", new URL("https://try.access.cko-bank-simulator.com/payments/transaction/events/" + "token"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }};

        PaymentResponse paymentResponse = new PaymentResponse("AUTHORISED", null, null, "CKO-asdasd-1", new Links(links));

        entityResponse = new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }

    void givenAPaymentRequest() {
        Merchant merchant = new Merchant();
        merchant.setMerchantName("Test Shop");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .paymentMethod("Card")
                .merchant(merchant)
                .basket(Basket.builder().amount(12.0).items(List.of(Item.builder().name("Item").price(12.0).description("Test item").quantity(1).build())).build())
                .cardDetails(Card.builder().cardNumber("1111222233334444").cvv("123").cardExpiryDate(CardExpiryDate.builder().month(11).year(2024).build()).build())
                .currency("GBP")
                .totalAmount(12.0)
                .build();

        this.paymentRequest = paymentRequest;
    }


    private void whenISendThePaymentRequestToTheEndpoint() {
        String url = "http://localhost:" + port + "/authorisation";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);

        response = testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                HttpStatus.class
        );
    }

    private void thenStatusIsOk() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
