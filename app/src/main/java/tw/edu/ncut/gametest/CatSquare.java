package tw.edu.ncut.gametest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wangjianhong on 2017/12/30.
 */

public class CatSquare{
    private int x,y;
    private int v=100;
    private Bitmap bitmap;
    public int kind;
    private int pX,pY;
    public boolean state = true;
    private int BitmapSize;
    public CatSquare(Context context, int x, int y,int pX,int pY, int kind){
        this.x =x;//圖像x
        this.y= y;//圖像y
        this.pX = pX;
        this.pY =pY;
        if(kind ==1) {//橙
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.miau35);
            Log.i("Cat","1");
        }
        else if(kind ==2){//紅
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.unnamed35);
            Log.i("Cat","2");
        }
        else if(kind ==3){//淺藍
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.miau3);
            Log.i("Cat","3");
        }
        else if(kind ==4){//深藍
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.miau2);
            Log.i("Cat","4");
        }
        BitmapSize = this.bitmap.getWidth();
        this.kind = kind;
        Log.i("Cat","CatCreate");
    }
    public void draw(Canvas canvas){
        if(state) {
            Paint pen = new Paint();
            pen.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, x, y, pen);
        }
    }
    public void move(int pY, int ScreenHeight){
        if(y+(BitmapSize+v)*(pY+1) < ScreenHeight){
            y+=v;
        }
        else {
            //y=ScreenHeight-70*pY;
        }
        if(v>=5){
            v*=0.8;
        }
        //Log.i("movey",y+"");
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void disappear(){
        kind = 0;
        state = false;
    }
}
