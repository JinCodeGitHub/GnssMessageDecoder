package me.kaini.message.decoder;

import java.util.Arrays;

/**
 *
 * @author Canney
 * @date 2016/12/29
 * @param <M>
 */
public abstract class ASCIIMessageDecoder<M> extends AbsMessageDecoder<M>{

    @Override
    protected final M onDecode(byte[] message) {

        String msgData = new String(message);
        String[] msgValues = msgData.split(getFieldSeparator());

        System.out.println("msgData" + Arrays.toString(msgValues));

        return onDecode(msgValues);

    }

    /**
     * Message field data separator.
     * @return
     */
    protected String getFieldSeparator(){
        return ",";
    }

    protected abstract M onDecode(String[] msgValues);

}
