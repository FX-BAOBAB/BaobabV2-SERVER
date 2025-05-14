package user.core.config.batch;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
import user.application.port.output.UserPersistencePort;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UnRegisterUserJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final UserPersistencePort userPersistencePort;
    private final MongoTemplate mongoTemplate;

    @Bean(name = "unRegisterUserJob")
    public Job unRegisterUserJob() {
        return new JobBuilder("unRegisterUserJob", jobRepository)
            .start(unRegisterUserStep())
            .preventRestart()
            .build();
    }

    @Bean
    public Step unRegisterUserStep() {
        return new StepBuilder("unRegisterUserStep", jobRepository)
            .<UserDocument, UserDocument>chunk(10, transactionManager)
            .reader(unRegisterUserReader())
            .writer(unRegisterUserWriter())
            .build();
    }

    @Bean 
    public MongoCursorItemReader<UserDocument> unRegisterUserReader() {
        Criteria criteria = new Criteria()
            .andOperator(
                Criteria.where("status").is(UserStatus.UNREGISTERED)
            );
        Query query = new Query(criteria);

        MongoCursorItemReader<UserDocument> reader = new MongoCursorItemReader<>();
        reader.setName("unRegisterUserReader");
        reader.setTemplate(mongoTemplate);
        reader.setQuery(query);
        reader.setTargetType(UserDocument.class);
        reader.setSort(Map.of("_id", Sort.Direction.ASC));
        reader.setBatchSize(10);
        return reader;
    }

    @Bean
    public ItemWriter<UserDocument> unRegisterUserWriter() {
        return userDocumentChunk -> {
            for (UserDocument userDocument : userDocumentChunk) {
                userPersistencePort.deleteUser(userDocument.getId());
            }
        };
    }

}
