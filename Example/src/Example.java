import me.kaini.message.MessageDecoderHandler;
import me.kaini.message.decoder.MessageDecoder;
import me.kaini.message.decoder.SupportedMessages;
import me.kaini.message.decoder.nmea.GPGGADecoder;
import me.kaini.message.decoder.nmea.GPRMCDecoder;

import java.io.File;
import java.io.FileInputStream;

/**
 * Example for gnss-message-decoder
 * @author Canney
 * @date 2017/4/4
 */
public class Example {

    public static void main(String[] args){

        try{

            //step 1
            //create decode handler
            MessageDecoderHandler decoderHandler = new MessageDecoderHandler();

            //step 2
            //register message decoder
            decoderHandler.register(new MessageDecoder[]{new GPGGADecoder(), new GPRMCDecoder()});

            //step 3
            //add decoded listener
            decoderHandler.getDecoder(SupportedMessages.GPGGA).addListener(new MessageDecoder.MessageListener<GPGGADecoder.GPGGA>() {
                @Override
                public void onData(GPGGADecoder.GPGGA data) {
                    System.out.println(data);
                }
            });

            decoderHandler.getDecoder(SupportedMessages.GPRMC).addListener(new MessageDecoder.MessageListener<GPRMCDecoder.GPRMC>() {
                @Override
                public void onData(GPRMCDecoder.GPRMC gprmc) {
                    System.out.println(gprmc);
                }
            });

            //step 4
            //start decode
            decoderHandler.start();

            //step 5
            //write into raw data to message stream
            MessageDecoderHandler.MessageStream ms = decoderHandler.getMessageStream();

            //Simulate message raw data
            FileInputStream fis = new FileInputStream(new File("F:\\MessageDecoder\\gga_rmc.cnb"));

            int len;

            byte[] buf = new byte[2048];

            while((len = fis.read(buf)) != -1){

                byte[] data = new byte[len];
                System.arraycopy(buf, 0, data, 0, len);
                ms.write(data);

                Thread.sleep(1000);
            }


        }catch (Exception e){
            e.printStackTrace();
        }


    }

}


