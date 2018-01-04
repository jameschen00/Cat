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

public class BlueCat extends CatCharacter {
    private int stepSize = 1;

    public BlueCat(Context context,int width, int height) {
        super(context, 100, 20, width - 15, height - 15, 15, 15, R.drawable.miau2);
        tag = "BLUE TEAM";
    }

    @Override
    protected void update(int screenWidth, int screenHeight) {
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
}
