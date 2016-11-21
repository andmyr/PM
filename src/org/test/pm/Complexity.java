package org.test.pm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class Complexity extends JFrame
{

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;


    /**
     * Создание окна настройки сложности следующей игры.
     * @param level
     *          - текущий уровень
     * @param size 
     *          - число уровней в игре
     */
    public Complexity(int level, int size)
    {
        setResizable(false);
        setTitle("Сложность проекта");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 398, 172);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        contentPane.setLayout(new GridLayout(4, 1, 0, 0));

        final JLabel lblNewLabel = new JLabel("Сложность проекта - " + level);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Новая сложность начнет действовать с новой игры");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(lblNewLabel_1);

        final JSlider slider = new JSlider();
        slider.setValue(level);
        slider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent arg0)
            {
                lblNewLabel.setText("Сложность проекта - " + slider.getValue());
            }
        });
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(1);
        slider.setValue(0);
        slider.setMaximum(size);
        slider.setMinimum(0);
        contentPane.add(slider);

        JButton btnNewButton = new JButton("Установить сложность");
        btnNewButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                ProjectManager.level = slider.getValue();
                dispose();
            }
        });
        contentPane.add(btnNewButton);
    }
}
