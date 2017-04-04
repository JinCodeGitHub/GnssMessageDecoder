package me.kaini.message;

import me.kaini.message.decoder.Cache;
import me.kaini.message.decoder.MessageDecoder;
import me.kaini.message.decoder.SupportedMessages;
import me.kaini.message.message.*;
import me.kaini.message.math.Math;

import java.util.HashMap;
import java.util.Map;

/**
 * Message Decode Handler.
 * use {@link #start()} start decode thread
 * use {@link #stop()} stop decode thread
 * use {@link #getMessageStream()} put message raw data
 *
 * @auther Canney
 * @date 2016/12/29
 */
public class MessageDecoderHandler {

    /**
     * Message raw data stream
     */
    public class MessageStream {

        private MessageDecoderHandler decoderHandler;

        public MessageStream(MessageDecoderHandler decoderHandler) {
            this.decoderHandler = decoderHandler;
        }

        public void write(byte[] data) {
            decoderHandler.receive(data);
        }
    }

    /**
     * Decode thread running state.
     */
    private boolean running = false;

    /**
     * Decode thread paused state.
     */
    private boolean paused = false;

    /**
     * Decode thread state control lock.
     */
    private Object lock = new Object();

    /**
     * Get message raw data size from cache(64kb)
     */
    private static int BUFF_SIZE = 65536;

    /**
     * Message raw cache(128kb)
     */
    private Cache cache = new Cache(BUFF_SIZE * 2);

    /**
     * Binary Decoders.
     * @param <Integer> Binary message ID {@link BinaryMessageHeader#id}
     */
    private Map<Integer, MessageDecoder> binaryDecoders = new HashMap<Integer, MessageDecoder>();

    /**
     * ASCII Decoders
     * @parm <String> ASCII Message ID {@link ASCIIMessageHeader#id}
     */
    private Map<String, MessageDecoder> asciiDecoders = new HashMap<String, MessageDecoder>();

    /**
     * Message decode thread.
     */
    private Thread decodeThread;

    /**
     * Message raw data stream.
     */
    private MessageStream stream = new MessageStream(this);

    public void register(MessageDecoder[] decoders){
        for (MessageDecoder decoder : decoders){
            register(decoder);
        }
    }

    public void register(MessageDecoder decoder){

        if(decoder.getMessage().header.messageType == Message.MessageType.A){
            asciiDecoders.put(((ASCIIMessageHeader)decoder.getMessage().header).id, decoder);
        }else{
            binaryDecoders.put(((BinaryMessageHeader)decoder.getMessage().header).id, decoder);
        }

        resume();
    }


    public MessageDecoder getDecoder(Message message){
        MessageDecoder decoder;
        if(message.header.messageType == Message.MessageType.B){
            decoder = binaryDecoders.get(((BinaryMessageHeader)message.header).id);
        }else{
            decoder= asciiDecoders.get(((ASCIIMessageHeader)message.header).id);
        }
        return decoder;
    }

    private void receive(byte[] data) {
        cache.put(data);
    }

    public void stop(){
        running = false;
    }

    public void start(){
        decodeThread = new DecodeThread();
        running = true;
        decodeThread.start();
    }

    /**
     * Pause decode thread
     * {@link #resume()} restart thread
     */
    public void pause(){
        if(!paused){
            try {
                synchronized (lock){
                    paused = true;
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * restart pause thread
     */
    public void resume(){
        if (paused) {
            synchronized (lock) {
                paused = false;
                lock.notify();
            }
        }
    }

    /**
     *
     * @return Message raw data stream
     */
    public MessageStream getMessageStream(){
        return stream;
    }

    /**
     * Decode thread
     */
    class DecodeThread extends Thread{
        @Override
        public void run() {

            while(running) {
                synchronized (lock) {
                    if (binaryDecoders.isEmpty() && asciiDecoders.isEmpty()) {
                        pause();
                    }
                }

                byte[] data = cache.get(BUFF_SIZE);

                int index = 0;

                while (index < BUFF_SIZE) {

                    byte b = data[index];

                    if (b == SupportedMessages.BINARY_MESSAGE.header.f1) {
                        BinaryMessageHeader binHeader = (BinaryMessageHeader) SupportedMessages.BINARY_MESSAGE.header;
                        if (index + 3 < data.length) {
                            //header pattern
                            if (data[index + 1] == binHeader.F2 && data[index + 2] == binHeader.F3) {
                                //body length pattern
                                if (index + SupportedMessages.BINARY_MESSAGE.header.length < data.length) {
                                    byte[] idByte = new byte[]{0, 0, 0, 0};
                                    System.arraycopy(data, index + 4, idByte, 0, 2);

                                    int id = Math.byteArrayToInt(idByte);

                                    //body length
                                    System.arraycopy(data, index + 8, idByte, 0, 2);
                                    int len = Math.byteArrayToInt(idByte);

                                    int msgLen = binHeader.length + len + SupportedMessages.BINARY_MESSAGE.crcLength;

                                    //完整报文头判断
                                    if (index + len + SupportedMessages.BINARY_MESSAGE.crcLength > data.length) {
                                        break;
                                    } else {
                                        MessageDecoder decoder = binaryDecoders.get(id);
                                        if(decoder != null){
                                            BinaryMessageHeader header = (BinaryMessageHeader) decoder.getMessage().header;

                                            byte[] messageBodyData = new byte[len];
                                            System.arraycopy(data, index + header.length, messageBodyData, 0, len);
                                            decoder.decode(messageBodyData);

                                            cache.clear(msgLen);

                                        }

                                        index += msgLen;

                                        continue;
                                    }

                                }

                            } else {
                                cache.clear(3);
                                index += 3;
                                continue;
                            }
                        }

                    }else if(b == SupportedMessages.ASCII_MESSAGE.header.f1){ //Decode ASCII Message

                        boolean patterned = false;

                        for (MessageDecoder decoder : asciiDecoders.values()){
                            ASCIIMessageHeader ASCIIHeader = (ASCIIMessageHeader) decoder.getMessage().header;

                            if(b == ASCIIHeader.F1){
                                if(b + ASCIIHeader.length < BUFF_SIZE){
                                    byte[] headerBytes = new byte[ASCIIHeader.length];
                                    System.arraycopy(data, index, headerBytes, 0, ASCIIHeader.length);

                                    String msgHeader = new String(headerBytes);

                                    if(ASCIIHeader.header.equals(msgHeader)){

                                        patterned = true;

                                        int endIdx = -1;
                                        for(int i = index; i < BUFF_SIZE - 1; i++){
                                            if(data[i] == ASCIIHeader.FE2 && data[i+1] == ASCIIHeader.FE1){
                                                endIdx = i;
                                                break;
                                            }
                                        }

                                        // Get message content
                                        if(endIdx != -1){

                                            int msgLen = endIdx - index;

                                            byte[] msgBytes = new byte[msgLen];

                                            System.arraycopy(data, index, msgBytes, 0, msgLen);

                                            //call message decoderHandler to decode single message
                                            decoder.decode(msgBytes);

                                            cache.clear(msgLen);
                                            index += msgLen;

                                            break;

                                        }

                                    }
                                }

                            }
                        }
                        if(!patterned){
                            cache.clear(1);
                            index++;
                        }

                    }else{
                        cache.clear(1);
                        index++;
                        continue;
                    }

                    index++;
                }

            }
        }
    }


}
