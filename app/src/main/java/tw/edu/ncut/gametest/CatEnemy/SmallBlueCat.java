package tw.edu.ncut.gametest.CatEnemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/7.
 */

public class SmallBlueCat extends BlueCat {
    public static final int CatWidth = 40;
    public static final int CatHeight = 40;

    public SmallBlueCat(Context context, int x, int y){
        this(context, 100, 20, x, y, CatWidth, CatHeight);
    }

    public SmallBlueCat(Context context, int heal, int attack, int x, int y, int w, int h) {
        super(heal, attack, x, y, w, h);
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.cat_blue), w, h, false);
        animation.addAnimation(new OneBitmapFrame(bmp));

        animation.start();
    }
}
