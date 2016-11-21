/*
 * ProjectManager
 * 
 * Основное окно программы, кнопки и обработка событий
 *
 */

package org.test.pm;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JProgressBar;
import javax.swing.BoxLayout;
import javax.swing.Timer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class ProjectManager extends JFrame
{
    /** Уровень сложности игры (чем меньше - тем легче) */
    public static int level = 0;

    public static ArrayList<Programmer> workers = new ArrayList<>();

    /** Запущена ли уже биржа */
    public static boolean exchangeFlag = false;

    private static final long serialVersionUID = 1L;

    /** Вероятность возникновения случайного события (чем меньше - тем чаще) */
    private static final int PROBABILITY_OF_EVENT = 30;

    /** вероятность поломки TRANIEE проекта (чем меньше - тем вероятнее) */
    private static final int FAILURE_INDEX = 30;

    private Timer timer;

    private JPanel contentPane;

    private JTable table;

    private JPanel panel;

    private JPanel panel_1;

    private JProgressBar progressBarDays;

    private JProgressBar progressBarMoney;

    private JLabel lblDaysGone;

    private JLabel lblMoneyLeft;

    private JLabel labelDaysLeft;

    private JProgressBar progressBarProgress;

    private JPanel panel_2;

    private JButton btnNewButton;

    private JButton btnNewButton_1;

    private JButton button;

    private JButton button_1;

    private JButton btnNewButton_2;

    private JButton btnExchange;

    /** номер выделеной строки */
    private int rawIndex = -1;

    private Project project;

    private CurrentStatus currentStatus;

    private JLabel lblTotalMoney;

    private JSpinner spinner;

    private boolean winLastGame = false;

    private LaborExchange frame;

    public ProjectManager()
    {

        setTitle("Project Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 833, 344);
        contentPane = new JPanel();
        contentPane.setVisible(false);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        final ProjectsList projectsList = new ProjectsList();

        // нарисуем меню
        paintMenuBar(projectsList);

        panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        table = new JTable()
        {
            private static final long serialVersionUID = 1L;

            // запретим редактирование таблицы
            @Override
            public boolean isCellEditable(int arg0, int arg1)
            {
                return false;
            }
        };

        repaintTable();

        table.getColumnModel().getColumn(2).setPreferredWidth(140);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel_2 = new JPanel();
        panel.add(panel_2);

        // нарисуем кнопки
        paintButtons();

        panel_1 = new JPanel();

        // нарисуем панель статистики
        paintStatPanel();

        addProject(project);

        // по центру экрана
        setLocationRelativeTo(null);

        // создадим список случайных событий
        final EventsList eventsList = new EventsList();

        // Основной алгоритм
        timer = new Timer(1500, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // уволим сотрудников с настроением меньше -9
                for (int i = 0; i < workers.size(); i++)
                {
                    if ((!workers.isEmpty()) && (workers.get(i).getMood() < -9))
                    {
                        // Выплатим сотруднику остаток зп за часть месяца
                        float f;
                        float f1 = currentStatus.getSalaryDay();
                        float f2 = workers.get(i).getFirstDay();

                        if (currentStatus.getDay() - workers.get(i).getFirstDay() < 30)
                        {
                            f = ((f1 - f2) / 30);
                        }
                        else
                        {
                            f = f1 / 30;
                        }
                        currentStatus.setMoney((int) (currentStatus.getMoney() - workers.get(i).getSalary() * f));
                        JOptionPane.showMessageDialog(null, workers.get(i).name + " уволился.");
                        workers.remove(i);
                        currentStatus.addFired();
                    }
                }

                Random random = new Random();

                // Проверяем наличие TEAM_LEAD в команде и расчитываем бонус
                float bonus = 0;
                int count = 0;
                for (Programmer p : workers)
                {
                    if (p.rank == Rank.TEAM_LEAD)
                    {
                        bonus += p.getTeamLeadBonus();
                        count++;
                    }
                }
                if (count > 0)
                {
                    bonus = bonus / count;
                }
                else
                {
                    bonus = 1;
                }

                for (Programmer p : workers)
                {
                    if (currentStatus.getSalaryDay() == 30) // начисление ЗП
                    {

                        if (currentStatus.getDay() - p.getFirstDay() < 30)
                        {
                            float f = p.getSalary() * (currentStatus.getDay() - p.getFirstDay() + 1) / 30;
                            // System.out.println(f);
                            currentStatus.setMoney(currentStatus.getMoney() - (int) f);
                        }
                        else
                        {
                            currentStatus.setMoney(currentStatus.getMoney() - p.getSalary());
                        }

                        // Расчет роста работоспособности
                        p.setEfficiency(p.getEfficiency() + p.getEducability() * 50);

                        // Расчет настроения
                        p.setMood(p.getMood() - random.nextInt(6) + 1);
                    }

                    // если текущая зп меньше чем, желаемая (wantsSalary) -
                    // ухудшить настроение

                    if (p.getWantsSalary() > p.getSalary())
                    {
                        p.setMood(p.getMood() - 1);
                    }

                    // Проверка на наличие trainee и поломки проекта

                    if (p.getRank() == Rank.TRANIEE)
                    {
                        if (random.nextInt(FAILURE_INDEX) == 0)
                        {
                            int temp = random.nextInt(290) + 10;
                            project.setPerformed(-temp);
                            JOptionPane.showMessageDialog(null, p.name + " сломал проект!");
                        }
                    }

                    // Расчет проделанной работы
                    float moodK;
                    if (p.getMood() > 0)
                    {
                        moodK = 1 + (float) p.getMood() / 2 / 100;
                    }
                    else
                    {
                        moodK = (10 + (float) p.getMood()) / 10;
                    }

                    int performed = (int) ((float) p.getEfficiency() / 30 * moodK * bonus);

                    project.setPerformed(performed);
                }

                setTitle("PM. Уровень " + level + "."); // Осталось отработать "
                // + (project.getLaboriousness() - project.getPerformed()) +
                // " трудодней");

                // Случайные события

                if (((float) project.getPerformed() / (float) project.getLaboriousness() * 100 > 1)
                        && (random.nextInt(PROBABILITY_OF_EVENT) == 0))
                {
                    PmEvent pMEvent = eventsList.getRandomEvent();

                    if (pMEvent.isOne() && workers.size() > 0)
                    {
                        // изменение параметров одного сотрудника
                        int temp = random.nextInt(workers.size() + 1);

                        // изменение работоспособности
                        if (pMEvent.getEfficiency() != 0)
                        {
                            workers.get(temp)
                                    .setEfficiency(workers.get(temp).getEfficiency() + pMEvent.getEfficiency());
                        }

                        // изменение настроения
                        if (pMEvent.getMood() != 0)
                        {
                            workers.get(temp).setMood(workers.get(temp).getMood() + pMEvent.getMood());
                        }
                        JOptionPane.showMessageDialog(null, workers.get(temp).name + " " + pMEvent.getName());
                    }
                    else
                    {
                        // изменение параметров всех сотрудников
                        for (Programmer p : workers)
                        {
                            // изменение работоспособности
                            if (pMEvent.getEfficiency() != 0)
                            {
                                p.setEfficiency(p.getEfficiency() + pMEvent.getEfficiency());
                            }

                            // изменение настроения
                            if (pMEvent.getMood() != 0)
                            {
                                p.setMood(p.getMood() + pMEvent.getMood());
                            }
                        }

                        // изменение прогресса проекта
                        if (pMEvent.getProgress() != 0)
                        {
                            if (pMEvent.getProgress() < 1)
                            {
                                project.setPerformed((int) (-project.getPerformed() * pMEvent.getProgress()));
                            }
                            else
                            {
                                project.setPerformed((int) (project.getPerformed() * (1 - pMEvent.getProgress())));
                            }
                        }

                        // изменение денег
                        if (pMEvent.getpCost() != 0)
                        {
                            currentStatus.setMoney((int) (currentStatus.getMoney() * pMEvent.getpCost()));
                        }

                        JOptionPane.showMessageDialog(null, pMEvent.getName());
                    }
                }

                // Проверка на победу
                if (currentStatus.isVictory(project))
                {
                    winLastGame = true;
                    timer.stop();

                    currentStatus.setTotalMoney(currentStatus.getMoney() + currentStatus.getTotalMoney());

                    // Проверим осталось ли у нас денег на ближайшую зарплату.
                    // Если нет - уволим всех сотрудников.

                    int lastSalary = 0;

                    for (Programmer p : workers)
                    {
                        if (currentStatus.getDay() + currentStatus.getSalaryDay() - p.getFirstDay() < 30)
                        {
                            float f = p.getSalary() * (currentStatus.getDay() - p.getFirstDay() + 1) / 30;
                            // currentStatus.setMoney(currentStatus.getMoney() -
                            // (int) f);
                            lastSalary += f;
                        }
                        else
                        {
                            lastSalary += p.getSalary();
                            // currentStatus.setMoney(currentStatus.getMoney() -
                            // p.getSalary());
                        }
                    }

                    // boolean isWorkersClear = false;

                    if (lastSalary < currentStatus.getTotalMoney() + currentStatus.getMoney())
                    {
                        JOptionPane.showMessageDialog(null, "Победа!");

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,
                                "У вас не хватает денег на ближайшую зарплату. Вы проиграли!");
                        winLastGame = false;
                        timer.stop();
                        StatWindow statWIndow = new StatWindow(currentStatus, workers, project);
                        statWIndow.setVisible(true);
                        return;
                    }

                    // StatWindow statWIndow = new StatWindow(currentStatus,
                    // workers, project);
                    /*
                     * if (isWorkersClear) { workers.clear(); }
                     */
                    // statWIndow.setVisible(true);

                    if (winLastGame)
                    {
                        level++;
                        // Проверяем, есть ли еще уровни
                        if (level < projectsList.getSize() + 1)
                        {
                            JOptionPane.showMessageDialog(null, "Перейдите на уровень " + level);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "�?гра закончилась! Больше нет уровней!");
                            JOptionPane.showMessageDialog(null, "Вы заработали " + currentStatus.getTotalMoney()
                                    + currentStatus.getMoney());
                            return;
                        }

                        project = projectsList.getProject(level);
                        currentStatus.restart(project);
                        timer.start();
                    }
                    winLastGame = false;
                    return;
                }

                // Проверка на проигрыш
                if (currentStatus.isLost(project))
                {
                    if (currentStatus.getMoney() + currentStatus.getTotalMoney() < 0)
                    {
                        JOptionPane.showMessageDialog(null, "Вы проиграли! У вас закончились деньги.");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Вы проиграли! Вы не вложились в сроки.");
                    }
                    // JOptionPane.showMessageDialog(null, "Вы проиграли!");
                    winLastGame = false;
                    timer.stop();
                    StatWindow statWIndow = new StatWindow(currentStatus, workers, project);
                    statWIndow.setVisible(true);
                    return;
                }

                currentStatus.incDay();
                repaintTable();
                repaintStat();

            }
        });

    }

    public void repaintTable()
    {

        rawIndex = table.getSelectionModel().getLeadSelectionIndex();
        // table.removeAll();
        DefaultTableModel model = new DefaultTableModel();
        while (model.getRowCount() > 0)
        {
            model.removeRow(0);
        }
        model.addColumn("Ф�?О");
        model.addColumn("Зарплата");
        model.addColumn("Работоспособность");
        model.addColumn("Настроение");
        model.addColumn("Уровень");

        for (Programmer p : workers)
        {
            String[] temp = { p.name, p.getSalary() + "", p.getEfficiency() + "", p.getMoodString(), p.rank + "" };
            model.addRow(temp);
        }

        table.setModel(model);

        // востанавливем текущую выделенную строку
        if ((rawIndex > -1) && (model.getRowCount() >= rawIndex + 1))
        {
            try
            {
                table.addRowSelectionInterval(rawIndex, rawIndex);
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
        }
    }

    public void addProject(Project project)
    {
        if (project == null)
            return;

        lblMoneyLeft.setText("Осталось денег: " + project.getCost());
        progressBarMoney.setValue(100);
        labelDaysLeft.setText("Дней до конца срока: " + project.getDays());
    }

    public void repaintStat()
    {
        lblDaysGone.setText("Дней прошло: " + currentStatus.getDay());
        lblMoneyLeft.setText("Осталось денег: " + currentStatus.getMoney());
        labelDaysLeft.setText("Дней до конца срока: " + currentStatus.getDaysLeft());
        lblTotalMoney.setText("Всего денег на счету: " + currentStatus.getTotalMoney());

        progressBarDays.setMaximum(project.getDays());
        progressBarDays.setValue(currentStatus.getDay());

        progressBarMoney.setMaximum(project.getCost());
        progressBarMoney.setValue(currentStatus.getMoney());

        progressBarProgress.setMaximum((int) project.getLaboriousness());
        progressBarProgress.setValue((int) project.getPerformed());
    }

    private void paintMenuBar(final ProjectsList projectsList)
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("�?гра");

        JMenuItem item1 = new JMenuItem("Новая");
        menuFile.add(item1);
        item1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                int n = JOptionPane.showConfirmDialog(null, "Начать новую игру?");
                if (n == 0)
                {
                    level = 0;
                    project = projectsList.getProject(level);
                    if (!workers.isEmpty())
                    {
                        workers.clear();
                    }

                    if (currentStatus == null)
                    {
                        currentStatus = new CurrentStatus(project);
                    }
                    else
                    {
                        currentStatus.restart(project);
                    }
                    currentStatus.setTotalMoney(0);

                    winLastGame = false;
                    contentPane.setVisible(true);
                    timer.start();
                }
            }
        });

        JMenuItem item4 = new JMenuItem("Выход");
        menuFile.add(item4);
        item4.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int n = JOptionPane.showConfirmDialog(null, "Выйти из игры?");
                if (n == 0)
                {
                    System.exit(0);
                }
            }
        });

        /*
         * JMenu menuView = new JMenu("Настройки"); JMenuItem item5 = new
         * JMenuItem("Сложность"); menuView.add(item5);
         * 
         * item5.addActionListener(new java.awt.event.ActionListener() { public
         * void actionPerformed(ActionEvent e) { Complexity complexity = new
         * Complexity(level, projectsList.getSize()); //
         * System.out.println(level); complexity.setVisible(true); } });
         */

        JMenu mnAbout = new JMenu("О программе");
        JMenuItem item6 = new JMenuItem("О программе");
        mnAbout.add(item6);

        item6.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "ProjectManager. Mushta Andrii 2015");
            }
        });

        menuBar.add(menuFile);
        // menuBar.add(menuView);
        menuBar.add(mnAbout);
        setJMenuBar(menuBar);
    }

    private void paintButtons()
    {
        btnExchange = new JButton("Биржа");
        btnExchange.setToolTipText("Вызов биржи для найма сотрудников");
        panel_2.add(btnExchange);
        btnExchange.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (timer.isRunning())
                {
                    if (!exchangeFlag)
                    {
                        exchangeFlag = true;
                        frame = new LaborExchange(currentStatus);
                        frame.setVisible(true);
                    }
                }
            };
        });

        btnNewButton = new JButton("Уволить");
        btnNewButton.setToolTipText("Уволить выбранного сотрудника");
        panel_2.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int[] temp = table.getSelectedRows();
                if (temp.length == 0)
                {
                    JOptionPane.showMessageDialog(null, "Выберите сотрудника");
                    return;
                }

                int index = table.getSelectionModel().getLeadSelectionIndex();
                int n = JOptionPane.showConfirmDialog(null, "Уволить сотрудника " + workers.get(index).name + "?");
                if (n == 0)
                {
                    // Выплатим сотруднику остаток зп за часть месяца
                    float f;
                    float f1 = currentStatus.getSalaryDay();
                    float f2 = workers.get(index).getFirstDay();

                    if (currentStatus.getDay() - workers.get(index).getFirstDay() < 30)
                    {
                        f = ((f1 - f2) / 30);
                    }
                    else
                    {
                        f = f1 / 30;
                    }
                    currentStatus.setMoney((int) (currentStatus.getMoney() - workers.get(index).getSalary() * f));

                    // Если испытательны срок закончился - при увольнении
                    // заплатим штраф 50%
                    if (currentStatus.getDay() - workers.get(index).getFirstDay() > 90)
                    {
                        currentStatus.setMoney(currentStatus.getMoney() - workers.get(index).getSalary() / 2);

                    }
                    workers.remove(index);
                    currentStatus.addFired();
                }
            }
        });

        btnNewButton_1 = new JButton("Увеличить ЗП на 50");
        btnNewButton_1.setToolTipText("Увеличить зарплату выбранному сотруднику с этого месяца");
        panel_2.add(btnNewButton_1);
        btnNewButton_1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int[] temp = table.getSelectedRows();
                if (temp.length == 0)
                {
                    JOptionPane.showMessageDialog(null, "Выберите сотрудника");
                    return;
                }
                int index = table.getSelectionModel().getLeadSelectionIndex();
                workers.get(index).changeSalary(50, 1);
            };

        });

        button = new JButton("Уменьшить ЗП на 50");
        button.setToolTipText("Уменьшить зарплату выделенному сотруднику с текущего месяца");
        panel_2.add(button);
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int[] temp = table.getSelectedRows();
                if (temp.length == 0)
                {
                    JOptionPane.showMessageDialog(null, "Выберите сотрудника");
                    return;
                }
                int index = table.getSelectionModel().getLeadSelectionIndex();
                if (workers.get(index).getSalary() > 50)
                {
                    workers.get(index).changeSalary(-50, 1);
                }
            };

        });

        button_1 = new JButton("Премия на 100");
        button_1.setToolTipText("Премировать выделенного сотрудника");
        panel_2.add(button_1);
        button_1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int[] temp = table.getSelectedRows();
                if (temp.length == 0)
                {
                    JOptionPane.showMessageDialog(null, "Выберите сотрудника");
                    return;
                }
                int index = table.getSelectionModel().getLeadSelectionIndex();

                workers.get(index).changeSalary(100, 0);

                currentStatus.setMoney(currentStatus.getMoney() - 100);
                currentStatus.addBonusPayment();
            };

        });

        btnNewButton_2 = new JButton("Повысить в должности");
        btnNewButton_2.setToolTipText("Повысить в должности (ранге) сотрудника");
        panel_2.add(btnNewButton_2);
        btnNewButton_2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int[] temp = table.getSelectedRows();
                if (temp.length == 0)
                {
                    JOptionPane.showMessageDialog(null, "Выберите сотрудника");
                    return;
                }
                int index = table.getSelectionModel().getLeadSelectionIndex();
                switch (workers.get(index).rank)
                {
                    case TRANIEE:
                        if (workers.get(index).getEfficiency() > 25)
                        {
                            workers.get(index).setMood(20);
                        }
                        else
                        {
                            workers.get(index).setMood(workers.get(index).getMood() - 20);
                        }
                        workers.get(index).setWantsSalary(301);
                        workers.get(index).setRank(Rank.JUNIOR);
                        break;

                    case JUNIOR:
                        if (workers.get(index).getEfficiency() > 100)
                        {
                            workers.get(index).setMood(20);

                        }
                        else
                        {
                            workers.get(index).setMood(workers.get(index).getMood() - 20);
                        }
                        workers.get(index).setWantsSalary(801);
                        workers.get(index).setRank(Rank.MIDDLE);
                        break;

                    case MIDDLE:
                        if (workers.get(index).getEfficiency() > 300)
                        {
                            workers.get(index).setMood(20);
                        }
                        else
                        {
                            workers.get(index).setMood(workers.get(index).getMood() - 20);
                        }
                        workers.get(index).setWantsSalary(1501);
                        workers.get(index).setRank(Rank.SENIOR);
                        break;

                    case SENIOR:
                        Random random = new Random();
                        if (workers.get(index).getEfficiency() > 1000)
                        {
                            workers.get(index).setMood(20);
                            workers.get(index).setTeamLeadBonus(1 + (float) (random.nextInt(20) + 11) / 100);
                        }
                        else
                        {
                            workers.get(index).setMood(workers.get(index).getMood() - 20);
                            workers.get(index).setTeamLeadBonus(1);
                        }
                        workers.get(index).setWantsSalary(3001);
                        workers.get(index).setRank(Rank.TEAM_LEAD);
                        break;

                    case TEAM_LEAD:
                        JOptionPane.showMessageDialog(null, "У сотрудника максимальный ранг!");
                        break;

                    default:
                        System.out.println("Ошибка выбора!");
                }
            };

        });
    }

    private void paintStatPanel()
    {
        contentPane.add(panel_1, BorderLayout.SOUTH);
        panel_1.setLayout(new GridLayout(4, 2, 0, 0));

        lblDaysGone = new JLabel("Дней прошло - 1");
        lblDaysGone.setHorizontalAlignment(SwingConstants.LEFT);
        panel_1.add(lblDaysGone);

        progressBarDays = new JProgressBar();
        progressBarDays.setToolTipText("Дней до окончания проекта:");
        progressBarDays.setMaximum(0);
        progressBarDays.setValue(0);
        progressBarDays.setStringPainted(true);
        panel_1.add(progressBarDays);

        lblMoneyLeft = new JLabel("Осталось денег: ");
        lblMoneyLeft.setHorizontalAlignment(SwingConstants.LEFT);
        panel_1.add(lblMoneyLeft);

        progressBarMoney = new JProgressBar();
        progressBarMoney.setToolTipText("Остаток денег");
        progressBarMoney.setMinimum(0);
        progressBarMoney.setValue(0);
        progressBarMoney.setStringPainted(true);
        panel_1.add(progressBarMoney);

        labelDaysLeft = new JLabel("Дней до конца срока: ");
        labelDaysLeft.setHorizontalAlignment(SwingConstants.LEFT);
        panel_1.add(labelDaysLeft);

        progressBarProgress = new JProgressBar();
        progressBarProgress.setToolTipText("Прогресс выполнения проекта");
        progressBarProgress.setMinimum(0);
        progressBarProgress.setValue(0);
        progressBarProgress.setStringPainted(true);
        panel_1.add(progressBarProgress);

        lblTotalMoney = new JLabel("Всего денег на счету: ");
        lblTotalMoney.setHorizontalAlignment(SwingConstants.LEFT);
        panel_1.add(lblTotalMoney);

        // Скорость игры
        spinner = new JSpinner();
        spinner.setToolTipText("Текущая скорость игры (меньше число - больше скорость)");
        spinner.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent arg0)
            {
                if (timer != null)
                {
                    timer.setDelay((int) spinner.getValue());
                }
            }
        });
        spinner.setModel(new SpinnerNumberModel(1500, 500, 5000, 50));
        panel_1.add(spinner);
    }
}
