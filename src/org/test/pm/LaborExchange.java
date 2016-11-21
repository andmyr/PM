/** Реализация биржи труда
 * 
 */

package org.test.pm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class LaborExchange extends JFrame
{
    public static ArrayList<Programmer> list = new ArrayList<Programmer>(10);

    private static final long serialVersionUID = -445988197239746436L;

    private JPanel contentPane;

    private JTable table;

    private JPanel panel;

    private JPanel panel_2;

    private JButton btnOffer;

    private JButton btnCancel;

    /** Сколько максимум генерировать сотрудников */
    private final static int m = 10;

    /**
     * Create the frame. Передаем объект статуса для учета денег.
     */
    public LaborExchange(final CurrentStatus currentStatus)
    {
        // генерируем кандидатов
        fill(list);

        final LaborExchange laborExchange = this;
        setTitle("Биржа труда");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 344);
        contentPane = new JPanel();
        contentPane.setVisible(false);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);

        table = new JTable()
        {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            // запретим редактирование таблицы
            @Override
            public boolean isCellEditable(int arg0, int arg1)
            {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        repaintTable();
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel_2 = new JPanel();
        panel.add(panel_2);

        btnOffer = new JButton("Сделать предложение");
        panel_2.add(btnOffer);
        btnOffer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                int[] temp = table.getSelectedRows();
                if (temp.length == 0)
                {
                    JOptionPane.showMessageDialog(null, "Выберите кандидата");
                    return;
                }

                int index = table.getSelectionModel().getLeadSelectionIndex();
                Recruitment dialog = new Recruitment(list.get(index), index, currentStatus, laborExchange);

                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            };
        });

        btnCancel = new JButton("Отмена");
        panel_2.add(btnCancel);
        btnCancel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ProjectManager.exchangeFlag = false;
                dispose();
            };
        });
        
        this.addWindowListener(new WindowListener() {

            @Override
            public void windowActivated(WindowEvent e)
            {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void windowClosed(WindowEvent e)
            {
                ProjectManager.exchangeFlag = false;
                
            }

            @Override
            public void windowClosing(WindowEvent e)
            {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void windowDeactivated(WindowEvent e)
            {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void windowDeiconified(WindowEvent e)
            {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void windowIconified(WindowEvent e)
            {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void windowOpened(WindowEvent e)
            {
                // TODO Auto-generated method stub
                
            }});
           

        // по центру экрана
        setLocationRelativeTo(null);

        contentPane.setVisible(true);
    }

    /** Прорисовка таблицы */
    public void repaintTable()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Уровень");
        model.addColumn("Ф�?О");
        model.addColumn("Обучаемость");
        model.addColumn("Требования");
        for (Programmer p : list)
        {
            String[] temp = { p.rank.toString(), p.name, p.getEducability().toString(), p.getWantsSalary() + "" };
            model.addRow(temp);
        }
        table.setModel(model);
    }

    private static void fill(ArrayList<Programmer> list)
    {
        list.clear();

        Random random = new Random();
        int n = random.nextInt(m) + 1;

        for (int i = 0; i < n; i++)
        {
            list.add(new Programmer());
        }
    }
}
