package uz.ulugbek.aws.lambda.sqs;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uz.ulugbek.aws.lambda.sqs.dto.ImageMetadataDto;

public class SqsHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(SqsHandler.class);

    private final AmazonSNS amazonSNS = AmazonSNSClientBuilder.defaultClient();
    public static final String TOPIC_ARN = "arn:aws:sns:us-east-1:608123326175:module-8-uploads-notification-topic";

    public void sqsHandler(SQSEvent sqsEvent) {

        sqsEvent.getRecords().stream().forEach(item -> {
            logger.info(item.getBody());

            try {

                ImageMetadataDto imageMetadataDto = objectMapper.readValue(item.getBody(), ImageMetadataDto.class);
                this.sendEmail(imageMetadataDto);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        });

    }

    public void sendEmail(ImageMetadataDto imageMetadata) {
        String link = String.format("http://%s:%d/api/file/download/%s",
                "ec2-52-91-2-97.compute-1.amazonaws.com",
                8080,
                imageMetadata.getId()
        );
        String message = String.format("New Image was added. Parameters like this:\nname= %s\ncontent type: %s\nfile size: %d\n download link: %s",
                imageMetadata.getOriginalName(),
                imageMetadata.getContentType(),
                imageMetadata.getFileSize(),
                link
        );
        sendEmail(message);
    }

    public void sendEmail(String message) {

        PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, message, "Email test");
        PublishResult result = amazonSNS.publish(publishRequest);
        logger.info(result.getMessageId());

    }

}
