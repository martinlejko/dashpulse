package io.github.dashpulse.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.logging.Logger;

public class GameScreen implements Screen {
    private static final Logger logger = Logger.getLogger(GameScreen.class.getName());
    private final  Stage stage = new Stage(new ExtendViewport(16f, 9f));
    private final Texture texture = new Texture("graphics/Idle-Sheet.png");
    @Override
    public void show() {
        logger.fine("Game screen is shown");
        Image image = new Image(texture);
        image.setPosition(1f, 1f);
        image.setSize(1f,1f);
        image.setScaling(Scaling.fill);
        stage.addActor(image);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //also center the camera and set the viewport
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        texture.dispose();
        stage.dispose();
    }
}
