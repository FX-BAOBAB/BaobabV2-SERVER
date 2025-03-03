package chat.application.sse;

public interface SseConnectionStore<T, R> {

    void saveEmitter(T userId, R connection);

    R findEmitter(T userId);

    void deleteEmitter(R connection);

}