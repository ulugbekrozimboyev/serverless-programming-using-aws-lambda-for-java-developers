package uz.ulugbek.aws.lambda.orderapi;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.ulugbek.aws.lambda.orderapi.dto.Order;

import java.util.HashMap;
import java.util.Map;

public class CreateOrderLambda {

    public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent request) throws JsonProcessingException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(request.getBody(), Order.class);

        String output = String.format("{ \"message\": \"Order saved with id: %s\" }", order.getId());

        return response
                .withStatusCode(200)
                .withBody(output);
    }

}
