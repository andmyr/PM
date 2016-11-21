/*
 * CurrentStatus
 * 
 * Текущий день и состояние финансов
 *
 * и другие служебные переменные
 */

package org.test.pm;

public class CurrentStatus
{
    private int money;

    /** всего денег за игру */
    private int totalMoney = 0;

    private int day;

    private int daysLeft;

    /** считает дни от последней выплаты зп */
    private int salaryDay;

    /** нанято за игру */
    private int hired;

    /** уволено за игру */
    private int fired;

    /** выплачено премий */
    private int bonusPayment;

    public CurrentStatus(Project project)
    {
        this.money = project.getCost();
        this.day = 1;
        this.salaryDay = 1;
        this.daysLeft = project.getDays();
        this.hired = 0;
        this.fired = 0;
        this.bonusPayment = 0;
    }

    public int getMoney()
    {
        return money;
    }

    public void setMoney(int money)
    {
        this.money = money;
    }

    public int getDay()
    {
        return day;
    }

    public int getDaysLeft()
    {
        return daysLeft;
    }

    public void incDay()
    {
        day++;
        daysLeft--;
        salaryDay++;

        if (salaryDay == 31)
        {
            salaryDay = 1;
        }
    }

    public int getSalaryDay()
    {
        return salaryDay;
    }

    public String getHired()
    {
        return String.valueOf(hired);
    }

    public void addHired()
    {
        this.hired++;
    }

    public String getFired()
    {
        return String.valueOf(fired);
    }

    public void addFired()
    {
        this.fired++;
    }

    public String getBonusPayment()
    {
        return String.valueOf(bonusPayment);
    }

    public void addBonusPayment()
    {
        this.bonusPayment++;
    }

    public boolean isVictory(Project project)
    {
        if ((project.isFinished() == true) && (daysLeft >= 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isLost(Project project)
    {
        if ((daysLeft < 0) || (money + totalMoney < 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getTotalMoney()
    {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney)
    {
        this.totalMoney = totalMoney;
    }

    public void restart(Project project)
    {
        this.money = project.getCost();
        this.day = 1;
        this.salaryDay = 1;
        this.daysLeft = project.getDays();
        this.hired = 0;
        this.fired = 0;
        this.bonusPayment = 0;
    }
}
