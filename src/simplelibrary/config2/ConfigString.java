package simplelibrary.config2;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
class ConfigString extends ConfigBase{
    private static final Logger LOG = Logger.getLogger(ConfigString.class.getName());
    private String data;
    ConfigString(){}
    ConfigString(String data){
        if(data==null){
            throw new NullPointerException("Data can't be null!");
        }
        this.data = data;
    }
    @Override
    void read(DataInputStream in, short version) throws IOException{
        //Version 0:  Read/write key.  Handled outside, ignore.
        //Version 1:  Current
        data = in.readUTF();
    }
    @Override
    void write(DataOutputStream out) throws IOException{
        out.writeUTF(data);
    }
    @Override
    String getData(){
        return data;
    }
}
