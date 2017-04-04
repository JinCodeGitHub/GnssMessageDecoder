package me.kaini.message.message;

/**
 * Message Header Define
 * @author Canney
 * @date 2016/12/29
 */
public class MessageHeader {

    /**
     * Message header first byte value
     */
    public final byte f1;

    /**
     * Message header length
     */
    public final int length;

    /**
     * Current Message Type {@link Message.MessageType}
     */
    public final Message.MessageType messageType;

    /**
     * Message Header Constructor
     * @param f1 First byte of message header
     * @param length header length
     * @param type message type ASCII/Binary
     */
    public MessageHeader(byte f1, int length, Message.MessageType type) {
        this.f1 = f1;
        this.length = length;
        this.messageType = type;
    }
}
