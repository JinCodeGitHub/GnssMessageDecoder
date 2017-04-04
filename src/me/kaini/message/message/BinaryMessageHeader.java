package me.kaini.message.message;

/**
 * Binary message header
 * @author Canney
 * @date 2016/12/28
 */
public class BinaryMessageHeader extends MessageHeader{

    //1byte
    public final byte F2 = (byte)0x44;
    //1byte
    public final byte F3 = (byte)0x12;

    /**
     * F4 2byte
     */
    public final int id;

    /**
     * Reserved
     */
    public final char F6 = 0;

    public final char F7 = 0;

    /**
     * 2 byte
     */
    public int messageLength;

    /**
     * Reserved 2byte
     */
    public final char F9 = 0;

    /**
     * Reserved 1byte
     */
    public final char F10 = 0;

    /**
     * Reserved 1byte
     */
    public final char F11 = 0;

    /**
     * 2 byte
     */
    public short week;

    /**
     * 4 byte
     */
    public long ms;

    /**
     * Reserved 4byte
     */
    public final char F14 =0;

    /**
     * Reserved 2byte
     */
    public final short F15 = 0;

    /**
     * 2byte
     */
    public short version;

    public BinaryMessageHeader(int id) {
        super((byte)0xAA, 28, Message.MessageType.B);
        this.id = id;
    }
}
