package article.adapter.input.web;

import article.adapter.input.web.request.ArticleSaveRequest;
import article.adapter.input.web.response.MyArticleListResponse;
import article.adapter.output.persistence.repository.Article;
import article.application.port.input.GetArticleUseCase;
import article.application.port.input.SaveArticleUseCase;
import article.core.common.converter.ArticleConverter;
import article.domain.command.ArticleSaveCommand;
import article.domain.command.ArticleSearchCommand;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleApiController {

    private final ImageIdUtils imageIdUtils;

    private final ArticleConverter articleConverter;

    private final SaveArticleUseCase saveArticleUseCase;

    private final GetArticleUseCase getArticleUseCase;

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
            // TODO Mongo DB Exception 놓칠 위험있음 Catch 부 변경 필요
            throw new ImageStorageException(ImageErrorCode.IMAGE_STORAGE_ERROR); 
        }
    }
    
    // TODO Login User 처리
    @GetMapping
    public Api<MyArticleListResponse> getMyArticles(@RequestParam String userId) {
        log.info("userId : {}", userId);
        return Api.OK(MyArticleListResponse.builder()
                .articles(getArticleUseCase.getMyArticles(userId))
            .build());
    }

    @GetMapping
    public Api<List<Article>> getAllArticles(ArticleSearchCommand articleSearchCommand) {
        // TODO Article List Algorithm 적용 필요
        return Api.OK(getArticleUseCase.getArticleList(articleSearchCommand));
    }

}


