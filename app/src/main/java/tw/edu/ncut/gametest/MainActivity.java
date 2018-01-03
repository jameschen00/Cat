package tw.edu.ncut.gametest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import tw.edu.ncut.gametest.CatEnemy.BlueCat;
import tw.edu.ncut.gametest.CatEnemy.GameManager;
import tw.edu.ncut.gametest.CatEnemy.RedCat;

public class MainActivity extends AppCompatActivity {
    private GameManager gameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameManager = (GameManager)findViewById(R.id.WarCatGameManager);
        gameManager.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                gameManager.regist(new RedCat(MainActivity.this, gameManager.getHeight()));
                gameManager.regist(new BlueCat(MainActivity.this,
                        gameManager.getWidth(),
                        gameManager.getHeight()));
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
    }

    public void make_red_cat(View view) {
        gameManager.regist(new RedCat(MainActivity.this, gameManager.getHeight()));
    }

    public void make_blue_cat(View view) {
        gameManager.regist(new BlueCat(MainActivity.this,
                gameManager.getWidth(),
                gameManager.getHeight()));
    }
}
