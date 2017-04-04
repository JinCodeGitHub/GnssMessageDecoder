package me.kaini.message.decoder.nmea;

import me.kaini.message.decoder.ASCIIMessageDecoder;
import me.kaini.message.decoder.MessageDecoder;
import me.kaini.message.decoder.SupportedMessages;
import me.kaini.message.message.Message;

import java.util.Arrays;

/**
 *
 * NMEA GPGGA message decoder
 *
 * @author Canney
 * @date 2017/4/4
 */
public class GPGGADecoder extends ASCIIMessageDecoder<GPGGADecoder.GPGGA>{

    public static class GPGGA{

        String gpgga;

        public GPGGA(String gpgga) {
            this.gpgga = gpgga;
        }

        @Override
        public String toString() {
            return "GPGGA{" +
                    "gpgga='" + gpgga + '\'' +
                    '}';
        }
    }

    @Override
    public Message getMessage() {
        return SupportedMessages.GPGGA;
    }


    @Override
    protected GPGGA onDecode(String[] msgValues) {

        return new GPGGA(Arrays.toString(msgValues));
    }
}
