package tw.edu.ncut.gametest;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private Bitmap bitmap;
    private int BitmapSize;
    CatSquare catSquares[][];

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
        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.miau35);
        BitmapSize = bitmap.getWidth(); //取得圖片寬度
        catSquares = new CatSquare[10][10];
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
                catSquares[i][j] = new CatSquare(getContext(), x + BitmapSize * j, y-i*BitmapSize, j,i,kind);
            }
        }
        while(flag){
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
                        if (catSquares[i][j].getX() < x && catSquares[i][j].getX() + BitmapSize > x
                                && catSquares[i][j].getY() < y && catSquares[i][j].getY() + BitmapSize > y) {
                            if(catSquares[i][j].state)
                            {
                                Log.i("No", i + "," + j);
                                Log.i("Kind",catSquares[i][j].kind+"");
                                catSquares[i][j].disappear(); //消除方塊
                                sort(); //排列方塊
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
                if(!catSquares[i-1][j].state)//判斷下面方塊是否消除
                {
                    CatSquare temp; //上下方塊交換
                    temp = catSquares[i-1][j];
                    catSquares[i-1][j] = catSquares[i][j];
                    catSquares[i][j] =temp;
                }

            }
        }
    }
}