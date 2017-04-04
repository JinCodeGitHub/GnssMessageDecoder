package me.kaini.message.decoder;

/**
 * Message Cache
 * @author Canney Chen
 */
public class Cache {

    /**
     * Cache size
     */
    private int size;

    private byte[] cache;

    /**
     * data index
     */
    private int index = 0;

    public Cache(int len) {
        this.size = len;
        cache = new byte[len];
    }

    /**
     * Push message data to this cache
     * @param data message raw data
     */
    public void put(byte[] data){

        if(index + data.length > size){
            clear();
        }

        synchronized(cache){
            System.arraycopy(data, 0, cache, index, data.length);
            index += data.length;
        }

    }

    /**
     * Clear cache
     */
    public  void clear(){
        clear(size);
    }

    /**
     * Clear data from current decode index
     * @param length clear raw data length
     */
    public void clear(int length){
        if(length <= 0){
            return ;
        }
        if(length == size || length > index){
            index = 0;
        }else{
            //将后半部分数据复制

            int validDataLen = (index + 1 - length);

            byte[] endData = new byte[validDataLen];

            synchronized(cache){
                System.arraycopy(cache, length, endData, 0, validDataLen);

                System.arraycopy(endData, 0, cache, 0, validDataLen);
                index -= validDataLen;
            }

        }
    }

    /**
     * Get raw data length, from current decode index.
     * @param length Get raw data length.
     * @return Raw message data
     */
    public byte[] get(int length){

        byte[] data = new byte[length];

        synchronized (cache){
            System.arraycopy(cache, index, data, 0, length);
        }

        return data;
    }

}
