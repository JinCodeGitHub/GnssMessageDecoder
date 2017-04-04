package me.kaini.message.message;

/**
 * ASCII message header
 * @author Canney
 * @date 2016/12/29
 */
public class ASCIIMessageHeader extends MessageHeader{

    public final byte F1;

    public final String header;

    public final String id;

    public final byte[] headerBytes;


    /**
     * Last but one byte.
     * {@link #FE1} before byte
     */
    public final byte FE2;

    /**
     * Last end byte.
     */
    public final byte FE1;

    /**
     * ASCII Message header constructor
     * @param id Message ID
     */
    public ASCIIMessageHeader(String id) {
        this('$', id, '\r', '\n');
    }

    public ASCIIMessageHeader(char firstChar, String id, char FE2, char FE1) {
        super((byte)firstChar, id.length() + 1, Message.MessageType.A);
        F1 = (byte)firstChar;
        this.id = id;

        header = firstChar+id;
        this.headerBytes = id.getBytes();
        this.FE2 = (byte)FE2;
        this.FE1 = (byte)FE1;
    }


}
