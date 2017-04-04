package me.kaini.message.decoder;

import me.kaini.message.message.ASCIIMessageHeader;
import me.kaini.message.message.BinaryMessageHeader;
import me.kaini.message.message.Message;

/**
 * Supported Messages
 * Created by ChenQiang on 2016/12/29.
 */
public interface SupportedMessages {

    Message ASCII_MESSAGE = new Message(new ASCIIMessageHeader('$', "", '\r', '\n'));

    Message BINARY_MESSAGE = new Message(new BinaryMessageHeader(-1));

    Message M911B = new Message(new BinaryMessageHeader(911));

    Message GPGGA = new Message(new ASCIIMessageHeader("GPGGA"));

    Message GPRMC = new Message(new ASCIIMessageHeader("GPRMC"));

}
