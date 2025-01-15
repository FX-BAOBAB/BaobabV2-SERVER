package file.adapter.output;

import file.application.port.output.ImageMetaDataPersistencePort;
import file.adapter.output.repository.MongoDBImageMetaDataRepository;
import file.domain.ImageMetaData;
import global.annotation.output.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class ImageMetaDataPersistenceAdapter implements ImageMetaDataPersistencePort {
    private final MongoDBImageMetaDataRepository mongoDBImageMetaDataRepository;

    @Override
    public ImageMetaData save(ImageMetaData metaData) {
        return mongoDBImageMetaDataRepository.save(metaData);
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
    public ImageMetaData deleteById(String imageId) {
        ImageMetaData imageMetaData = findById(imageId).orElse(null);
        mongoDBImageMetaDataRepository.deleteById(imageId);
        return imageMetaData;
    }
}
