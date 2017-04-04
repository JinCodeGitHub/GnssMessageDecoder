# GnssMessageDecoder
这是一个GNSS报文解析Java语言的库，可以解析二进制、ASCII报文。
已添加常用NMEA报文，如有特殊格式报文可自定义实现解析器进行解析。
# 报文数据来源
* 专用GNSS接收机
* Android设备的NMEA报文(通过LocationManager类添加监听器获取，具体查看Android官方帮助文档)。
* 原始报文数据文件

# 使用步骤
```java
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

decoderHandler.getDecoder(SupportedMessages.GPRMC).addListener(new 		  MessageDecoder.MessageListener<GPRMCDecoder.GPRMC>() {
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
```
