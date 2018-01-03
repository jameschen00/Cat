package tw.edu.ncut.gametest.CatEnemy;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.LinkedList;
import java.util.List;

abstract class Character {
    enum CharacterState {
        COLLISION_ON,
        COLLISION_OFF,
        WAIT_FOR_DESTROY,
    }

    public Character(int heal, int attack){
        this.heal = heal;
        this.attack = attack;
        state = CharacterState.COLLISION_ON;
    }

    protected void onCollisionEnter(Character character){};
    protected void onCollisionLeave(Character character){};
    protected void onDestroy() {};

    private List<Character> tmp;
    final void __collision_init() {
        tmp = new LinkedList<>(_collisionList);
    }

    final void __collision(Character character) {
        if(!_collisionList.contains(character)){
            _collisionList.add(character);
            onCollisionEnter(character);
        } else {
            tmp.remove(character);
        }
    }

    final void __collision_end() {
        for (Character cc : tmp){
            onCollisionLeave(cc);
            _collisionList.remove(cc);
        }
    }

    protected abstract void moveEvent(int screenWidth, int screenHeight);

    public abstract void onHit(Character character);

    public abstract Rect getRect();

    abstract void onDraw(Canvas canvas);

    public final int getHeal() { return heal; }

    public final int getAttack() { return attack; }

    public final void setTag(String tag) { this.tag = tag; }
    public final String getTag() { return tag; }

    protected final List<Character> getCollisionList() { return new LinkedList<>(_collisionList); }

    public CharacterState getState(){ return state; }

    protected int heal;
    protected int attack;
    protected CharacterState state;
    protected String tag;
    private List<Character> _collisionList = new LinkedList<>();
}
