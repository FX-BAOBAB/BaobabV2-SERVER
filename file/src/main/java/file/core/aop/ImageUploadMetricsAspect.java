package file.core.aop;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
// aop 수동 활성을 위한 어노테이션
@ConditionalOnProperty(name = "timing.enabled", havingValue = "true")
public class ImageUploadMetricsAspect {

    private final MeterRegistry meterRegistry;

    @Around("@annotation(file.core.common.annotation.TrackTime)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();

            long uploadTime = end - start;
            log.info("image.upload.time = {}", uploadTime);

            meterRegistry.timer("image.upload.time").record(uploadTime, TimeUnit.MILLISECONDS);

            return result;
        }
        catch (Throwable t) {
            throw new Exception(t);
        }
    }
}