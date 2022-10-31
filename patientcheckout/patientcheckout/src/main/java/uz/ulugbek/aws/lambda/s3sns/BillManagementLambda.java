package uz.ulugbek.aws.lambda.s3sns;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.ulugbek.aws.lambda.s3sns.dto.PatientCheckoutEventDto;

public class BillManagementLambda {

    private final ObjectMapper mapper = new ObjectMapper();

    public void handler(SNSEvent event) {
        event.getRecords().forEach(snsRecord -> {
            try {
                PatientCheckoutEventDto checkoutEventDto = mapper.readValue(snsRecord.getSNS().getMessage(), PatientCheckoutEventDto.class);
                System.out.println(checkoutEventDto);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

}
