package tw.edu.ncut.gametest.CatEnemy;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

public class BitmapFrame implements AnimationFrame{
    List<Bitmap> frames;
    List<Long> playTime;
    int frameCount = 0;
    private boolean replay = false;
    private long timer;

    public BitmapFrame(List<Bitmap> frames, List<Long> playTime){
        this.frames = frames;
        this.playTime = playTime;
        timer = System.currentTimeMillis();
    }

    @Override
    public void init() {
        frameCount = 0;
        timer = System.currentTimeMillis();
    }

    @Override
    public int size() {
        return frames.size();
    }

    @Override
    public int getIndex() {
        return frameCount;
    }

    @Override
    public boolean setIndex(int index) {
        if(index >= size())
            return false;
        this.frameCount = index;
        return true;
    }

    @Override
    public boolean isEnd() {
        if(replay)
            return false;
        long time = System.currentTimeMillis() - timer;
        if(frameCount == frames.size()) {
            if (time > playTime.get(frameCount - 1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        long time = System.currentTimeMillis() - timer;
        if(frameCount == frames.size()) {
            canvas.drawBitmap(frames.get(frameCount - 1), x, y, null);
            return;
        }
        if(time > playTime.get(frameCount)) {
            canvas.drawBitmap(frames.get(frameCount), x, y, null);
            ++frameCount;
            if (frameCount == frames.size() && replay)
                frameCount = 0;
            timer = System.currentTimeMillis();
        } else {
            canvas.drawBitmap(frames.get(frameCount), x, y, null);
        }
    }

    public void setReplay(boolean replay) { this.replay = replay; }
}
