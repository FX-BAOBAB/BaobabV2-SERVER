package article.adapter.input.web;

import article.adapter.input.web.request.ArticleSaveRequest;
import article.application.port.input.SaveArticleUseCase;
import article.core.common.converter.ArticleConverter;
import article.domain.command.ArticleSaveCommand;
import file.application.port.input.ImageStorageUseCase;
import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.domain.ImageCommand;
import file.domain.ImageKind;
import file.domain.ImageMetaData;
import global.api.Api;
import global.utils.ImageIdUtils;
import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleApiController {

    private final ImageIdUtils imageIdUtils;

    private final ArticleConverter articleConverter;

    private final SaveArticleUseCase saveArticleUseCase;

    // TODO Module Code Environment DB 처리
    private final ImageStorageUseCase imageStorageUseCase;

    private static final String IMAGE_MODULE_CODE = "ART";

    @PostMapping()
    public Api<Boolean> save(@Valid ArticleSaveRequest articleSaveRequest) {
        try {
            List<ImageCommand> imageCommandList = articleSaveRequest.getImageList().stream()
                .map(image -> {
                    // TODO 유저 아이디 처리
                    // TODO Module Code Environment DB 처리
                    String imageId = imageIdUtils.generateImageId(IMAGE_MODULE_CODE, "userId");

                    return ImageCommand.builder()
                        .id(imageId)
                        .file(image)
                        .kind(ImageKind.ARTICLE)
                        .build();
                }).toList();

            List<ImageMetaData> imageMetaDataList = imageStorageUseCase.saveImageList(imageCommandList).get();
            // TODO 유저 아이디 처리
            ArticleSaveCommand articleSaveCommand = articleConverter.toSaveCommand(articleSaveRequest, imageMetaDataList, "userId");
            boolean isSaved = saveArticleUseCase.saveArticle(articleSaveCommand);
            return Api.OK(isSaved);

        } catch (InterruptedException | ExecutionException e) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR);
        }
    }

}


