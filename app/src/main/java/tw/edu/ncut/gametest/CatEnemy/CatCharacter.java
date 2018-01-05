package tw.edu.ncut.gametest.CatEnemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/4.
 */

//see annotation on Character.java
public abstract class CatCharacter extends Character {

    protected int heal;
    protected int attack;
    protected int x, y, w, h;
    protected Bitmap bitmap;

    @Deprecated
    public CatCharacter(Context context, int heal, int attack, int x, int y, int w, int h, int r_bitmap) {
        this.heal = heal;
        this.attack = attack;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        bitmap = BitmapFactory.decodeResource(context.getResources(), r_bitmap);
        if(bitmap.getWidth() != w && bitmap.getHeight() != h) {
            bitmap = Bitmap.createScaledBitmap(bitmap, w, h, false);
        }
    }

    public CatCharacter(Bitmap bitmap, int heal, int attack, int x, int y, int w, int h){
        this.heal = heal;
        this.attack = attack;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.bitmap = bitmap;
    }

    public CatCharacter(int heal, int attack, int x, int y, int w, int h){
        this.heal = heal;
        this.attack = attack;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    protected abstract void update(int screenWidth, int screenHeight);

    @Override
    public abstract void onHit(Character character);

    @Override
    public Rect getRect() {
        return new Rect(x, y, x + w, y + h);
    }

    @Override
    void onDraw(Canvas canvas) {
        if(bitmap != null) {
            canvas.drawBitmap(bitmap, x, y, null);
        }
    }

    @Override
    public int getHeal() {
        return heal;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    void moveRight(int screenWidth, int stepSize) {
        if (x + w + stepSize > screenWidth) {
            return;
        }
        x += stepSize;
    }

    void moveLeft(int stepSize) {

        if (x - stepSize < 0) {
            return;
        }
        x -= stepSize;
    }
}
