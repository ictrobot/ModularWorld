package ethanjones.modularworld.networking.common;

import ethanjones.modularworld.networking.common.packet.Packet;
import ethanjones.modularworld.networking.common.packet.PacketBuffer;
import ethanjones.modularworld.networking.common.packet.PacketHandler;
import ethanjones.modularworld.networking.common.socket.SocketMonitor;
import ethanjones.modularworld.side.Side;

public abstract class Networking implements PacketHandler {

  private PacketBuffer packetBuffer;
  private final Side side;
  private volatile NetworkingState networkingState;

  public Networking(Side side) {
    this.side = side;
    this.packetBuffer = new PacketBuffer();
  }

  protected void setNetworkingState(NetworkingState networkingState) {
    this.networkingState = networkingState;
  }

  public NetworkingState getNetworkingState() {
    return networkingState;
  }

  public abstract void start();

  public abstract void update();

  public abstract void stop();

  /**
   * @param e may be null
   */
  public abstract void disconnected(SocketMonitor socketMonitor, Exception e);

  @Override
  public final void received(Packet packet) {
    packetBuffer.addPacket(packet, side);
  }

  /**
   * Call on main thread
   */
  public final void processPackets() {
    packetBuffer.process();
  }
}
