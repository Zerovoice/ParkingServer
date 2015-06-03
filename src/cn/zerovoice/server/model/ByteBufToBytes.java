package cn.zerovoice.server.model;  
  
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
  
public class ByteBufToBytes {  
    private ByteBuf temp;  
  
    private boolean end = true;  
  
    public ByteBufToBytes() {}  
  
    public ByteBufToBytes(int length) {  
        temp = Unpooled.buffer(length);  
    }  
  
    public void reading(ByteBuf datas) {  
        datas.readBytes(temp, datas.readableBytes());  
        if (this.temp.writableBytes() != 0) {  
            end = false;  
        } else {  
            end = true;  
        }  
    }  
  
    public boolean isEnd() {  
        return end;  
    }  
  
    public byte[] readFull() {  
        if (end) {  
            byte[] contentByte = new byte[this.temp.readableBytes()];  
            this.temp.readBytes(contentByte);  
            this.temp.release();  
            return contentByte;  
        } else {  
            return null;  
        }  
    }  
  
    public byte[] read(ByteBuf datas) {  
        byte[] bytes = new byte[datas.readableBytes()];  
        datas.readBytes(bytes);  
        return bytes;  
    }  
    public static Object getObjectFromBytes(byte[] objBytes) throws Exception{ 
        if (objBytes == null || objBytes.length == 0){ 
            return null; 
        } 
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes); 
        ObjectInputStream oi = new ObjectInputStream(bi); 
        return oi.readObject(); 
    }

    public static Object readMessage(Object msg) throws Exception {
        ByteBuf datas = (ByteBuf) msg;
        byte[] bytes = new byte[datas.readableBytes()];
        datas.readBytes(bytes);
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }
    
}  
