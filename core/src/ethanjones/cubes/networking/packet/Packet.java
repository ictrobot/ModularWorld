package ethanjones.cubes.networking.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import ethanjones.cubes.networking.socket.SocketMonitor;

public abstract class Packet {

  private PacketPriority packetPriority;
  private SocketMonitor socketMonitor;

  public Packet() {
    packetPriority = PacketPriority.Medium;
  }

  public abstract void write(DataOutputStream dataOutputStream) throws Exception;

  public abstract void read(DataInputStream dataInputStream) throws Exception;

  /**
   * Called right before writing packet into output stream
   *
   * @return if packet should be send
   */
  public boolean shouldSend() {
    return true;
  }

  public abstract void handlePacket();

  public void setPacketPriority(PacketPriority packetPriority) {
    this.packetPriority = packetPriority;
  }

  public PacketPriority getPacketPriority() {
    return packetPriority;
  }

  public void setSocketMonitor(SocketMonitor socketMonitor) {
    this.socketMonitor = socketMonitor;
  }

  public SocketMonitor getSocketMonitor() {
    return socketMonitor;
  }
}
