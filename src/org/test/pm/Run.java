/*
 * Главный класс
 * 
 * Хранит ссылки на ключевые статические объекты
 *
 */
package org.test.pm;

public class Run
{
    public static void main(String[] args)
    {
        ProjectManager projectManager = new ProjectManager();
        projectManager.setVisible(true);
    }
}
