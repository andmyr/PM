/**
 * Окно предложения уровня зарплаты
 */

package org.test.pm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Recruitment extends JDialog
{

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();

    private JSpinner spinner;

    /**
     * Create the dialog.
     * 
     * @param index
     *            - позиция в массиве
     * 
     *            Так же передаем ссылки на окна для перерисовки
     */
    public Recruitment(final Programmer programmer, final int index, final CurrentStatus currentStatus,
            final LaborExchange laborExchange)
    {
        setBounds(100, 100, 426, 144);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
        {
            JLabel lblNewLabel_1 = new JLabel("Ф�?О кандидата");
            contentPanel.add(lblNewLabel_1);
        }
        {
            JLabel lblName = new JLabel(programmer.name);
            lblName.setFont(new Font("Tahoma", Font.BOLD, 13));
            contentPanel.add(lblName);
        }
        {
            JLabel label = new JLabel("Желаемая зарплата");
            contentPanel.add(label);
        }
        {
            JLabel lblWantsSalary = new JLabel("" + (programmer.getWantsSalary()));
            contentPanel.add(lblWantsSalary);
        }
        {
            JLabel lblNewLabel_2 = new JLabel("Вы предлагаете");
            contentPanel.add(lblNewLabel_2);
        }
        {
            spinner = new JSpinner();
            spinner.setValue(programmer.getWantsSalary());
            contentPanel.add(spinner);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);

                okButton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        if (programmer.isAgree((int) spinner.getValue()) == true)

                        {
                            programmer.setSalary((int) spinner.getValue());
                            programmer.setFirstDay(currentStatus.getDay());
                            programmer.setWantsSalary((int) (programmer.getWantsSalary() * 0.7));
                            ProjectManager.workers.add(programmer);
                            LaborExchange.list.remove(index);

                            laborExchange.repaintTable();
                            currentStatus.addHired();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Предложение отвергнуто");
                        }
                        dispose();
                    };
                });

            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);

                cancelButton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        dispose();
                    };
                });
            }
            setLocationRelativeTo(null);
        }
    }
}
