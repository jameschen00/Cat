package tw.edu.ncut.gametest.CatEnemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/3.
 */

public class BlueCat extends Character {
    private Bitmap bitmap;
    private int x = 0;
    private int y = 0;
    private int w = 15;
    private int h = 15;
    private int stepSize = 1;

    public BlueCat(Context context,int width, int height) {
        super(100, 20);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.miau2);
        bitmap = Bitmap.createScaledBitmap(bitmap, w, h, false);
        x = width - w;
        y = height - h;
        tag = "BLUE TEAM";
    }

    @Override
    protected void moveEvent(int screenWidth, int screenHeight) {
        List<Character> collisionList = this.getCollisionList();
        Character character = null;

        for(Character c : collisionList) {
            if(c.getTag().equals("RED TEAM")){
                character = c;
                character.onHit(this);
                break;
            }
        }

        if(character == null) {
            if (x - stepSize < 0) {
                return;
            }
            x -= stepSize;
        }
    }

    @Override
    public void onHit(Character character) {
        heal -= character.getAttack();
        if(heal <= 0) {
            this.state = CharacterState.WAIT_FOR_DESTROY;
        }
    }

    @Override
    public Rect getRect() {
        return new Rect(x, y, x + w, y + h);
    }

    @Override
    void onDraw(Canvas canvas) {
        Paint pen = new Paint();
        canvas.drawBitmap(bitmap, x, y, pen);
    }
}
