package uz.ulugbek.aws.lambda.s3sns;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.ulugbek.aws.lambda.s3sns.dto.PatientCheckoutEventDto;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PatientCheckoutLambda {

    AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();
    ObjectMapper mapper = new ObjectMapper();

    public void handler(S3Event event) {
        event.getRecords().forEach(record -> {
            S3ObjectInputStream s3ObjectInputStream = amazonS3.getObject(record.getS3().getBucket().getName(),
                                record.getS3().getObject().getKey())
                    .getObjectContent();
            try {
                List<PatientCheckoutEventDto> patientCheckoutLambdaList =
                        Arrays.asList(mapper.readValue(s3ObjectInputStream, PatientCheckoutEventDto[].class));

                System.out.println(patientCheckoutLambdaList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
