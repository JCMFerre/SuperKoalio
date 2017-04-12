package com.mygdx.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetManager {

    public static Texture koalaTexture, backgroundTexture;
    public static Animation<TextureRegion> stand, walk, jump;
    public static TiledMap nivel1;
    public static TextureRegion background;

    public static float koalaWidth, koalaHeight;

    public static void load() {
        koalaTexture = new Texture("koalio.png");
        TextureRegion[] regions = TextureRegion.split(koalaTexture, 18, 26)[0];
        koalaWidth = 1 / 16f * regions[0].getRegionWidth();
        koalaHeight = 1 / 16f * regions[0].getRegionHeight();
        stand = new Animation(0, regions[0]);
        jump = new Animation(0, regions[1]);
        walk = new Animation(0.15f, regions[2], regions[3], regions[4]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        nivel1 = new TmxMapLoader().load("level1.tmx");
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        background = new TextureRegion(backgroundTexture);
        background.flip(false, true);
    }

    public static void dispose() {
        koalaTexture.dispose();
        backgroundTexture.dispose();
    }
}
