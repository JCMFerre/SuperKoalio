package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class InicialScreen implements Screen {

    private Game game;
    private Stage stage;

    public InicialScreen(Game game) {
        this.game = game;
        this.stage = new Stage();

        this.stage.addActor(new Image());
    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void dispose() {
        game.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

}
