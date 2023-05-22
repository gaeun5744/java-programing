//JTable���� text �߰� ��
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class TextEditorExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Text Editor Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sample data for the table
        Object[][] data = {
                {"1", "John Doe"},
                {"2", "Jane Smith"},
                {"3", "Bob Johnson"}
        };

        // Column names
        String[] columnNames = {"ID", "Name"};

        // Create a DefaultTableModel with sample data
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Create a JTable with the DefaultTableModel
        JTable table = new JTable(model);

        // Create a custom cell editor for text input
        TableCellEditor textEditor = new DefaultCellEditor(new JTextField());

        // Set the cell editor for all columns in the table
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setCellEditor(textEditor);
        }

        // Add the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a button to add rows
        JButton addButton = new JButton("Add Row");
        addButton.addActionListener(e -> {//Add Row ��ư ������, �� �� ����
            model.addRow(new Object[table.getColumnCount()]);
        });

        // Create a panel to hold the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        // Set the layout manager for the frame
        frame.setLayout(new BorderLayout());
//frame�� �������� add�ϴ� �κ�
        // Add the table to the center of the frame
        frame.add(scrollPane, BorderLayout.CENTER);//scraollpane�� JTable�� ���� ���·� ���� JFrame�� ������

        // Add the button panel to the south of the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }
}