//package user.core.config.batch;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.batch.core.ExitStatus;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.test.JobLauncherTestUtils;
//import org.springframework.batch.test.context.SpringBatchTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import user.adapter.output.persistence.enums.UserStatus;
//import user.adapter.output.persistence.repository.UserDocument;
//import user.adapter.output.persistence.repository.UserMongoRepository;
//import user.config.AcceptanceTestWithMongo;
//import user.config.EnableMongoTestServer;
//import user.domain.dto.UserAccount;
//
//@SpringBootTest
//@SpringBatchTest
//@EnableMongoTestServer
//class UnRegisterUserJobConfigTest extends AcceptanceTestWithMongo {
//
//    @Autowired
//    private JobLauncherTestUtils jobLauncherTestUtils;
//
//    @Autowired
//    private UserMongoRepository userMongoRepository;
//
//    @Autowired
//    @Qualifier("unRegisterUserJob")
//    private Job unRegisterUserJob;
//
//    @BeforeEach
//    void setUp() {
//        jobLauncherTestUtils.setJob(unRegisterUserJob);
//    }
//
//
//    @Test
//    void 회원탈퇴_배치_성공() throws Exception {
//
//        // Given
//        saveUnRegisterUsers(50);
//
//        // When
//        JobParameters jobParameters = new JobParametersBuilder()
//            .addLong("time", System.currentTimeMillis())
//            .toJobParameters();
//
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
//
//        // Then
//        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
//
//        long countAfter = userMongoRepository.findByStatus(UserStatus.UNREGISTERED).size();
//        assertThat(countAfter).isEqualTo(0);
//    }
//
//    private void saveUnRegisterUsers(int count) {
//        for (int i = 0; i < count; i++) {
//            UserDocument document = UserDocument.builder()
//                .userAccount(UserAccount.builder()
//                    .email("baobab" + i + "@baobab.com")
//                    .password("password" + i)
//                    .build())
//                .status(UserStatus.UNREGISTERED)
//                .build();
//            userMongoRepository.save(document);
//        }
//    }
//
//}