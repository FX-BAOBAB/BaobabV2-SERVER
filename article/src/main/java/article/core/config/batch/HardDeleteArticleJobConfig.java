package article.core.config.batch;

import article.adapter.output.persistence.enums.ArticleVisibilityStatus;
import article.adapter.output.persistence.repository.Article;
import article.application.port.input.DeleteArticleUseCase;
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

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HardDeleteArticleJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final MongoTemplate mongoTemplate;
    private final DeleteArticleUseCase deleteArticleUseCase;

    @Bean(name = "hardDeleteArticleJob")
    public Job hardDeleteArticleJob() {
        return new JobBuilder("hardDeleteArticleJob", jobRepository)
            .start(hardDeleteArticleStep())
            .preventRestart()
            .build();
    }

    @Bean
    public Step hardDeleteArticleStep() {
        return new StepBuilder("hardDeleteArticleStep", jobRepository)
            .<Article, Article>chunk(10, transactionManager)
            .reader(hardDeleteArticleReader())
            .writer(hardDeleteArticleWriter())
            .build();
    }

    @Bean
    public MongoCursorItemReader<Article> hardDeleteArticleReader() {
        Criteria criteria = new Criteria()
            .andOperator(
                Criteria.where("visibilityStatus").is(ArticleVisibilityStatus.DELETED)
            );
        Query query = new Query(criteria);

        MongoCursorItemReader<Article> reader = new MongoCursorItemReader<>();
        reader.setName("hardDeleteArticleReader");
        reader.setTemplate(mongoTemplate);
        reader.setQuery(query);
        reader.setTargetType(Article.class);
        reader.setSort(Map.of("_id", Sort.Direction.ASC));
        reader.setBatchSize(10);
        return reader;
    }

    @Bean
    public ItemWriter<Article> hardDeleteArticleWriter() {
        return articleChunk -> {
            for (Article article : articleChunk) {
                deleteArticleUseCase.hardDeleteArticle(article.getId(), article.getImageList());
            }
        };
    }

}
