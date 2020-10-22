package simplelibrary.net.packet;
public class PacketCheckEncryption extends PacketString{
    private static PacketCheckEncryption baseInstance;
    public PacketCheckEncryption(){
        if(baseInstance==null){
            baseInstance = this;
        }
    }
    public PacketCheckEncryption(String value){
        super(value);
    }
    @Override
    public Packet newInstance(){
        return new PacketCheckEncryption();
    }
    @Override
    public Packet baseInstance(){
        return baseInstance;
    }
}
