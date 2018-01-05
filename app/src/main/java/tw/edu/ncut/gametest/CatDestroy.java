package tw.edu.ncut.gametest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by apple on 2018/1/5.
 */


public class CatDestroy {
    int x,y,BitmapSize,width,height;
    Bitmap bitmap;
    int pX=0,pY=0;
    boolean state = true;

    public CatDestroy(Context context,Bitmap bitmap,int x, int y, int BitmapSize,int width,int height) {
        this.x = x;
        this.y = y;
        //
        this.bitmap = Bitmap.createScaledBitmap(bitmap, BitmapSize, BitmapSize, false);
        this.width = width;
        this.height = height;
    }
    public void Draw(Canvas canvas){
        canvas.drawBitmap(bitmap,x+pX,y+pY,null);
    }
    public void move(){
        x+=10;
        y+=10;
        if(x >width || x<0 ||y>height ||y<0){
            state = false;
        }
    }

}
