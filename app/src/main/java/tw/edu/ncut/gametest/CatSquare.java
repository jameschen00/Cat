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
    private Bitmap bitmap;
    private int pX,pY;
    private boolean state = true;

    public CatSquare(Context context, int x, int y,int pX,int pY){
        this.x =x;
        this.y= y;
        this.bitmap =BitmapFactory.decodeResource(context.getResources(), R.drawable.unnamed35);
        this.pX = pX;
        this.pY = pY;
        Log.i("Cat","CatCreate");
    }

    public void draw(Canvas canvas){
        if(state) {
            Paint pen = new Paint();
            pen.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, x, y, pen);
        }

        //Log.i("draw","draw");
    }
    public void move(int pY, int ScreenHeight){
        if(y+80*(pY+1) < ScreenHeight){
            y+=10;
        }
        else {
            //y=ScreenHeight-70*pY;
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
        state = false;
    }
}
