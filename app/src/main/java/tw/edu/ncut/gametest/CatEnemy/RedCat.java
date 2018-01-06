package tw.edu.ncut.gametest.CatEnemy;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/3.
 */

//see annotation on Character.java
public class RedCat extends CatCharacter {
    private int stepSize = 1;
    private float attackSpeed = 1;
    private float timer = 0;

    public static final int CatWidth = 15;
    public static final int CatHeight = 15;

    @Deprecated
    public RedCat(Bitmap bitmap, int x, int y) {
        super(bitmap, 100, 20, x, y, CatWidth, CatHeight);
        tag = "RED TEAM";
    }

    @Deprecated
    public RedCat(Bitmap bitmap, int x, int y, int heal, int attack) {
        super(bitmap, heal, attack, x, y, CatWidth, CatHeight);
        tag = "RED TEAM";
    }

    public RedCat(Animation animation, int heal, int attack, int x, int y, int w, int h) {
        super(animation, heal, attack, x, y, w, h);
        tag = "RED TEAM";
        animation.start();
    }

    @Override
    protected void update(int screenWidth, int screenHeight) {
        List<Character> collisionList = this.getCollisionList();
        Character character = null;

        for(Character c : collisionList) {
            if(c.getTag().equals("BLUE TEAM") && c.getState() != CharacterState.WAIT_FOR_DESTROY){
                character = c;
                if(timer >= attackSpeed) {
                    if(animation != null) animation.setAnimationFrameIndex(ATTACK_ANIMATION);
                    timer = 0;
                    character.onHit(this);
                } else {
                    timer += getDelTime();
                }
                break;
            }
        }

        if(character == null) {
            timer = 0;
            if(animation != null) animation.setAnimationFrameIndex(WALK_ANIMATION);
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
