package tw.edu.ncut.gametest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

import tw.edu.ncut.gametest.CatEnemy.GameManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    //
    private int count;
    //
    final private int size_y=12,size_x=10;
    private int BitmapSize;
    private int width,height;//螢幕的寬高
    private CatSquare catSquares[][];
    //
    private GameManager gameManager;
    private Thread thread;//遊戲執行緒
    private Canvas canvas;//畫布
    private SurfaceHolder holder;//SurfaceView
    private List<CatDestroy> list;
    private SoundPool sound;
    private boolean gameState;
    int normal_sound;
    //
    public GameView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        thread = new Thread(this);
        holder = this.getHolder();
        holder.addCallback(this);
        catSquares = new CatSquare[size_y][size_x];
        gameState = true;
        list = new ArrayList<CatDestroy>();
        sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
        normal_sound = sound.load(context, R.raw.normal_sound , 1);
        Log.i("nyw",normal_sound+"");
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    public void GamePause(){
        gameState = false;
    }
    public void GameStart() {
        gameState = true;
    }

    private void catDraw(){
        try {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            for(int i=0;i<size_y;i++)
                for(int j=0;j<size_x;j++){
                    catSquares[i][j].draw(canvas);
                    catSquares[i][j].move(i,height);
                }
            for(int i=0;i<list.size();i++) {
                list.get(i).Draw(canvas);
                list.get(i).move();
                if(!list.get(i).state)
                    list.remove(i);
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
        for(int i=0; i<size_y;i++) {
            for (int j = 0; j < size_x; j++) {
                int kind = (int) (Math.random()*10000)%4+1;
                catSquares[i][j] = new CatSquare(getContext(),  BitmapSize * j, -i*BitmapSize, j,i,kind,BitmapSize);
            }
        }
        while(true){
            catDraw();
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
    //SurfaceView
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

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(!gameState){
            return false;
        }

        float x = event.getX();
        float y = event.getY();

        int j = (int) x/BitmapSize; //算出方塊的列

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                for(int i=0;i<size_y & j<size_x;i++)
                    if (catSquares[i][j].getX() < x && catSquares[i][j].getX() + BitmapSize > x
                            && catSquares[i][j].getY() < y && catSquares[i][j].getY() + BitmapSize > y) {
                        if(catSquares[i][j].state)
                        {
                            sound.play(normal_sound,1,1,0,0,1);

                            Log.i("No", i + "," + j );
                            Log.i("Kind",catSquares[i][j].kind+"");
                            count = 0;
                            search(i,j,catSquares[i][j].kind);
                            sortSquare(); //排列方塊
                            return true;
                        }
                    }
                return true;
        }
        return false;
    }
    public void search(int i,int j,int kind){
        boolean f = true;
        int d = 0;
        while (f){
            switch (d) {
                case 0:
                    up(i, j, kind);
                    Log.i("up","up");
                    d++;
                    break;
                case 1:
                    right(i, j, kind);
                    Log.i("right","right");
                    d++;
                    break;
                case 2:
                    down(i, j,kind);
                    Log.i("down","down");
                    d++;
                    break;
                case 3:
                    left(i, j,kind);
                    Log.i("left","left");
                    d++;
                    break;
                default:
                    f = false;
                    break;
        }

        destroySquare(i,j);
        }
    }
    public void up(int i,int j,int kind){
        if(i+1<size_y){
            if(catSquares[i+1][j].kind == kind){
                catSquares[i][j].kind = kind*10; //標記
                search(i+1,j,kind);
            }
        }
    }
    public void right(int i,int j,int kind){
        if(j+1<size_x){
            if(catSquares[i][j+1].kind == kind){
                catSquares[i][j].kind = kind*10; //標記
                search(i,j+1,kind);
            }
        }
    }
    public void down(int i,int j,int kind){
        if(i-1>=0){
            if(catSquares[i-1][j].kind == kind){
                catSquares[i][j].kind = kind*10; //標記
                search(i-1,j,kind);
            }
        }
    }
    public void left(int i,int j,int kind){
        if(j-1>=0){
            if(catSquares[i][j-1].kind == kind){
                catSquares[i][j].kind = kind*10; //標記
                search(i,j-1,kind);
            }
        }
    }

    public void destroySquare(int i,int j){
        if(catSquares[i][j].state) {
            catSquares[i][j].disappear();
            count++;
            list.add(new CatDestroy(getContext(),catSquares[i][j].bitmap,catSquares[i][j].getX(),catSquares[i][j].getY(),BitmapSize,width,height));
        }
    }
    public void sortSquare(){
        int a=0;
        while(a<size_y) {
            a++;
            for (int i = 1; i < size_y; i++) {
                for (int j = 0; j < size_x; j++) {
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
        CreateNewSquare();

    }
    public void CreateNewSquare(){
        for (int i = 0; i < size_y; i++) {
            for (int j = 0; j < size_x; j++) {
                if(!catSquares[i][j].state)
                {
                    int kind = (int) (Math.random()*10000)%4+1;
                    CatSquare c = new CatSquare(getContext(),  BitmapSize * j, -i*BitmapSize, j,i,kind,BitmapSize);
                    catSquares[i][j] = c;

                }
            }
        }
        new SummonMonster(gameManager,count).start();
    }


}