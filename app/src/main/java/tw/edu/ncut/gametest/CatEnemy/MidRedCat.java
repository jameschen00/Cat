package tw.edu.ncut.gametest.CatEnemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/3.
 */

//see annotation on Character.java
public class MidRedCat extends CatCharacter {
    private int stepSize = 1;
    private int attackSpeed = 0;
    private int attackCount = 0;

    public static final int CatWidth = 100;
    public static final int CatHeight = 100;

    @Deprecated
    public MidRedCat(Context context, int x, int y) {
        super(context, 200, 32, x, y, CatWidth, CatHeight, R.drawable.cat_red);
        tag = "RED TEAM";
    }

    public MidRedCat(Bitmap bitmap, int x, int y) {
        super(bitmap, 200, 32, x, y, CatWidth, CatHeight);
        tag = "RED TEAM";
    }

    @Override
    protected void update(int screenWidth, int screenHeight) {
        List<Character> collisionList = this.getCollisionList();
        Character character = null;

        for(Character c : collisionList) {
            if(c.getTag().equals("BLUE TEAM") && c.getState() != CharacterState.WAIT_FOR_DESTROY){
                character = c;
                if(attackCount == attackSpeed) {
                    attackCount = 0;
                    character.onHit(this);
                } else {
                    attackCount += 1;
                }
                break;
            }
        }

        if(character == null) {
            attackCount = 0;
            moveRight(screenWidth, stepSize);
        }
    }
    @Override
    void onDraw(Canvas canvas) {
        if(bitmap != null) {
            canvas.drawBitmap(bitmap, x, y, null);
        }
    }

    @Override
    public void onHit(Character character) {
        heal -= character.getAttack();
        if(heal <= 0) {
            this.state = CharacterState.WAIT_FOR_DESTROY;
        }
    }
}
