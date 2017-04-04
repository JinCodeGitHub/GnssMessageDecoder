package me.kaini.message.message;

/**
 * Message
 * @author Canney
 * @date 2016/12/28
 */
public class Message {

    /**
     * Message Type Enum
     */
    public enum MessageType{
        /**
         *  ASCII Message
         */
        A,

        /**
         * Binary Message
         */
        B
    }

    /**
     * Message Header
     */
    public final MessageHeader header;

    /**
     * Message CRC adjust byte length
     */
    public int crcLength;

    public Message(MessageHeader header, int crcLength) {
        this.header = header;
        this.crcLength = crcLength;
    }

    public Message(MessageHeader header){
        this(header, 4);
    }
}
