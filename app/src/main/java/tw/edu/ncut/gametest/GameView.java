package tw.edu.ncut.gametest;

import android.content.Context;

import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.Random;

/**
 * Created by wangjianhong on 2017/12/30.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    enum GameState{
        Menu,
        Playing,
        Over;
    }
    CatSquare catSquares[][];
    int catArray[][];
    GameState gameState;//遊戲狀態
    Thread thread;//遊戲執行緒
    Boolean flag;
    Canvas canvas;//畫布
    Paint paint;//畫筆
    SurfaceHolder holder;//SurfaceView
    int x=0,y=0;
    int count=0;
    int width,height;//螢幕的寬高
    public GameView(Context context,AttributeSet attributeSet) {
        super(context);
        flag = true;
        paint = new Paint();
        thread = new Thread(this);

        holder = this.getHolder();
        holder.addCallback(this);

        catSquares = new CatSquare[10][10];
        catArray = new int[10][10];
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setBackgroundColor(0Xffffffff);
    }

    private void mDraw(){
        try {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);

            for(int i=0;i<10;i++)

                for(int j=0;j<10;j++){

                    catSquares[i][j].draw(canvas);
                    catSquares[i][j].move(i,height);
                }
            }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        for(int i=0; i<10;i++) {
            for (int j = 0; j < 10; j++) {
                int kind = (int) (Math.random()*10000)%4+1;
                catSquares[i][j] = new CatSquare(getContext(), x + 70 * j, y-i*70, j,i,kind);
                catArray[i][j] = kind;
            }

        }
        while(true){
            //Log.i("run","run");
            mDraw();
            try {
                Thread.sleep(10);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //int specWMode = MeasureSpec.getMode(widthMeasureSpec);
        //int specHMode = MeasureSpec.getMode(heightMeasureSpec);

        this.width = MeasureSpec.getSize(widthMeasureSpec);
        this.height = MeasureSpec.getSize(heightMeasureSpec);
        //
        setMeasuredDimension( width, height);
    }
    //SurfaceView创建时调用
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("","");

        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                for(int i=0;i<10;i++)
                    for(int j=0;j<10;j++) {
                        if (catSquares[i][j].getX() < x && catSquares[i][j].getX() + 70 > x
                                && catSquares[i][j].getY() < y && catSquares[i][j].getY() + 70 > y) {

                            if(catArray[i][j] != 0)
                            {
                                Log.i("No", i + "," + j);
                                Log.i("Kind",catArray[i][j]+"");
                                catArray[i][j] = 0;
                                catSquares[i][j].disappear();
                                sort();
                            }
                        }
                    }
                return true;
        }
        return false;
    }
    public void sort(){
        for(int i=1;i<10;i++){
            for(int j=0;j<10;j++){
                if(catArray[i-1][j]== 0 )
                {
                    catArray[i-1][j] = catArray[i][j];
                    catArray[i][j] = 0;
                    catSquares[i-1][j] = catSquares[i][j];

                }
            }
        }
    }
}