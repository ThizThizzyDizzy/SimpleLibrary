package simplelibrary.net.packet;
public class PacketEncryptionSupported extends PacketString{
    private static PacketEncryptionSupported baseInstance;
    public PacketEncryptionSupported(){
        if(baseInstance==null){
            baseInstance = this;
        }
    }
    public PacketEncryptionSupported(String value){
        super(value);
    }
    @Override
    public Packet newInstance(){
        return new PacketEncryptionSupported();
    }
    @Override
    public Packet baseInstance(){
        return baseInstance;
    }
}
