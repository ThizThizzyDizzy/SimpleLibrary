package simplelibrary.net.packet;
public class PacketEncryptionNotSupported extends PacketString{
    private static PacketEncryptionNotSupported baseInstance;
    public PacketEncryptionNotSupported(){
        if(baseInstance==null){
            baseInstance = this;
        }
    }
    public PacketEncryptionNotSupported(String value){
        super(value);
    }
    @Override
    public Packet newInstance(){
        return new PacketEncryptionNotSupported();
    }
    @Override
    public Packet baseInstance(){
        return baseInstance;
    }
}
