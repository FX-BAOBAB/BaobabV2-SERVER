package article.application;

import article.application.port.input.SaveArticleUseCase;
import article.application.port.output.ArticlePersistencePort;
import article.core.common.converter.ArticleConverter;
import article.domain.command.ArticleCommand;
import article.domain.command.ArticleSaveCommand;
import file.core.LocalFileAndDBImageStorageService;
import file.domain.ImageCommand;
import file.domain.ImageMetaData;
import global.utils.ImageIdUtils;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArticleSaveService implements SaveArticleUseCase {

    private final ImageIdUtils imageIdUtils;

    private final ArticleConverter articleConverter;

    private final ArticlePersistencePort articlePersistencePort;

    private final LocalFileAndDBImageStorageService localFileAndDBImageStorageService;

    @Override
    public boolean saveArticle(ArticleSaveCommand articleSaveCommand) {
        List<ImageCommand> imageList = generateImageList(articleSaveCommand.getImageList());
        List<ImageMetaData> imageMetaDataList = saveImages(imageList);
        ArticleCommand articleCommand = articleConverter.toArticleCommand(articleSaveCommand, imageMetaDataList);
        return articlePersistencePort.saveArticle(articleCommand);
    }

    private List<ImageCommand> generateImageList(List<MultipartFile> imageList) {
        return imageList.stream()
            .map(image -> {
                // TODO 유저 아이디 처리
                String imageId = imageIdUtils.generateImageId("ART", "userId");
                return ImageCommand.builder()
                    .id(imageId)
                    .file(image)
                    .build();
            })
            .toList();
    }

    private List<ImageMetaData> saveImages(List<ImageCommand> imageCommands) {

        List<CompletableFuture<ImageMetaData>> saveFutures = imageCommands.stream()
            .map(this::saveImageAsync)
            .toList();

        CompletableFuture<Void> allImagesSaved = CompletableFuture.allOf(saveFutures.toArray(new CompletableFuture[0]));

        return allImagesSaved.thenApply(ignored -> collectSavedImageResults(saveFutures)).join();
    }

    private CompletableFuture<ImageMetaData> saveImageAsync(ImageCommand imageCommand) {
        return localFileAndDBImageStorageService.saveImage(imageCommand);
    }

    private List<ImageMetaData> collectSavedImageResults(List<CompletableFuture<ImageMetaData>> futures) {
        return futures.stream()
            .map(CompletableFuture::join)
            .toList();
    }

}
