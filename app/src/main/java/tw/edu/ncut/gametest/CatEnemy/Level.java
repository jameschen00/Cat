package tw.edu.ncut.gametest.CatEnemy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import tw.edu.ncut.gametest.MainActivity;
import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/5.
 */

public class Level extends Thread{

    private GameManager gameManager;
    private Bitmap cat1;
    private boolean gameClear = false;
    private boolean gameOver = false;

    public Level(GameManager gameManager){
        this.gameManager = gameManager;
        cat1 = BitmapFactory.decodeResource(gameManager.getContext().getResources(), R.drawable.cat_blue);

        gameManager.regist(new Castle(
                BitmapFactory.decodeResource(gameManager.getContext().getResources(), R.drawable.red),
                1000,
                0,
                0,
                0,
                gameManager.getHeight() / 2,
                gameManager.getHeight(),
                "RED TEAM",
                new Castle.DestroyCallBack() {
                    @Override
                    public void onDestroy() {
                        Level.this.gameManager.regist(new TextCharacter("GAME OVER",
                                Level.this.gameManager.getWidth() / 2 - 60,
                                Level.this.gameManager.getHeight() / 2 ,
                                20));
                        gameOver = true;
                    }
                }));

        gameManager.regist(new Castle(
                BitmapFactory.decodeResource(gameManager.getContext().getResources(), R.drawable.blue),
                1000,
                0,
                gameManager.getWidth() - gameManager.getHeight() / 2,
                0,
                gameManager.getHeight() / 2,
                gameManager.getHeight(),
                "BLUE TEAM",
                new Castle.DestroyCallBack() {
                    @Override
                    public void onDestroy() {
                        Level.this.gameManager.regist(new TextCharacter("GAME CLEAR",
                                Level.this.gameManager.getWidth() /  2 - 60,
                                Level.this.gameManager.getHeight() / 2,
                                20));
                        gameClear = true;
                    }
                }));
    }

    @Override
    public void run() {
        while(!(gameClear || gameOver)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (gameOver) {
                //TODO
            } else if (gameClear) {
                //TODO
            } else {
                gameManager.regist(
                        new BlueCat(cat1,
                                gameManager.getWidth() - BlueCat.CatWidth / 2 - gameManager.getHeight() / 2,
                                gameManager.getHeight() - BlueCat.CatHeight));
            }
        }
    }
}
