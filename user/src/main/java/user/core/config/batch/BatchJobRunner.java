package user.core.config.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchJobRunner {

    private final JobLauncher jobLauncher;
    private final Job dormantAccountJob;
    private final Job unRegisterUserJob;

    public BatchJobRunner(
        JobLauncher jobLauncher,
        @Qualifier("dormantUserJob") Job dormantAccountJob,
        @Qualifier("unRegisterUserJob") Job unRegisterUserJob
    ) {
        this.jobLauncher = jobLauncher;
        this.dormantAccountJob = dormantAccountJob;
        this.unRegisterUserJob = unRegisterUserJob;
    }

    @Scheduled(cron = "0 0 7 * * *") // 매일 오전 7시 0분 0초에 실행
    public void runDormantUserJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

            jobLauncher.run(dormantAccountJob, jobParameters);

            log.info("DormantUserJob 실행 완료");
        } catch (Exception e) {
            log.error("DormantUserJob 실행 실패", e);
        }
    }

    @Scheduled(cron = "0 0 3 * * *") // 매일 오전 3시 0분 0초에 실행
    public void runUnRegisterUserJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

            jobLauncher.run(unRegisterUserJob, jobParameters);

            log.info("UnRegisterUserJob 실행 완료");
        } catch (Exception e) {
            log.error("UnRegisterUserJob 실행 실패", e);
        }
    }

}
