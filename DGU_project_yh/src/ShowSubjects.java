import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;


class ShowSubjects extends JFrame implements ActionListener {
    private JTable table;

    public ShowSubjects(Student student) {
        super("기수강과목");

        this.setBounds(500, 500, 500, 500);
        this.setLayout(new FlowLayout());

        String[] columnNames = {"과목명"};
        String[][] data = new String[student.subject.size()][1];

        for (int i = 0; i < student.subject.size(); i++) {
            data[i][0] = student.subject.get(i);
        }

        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 400));

        this.add(scrollPane);

        this.setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
