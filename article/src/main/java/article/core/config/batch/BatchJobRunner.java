package article.core.config.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchJobRunner {

    private final JobLauncher jobLauncher;
    private final Job hardDeleteArticleJob;

    @Scheduled(cron = "0 0 3 * * *") // 매일 오전 3시 0분 0초에 실행
    public void runHardDeleteArticleJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

            jobLauncher.run(hardDeleteArticleJob, jobParameters);

            log.info("HardDeleteArticleJob 실행 완료");
        } catch (Exception e) {
            log.error("HardDeleteArticleJob 실행 실패", e);
        }

    }

}
