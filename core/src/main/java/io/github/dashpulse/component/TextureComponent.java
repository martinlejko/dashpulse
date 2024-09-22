package io.github.dashpulse.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class TextureComponent implements Component {
    public Texture texture;

    // Default constructor, no texture needed at first
    public TextureComponent() {
        this.texture = null; // Texture will be set later by the AnimationSystem
    }

    // Constructor that allows passing a texture
    public TextureComponent(Texture texture) {
        this.texture = texture;
    }
}
