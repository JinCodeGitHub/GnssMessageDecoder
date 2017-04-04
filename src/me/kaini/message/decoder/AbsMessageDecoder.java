package me.kaini.message.decoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Message decoder
 * Manage decode listener and dispatch decode data to listener.
 * @author Canney
 * @date 2016/12/29
 */
public abstract class AbsMessageDecoder<M> implements MessageDecoder<M> {

    /**
     * Decoder listeners
     */
    private final List<MessageListener<?>> listeners = new ArrayList<MessageListener<?>>();

    @Override
    public final void decode(byte[] messageBodyData) {

        M data = onDecode(messageBodyData);

        onData(data);
    }

    @Override
    public void addListener(MessageListener messageListener) {
        listeners.add(messageListener);
    }

    @Override
    public void removeListener(MessageListener listener){
        listeners.remove(listener);
    }

    /**
     * decode single message
     * @param messageBodyData
     * @return
     */
    protected abstract M onDecode(byte[] messageBodyData);

    /**
     * dispatch data to listener
     * @param data
     */
    private final void onData(M data) {

        MessageListener [] ls;
        synchronized (listeners){
            ls = new MessageListener[listeners.size()] ;
            listeners.toArray(ls);
        }

        for (MessageListener l : ls){
            l.onData(data);
        }
    }
}
