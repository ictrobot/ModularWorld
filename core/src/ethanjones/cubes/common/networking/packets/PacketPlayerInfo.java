package ethanjones.cubes.common.networking.packets;

import com.badlogic.gdx.math.Vector3;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import ethanjones.cubes.common.core.util.VectorUtil;
import ethanjones.cubes.common.networking.packet.Packet;
import ethanjones.cubes.common.Side;
import ethanjones.cubes.common.Sided;
import ethanjones.cubes.Cubes;

public class PacketPlayerInfo extends Packet {

  public Vector3 angle;
  public Vector3 position;

  @Override
  public void write(DataOutputStream dataOutputStream) throws Exception {
    VectorUtil.writeVector3(angle, dataOutputStream);
    VectorUtil.writeVector3(position, dataOutputStream);
  }

  @Override
  public void read(DataInputStream dataInputStream) throws Exception {
    angle = VectorUtil.readVector3(dataInputStream);
    position = VectorUtil.readVector3(dataInputStream);
  }

  @Override
  public void handlePacket() {
    if (Sided.getSide() == Side.Server) {
      Cubes.getServer().getClient(getSocketMonitor()).getPlayerManager().handlePacket(this);
    } else {
      Cubes.getClient().player.angle.set(angle);
      Cubes.getClient().player.position.set(position);
    }
  }
}