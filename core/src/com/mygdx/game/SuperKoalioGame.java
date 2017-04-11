package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.screens.GameScreen;

public class SuperKoalioGame extends Game {

    @Override
    public void create() {
        AssetManager.load();
        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetManager.dispose();
    }
}
