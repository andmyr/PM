package org.test.pm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;

public class StatWindow extends JFrame
{
    /**
     * Класс вывода статистики
     */
    private static final long serialVersionUID = 3272066239398098127L;

    private JPanel contentPane;

    /**
     * Класс вывода статистики.
     * 
     * 
     */
    public StatWindow(CurrentStatus cs, ArrayList<Programmer> workers, Project project)
    {
        setFont(new Font("Dialog", Font.PLAIN, 14));
        setResizable(false);
        setTitle("Статистика игры");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 313, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(5, 2, 0, 0));

        JLabel lblNewLabel = new JLabel("Проект выполнен на");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblNewLabel);

        int temp = Math.round(project.getPerformed() / project.getLaboriousness() * 100);
        JLabel lblProjectStatus;
        if (temp > 100)
        {
            lblProjectStatus = new JLabel("100 %");
        }
        else
        {
            lblProjectStatus = new JLabel(String.valueOf(temp) + " %");
        }

        lblProjectStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lblProjectStatus.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblProjectStatus);

        JLabel lblNewLabel_4 = new JLabel("Число сотрудников");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblNewLabel_4);

        JLabel lblWorkers = new JLabel(String.valueOf(workers.size()));
        lblWorkers.setHorizontalAlignment(SwingConstants.CENTER);
        lblWorkers.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblWorkers);

        JLabel lblNewLabel_6 = new JLabel("Число наймов");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblNewLabel_6);

        JLabel lblHired = new JLabel(cs.getHired());
        lblHired.setHorizontalAlignment(SwingConstants.CENTER);
        lblHired.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblHired);

        JLabel lblNewLabel_8 = new JLabel("Число увольнений");
        lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblNewLabel_8);

        JLabel lblFired = new JLabel(cs.getFired());
        lblFired.setHorizontalAlignment(SwingConstants.CENTER);
        lblFired.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblFired);

        JLabel lblNewLabel_5 = new JLabel("Премий выплачено");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblNewLabel_5);

        JLabel lblBonus = new JLabel(cs.getBonusPayment());
        lblBonus.setHorizontalAlignment(SwingConstants.CENTER);
        lblBonus.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(lblBonus);
        setLocationRelativeTo(null);
    }
}
