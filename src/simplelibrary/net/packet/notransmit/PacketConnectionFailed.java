package simplelibrary.net.packet.notransmit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import simplelibrary.net.packet.Packet;
/**
 * A class used by the connection subsystem to report file receipt progress.  Do not transmit.
 * @author Bryan
 */
public class PacketConnectionFailed implements Packet{
    public final String reason;
    public PacketConnectionFailed(String reason){
        this.reason = reason;
    }
    @Override
    public Packet newInstance(){
        throw new IllegalStateException("This packet type not to be transmitted.");
    }
    @Override
    public void readPacketData(DataInputStream in) throws IOException{
        throw new IllegalStateException("This packet type not to be transmitted.");
    }
    @Override
    public Packet baseInstance(){
        throw new IllegalStateException("This packet type not to be transmitted.");
    }
    @Override
    public void writePacketData(DataOutputStream out) throws IOException{
        throw new IllegalStateException("This packet type not to be transmitted.");
    }
    @Override
    public String toString(){
        return getClass().getName()+"(reason="+reason+")";
    }
}
