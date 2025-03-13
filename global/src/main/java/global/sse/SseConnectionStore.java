package global.sse;

import java.util.Optional;

public interface SseConnectionStore<T, R> {

    R saveEmitter(T uniqueKey, R connection);

    Optional<R> findEmitter(T uniqueKey);

    void deleteEmitter(T uniqueKey);

}