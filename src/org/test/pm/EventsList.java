package org.test.pm;

import java.util.ArrayList;
import java.util.Random;

public class EventsList
{
    private ArrayList<PmEvent> list = new ArrayList<>();

    public EventsList()
    {
        // Хорошие события
        list.add(new PmEvent(
                "Все сотрудники прошли бесплатное обучение в Google.",
                0, 10, 0, 0, false));
        list.add(new PmEvent(
                "Небольшие изменения архитектуры резко улучшили прогресс.",
                0, 0, 1.05f, 0, false));
        list.add(new PmEvent(
                "Рядом открылось новое кафе со скидками для программистов",
                0, 10, 0, 0, false));
        list.add(new PmEvent(
                "Рядом открылось модельное агентство.",
                10, 0, 0, 0, false));
        list.add(new PmEvent(
                "Новая симпатичная секретарша.",
                5, 0, 0, 0, false));
        list.add(new PmEvent(
                "Экономический кризис, все сотрудники рады, что у них еще есть работа.",
                30, 0, 0, 0, false));
        
        list.add(new PmEvent("защитил диссертацию.",
                0, 200, 0, 0, true));
        
        // Плохие события
        list.add(new PmEvent(
                "Соседи сверху затопили северную, потеряно 10% прогресса проекта.",
                0, 0, 0.1f, 0, false));
        list.add(new PmEvent(
                "Заказчик урезал бюджет проекта на 10%",
                0, 0, 0, 0.9f, false));
        list.add(new PmEvent(
                "Повысилась востребованность программистов на рынке, все просят больше зарплаты, -7 к настроению всем.",
                -7, 0, 0, 0, false));
        list.add(new PmEvent("Злобный вирус съел 20% прогресса проекта",
                0, 0, 0.2f, 0, false));
      
    }

    public PmEvent getRandomEvent()
    {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
}
