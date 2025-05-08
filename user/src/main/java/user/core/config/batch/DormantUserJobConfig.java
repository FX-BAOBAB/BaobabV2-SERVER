package user.core.config.batch;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.PlatformTransactionManager;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.application.MailService;
import user.application.port.output.UserPersistencePort;
import user.domain.form.DormantUserSaveForm;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DormantUserJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final UserPersistencePort userPersistencePort;
    private final MailService mailService;
    private final MongoTemplate mongoTemplate;

    @Bean
    public Job dormantUserJob() {
        return new JobBuilder("dormantUserJob", jobRepository)
            .start(dormantUserStep())
            .preventRestart()
            .build();
    }

    @Bean
    public Step dormantUserStep() {
        return new StepBuilder("dormantUserStep", jobRepository)
            .<UserDocument, UserDocument>chunk(10, transactionManager)
            .reader(dormantUserReader())
            .processor(dormantUserProcessor())
            .writer(dormantUserWriter())
            .build();
    }

    @Bean
    public MongoCursorItemReader<UserDocument> dormantUserReader() {

        Criteria criteria = new Criteria()
            .andOperator(
                Criteria.where("lastLoginAt").lt(LocalDateTime.now().minusYears(1)),
                Criteria.where("status").is(UserStatus.REGISTERED)
            );
        Query query = new Query(criteria);

        MongoCursorItemReader<UserDocument> reader = new MongoCursorItemReader<>();
        reader.setName("dormantUserReader");
        reader.setTemplate(mongoTemplate);
        reader.setQuery(query);
        reader.setTargetType(UserDocument.class);
        reader.setSort(Map.of("_id", Sort.Direction.ASC));
        reader.setBatchSize(10);
        return reader;
    }

    @Bean
    public ItemProcessor<UserDocument, UserDocument> dormantUserProcessor() {
        return userDocument -> {
            userDocument.setStatus(UserStatus.DORMANT);
            mailService.sendDormantUserMail(userDocument.getUserAccount().getEmail());
            return userDocument;
        };
    }

    @Bean
    public ItemWriter<UserDocument> dormantUserWriter() {
        return userDocumentChunk -> {
            for (UserDocument userDocument : userDocumentChunk) {
                userPersistencePort.saveDormantUser(DormantUserSaveForm.of(userDocument));
            }
        };
    }

}
