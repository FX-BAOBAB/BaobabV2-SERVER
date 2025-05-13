package user.core.config.batch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import user.adapter.output.persistence.enums.CarrierType;
import user.adapter.output.persistence.enums.UserStatus;
import user.adapter.output.persistence.repository.UserDocument;
import user.adapter.output.persistence.repository.UserMongoRepository;
import user.application.email.MailService;
import user.application.email.MailType;
import user.config.AcceptanceTestWithMongo;
import user.config.EnableMongoTestServer;
import user.domain.dto.UserAccount;
import user.domain.dto.UserAddress;
import user.domain.dto.UserPhoneInfo;

@SpringBootTest
@SpringBatchTest
@EnableMongoTestServer
class DormantUserJobConfigTest extends AcceptanceTestWithMongo {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private UserMongoRepository userMongoRepository;

    @Autowired
    private Job dormantUserJob;

    @MockitoBean
    private MailService mailService;

    @BeforeEach
    void setUp() {
        jobLauncherTestUtils.setJob(dormantUserJob);
    }

    @Test
    void 휴면유저_전환_성공() throws Exception {

        // Given
        saveUser(4, LocalDateTime.now().minusYears(1).minusDays(1));

        // When
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("dormantUserStep",
            jobParameters);

        // Then
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        long dormantUserCount = userMongoRepository.findByStatus(UserStatus.DORMANT).size();
        assertThat(dormantUserCount).isEqualTo(4);


    }

    @Test
    void 휴면_전환_전_메일_전송_성공() throws Exception {

        // Given
        saveUser(4, LocalDateTime.now().minusDays(362).minusHours(1));

        // When
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("preDormantUserMailStep");

        // Then
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        long dormantUserCount = userMongoRepository.findByStatus(UserStatus.REGISTERED).size();
        assertThat(dormantUserCount).isEqualTo(4);

        verify(mailService, times(4)).sendDormantUserMail(contains("obab"),
            eq(MailType.DORMANT_WARNING));

    }


    private void saveUser(int count, LocalDateTime lastLoginAt) {
        for (int i = 0; i < count; i++) {
            userMongoRepository.save(UserDocument.builder()
                .userAccount(UserAccount.builder()
                    .email("obab" + i + "@baobab.com")
                    .password("password")
                    .build())
                .status(UserStatus.REGISTERED)
                .userPhoneInfo(UserPhoneInfo.builder()
                    .carrierType(CarrierType.SKT)
                    .phoneNumber("010-8608-8545")
                    .build())
                .userAddress(UserAddress.builder()
                    .address("Seoul")
                    .detailAddress("Gangnam")
                    .basicAddress(true)
                    .post("12345")
                    .build())
                .lastLoginAt(lastLoginAt)
                .build());
        }
    }

}
