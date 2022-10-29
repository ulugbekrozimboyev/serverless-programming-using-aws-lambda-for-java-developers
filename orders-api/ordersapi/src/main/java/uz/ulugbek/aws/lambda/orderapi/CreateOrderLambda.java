package uz.ulugbek.aws.lambda.orderapi;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.ulugbek.aws.lambda.orderapi.dto.Order;

import java.util.HashMap;
import java.util.Map;

public class CreateOrderLambda {

    private final ObjectMapper mapper;

    private final DynamoDB dynamoDB;

    public CreateOrderLambda() {
        this.mapper = new ObjectMapper();
        this.dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent request) throws JsonProcessingException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        Order order = mapper.readValue(request.getBody(), Order.class);

        Table table = dynamoDB.getTable(System.getenv("ORDERS_TABLE"));
        Item item = new Item()
                .withPrimaryKey("id", order.getId())
                .withString("itemName", order.getItemName())
                .withInt("quantity", order.getQuantity());
        table.putItem(item);

        String output = String.format("{ \"message\": \"Order saved with id: %s\" }", order.getId());
        return response
                .withStatusCode(200)
                .withBody(output);
    }

}
