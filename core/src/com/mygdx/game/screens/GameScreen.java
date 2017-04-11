package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.objects.Koala;

public class GameScreen implements Screen {

    private Game game;
    private Koala koala;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    public GameScreen(Game game) {
        this.game = game;
        this.koala = new Koala(20, 20, AssetManager.koalaWidth, AssetManager.koalaHeight);
        this.camera = new OrthographicCamera();
        this.renderer = new OrthogonalTiledMapRenderer(AssetManager.nivel1, 1 / 16f);
        this.camera.setToOrtho(false, 30, 20);
        this.camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        koala.act(delta);
        camera.position.x = koala.getPosition().x;
        camera.update();
        renderer.setView(camera);
        renderer.render();
        koala.draw(renderer.getBatch());
        if (koala.isDead()) {
            lanzarScreenInicial();
        }
    }

    private void lanzarScreenInicial() {
        game.setScreen(new InicialScreen(game));
    }

    @Override
    public void dispose() {
        game.dispose();
    }

    @Override
    public void show() {
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

    @Override
    public void resize(int width, int height) {
    }
}
