package uz.ulugbek.aws.lambda.s3sns;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uz.ulugbek.aws.lambda.s3sns.dto.PatientCheckoutEventDto;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PatientCheckoutLambda {

    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();
    private final AmazonSNS amazonSNS = AmazonSNSClientBuilder.defaultClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String SNS_TOPIC = System.getenv("PATIENT_CHECKOUT_TOPIC");

    private final Logger logger = LoggerFactory.getLogger(PatientCheckoutLambda.class);

    public void handler(S3Event event) {

        event.getRecords().forEach(record -> {
            S3ObjectInputStream s3ObjectInputStream = amazonS3.getObject(
                        record.getS3().getBucket().getName(),
                        record.getS3().getObject().getKey()
                    )
                    .getObjectContent();
            try {
                logger.info("Reading data from S3");
                List<PatientCheckoutEventDto> patientCheckoutLambdaList =
                        Arrays.asList(mapper.readValue(s3ObjectInputStream, PatientCheckoutEventDto[].class));

                s3ObjectInputStream.close();
                logger.info(patientCheckoutLambdaList.toString());
                logger.info("Message being published to SNS");
                publishPatiantEvents(patientCheckoutLambdaList);
            } catch (IOException e) {
                logger.error("Exception is: ", e);
                throw new RuntimeException(e);
            }
        });
    }

    private void publishPatiantEvents(List<PatientCheckoutEventDto> patientCheckoutLambdaList) {
        patientCheckoutLambdaList.forEach(patientCheckoutEvent -> {
            try {
                amazonSNS.publish(
                        SNS_TOPIC,
                        mapper.writeValueAsString(patientCheckoutEvent)
                );
            } catch (JsonProcessingException e) {
                logger.error("Patient publish error: ", e);
                throw new RuntimeException(e);
            }
        });
    }

}
