//JTable셀에 text 추가 예
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
        addButton.addActionListener(e -> {//Add Row 버튼 누르면, 빈 행 생성
            model.addRow(new Object[table.getColumnCount()]);
        });

        // Create a panel to hold the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        // Set the layout manager for the frame
        frame.setLayout(new BorderLayout());
//frame에 최종으로 add하는 부분
        // Add the table to the center of the frame
        frame.add(scrollPane, BorderLayout.CENTER);//scraollpane에 JTable을 넣은 상태로 최종 JFrame에 넣은것

        // Add the button panel to the south of the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }
}