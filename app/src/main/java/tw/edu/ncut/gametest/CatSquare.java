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


public class CatSquare{
    private int x,y;
    private int v=100;
    public Bitmap bitmap;
    public int kind;
    public boolean state = true;
    private int BitmapSize;
    public CatSquare(Context context, int x, int y,int pX,int pY, int kind,int BitmapSize){
        this.x =x;//圖像x
        this.y= y;//圖像y
        if(kind ==1) {//橙
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cat_orange);
            bitmap = Bitmap.createScaledBitmap(bitmap,BitmapSize,BitmapSize,false);
        }
        else if(kind ==2){//紅
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cat_red);
            bitmap = Bitmap.createScaledBitmap(bitmap,BitmapSize,BitmapSize,false);

        }
        else if(kind ==3){//淺藍
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cat_skyblue);
            bitmap = Bitmap.createScaledBitmap(bitmap,BitmapSize,BitmapSize,false);
        }
        else if(kind ==4){//深藍
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cat_blue);
            bitmap = Bitmap.createScaledBitmap(bitmap,BitmapSize,BitmapSize,false);
        }
        this.BitmapSize = BitmapSize;
        this.kind = kind;
        //Log.i("Cat","CatCreate");
    }
    public void draw(Canvas canvas){
        if(state) {
            canvas.drawBitmap(bitmap, x, y, null);
        }

    }
    public void move(int pY, int ScreenHeight){
        if(y+(BitmapSize+50)*(pY+1) < ScreenHeight*0.5){
            y+=50;
        }
        if(y+(BitmapSize+25)*(pY+1) < ScreenHeight*0.7){
            y+=25;
        }

        else if(y+(BitmapSize+10)*(pY+1) < ScreenHeight*0.9){
            y+=10;
        }
        else if(y+(BitmapSize+5)*(pY+1) <= ScreenHeight){
            y+=5;
        }
        else if(y+(BitmapSize+2)*(pY+1) <= ScreenHeight){
            y+=2;
        }
        else if(y+(BitmapSize+1)*(pY+1) <= ScreenHeight){
            y+=1;
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
