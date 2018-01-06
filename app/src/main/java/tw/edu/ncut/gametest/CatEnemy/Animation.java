package tw.edu.ncut.gametest.CatEnemy;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HatsuneMiku on 2018/1/6.
 */

interface AnimationFrame {
    void init();
    int size();
    int getIndex();
    boolean setIndex(int index);
    void draw(Canvas canvas, int x, int y);
}

class BitmapFrame implements AnimationFrame{
    List<Bitmap> frames;
    int frameCount = 0;
    private boolean replay = true;

    public BitmapFrame(List<Bitmap> frames){
        this.frames = frames;
    }

    @Override
    public void init() {
        frameCount = 0;
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
    public void draw(Canvas canvas, int x, int y) {
        if(frameCount == frames.size())
            return;
        canvas.drawBitmap(frames.get(frameCount), x, y, null);
        ++frameCount;
        if(frameCount == frames.size() && replay)
            frameCount = 0;
    }

    public void setReplay(boolean replay) { this.replay = replay; }
}

public abstract class Animation {
    private boolean stop = true;
    private int animationIndex = 0;
    private float timer;
    private List<AnimationFrame> animationList = new LinkedList<>();
    private List<List<Float>> playTimeList = new LinkedList<>();

    public Animation() {};
    public void playAnimation(int index) {
        if(index >= animationList.size()){
            return;
        }
        animationIndex = index;
        animationList.get(index).init();
    }
    public boolean addAnimation(AnimationFrame animationFrame, List<Float> playTimeList){
        if(animationList.contains(animationFrame) || playTimeList.size() < animationFrame.size()){
            return false;
        }
        return animationList.add(animationFrame);
    }
    public void onDraw(Canvas canvas, int x, int y) {
        if(!stop) {
            AnimationFrame animationFrame = animationList.get(animationIndex);
            float time = System.currentTimeMillis() - timer;
            if(time >= playTimeList.get(animationIndex).get(animationFrame.getIndex())){
                animationFrame.draw(canvas, x, y);
                timer = System.currentTimeMillis();
            }
        }
    }
    public int getAnimationIndex() { return animationIndex; }
    public int getAnimationSize() { return animationList.size(); }
    public boolean setAnimationFrameIndex(int index) {
        if(animationIndex >= animationList.size()) {
            return false;
        }
        return animationList.get(animationIndex).setIndex(index);
    }
    public boolean start() {
        if(animationIndex >= animationList.size())
            return false;
        stop = false;
        timer = (float)System.currentTimeMillis() / 1000;
        return true;
    }
    public void stop() { stop = false; }
}
