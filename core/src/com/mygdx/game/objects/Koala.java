package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.helpers.AssetManager;

public class Koala {

    public static float MAX_VELOCITY = 10f;
    public static float JUMP_VELOCITY = 40f;
    public static float DAMPING = 0.87f;

    enum State {
        Standing, Walking, Jumping
    }

    private float width, height;
    private Vector2 position;
    private Vector2 velocity;
    private State state;
    private float stateTime;
    private boolean facesRight;
    private boolean grounded;
    private Pool<Rectangle> rectPool;
    private Array<Rectangle> tiles;
    private TiledMap nivel1;
    private Animation<TextureRegion> stand, walk, jump;

    private boolean dead;

    public Koala(float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2();
        this.state = State.Walking;
        this.facesRight = true;
        this.rectPool = new Pool<Rectangle>() {
            @Override
            protected Rectangle newObject() {
                return new Rectangle();
            }
        };
        this.nivel1 = AssetManager.nivel1;
        this.stand = AssetManager.stand;
        this.walk = AssetManager.walk;
        this.jump = AssetManager.jump;
        this.tiles = new Array<Rectangle>();
    }

    public void draw(Batch batch) {
        batch.begin();
        if (facesRight) {
            batch.draw(getTextureRegionKoala(), position.x, position.y, width, height);
        } else {
            batch.draw(getTextureRegionKoala(), position.x + width, position.y, -width, height);
        }
        batch.end();
    }

    public void act(float delta) {
        if (delta == 0) return;
        if (delta > 0.1f)
            delta = 0.1f;
        stateTime += delta;
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || isTouched(0.5f, 1)) && grounded) {
            velocity.y += Koala.JUMP_VELOCITY;
            state = Koala.State.Jumping;
            grounded = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A) || isTouched(0, 0.25f)) {
            velocity.x = -Koala.MAX_VELOCITY;
            if (grounded) state = Koala.State.Walking;
            facesRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D) || isTouched(0.25f, 0.5f)) {
            velocity.x = Koala.MAX_VELOCITY;
            if (grounded) state = Koala.State.Walking;
            facesRight = true;
        }
        velocity.add(0, /*GRAVITY*/-2.5f);
        velocity.x = MathUtils.clamp(velocity.x,
                -Koala.MAX_VELOCITY, Koala.MAX_VELOCITY);
        if (Math.abs(velocity.x) < 1) {
            velocity.x = 0;
            if (grounded) state = Koala.State.Standing;
        }
        velocity.scl(delta);

        Rectangle koalaRect = rectPool.obtain();
        koalaRect.set(position.x, position.y, width, height);
        int startX, startY, endX, endY;
        if (velocity.x > 0) {
            startX = endX = (int) (position.x + width + velocity.x);
        } else {
            startX = endX = (int) (position.x + velocity.x);
        }
        startY = (int) (position.y);
        endY = (int) (position.y + height);
        getTiles(startX, startY, endX, endY, tiles);
        koalaRect.x += velocity.x;
        for (Rectangle tile : tiles) {
            if (koalaRect.overlaps(tile)) {
                velocity.x = 0;
                break;
            }
        }
        koalaRect.x = position.x;
        if (velocity.y > 0) {
            startY = endY = (int) (position.y + height + velocity.y);
        } else {
            startY = endY = (int) (position.y + velocity.y);
        }
        startX = (int) (position.x);
        endX = (int) (position.x + width);
        getTiles(startX, startY, endX, endY, tiles);
        koalaRect.y += velocity.y;
        for (Rectangle tile : tiles) {
            if (koalaRect.overlaps(tile)) {
                if (velocity.y > 0) {
                    position.y = tile.y - height;
                    TiledMapTileLayer layer = (TiledMapTileLayer) nivel1.getLayers().get("walls");
                    layer.setCell((int) tile.x, (int) tile.y, null);
                } else {
                    position.y = tile.y + tile.height;
                    grounded = true;
                }
                velocity.y = 0;
                break;
            }
        }
        rectPool.free(koalaRect);
        position.add(velocity);
        velocity.scl(1 / delta);
        velocity.x *= DAMPING;
        dead = position.y <= 0;
    }

    private boolean isTouched(float startX, float endX) {
        for (int i = 0; i < 2; i++) {
            float x = Gdx.input.getX(i) / (float) Gdx.graphics.getWidth();
            if (Gdx.input.isTouched(i) && (x >= startX && x <= endX)) {
                return true;
            }
        }
        return false;
    }

    public Vector2 getPosition() {
        return position;
    }

    private TextureRegion getTextureRegionKoala() {
        TextureRegion frame = null;
        switch (state) {
            case Standing:
                frame = stand.getKeyFrame(stateTime);
                break;
            case Walking:
                frame = walk.getKeyFrame(stateTime);
                break;
            case Jumping:
                frame = jump.getKeyFrame(stateTime);
                break;
        }
        return frame;
    }

    private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
        TiledMapTileLayer layer = (TiledMapTileLayer) nivel1.getLayers().get("walls");
        rectPool.freeAll(tiles);
        tiles.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 1, 1);
                    tiles.add(rect);
                }
            }
        }
    }

    public boolean isDead() {
        return dead;
    }
}
