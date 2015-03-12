package ethanjones.cubes.common.world.thread;

import java.util.concurrent.Callable;

import ethanjones.cubes.common.networking.NetworkingManager;
import ethanjones.cubes.common.networking.packets.PacketArea;
import ethanjones.cubes.common.networking.server.ClientIdentifier;
import ethanjones.cubes.server.PlayerManager;
import ethanjones.cubes.common.world.storage.Area;

public class SendWorldCallable implements Callable {

  private final ClientIdentifier clientIdentifier;
  private final GenerateWorldCallable generateWorldCallable;
  private final Area area;
  private final PlayerManager playerManager;

  public SendWorldCallable(Area area, ClientIdentifier clientIdentifier, PlayerManager playerManager) {
    this.clientIdentifier = clientIdentifier;
    this.generateWorldCallable = null;
    this.area = area;
    this.playerManager = playerManager;
  }

  public SendWorldCallable(GenerateWorldCallable generateWorldCallable, ClientIdentifier clientIdentifier, PlayerManager playerManager) {
    this.clientIdentifier = clientIdentifier;
    this.generateWorldCallable = generateWorldCallable;
    this.area = null;
    this.playerManager = playerManager;
  }

  @Override
  public Object call() throws Exception {
    Area area = generateWorldCallable != null ? generateWorldCallable.call() : this.area;
    PacketArea packetArea = new PacketArea();
    packetArea.area = area;
    packetArea.playerManager = playerManager;
    NetworkingManager.sendPacketToClient(packetArea, clientIdentifier);
    return area;
  }
}