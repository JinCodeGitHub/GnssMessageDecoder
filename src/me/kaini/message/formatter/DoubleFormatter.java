package me.kaini.message.formatter;

/**
 * String double value to double formatter
 * @author Canney
 * @date 2017/2/21
 */
public class DoubleFormatter {

    public static DoubleFormatter instance;

    private DoubleFormatter(){}

    public static DoubleFormatter getInstance(){
        if(instance == null){
            instance = new DoubleFormatter();
        }
        return instance;
    }


    public double format(String value){
        if("".equals(value)){
            return 0;
        }else{
            try{
                return Double.valueOf(value);
            }catch (NumberFormatException e){
                return 0;
            }
        }
    }

}
