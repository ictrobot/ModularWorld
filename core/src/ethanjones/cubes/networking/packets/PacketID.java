package ethanjones.cubes.networking.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import ethanjones.cubes.networking.packet.Packet;
import ethanjones.cubes.networking.packet.PacketPriority;

public class PacketID extends Packet {

  public String c;
  public int id;

  public PacketID() {
    setPacketPriority(PacketPriority.High);
  }

  @Override
  public void write(DataOutputStream dataOutputStream) throws Exception {
    dataOutputStream.writeUTF(c);
    dataOutputStream.writeInt(id);
  }

  @Override
  public void read(DataInputStream dataInputStream) throws Exception {
    c = dataInputStream.readUTF();
    id = dataInputStream.readInt();
    getSocketMonitor().getNetworking().getPacketIDDatabase().process(this);
  }

  @Override
  public void handlePacket() {
    //Should process here...
  }
}
