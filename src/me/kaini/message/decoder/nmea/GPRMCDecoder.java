package me.kaini.message.decoder.nmea;

import me.kaini.message.decoder.ASCIIMessageDecoder;
import me.kaini.message.decoder.MessageDecoder;
import me.kaini.message.formatter.DoubleFormatter;
import me.kaini.message.formatter.NESWFormatter;
import me.kaini.message.decoder.SupportedMessages;
import me.kaini.message.message.Message;

/**
 * NMEA GPRMC Message Decoder
 * @author Canney
 * @date 2017/4/4
 */
public class GPRMCDecoder extends ASCIIMessageDecoder{

    public static final class GPRMC{

        public final double magVar;

        public final int varDir;

        public GPRMC(double magVar, int varDir) {
            this.magVar = magVar;
            this.varDir = varDir;
        }

        public double getSignedMagVar(){
            return varDir * magVar;
        }

        @Override
        public String toString() {
            return "GPRMC{" +
                    "signedMagVar=" + getSignedMagVar()+
                    '}';
        }
    }

    private double magVar;

    @Override
    public Message getMessage() {
        return SupportedMessages.GPRMC;
    }


    @Override
    protected GPRMC onDecode(String[] msgValues) {

        magVar = DoubleFormatter.getInstance().format(msgValues[10]);
        int magDir = NESWFormatter.getInstance().format(msgValues[11]);

        return new GPRMC(magVar, magDir);
    }
}
