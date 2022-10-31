package uz.ulugbek.aws.lambda.s3sns;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.ulugbek.aws.lambda.s3sns.dto.StudentDto;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StudentS3Listener {

    public static final String GRADE_A = "A";
    public static final String GRADE_B = "B";
    public static final String GRADE_C = "C";

    private final String SNS_TOPIC = System.getenv("STUDENT_CHECKOUT_TOPIC");

    private final AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();
    private final AmazonSNS amazonSNS = AmazonSNSClientBuilder.defaultClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public void studentHandler(S3Event event) {
        event.getRecords().forEach(record -> {
            S3ObjectInputStream s3ObjectInputStream = amazonS3.getObject(record.getS3().getBucket().getName(),
                            record.getS3().getObject().getKey())
                    .getObjectContent();
            try {
                List<StudentDto> studentList =
                        Arrays.asList(mapper.readValue(s3ObjectInputStream, StudentDto[].class));

                s3ObjectInputStream.close();
                System.out.println(studentList);
                updateGrades(studentList);
                publishStudentEvents(studentList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void publishStudentEvents(List<StudentDto> studentList) {
        studentList.forEach(studentDto -> {
            try {
                amazonSNS.publish(
                        SNS_TOPIC,
                        mapper.writeValueAsString(studentDto)
                );
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateGrades(List<StudentDto> studentList) {
        studentList.stream().forEach(studentDto -> {
            studentDto.setGrade(getGradeByTestScore(studentDto.getTestScore()));
        });
    }

    private String getGradeByTestScore(Integer testScore) {

        if (testScore > 80) {
            return GRADE_A;
        } else if (testScore > 70) {
            return GRADE_B;
        }

        return GRADE_C;
    }

}
