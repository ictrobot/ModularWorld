package ethanjones.cubes.side.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import ethanjones.cubes.core.mod.ModManager;
import ethanjones.cubes.core.mod.event.StartingClientEvent;
import ethanjones.cubes.core.mod.event.StoppingClientEvent;
import ethanjones.cubes.core.platform.Adapter;
import ethanjones.cubes.core.platform.Compatibility;
import ethanjones.cubes.core.system.CubesException;
import ethanjones.cubes.entity.living.player.Player;
import ethanjones.cubes.graphics.rendering.Renderer;
import ethanjones.cubes.input.InputChain;
import ethanjones.cubes.input.keyboard.KeyboardHelper;
import ethanjones.cubes.networking.NetworkingManager;
import ethanjones.cubes.side.Side;
import ethanjones.cubes.side.common.Cubes;
import ethanjones.cubes.world.client.WorldClient;

public class CubesClient extends Cubes implements ApplicationListener {

  public Player player;
  public InputChain inputChain;
  public Renderer renderer;

  public CubesClient() {
    super(Side.Client);
    if (Compatibility.get().isServer()) throw new CubesException("Cannot run client on server");
  }

  @Override
  public void create() {
    super.create();
    NetworkingManager.clientInit();

    inputChain = new InputChain();
    renderer = new Renderer();
    player = new Player(renderer.block.camera);

    inputChain.setup();
    Gdx.input.setInputProcessor(InputChain.getInputMultiplexer());

    world = new WorldClient();

    ModManager.postModEvent(new StartingClientEvent());
  }

  @Override
  public void render() {
    if (stopped) return;
    if (KeyboardHelper.isKeyDown(Input.Keys.ESCAPE)) {
      Adapter.gotoMainMenu();
      return;
    }
    super.render();
    inputChain.beforeRender();
    if (renderer.hud.isDebugEnabled()) ClientDebug.update();
    renderer.render();
    inputChain.afterRender();
    player.update();
    checkStop();
  }

  @Override
  public void stop() {
    if (stopped) return;
    ModManager.postModEvent(new StoppingClientEvent());
    super.stop();
    renderer.dispose();
    inputChain.dispose();
  }

  @Override
  public void tick() {
    super.tick();
    inputChain.cameraController.tick();
  }

  @Override
  public void resize(int width, int height) {
    renderer.resize();
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }
}
