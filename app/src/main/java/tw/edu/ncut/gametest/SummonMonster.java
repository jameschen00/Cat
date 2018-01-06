package tw.edu.ncut.gametest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import tw.edu.ncut.gametest.CatEnemy.BigRedCat;
import tw.edu.ncut.gametest.CatEnemy.GameManager;
import tw.edu.ncut.gametest.CatEnemy.MidRedCat;
import tw.edu.ncut.gametest.CatEnemy.RedCat;
import tw.edu.ncut.gametest.MainActivity;
import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/5.
 */

public class SummonMonster extends Thread{

    private GameManager gameManager;
    private Bitmap normal_cat,mid_cat,big_cat;
    private int count,normal,mid,big;
    public boolean state =true;


    public SummonMonster(GameManager gameManager,int count){
        this.gameManager = gameManager;
        this.count = count;
        normal_cat = BitmapFactory.decodeResource(gameManager.getContext().getResources(), R.drawable.cat_red);
        mid_cat = BitmapFactory.decodeResource(gameManager.getContext().getResources(), R.drawable.cat_skyblue);
        big_cat = BitmapFactory.decodeResource(gameManager.getContext().getResources(), R.drawable.cat_orange);
    }

    @Override
    public void run() {
        Log.i("-------------","count"+count);

        if(count >= 3) {
            big = (count - 2) / 9;
            mid = ((count - 2) % 9) / 3;
            normal = (((count - 2) % 9) % 3);
        }
        while(state) {
            Log.i("-------------","runing");

            try {
                if (normal > 0) {
                    if (gameManager != null) {
                        gameManager.regist(new RedCat(normal_cat,
                                gameManager.getHeight() / 2,
                                gameManager.getHeight() - RedCat.CatHeight));
                    }
                    normal--;
                }
                else if (mid > 0) {
                    if (gameManager != null) {
                        gameManager.regist(new MidRedCat(mid_cat,
                                gameManager.getHeight() / 2,
                                gameManager.getHeight() - RedCat.CatHeight));
                    }
                    mid--;
                }
                else if (big > 0) {
                    if (gameManager != null) {
                        gameManager.regist(new BigRedCat(big_cat,
                                gameManager.getHeight() / 2,
                                gameManager.getHeight() - RedCat.CatHeight));
                    }
                    big--;
                }
                else
                    state = false;

                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!state) {
                //TODO
            }
        }
    }
}
