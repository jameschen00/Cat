package tw.edu.ncut.gametest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import tw.edu.ncut.gametest.CatEnemy.BigRedCat;
import tw.edu.ncut.gametest.CatEnemy.GameManager;
import tw.edu.ncut.gametest.CatEnemy.MidRedCat;
import tw.edu.ncut.gametest.CatEnemy.RedCat;
import tw.edu.ncut.gametest.CatEnemy.SmallRedCat;

/**
 * Created by HatsuneMiku on 2018/1/5.
 */

public class SummonMonster extends Thread{

    private GameManager gameManager;
    private int count,normal,mid,big;
    public boolean state =true;

    public SummonMonster(GameManager gameManager,int count){
        this.gameManager = gameManager;
        this.count = count;
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
                        gameManager.regist(new SmallRedCat(gameManager.getContext(),
                                gameManager.getHeight() / 2,
                                gameManager.getHeight() - SmallRedCat.CatHeight));
                    }
                    normal--;
                }
                else if (mid > 0) {
                    if (gameManager != null) {
                        gameManager.regist(new MidRedCat(gameManager.getContext(),
                                gameManager.getHeight() / 2,
                                gameManager.getHeight() - MidRedCat.CatHeight));
                    }
                    mid--;
                }
                else if (big > 0) {
                    if (gameManager != null) {
                        gameManager.regist(new BigRedCat(gameManager.getContext(),
                                gameManager.getHeight() / 2,
                                gameManager.getHeight() - BigRedCat.CatHeight));
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
