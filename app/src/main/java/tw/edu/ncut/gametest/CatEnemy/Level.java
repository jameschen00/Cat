package tw.edu.ncut.gametest.CatEnemy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import tw.edu.ncut.gametest.GameView;
import tw.edu.ncut.gametest.MainActivity;
import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/5.
 */

public class Level extends Thread{

    private GameManagerWithCounter gameManager;
    private GameView gameView;
    private Bitmap cat1;
    private boolean gameClear = false;
    private boolean gameOver = false;
    private boolean allOver = false;
    private Activity activity;

    public Level(Activity activity, GameManagerWithCounter gameManager, GameView gameView){
        this.gameManager = gameManager;
        this.gameView = gameView;
        this.activity = activity;
        cat1 = BitmapFactory.decodeResource(gameManager.getContext().getResources(), R.drawable.cat_blue);
        init();
    }

    @Override
    public void run() {
        while(!allOver) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!(gameClear || gameOver)) {
                gameManager.regist(
                        new BlueCat(cat1,
                                gameManager.getWidth() - BlueCat.CatWidth / 2 - gameManager.getHeight() / 2,
                                gameManager.getHeight() - BlueCat.CatHeight));
            }
        }
    }

    private void init() {
        gameClear = false;
        gameOver = false;
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
                        Level.this.gameManager.pauseGame();
                        gameOver = true;
                        onGameEnd("GAME OVER");
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
                        Level.this.gameManager.pauseGame();
                        gameClear = true;
                        onGameEnd("GAME CLEAR");
                    }
                }));
    }

    private void onGameEnd(final String gameState) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(gameManager.getContext());
                dlgAlert.setMessage(String.format(
                        gameState + "\n" +
                                "your call %d cat\n" +
                                "and kill %d enemy\n"
                        ,
                        gameManager.getRegistTagCount("RED TEAM") - 1,
                        gameManager.getUnregistTagCount("BLUE TEAM")
                ));
                dlgAlert.setTitle("Game Score");
                dlgAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gameManager.cleanAllObject();
                        gameManager.resumeGame();
                        init();
                        gameManager.updateScreen();
                    }
                });
                dlgAlert.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Level.this.allOver = true;
                    }
                });
                dlgAlert.create().show();
            }
        });
    }
}
