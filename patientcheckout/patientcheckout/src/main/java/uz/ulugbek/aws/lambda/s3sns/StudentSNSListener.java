package uz.ulugbek.aws.lambda.s3sns;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uz.ulugbek.aws.lambda.s3sns.dto.StudentDto;

public class StudentSNSListener {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(StudentSNSListener.class);

    public void studentHandler(SNSEvent event) {
        event.getRecords().forEach(snsRecord -> {
            try {
                StudentDto studentDto = mapper.readValue(
                        snsRecord.getSNS().getMessage(),
                        StudentDto.class
                );
                logger.info(studentDto.toString());
            } catch (JsonProcessingException e) {
                logger.error("Student parse error: ", e);
            }
        });
    }
}
