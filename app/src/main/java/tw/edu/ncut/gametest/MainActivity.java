package tw.edu.ncut.gametest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;

import tw.edu.ncut.gametest.CatEnemy.GameManager;
import tw.edu.ncut.gametest.CatEnemy.Level;

public class MainActivity extends AppCompatActivity {
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameManager = (GameManager)findViewById(R.id.WarCatGameManager);
        final GameView gameView = (GameView)findViewById(R.id.GameView);
        gameManager.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                gameView.setGameManager(gameManager);
                (new Level(gameManager)).start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) { }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) { }
        });
    }
}
