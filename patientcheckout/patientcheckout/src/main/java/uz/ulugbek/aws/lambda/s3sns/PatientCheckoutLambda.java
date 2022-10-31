package uz.ulugbek.aws.lambda.s3sns;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.ulugbek.aws.lambda.s3sns.dto.PatientCheckoutEventDto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class PatientCheckoutLambda {

    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();
    private final AmazonSNS amazonSNS = AmazonSNSClientBuilder.defaultClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String SNS_TOPIC = System.getenv("PATIENT_CHECKOUT_TOPIC");

    public void handler(S3Event event, Context context) {

        LambdaLogger logger = context.getLogger();
        event.getRecords().forEach(record -> {
            S3ObjectInputStream s3ObjectInputStream = amazonS3.getObject(
                        record.getS3().getBucket().getName(),
                        record.getS3().getObject().getKey()
                    )
                    .getObjectContent();
            try {
                logger.log("Reading data from S3");
                List<PatientCheckoutEventDto> patientCheckoutLambdaList =
                        Arrays.asList(mapper.readValue(s3ObjectInputStream, PatientCheckoutEventDto[].class));

                s3ObjectInputStream.close();
                logger.log(patientCheckoutLambdaList.toString());
                logger.log("Message being published to SNS");
                publishPatiantEvents(patientCheckoutLambdaList);
            } catch (IOException e) {
                StringWriter stringWriter = new StringWriter();
                e.printStackTrace(new PrintWriter(stringWriter));
                logger.log(stringWriter.toString());
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
                e.printStackTrace();
            }
        });
    }

}
