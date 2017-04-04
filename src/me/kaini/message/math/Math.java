package me.kaini.message.math;

/**
 * Math util
 * @author Canney
 * @date 2016/12/29
 */
public class Math {

    public static int byteArrayToInt(byte[] b) {
        return   b[0] & 0xFF |
                (b[1] & 0xFF) << 8 |
                (b[2] & 0xFF) << 16 |
                (b[3] & 0xFF) << 24;
    }

    public static boolean[] getBooleanArray(byte b) {
        boolean[] array = new boolean[8];
        for (int i = 7; i >= 0; i--) { //对于byte的每bit进行判定
            array[7-i] = (b & 1) == 1;   //判定byte的最后一位是否为1，若为1，则是true；否则是false
            b = (byte) (b >> 1);       //将byte右移一位
        }
        return array;
    }

}
