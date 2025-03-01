package chat.application.port.output;

public interface SseConnectionRegistryPort<T, R> {

    void saveEmitter(T userId, R session);

    R findEmitter(T userId);

    void deleteEmitter(R session);

}
