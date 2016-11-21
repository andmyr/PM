/*
 * Project
 * 
 * Проект и его генерация
 */

package org.test.pm;

public class Project
{
    /** Дней на завершение */
    private int days;

    /** Бюджет */
    private int cost;

    /** Сложность в трудоднях */
    private float laboriousness;

    /** Трудодней завершено */
    private float performed;
    
    /**
     * Создание проекта.
     * 
     * @param days
     *            - Сроки проекта.
     * @param cost
     *            - Бюджет проекта
     * @param laboriousness
     *            - Сложность в трудоднях
     */
    
    public Project(int days, int cost, float laboriousness)
    {
        super();
        this.days = days;
        this.cost = cost;
        this.laboriousness = laboriousness;
        this.performed = 0;
    }

    public float getPerformed()
    {
        return performed;
    }

    public void setPerformed(int performed)
    {
        if (this.performed + performed > 0)
        {
            this.performed += performed;
        }
        else
        {
            this.performed = 0;
        }
    }

    public int getDays()
    {
        return days;
    }

    public int getCost()
    {
        return cost;
    }

    public float getLaboriousness()
    {
        return laboriousness;
    }

    /** Проверка на выполнение всех трудодней проекта */
    public boolean isFinished()
    {
        if (laboriousness <= performed)
            return true;
        else
            return false;
    }
}
