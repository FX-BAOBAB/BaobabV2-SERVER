package file.core;

import file.core.common.error.ImageErrorCode;
import file.core.common.exception.image.ImageStorageException;
import file.core.common.utils.RetryUtils;
import file.domain.ImageMetaData;
import file.domain.ImageCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;

import static java.lang.Thread.*;
import static java.lang.Thread.sleep;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalFileAndDBImageStorageService {

    private final LocalFileStorageService fileStorageService;
    private final MongoDBImageMetaDataService imageMetaDataService;
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public ImageMetaData saveImage(ImageCommand imageCommand) {

        if (imageCommand.getFile().isEmpty())
            throw new ImageStorageException(ImageErrorCode.NULL_POINT);

        Path filePath = fileStorageService.uploadImage(imageCommand.getFile());

        return imageMetaDataService.saveImageMetaData(imageCommand, filePath);
    }

    @Transactional
    public List<ImageMetaData> saveImageList(List<ImageCommand> imageCommandList) {
        return imageCommandList.stream()
                .map(this::saveImage)
                .toList();
    }

    @Transactional
    public ImageMetaData updateImage(ImageCommand imageCommand) {
        return saveImage(imageCommand);
    }

    @Transactional
    public List<ImageMetaData> updateImageList(List<ImageCommand> imageCommandList) {
        return saveImageList(imageCommandList);
    }

    public void deleteImage(String imageId) {

        if (imageId == null || imageId.isEmpty()) {
            log.warn("Image id is null or empty");
            return;
        }

        // 이미지 메타데이터 삭제 실패 시 지수 백오프 수행
        ImageMetaData deleteMetaData = RetryUtils.performRetryableTask(() ->
                        imageMetaDataService.deleteImageMetaData(imageId), "delete imageMetaData", imageId);

//        ImageMetaData deleteMetaData = imageMetaDataService.deleteImageMetaData(imageId);

        // 이미지 메타데이터가 존재하지 않으면 무시
        if (deleteMetaData == null) {
            return;
        }

        Path path = Path.of(URI.create(deleteMetaData.getUrl()).getPath());
        Path filePath = Path.of(uploadDir, path.getFileName().toString());

        // 이미지 메타데이터 삭제 실패 시 지수 백오프 수행
        RetryUtils.performRetryableTask(() -> {
                fileStorageService.deleteImage(filePath);
                return null;
                }, "delete file", imageId);

//        fileStorageService.deleteImage(filePath);
    }

    public void deleteImageList(List<String> imageId) {
        imageId.forEach(this::deleteImage);
    }

    public String findImageUrl(String imageId) {

        if (imageId == null || imageId.isEmpty()) {
            throw new ImageStorageException(ImageErrorCode.IMAGE_DELETE_ERROR);
        }

        ImageMetaData imageMetaData = imageMetaDataService.findImageMetaData(imageId);

        URI uri = URI.create(imageMetaData.getUrl());
        Path contextPath = Path.of(uri.getPath());
        Path filePath = Path.of(uploadDir, contextPath.getFileName().toString());

        fileStorageService.checkIfExistImage(filePath);

        return uri.toString();
    }

    public List<String> findImageUrlList(List<String> imageId) {
        return imageId.stream()
                .map(this::findImageUrl)
                .toList();
    }

}
