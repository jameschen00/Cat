package tw.edu.ncut.gametest.CatEnemy;

import android.content.Context;

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

    public Castle(Context context, int heal, int attack, int x, int y, int w, int h,
                  int r_bitmap,
                  DestroyCallBack onDestroyCallBack) {
        super(context, heal, attack, x, y, w, h, r_bitmap);
        destroyCallBack = onDestroyCallBack;
    }

    public Castle(Context context, int heal, int attack, int x, int y, int w, int h,
                  int r_bitmap, String tag,
                  DestroyCallBack onDestroyCallBack) {
        super(context, heal, attack, x, y, w, h, r_bitmap);
        destroyCallBack = onDestroyCallBack;
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
