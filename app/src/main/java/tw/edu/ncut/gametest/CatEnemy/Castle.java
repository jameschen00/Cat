package tw.edu.ncut.gametest.CatEnemy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/4.
 */

public class Castle extends CatCharacter {

    public interface DestroyCallBack {
        void onDestroy();
    }

    private DestroyCallBack destroyCallBack;
    private final int onHitMove = 2;
    private final int onHitAnimationMaxFrameCount = 6;
    private int onHitAnimationFrameCount = 0;
    private boolean isOnHit = false;

    public Castle(Bitmap bitmap, int heal, int attack, int x, int y, int w, int h, DestroyCallBack onDestroyCallBack){
        super(heal, attack, x, y, w, h);
        Bitmap bmp = Bitmap.createScaledBitmap(bitmap, w, h, false);
        animation.addAnimation(new OneBitmapFrame(bmp));
        destroyCallBack = onDestroyCallBack;
        animation.start();
    }

    public Castle(Bitmap bitmap, int heal, int attack, int x, int y, int w, int h,
                  String tag, DestroyCallBack onDestroyCallBack) {

        super(heal, attack, x, y, w, h);
        Bitmap bmp = Bitmap.createScaledBitmap(bitmap, w, h, false);
        animation.addAnimation(new OneBitmapFrame(bmp));
        destroyCallBack = onDestroyCallBack;
        animation.start();
        this.tag = tag;
    }

    @Override
    protected void update(int screenWidth, int screenHeight) {
        if(isOnHit) {
            if(onHitAnimationFrameCount == onHitAnimationMaxFrameCount){
                onHitAnimationFrameCount = 0;
                isOnHit = false;
                return;
            }

            if(onHitAnimationFrameCount % 2 == 0) {
                this.x -= onHitMove;
            } else {
                this.x += onHitMove;
            }
            onHitAnimationFrameCount += 1;
        }
    }

    @Override
    protected void onDestroy() {
        if(destroyCallBack != null)
            destroyCallBack.onDestroy();
    }

    @Override
    public void onHit(Character character) {
        isOnHit = true;
        heal -= character.getAttack();
        if(heal <= 0) {
            state = CharacterState.WAIT_FOR_DESTROY;
        }
    }
}
