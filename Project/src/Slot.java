package be.kul.gantry.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Wim on 27/04/2015.
 */
public class Slot {

    private final int id;
    private final int centerX, centerY, z;
    private Item item;
    private final SlotType type;
    private Interval interval;
    private List<Slot> parents;
    private List<Slot> childeren;

    public Slot(int id, int centerX, int centerY, int xMin, int xMax, int yMin, int yMax, int z, SlotType type, Item item) {
        this.id = id;
        this.centerX = centerX;
        this.centerY = centerY;
        this.interval = new Interval(xMin, xMax, yMin, yMax);
        this.z = z;
        this.item = item;
        this.type = type;
        parents = new ArrayList<>();
        childeren = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public int getZ() {
        return z;
    }

    public int getXMin() {
        return interval.xMin;
    }

    public int getXMax() {
        return interval.xMax;
    }

    public int getYMin() {
        return interval.yMin;
    }

    public int getYMax() {
        return interval.yMax;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public SlotType getType() {
        return type;
    }
    
    public boolean compare(Slot slot){
        return this.interval.compare(slot.interval);
    }

    public static enum SlotType {
        INPUT,
        OUTPUT,
        STORAGE
    }
    
    public void addParent(Slot slot){
        parents.add(slot);
    }
    
    public void addChild(Slot slot){
        childeren.add(slot);
    }
}
