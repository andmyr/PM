/*
 * Programmer
 * 
 * Программист со всеми свойствами
 */

package org.test.pm;

import java.util.ArrayList;
import java.util.Random;

public class Programmer
{
    String name;

    Rank rank;

    /** Работоспособность (процент трудодня за день) */
    private int efficiency;

    /** текущая зп */
    private int salary;

    /** желаемая зп */
    private int wantsSalary;

    /** настроение */
    private int mood;

    /** обучаемость, на сколько растет работоспособность в день */
    private int educability;

    /** рост производительности команды если сотрудник TEAM_LEAD */
    private float teamLeadBonus;

    /** День начала работы */
    private int firstDay;

    private static String[] nname = { "Глеб", "Степан", "Руслан", "Анатолий", "Кирилл", "Семен", "�?ван", "Николай",
            "Михаил" };

    private static String[] fname = { "Шилов", "Николаев", "Титов", "Бобылев", "Егоров", "Рожков", "Дорогин",
            "Гребешков", "�?ванов", "Чудилов", "Закидонов" };

    public Programmer()
    {
        this.name = generateName(); // генерирование уникальных имен

        Random random = new Random();

        int chooser = random.nextInt(100);
        int chooser2 = 0;
        if (chooser < 50)
        {
            chooser2 = 0;
        }
        else if (chooser < 80)
        {
            chooser2 = 1;
        }
        else if (chooser < 90)
        {
            chooser2 = 2;
        }
        else if (chooser < 98)
        {
            chooser2 = 3;
        }
        else if (chooser < 100)
        {
            chooser2 = 4;
        }

        switch (chooser2)
        {
            case 0:
            {
                this.rank = Rank.TRANIEE;
                this.efficiency = random.nextInt(25) + 1;
                this.setWantsSalary(random.nextInt(30) * 10);
                this.mood = 20;
                this.teamLeadBonus = 1;
                break;
            }
            case 1:
            {
                this.rank = Rank.JUNIOR;
                this.efficiency = random.nextInt(75) + 25;
                this.setWantsSalary((random.nextInt(50) + 30) * 10);
                this.mood = 10;
                this.teamLeadBonus = 1;
                break;
            }
            case 2:
            {
                this.rank = Rank.MIDDLE;
                this.efficiency = random.nextInt(200) + 100;
                this.setWantsSalary((random.nextInt(70) + 80) * 10);
                this.mood = 10;
                this.teamLeadBonus = 1;
                break;
            }
            case 3:
            {
                this.rank = Rank.SENIOR;
                this.efficiency = random.nextInt(700) + 300;
                this.setWantsSalary((random.nextInt(150) + 150) * 10);
                this.mood = 5;
                this.teamLeadBonus = 1;
                break;
            }
            case 4:
            {
                this.rank = Rank.TEAM_LEAD;
                this.efficiency = random.nextInt(200) + 1000;
                this.setWantsSalary((random.nextInt(200) + 300) * 10);
                this.mood = 20;
                this.teamLeadBonus = 1 + (float) (random.nextInt(20) + 11) / 100;
                break;
            }
        }

        this.educability = random.nextInt(3) + 1;
        this.salary = 0;

    }

    private static String generateName()
    {
        Random random = new Random();
        String newName = "";
        do
        {
            newName = fname[random.nextInt(fname.length)] + " " + nname[random.nextInt(nname.length)];
        } while (nameContains(LaborExchange.list, newName) == true);

        return newName;
    }

    private static boolean nameContains(ArrayList<Programmer> list2, String newName)
    {
        if (list2.isEmpty() == true)
            return false;
        for (Programmer p : list2)
        {
            if (p.name.equalsIgnoreCase(newName))
            {
                return true;
            }
        }
        for (Programmer p : ProjectManager.workers)
        {
            if (p.name.equalsIgnoreCase(newName))
            {
                return true;
            }
        }

        return false;
    }

    public void setEducability(int educability)
    {
        this.educability = educability;
    }

    public Rank getRank()
    {
        return rank;
    }

    public void setRank(Rank rank)
    {
        this.rank = rank;
    }

    public int getEfficiency()
    {
        return efficiency;
    }

    public void setEfficiency(int efficiency)
    {
        this.efficiency = efficiency;
    }

    public int getSalary()
    {
        return salary;
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }

    public int getWantsSalary()
    {
        return wantsSalary;
    }

    public void setWantsSalary(int wantsSalary)
    {
        this.wantsSalary = wantsSalary;
    }

    public int getMood()
    {
        return mood;
    }

    public void setMood(int mood)
    {
        if (mood > 20)
            this.mood = 20;
        else
            this.mood = mood;
    }

    public Integer getEducability()
    {
        return educability;
    }

    public int getFirstDay()
    {
        return firstDay;
    }

    public void setFirstDay(int firstDay)
    {
        this.firstDay = firstDay;
    }

    public float getTeamLeadBonus()
    {
        return teamLeadBonus;
    }

    public void setTeamLeadBonus(float teamLeadBonus)
    {
        this.teamLeadBonus = teamLeadBonus;
    }

    /**
     * �?зменение зарплаты и расчет изменения настроения после этого
     * 
     * @param change
     *            - изменение зарплаты
     * @param mode
     *            - 0 - разовая премия, 1 - изменение зарплаты
     */
    public void changeSalary(int change, int mode)
    {
        if (mode == 1)
        {
            salary = salary + change < 0 ? 0 : salary + change;
        }

        float fmood, fchange, fsalary;
        fmood = mood;
        fchange = change;
        fsalary = salary;

        if (mode == 1)
        {
            if (change < 0)
            {
                fmood = fmood + fchange / fsalary * 100 / 2;
            }
            else
            {
                fmood = fmood + fchange / fsalary * 100;
            }
        }
        else
        {
            fmood = fmood + fchange / fsalary * 100 / 2;
        }
        mood = (int) fmood;

        if (mood > 20)
        {
            mood = 20;
        }
        if (mood < -10)
        {
            mood = -10;
        }
    }

    /** возвращает настроение как строку для скрытия конкретного значения */
    public String getMoodString()
    {
        if (mood > 10)
            return "Отличное";
        else if (mood > 4)
            return "Хорошее";
        else if (mood > 0)
            return "Нормальное";
        else if (mood > -8)
            return "Плохое";
        else
            return "Ужасное";
    }

    /**
     * Принимает ли кандидат ваше предложение о найме или нет.
     * 
     * @param salary
     *            - предложенная зп
     *
     */
    public boolean isAgree(int salary)
    {
        Random random = new Random();
        Float fsalary = (float) salary;
        Float fwantsSalary = (float) wantsSalary;

        switch (rank)
        {
            case TRANIEE:
            {
                if (((fsalary > fwantsSalary) || (!(random.nextInt(20) == 0))) && (fsalary > 0))
                {
                    return true;
                }
                break;
            }
            case JUNIOR:
            {
                Float f = ((fsalary / fwantsSalary) * (((float) random.nextInt(15) + 85) / 100));
                // System.out.println(f);
                if (f >= 0.85)
                {
                    return true;
                }
                break;
            }
            case MIDDLE:
            {
                Float f = ((fsalary / fwantsSalary) * (((float) random.nextInt(25) + 75) / 100));
                // System.out.println(f);
                if (f >= 0.75)
                {
                    return true;
                }
                break;
            }
            case SENIOR:
            {
                Float f = ((fsalary / fwantsSalary) * (((float) random.nextInt(40) + 60) / 100));
                // System.out.println(f);
                if (f >= 0.60)
                {
                    return true;
                }
                break;
            }
            case TEAM_LEAD:
            {
                Float f = ((fsalary / fwantsSalary) * (((float) random.nextInt(50) + 50) / 100));
                // System.out.println(f);
                if (f >= 0.5)
                {
                    return true;
                }
                break;
            }
            default:
            {
                System.out.println("Default");
            }
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "Programmer [name=" + name + ", rank=" + rank + ", efficiency=" + efficiency + ", salary=" + salary
                + ", wantsSalary=" + wantsSalary + ", mood=" + mood + ", educability=" + educability
                + ", teamLeadBonus=" + teamLeadBonus + ", firstDay=" + firstDay + "]";
    }
}
