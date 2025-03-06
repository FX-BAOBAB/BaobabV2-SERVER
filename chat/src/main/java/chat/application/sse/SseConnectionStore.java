package chat.application.sse;

import java.util.List;

public interface SseConnectionStore<T, R> {

    void saveEmitter(T userId, R connection);

    List<R> findEmitter(List<T> userId);

    void deleteEmitter(R connection);

}