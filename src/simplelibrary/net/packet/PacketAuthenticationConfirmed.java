package simplelibrary.net.packet;
import java.io.DataInputStream;
import java.io.DataOutputStream;
public class PacketAuthenticationConfirmed implements Packet {
    private static PacketAuthenticationConfirmed baseInstance;
    public PacketAuthenticationConfirmed(){
        if(baseInstance==null){
            baseInstance = this;
        }
    }
    @Override
    public Packet newInstance(){
        return new PacketAuthenticationConfirmed();
    }
    @Override
    public void readPacketData(DataInputStream datastream){}
    @Override
    public Packet baseInstance(){
        return baseInstance;
    }
    @Override
    public void writePacketData(DataOutputStream data){}
    @Override
    public String toString(){
        return getClass().getName()+"()";
    }
}
