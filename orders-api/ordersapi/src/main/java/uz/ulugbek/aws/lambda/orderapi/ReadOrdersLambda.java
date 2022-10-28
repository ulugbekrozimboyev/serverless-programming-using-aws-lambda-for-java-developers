package uz.ulugbek.aws.lambda.orderapi;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.ulugbek.aws.lambda.orderapi.dto.Order;

import java.util.HashMap;
import java.util.Map;

public class ReadOrdersLambda {


    public APIGatewayProxyResponseEvent getOrder(APIGatewayProxyRequestEvent request) throws JsonProcessingException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        ObjectMapper mapper = new ObjectMapper();
        Order order = new Order(1234, "Kartoshka", 200);

        return response
                .withStatusCode(200)
                .withBody(mapper.writeValueAsString(order));
    }

}
