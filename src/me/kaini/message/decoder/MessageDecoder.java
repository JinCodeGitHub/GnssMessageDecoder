package me.kaini.message.decoder;

import me.kaini.message.message.Message;

/**
 * Message decode common interface
 * @param <M> Decode message result entity
 * @author Canney
 */
public interface MessageDecoder<M> {

    /**
     * Message decode listener
     */
    interface MessageListener<M> {

        void onData(M message);
    }

    /**
     * Decoder is decode's message
     * @return
     */
    Message getMessage();

    /**
     *
     * @param messageBodyData
     */
    void decode(byte[] messageBodyData);

    /**
     * Add message decode result listener.
     * remove use {@link #removeListener(MessageListener)}
     * @param messageListener
     */
    void addListener(MessageListener<M> messageListener);

    /**
     * Remove message decode result listener
     * @param messageListener
     */
    void removeListener(MessageListener messageListener);
}
