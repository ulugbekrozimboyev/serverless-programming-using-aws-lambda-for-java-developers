package uz.ulugbek.aws.lambda.orderapi;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.ulugbek.aws.lambda.orderapi.dto.CustomerDto;

import java.util.HashMap;
import java.util.Map;

public class CreateCustomerLambda {

    private final ObjectMapper mapper;

    private final DynamoDB dynamoDB;

    public CreateCustomerLambda() {
        this.mapper = new ObjectMapper();
        this.dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public APIGatewayProxyResponseEvent createCustomer(APIGatewayProxyRequestEvent request) throws JsonProcessingException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        CustomerDto customer = mapper.readValue(request.getBody(), CustomerDto.class);

        Table table = dynamoDB.getTable(System.getenv("CUSTOMERS_TABLE"));
        Item item = new Item()
                .withPrimaryKey("id", customer.getId())
                .withString("firstName", customer.getFirstName())
                .withString("lastName", customer.getLastName())
                .withDouble("rewardPoints", customer.getRewardPoints());
        table.putItem(item);

        String output = String.format("{ \"message\": \"Customer saved with id: %s\" }", customer.getId());
        return response
                .withStatusCode(200)
                .withBody(output);
    }

}
