package uz.ulugbek.aws.lambda.orderapi;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.ulugbek.aws.lambda.orderapi.dto.CustomerDto;
import uz.ulugbek.aws.lambda.orderapi.dto.OrderDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReadCustomerLambda {

    private final ObjectMapper mapper;
    private final AmazonDynamoDB amazonDynamoDB;


    public ReadCustomerLambda() {
        this.mapper = new ObjectMapper();
        this.amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
    }

    public APIGatewayProxyResponseEvent getCustomers(APIGatewayProxyRequestEvent request) throws JsonProcessingException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        ScanResult result = amazonDynamoDB.scan(new ScanRequest().withTableName(System.getenv("CUSTOMERS_TABLE")));
        List<CustomerDto> customerList = result.getItems()
                .stream()
                .map(item -> new CustomerDto(
                        Integer.parseInt(item.get("id").getN()),
                        item.get("firstName").getS(),
                        item.get("lastName").getS(),
                        Double.parseDouble(item.get("rewardPoints").getN())
                ))
                .collect(Collectors.toList());

        return response
                .withStatusCode(200)
                .withBody(mapper.writeValueAsString(customerList));
    }
}
