package uz.ulugbek.aws.lambda.s3sns.errorhandling;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uz.ulugbek.aws.lambda.s3sns.BillManagementLambda;

public class ErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(BillManagementLambda.class);

    public void handler(SNSEvent event) {
        event.getRecords().forEach(snsRecord -> logger.info("Dead Letter Queue Event" + snsRecord.toString()));
    }

}
