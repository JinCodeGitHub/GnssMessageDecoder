package me.kaini.message.formatter;

/**
 * North South East West to int
 *          1(N)
 *           |
 *           |
 * -1(W) -------- 1(E)
 *           |
 *           |
 *         -1(S)
 * @author Canney
 * @date 2017/2/21
 */
public class NESWFormatter {

    private static NESWFormatter ourInstance = new NESWFormatter();

    public static final char W = 'W';
    public static final char E = 'E';
    public static final char S = 'S';
    public static final char N = 'N';

    public static NESWFormatter getInstance() {
        return ourInstance;
    }

    private NESWFormatter() {
    }

    public int format(String value){
        //default value 0;
        char val = 48;
        if(!"".equals(value)){
            val = value.charAt(0);
        }
        return format(val);
    }

    public int format(char value){

        switch (value){
            case W:
                return -1;
            case E:
                return 1;
            case S:
                return -1;
            case N:
                return 1;
            default:
                return 1;
        }

    }
}
