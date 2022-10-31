package uz.ulugbek.aws.lambda.s3sns;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uz.ulugbek.aws.lambda.s3sns.dto.PatientCheckoutEventDto;

public class BillManagementLambda {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(BillManagementLambda.class);

    public void handler(SNSEvent event) {
        event.getRecords().forEach(snsRecord -> {
            try {
                PatientCheckoutEventDto checkoutEventDto = mapper.readValue(snsRecord.getSNS().getMessage(), PatientCheckoutEventDto.class);
                logger.info(checkoutEventDto.toString());
            } catch (JsonProcessingException e) {
                logger.error("Patient parse error: ", e);
            }
        });
    }

}
