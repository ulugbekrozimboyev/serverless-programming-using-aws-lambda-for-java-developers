package uz.ulugbek.aws.lambda.sqs;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;

public class ClaimManagementLambda {

    public void handler(SQSEvent event) {
        event.getRecords().forEach(sqsMessage -> {
            System.out.println(sqsMessage.getBody());
        });
    }

}
