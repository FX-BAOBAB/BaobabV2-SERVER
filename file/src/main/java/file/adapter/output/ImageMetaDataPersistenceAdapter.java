package file.adapter.output;

import file.application.port.output.ImageMetaDataPersistencePort;
import file.adapter.output.repository.MongoDBImageMetaDataRepository;
import file.domain.ImageMetaData;
import global.annotation.output.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
@Slf4j
public class ImageMetaDataPersistenceAdapter implements ImageMetaDataPersistencePort {

    private final MongoDBImageMetaDataRepository mongoDBImageMetaDataRepository;

    @Override
    public ImageMetaData save(ImageMetaData metaData) {
        ImageMetaData imageMetaData = mongoDBImageMetaDataRepository.save(metaData);
        log.info("Image MetaData saved successfully {}", imageMetaData);
        return imageMetaData;
    }

    @Override
    public boolean existsById(String imageId) {
        return mongoDBImageMetaDataRepository.existsById(imageId);
    }

    @Override
    public Optional<ImageMetaData> findById(String imageId) {
        return mongoDBImageMetaDataRepository.findById(imageId);
    }

    @Override
    public void deleteById(String imageId) {
        mongoDBImageMetaDataRepository.deleteById(imageId);
    }
}
