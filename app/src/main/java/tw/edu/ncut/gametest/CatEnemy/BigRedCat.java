package tw.edu.ncut.gametest.CatEnemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.LinkedList;
import java.util.List;

import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/7.
 */

public class BigRedCat extends RedCat{

    public static final int CatWidth = 100;
    public static final int CatHeight = 56;

    public BigRedCat(Context context, int x, int y){
        this(context, 200, 15, x, y, CatWidth, CatHeight);
    }

    public BigRedCat(Context context, int heal, int attack, int x, int y, int w, int h) {
        super(heal, attack, x, y, w, h);

        setAttackSpeed(0.6f);

        List<Bitmap> w_frame = new LinkedList<>();
        w_frame.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.neko21), w, h, false));
        w_frame.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.neko22), w, h, false));

        List<Long> w_time = new LinkedList<>();
        w_time.add(300L);
        w_time.add(300L);
        animation.addAnimation(new BitmapFrame(w_frame, w_time));

        List<Bitmap> a_frame = new LinkedList<>();
        a_frame.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.neko23), w, h, false));
        a_frame.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.neko24), w, h, false));

        List<Long> a_time = new LinkedList<>();
        a_time.add((long)(getAttackSpeed() * 500));
        a_time.add((long)(getAttackSpeed() * 500));
        animation.addAnimation(new BitmapFrame(a_frame, a_time));

        animation.start();
    }
}
