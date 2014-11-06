package ethanjones.cubes.core.settings.type;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ethanjones.data.DataGroup;

import ethanjones.cubes.core.settings.Setting;
import ethanjones.cubes.core.settings.VisualSettingManager;
import ethanjones.cubes.graphics.menu.Menu;

public class BooleanSetting extends Setting {

  private boolean b;

  public BooleanSetting() {
    this(false);
  }

  public BooleanSetting(boolean b) {
    this.b = b;
  }

  @Override
  public Actor getActor(VisualSettingManager visualSettingManager) {
    final TextButton textButton = new TextButton(b ? "True" : "False", Menu.skin);
    textButton.addListener(new EventListener() {
      @Override
      public boolean handle(Event event) {
        if (!(event instanceof ChangeListener.ChangeEvent)) return false; //easier than SettingsMenu.SaveEvent and works
        set(!get());
        textButton.setText(b ? "True" : "False");
        return true;
      }
    });
    return textButton;
  }

  public void set(boolean b) {
    this.b = b;
  }

  public boolean get() {
    return b;
  }

  @Override
  public String toString() {
    return Boolean.toString(b);
  }

  @Override
  public DataGroup write() {
    DataGroup dataGroup = new DataGroup();
    dataGroup.setBoolean("data", b);
    return dataGroup;
  }

  @Override
  public void read(DataGroup data) {
    b = data.getBoolean("data");
  }
}