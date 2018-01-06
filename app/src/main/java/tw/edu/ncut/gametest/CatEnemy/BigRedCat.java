package tw.edu.ncut.gametest.CatEnemy;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/3.
 */

//see annotation on Character.java
public class BigRedCat extends CatCharacter {
    private int stepSize = 1;
    private int attackSpeed = 0;
    private int attackCount = 0;

    public static final int CatWidth = 15;
    public static final int CatHeight = 15;

    @Deprecated
    public BigRedCat(Context context, int x, int y) {
        super(context, 600, 70, x, y, CatWidth, CatHeight, R.drawable.cat_red);
        tag = "RED TEAM";
    }

    public BigRedCat(Bitmap bitmap, int x, int y) {
        super(bitmap, 600, 70, x, y, CatWidth, CatHeight);
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
    public void onHit(Character character) {
        heal -= character.getAttack();
        if(heal <= 0) {
            this.state = CharacterState.WAIT_FOR_DESTROY;
        }
    }
}
