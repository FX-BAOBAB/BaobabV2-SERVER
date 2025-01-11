package file.eureka;

import file.application.port.input.ImageStorageUseCase;
import file.domain.ImageCommand;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/files")
@Slf4j
public class EurekaFileTestController {

    Environment env;

    private final ImageStorageUseCase imageStorageUseCase;

    public EurekaFileTestController(Environment env, ImageStorageUseCase imageStorageUseCase){
        this.env = env;
        this.imageStorageUseCase = imageStorageUseCase;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome First Service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header){
        log.info(header);
        return "Hello First Service";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request){
        // 포트를 가져오는 방법 두 가지
        log.info("Server port={}", request.getServerPort());
        return String.format("Check First Service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/image/upload")
    public CompletableFuture<ImageMetaData> upload(@RequestParam("file") MultipartFile file){
        return imageStorageUseCase.saveImage(ImageCommand.builder()
                        .id("imageABC")
                        .kind(ImageKind.USER)
                        .file(file)
                .build());
    }

    @PostMapping("/images/upload")
    public CompletableFuture<List<ImageMetaData>> upload(@RequestParam("files") MultipartFile[] files){
        List<ImageCommand> imageCommandList = IntStream.range(0, files.length)
                .mapToObj(i -> ImageCommand.builder()
                        .id("image" + i)
                        .kind(ImageKind.USER)
                        .file(files[i])
                        .build())
                .toList();

        return imageStorageUseCase.saveImageList(imageCommandList);
    }

}
