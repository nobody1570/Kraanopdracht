//package be.kul.gantry.domain;

/**
 * Created by Wim on 27/04/2015.
 */
public class Slot {

    private final int id;
    private final int centerX, centerY, xMin, xMax, yMin, yMax,z;
    private Item item;
    private final SlotType type;

    public Slot(int id, int centerX, int centerY, int xMin, int xMax, int yMin, int yMax, int z, SlotType type, Item item) {
        this.id = id;
        this.centerX = centerX;
        this.centerY = centerY;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.z = z;
        this.item = item;
        this.type = type;
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
        return xMin;
    }

    public int getXMax() {
        return xMax;
    }

    public int getYMin() {
        return yMin;
    }

    public int getYMax() {
        return yMax;
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

    public static enum SlotType {
        INPUT,
        OUTPUT,
        STORAGE
    }
}
