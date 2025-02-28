package chat.application.port.output;

public interface SseConnectionPoolPort<T, R> {

    void addSession(T userId, R session);

    R getSession(T userId);

    void removeSession(R session);

}
