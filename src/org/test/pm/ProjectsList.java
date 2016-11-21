package org.test.pm;

import java.util.ArrayList;

/**
 * Массив проектов
 *
 */
public class ProjectsList
{
    private ArrayList<Project> list = new ArrayList<>();

    public ProjectsList()
    {
        list.add(new Project(95, 10000, 1500));
        list.add(new Project(80, 8000, 3000));
        list.add(new Project(40, 5000, 5000));
        list.add(new Project(90, 5000, 7000));
        list.add(new Project(45, 6000, 6000));
        list.add(new Project(60, 1000, 10000));
        list.add(new Project(60, 5000, 14000));
        list.add(new Project(60, 15000, 16000));
        list.add(new Project(60, 15000, 15000));
        list.add(new Project(60, 15000, 18000));
        list.add(new Project(60, 30000, 20000));
        list.add(new Project(60, 15000, 10000));
        list.add(new Project(40, 15000, 10000));
        list.add(new Project(90, 50000, 40000));
        list.add(new Project(50, 15000, 60000));      
    }
        
    public Project getProject(int index)
    {         
        return list.get(index);       
    }
    
    public int getSize()
    {
        return list.size();       
    }
}
