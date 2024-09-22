package io.github.dashpulse.event;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Event;

public class MapChangeEvent extends Event {
    public final TiledMap map;

    public MapChangeEvent(TiledMap map) {
        this.map = map;
    }
}
