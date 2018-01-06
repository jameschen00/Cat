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

//see annotation on Character.java
public class BlueCat extends CatCharacter {
    private int stepSize = 1;
    private int attackSpeed = 0;
    private int attackCount = 0;

    public static final int CatWidth = 15;
    public static final int CatHeight = 15;

    @Deprecated
    public BlueCat(Context context, int x, int y) {
        super(context, 100, 20, x, y, CatWidth, CatHeight, R.drawable.cat_blue);
        tag = "BLUE TEAM";
    }

    public BlueCat(Bitmap bitmap, int x, int y) {
        super(bitmap, 100, 20, x, y, CatWidth, CatHeight);
        tag = "BLUE TEAM";
    }

    public BlueCat(Bitmap bitmap, int x, int y, int heal, int attack) {
        super(bitmap, heal, attack, x, y, CatWidth, CatHeight);
        tag = "BLUE TEAM";
    }

    @Override
    protected void update(int screenWidth, int screenHeight) {
        List<Character> collisionList = this.getCollisionList();
        Character character = null;

        for(Character c : collisionList) {
            if(c.getTag().equals("RED TEAM") && c.getState() != CharacterState.WAIT_FOR_DESTROY){
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
            moveLeft(stepSize);
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
