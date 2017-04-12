package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.helpers.Settings;

public class InicialScreen implements Screen {

    private final Game game;
    private Stage stage;

    public InicialScreen(Game game) {
        // Hay que ser preciso con los botones porque si no, no van.
        this.game = game;
        configCamara();
        this.stage.addActor(new Image(AssetManager.backgroundTexture));
        configurarTextButtonVacios();
        Gdx.input.setInputProcessor(stage);
    }

    private void configurarTextButtonVacios() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        TextButton btnJugar = new TextButton("", textButtonStyle);
        btnJugar.setTransform(true);
        btnJugar.setWidth(3f);
        btnJugar.setHeight(1f);
        btnJugar.setScale(2f);
        btnJugar.setPosition(135 + 25f, 142 - 3f);
        btnJugar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                InicialScreen.this.game.setScreen(new GameScreen(InicialScreen.this.game));
            }
        });
        TextButton btnSalir = new TextButton("", textButtonStyle);
        btnSalir.setTransform(true);
        btnSalir.setWidth(2f);
        btnSalir.setHeight(1f);
        btnSalir.setScale(2f);
        btnSalir.setPosition(130f + 30f, 95f - 5f);
        btnSalir.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        stage.addActor(btnJugar);
        stage.addActor(btnSalir);
    }

    private void configCamara() {
        OrthographicCamera camera = new OrthographicCamera(Settings.WIDTH, Settings.HEIGHT);
        StretchViewport viewport = new StretchViewport(Settings.WIDTH, Settings.HEIGHT, camera);
        this.stage = new Stage(viewport);
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
