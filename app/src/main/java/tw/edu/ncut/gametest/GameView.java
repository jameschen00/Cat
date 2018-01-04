package tw.edu.ncut.gametest;

import android.content.Context;

import android.content.Intent;
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

import tw.edu.ncut.gametest.CatEnemy.BlueCat;
import tw.edu.ncut.gametest.CatEnemy.GameManager;
import tw.edu.ncut.gametest.CatEnemy.RedCat;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    enum GameState{
        Menu,
        Playing,
        Over;
    }

    private Bitmap bitmap;
    private int BitmapSize;
    private int a;
    private int b;
    boolean f =true;
    private int k =0;
    int stack[] = new int[100] ;
    private int d =0;
    private int z =0;
    private CatSquare catSquares[][];
    private GameManager gameManager;
    GameState gameState;//遊戲狀態
    Thread thread;//遊戲執行緒
    Boolean flag;
    Canvas canvas;//畫布
    SurfaceHolder holder;//SurfaceView
    int x=0,y=0;
    int width,height;//螢幕的寬高
    public GameView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        flag = true;
        thread = new Thread(this);
        holder = this.getHolder();
        holder.addCallback(this);

        catSquares = new CatSquare[10][10];
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
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
                catSquares[i][j] = new CatSquare(getContext(), x + BitmapSize * j, y-i*BitmapSize, j,i,kind,BitmapSize);
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
        BitmapSize =  width/10 ;//取得圖片寬度
        Log.i("bitmapsize",BitmapSize+"");
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

        int j = (int) x/BitmapSize; //算出方塊的列

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                for(int i=0;i<10 & j<10;i++)
                        if (catSquares[i][j].getX() < x && catSquares[i][j].getX() + BitmapSize > x
                                && catSquares[i][j].getY() < y && catSquares[i][j].getY() + BitmapSize > y) {
                            if(catSquares[i][j].state)
                            {
                                Log.i("No", i + "," + j );
                                Log.i("Kind",catSquares[i][j].kind+"");
                                search(i,j,catSquares[i][j].kind);
                                sortSquare(); //排列方塊
                            }
                        }
                return true;
        }
        return false;
    }
    public void search(int i,int j,int kind){
        f=true;
        a = i;
        b = j;
        k = kind;
        z = 0;
        d = 0;
        while (f) {
            switch (d){
                case 0:
                    Log.i("u","up();");
                    up();
                    break;
                case 1:
                    Log.i("r","right();");

                    right();
                    break;
                case 2:
                    Log.i("d","down();");

                    down();
                    break;
                case 3:
                    Log.i("l","left();");

                    left();
                    break;
                case 4:
                    Log.i("r","ret();");
                    ret();
                    break;
            }
            destorySquare();
        }
    }
    public void up(){
        if(a+1<10){
            if(catSquares[a+1][b].kind == k){
                catSquares[a][b].kind *= 10; //標記
                a++;    //走上面
                d=0;
                stack[z++] = 0;
            }
            else
                d++;
        }
        else
            d++;
    }
    public void right(){
        if(b+1<10){
            if(catSquares[a][b+1].kind == k){
                catSquares[a][b].kind *= 10; //標記
                Log.i("Kind",catSquares[a][b].kind+"");
                b++;    //走右
                d=0;
                stack[z++] = 1;
            }
            else
                d++;
        }
        else
            d++;
    }
    public void down(){
        if(a-1>=0){
            if(catSquares[a-1][b].kind == k){
                catSquares[a][b].kind *= 10; //標記
                Log.i("Kind",catSquares[a][b].kind+"");
                a--;    //走下
                d=0;
                stack[z++] = 2;
            }
            else
                d++;
        }
        else
            d++;
    }
    public void left(){
        if(b-1>=0){
            if(catSquares[a][b-1].kind == k){
                catSquares[a][b].kind *= 10; //標記
                Log.i("Kind",catSquares[a][b].kind+"");
                b--;    //走左
                d=0;
                stack[z++] = 3;
            }
            else
                d++;
        }
        else
            d++;
    }
    public void ret(){
        catSquares[a][b].kind *= 10; //標記
        if(--z <0){
            f =false;
            return;
        }
        else{
            Log.i("Kind",catSquares[a][b].kind+"");
            switch (stack[z]){
                case 0:a--;
                    break;
                case 1:b--;
                    break;
                case 2:a++;
                    break;
                case 3:b++;
                    break;
            }
            d=0;
        }
    }

    public void destorySquare(){
       /* for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(catSquares[i][j].kind == k*10)
                {
                    catSquares[i][j].disappear();

                }
            }
        }*/
        catSquares[a][b].disappear();
    }
    public void sortSquare(){
        int a=0;
        while(a<10) {
            a++;
            for (int i = 1; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (!catSquares[i - 1][j].state && catSquares[i][j].state)//判斷下面方塊是否消除
                    {
                        CatSquare temp; //上下方塊交換
                        temp = catSquares[i - 1][j];
                        catSquares[i - 1][j] = catSquares[i][j];
                        catSquares[i][j] = temp;
                    }

                }
            }
        }
        CreatNewSquare();

    }
    public void CreatNewSquare(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(!catSquares[i][j].state)
                {
                    int kind = (int) (Math.random()*10000)%4+1;
                    CatSquare c = new CatSquare(getContext(), x + BitmapSize * j, y-i*BitmapSize, j,i,kind,BitmapSize);
                    catSquares[i][j] = c;

                }

            }
        }
        Log.i("-------------","-------------------------------------------------------");
        if(gameManager != null) {
            gameManager.regist(new RedCat(getContext(),
                    gameManager.getHeight() / 2,
                    gameManager.getHeight() - RedCat.CatHeight));
        }
    }
}