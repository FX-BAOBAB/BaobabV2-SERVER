package global.sse;

import java.util.Optional;

public interface SseConnectionStore<T, R> {

    R saveEmitter(T userId, R connection);

    Optional<R> findEmitter(T userId);

    void deleteEmitter(T userId);

}