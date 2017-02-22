# GnssMessageDecoder
这是一个GNSS报文解析Java语言的库，可以解析二进制、ASCII报文。
已添加常用NMEA报文，如有特殊格式报文可自定义实现解析器进行解析。
# 报文数据来源
* 专用GNSS接收机
* Android设备的NMEA报文(通过LocationManager类添加监听器获取，具体查看Android官方帮助文档)。
