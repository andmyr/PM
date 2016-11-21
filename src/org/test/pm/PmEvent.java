package org.test.pm;

/**класс одиночного события */
public class PmEvent
{
    private String name;

    // параметры на которотые влияет событие
    private int mood;

    private int efficiency;

    private float progress;

    /** изменение стоимости проекта */
    private float pCost;

    /** Влияет событие на одного сотрудника (true) или на всех (false) */
    private boolean isOne;
        

    public PmEvent(String name, int mood, int efficiency, float progress, float pCost, boolean isOne)
    {
        super();
        this.name = name;
        this.mood = mood;
        this.efficiency = efficiency;
        this.progress = progress;
        this.pCost = pCost;
        this.isOne = isOne;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getMood()
    {
        return mood;
    }

    public void setMood(int mood)
    {
        this.mood = mood;
    }

    public int getEfficiency()
    {
        return efficiency;
    }

    public void setEfficiency(int efficiency)
    {
        this.efficiency = efficiency;
    }

    public float getProgress()
    {
        return progress;
    }

    public void setProgress(float progress)
    {
        this.progress = progress;
    }

    public float getpCost()
    {
        return pCost;
    }

    public void setpCost(int pCost)
    {
        this.pCost = pCost;
    }

    public void setpCost(float pCost)
    {
        this.pCost = pCost;
    }

    public boolean isOne()
    {
        return isOne;
    }

    public void setOne(boolean isOne)
    {
        this.isOne = isOne;
    }

    @Override
    public String toString()
    {
        return "PMEvent [name=" + name + ", mood=" + mood + ", efficiency="
                + efficiency + ", progress=" + progress + ", pCost=" + pCost
                + ", isOne=" + isOne + "]";
    }
}
