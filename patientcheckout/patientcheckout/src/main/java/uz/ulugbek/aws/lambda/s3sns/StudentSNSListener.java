package uz.ulugbek.aws.lambda.s3sns;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.ulugbek.aws.lambda.s3sns.dto.StudentDto;

public class StudentSNSListener {

    private final ObjectMapper mapper = new ObjectMapper();

    public void studentHandler(SNSEvent event) {
        event.getRecords().forEach(snsRecord -> {
            try {
                StudentDto studentDto = mapper.readValue(
                        snsRecord.getSNS().getMessage(),
                        StudentDto.class
                );
                System.out.println(studentDto);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
