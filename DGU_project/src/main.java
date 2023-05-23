import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.AbstractCellEditor;
import java.awt.Component;

class main {

	public static void main(String[] args) {
		new MainView();
	}

}
//
class MainView extends JFrame implements ActionListener { // ���κ� ����
	JButton btn1 = null;
	JButton btn2 = null;
	JButton btn3 = null;
	JTable table = null;

	public MainView() {
		super("���ռ���Ʈ���� �������� FAQ�ý���");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(200, 200, 800, 500);
		this.setLayout(new FlowLayout());

		JPanel panelTable = new JPanel();//���� ���������� table�� btn1, btn2, btn3�� ���� �����̳�
		JPanel panelNormal = new JPanel();

		panelTable.setLayout(new FlowLayout());
		panelNormal.setLayout(new FlowLayout());

		// panelTable
		String title[] = new String[5];
		title[0] = "�л� �̸�";
		title[1] = "�л� ����";
		title[2] = "��� ����";
		title[3] = "���� ��ȣ ����";
		title[4] = "���� ���� ����";
		String data[][] = new String[0][0];

		// ���̺� ����
		table = new JTable(data, title);
		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(700, 200));

		panelTable.add(sp);

		// panelNormal
		// ��ư ����
		btn1 = new JButton("���� �� ���� �Է�");
		btn1.addActionListener(this);
		panelNormal.add(btn1);

		btn2 = new JButton("���� ����");
		btn2.addActionListener(this);
		panelNormal.add(btn2);

		btn3 = new JButton("���� ��ȸ");
		btn3.addActionListener(this);//btn3������, ������ ����
		panelNormal.add(btn3);

		this.add(panelTable);
		this.add(panelNormal);

		this.setVisible(true);
	}

	// �л� ���� ���� ��, �ٽ� ���̺� ������� �� ����ϴ� �Լ�
	public void refreshTable() { 

		String titleTemp[] = new String[5];
		titleTemp[0] = "�л� �̸�";
		titleTemp[1] = "�л� ����";
		titleTemp[2] = "��� ����";
		titleTemp[3] = "���� ��ȣ ����";
		titleTemp[4] = "���� ���� ����";

		int size = StuManager.list.size();//�� �ؿ� StuManager class�� Student ��ü�� �����ϰ� �ִ� list�� ������ ����. �̴� list�ȿ� �� ����(Student ��ü) ����.
//		String[][] dataStudentArray = new String[size][5];
		//datastudentarray�� J���̺� ��Ÿ�� ���̺���. 
		Object [][] dataStudentArray = new Object[size][5];
		for (int i = 0; i < size; i++) {//student ��ü �� subject ��� ���� �� �྿ �����ϵ���(��, student ��ü �� �� �Ѱ��� �ǵ���)
			Student dataStudent = StuManager.list.get(i);
			dataStudentArray[i][0] = dataStudent.name;
			String subject = "";//String ��ü �� ��� subject�� �ϳ��� �̾� ���� ��
			//���⸦ �� �ٷ� �Է��� �� �ֵ��� �ٲٱ�
			for(int j=0; j<dataStudent.subject.length;j++) {
				subject=subject.concat(dataStudent.subject[j]);
				subject=subject.concat(", ");
			}
			dataStudentArray[i][1]=subject;
			dataStudentArray[i][2]=dataStudent.average_grade;
		}

		// ǥ�� ��ư �ִ� �ڵ�
		table.setModel(new DefaultTableModel(dataStudentArray, titleTemp) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3 || column == 4; // Ư�� ��(��ư ��)�� ���� �����ϵ��� ����
			}
		});

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(3).setCellRenderer(new TableCell("���� ��ȣ ����"));
		columnModel.getColumn(3).setCellEditor(new TableCell("���� ��ȣ ����"));
		columnModel.getColumn(4).setCellRenderer(new TableCell("���� ���� ����"));
		columnModel.getColumn(4).setCellEditor(new TableCell("���� ���� ����"));
	}

	// ǥ �ȿ� ��ư�� �ֱ� ���� ����ϴ� �Լ�
	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

		JButton jb;

		public TableCell(String text) {
			// TODO Auto-generated constructor stub
			jb = new JButton(text);

			if (text.equals("���� ��ȣ ����")) {
				jb.addActionListener(e -> {
					jb.addActionListener(new SearchMutualSubject());
				});
			} else {
				jb.addActionListener(e -> {
					jb.addActionListener(new SearchCourseInformation());
				});
			}

		}

		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// TODO Auto-generated method stub
			return jb;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			// TODO Auto-generated method stub
			return jb;
		}

	}
	
	// Action ���� �Լ�
	// ActionListener �� implement �����Ƿ� �ݵ�� �ʿ��� �Լ� 
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btn1) {
			new InputSubject();//InputSubject class�� JFrame�� ������ ������, �̷κ��� ���ο� �����̳�(�� â)�� �����
		}

		if (e.getSource() == btn2) {//btn2�� �������� ��
			if (table.getSelectedRow() == -1) {
			} else {
				//���õ� ���� index ���� �κ�(�ش� �л� ���� ���� ����)
				int deleteIndex = table.getSelectedRow();
				int size = StuManager.list.size();
				int row_num = 0;//���̺� �� ���� ���ϱ�
				for (int i=0;i<size;i++) {
					row_num+=StuManager.list.get(i).grade.length;//�� student ��ü �� �迭(grade, ������ �� �����ϴ�) ���� �� ����
					if(row_num>deleteIndex) {//deleteIndex ���� ���� student ��ü(i) ã����, list���� student ����(deleteIndex�� rownum���� �۰ų� ������, ���� �ش� student�� ���� x �ǹ�)
						StuManager.list.remove(i);
						break;
					}
				}
//				StuManager.list.remove(deleteIndex);
				this.refreshTable();
			}
		}

		if (e.getSource() == btn3) {//this�� mainView
			String titleTemp[] = new String[5];
			titleTemp[0] = "�л� �̸�";
			titleTemp[1] = "�л� ����";
			titleTemp[2] = "��� ����";
			titleTemp[3] = "���� ��ȣ ����";
			titleTemp[4] = "���� ���� ����";

			int size = StuManager.list.size();//�� �ؿ� StuManager class�� Student ��ü�� �����ϰ� �ִ� list�� ������ ����. �̴� list�ȿ� �� ����(Student ��ü) ����.
//			String[][] dataStudentArray = new String[size][5];
			//datastudentarray�� J���̺� ��Ÿ�� ���̺���. 
			Object [][] dataStudentArray = new Object[size][5];
			for (int i = 0; i < size; i++) {//student ��ü �� subject ��� ���� �� �྿ �����ϵ���(��, student ��ü �� �� �Ѱ��� �ǵ���)
				Student dataStudent = StuManager.list.get(i);
				dataStudentArray[i][0] = dataStudent.name;
				String subject = "";//String ��ü �� ��� subject�� �ϳ��� �̾� ���� ��
				//���⸦ �� �ٷ� �Է��� �� �ֵ��� �ٲٱ�
				for(int j=0; j<dataStudent.subject.length;j++) {
					subject=subject.concat(dataStudent.subject[j]);
					subject=subject.concat(", ");
				}
				dataStudentArray[i][1]=subject;
				dataStudentArray[i][2]=dataStudent.average_grade;
			}


			table.setModel(new DefaultTableModel(dataStudentArray, titleTemp) {//table �����
				@Override
				public boolean isCellEditable(int row, int column) {
					return column == 3 || column == 4; // Ư�� ��(��ư ��)�� ���� �����ϵ��� ����
				}
			});

			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(3).setCellRenderer(new TableCell("���� ��ȣ ����"));
			columnModel.getColumn(3).setCellEditor(new TableCell("���� ��ȣ ����"));
			columnModel.getColumn(4).setCellRenderer(new TableCell("���� ���� ����"));
			columnModel.getColumn(4).setCellEditor(new TableCell("���� ���� ����"));
		}

	}

}

// �л� ���� �Է� Ŭ����
class InputSubject extends JFrame implements ActionListener {

	JButton btnOk, btnClose;
	JLabel name, subject, grade;
	JTextField tv_name, tv_subject, tv_grade;
	JTable table;
	
	public InputSubject() {//
		super("���� �� ���� �Է�");
		this.setBounds(200, 200, 250, 300);
		this.setLayout(new BorderLayout());

		JPanel panelName = new JPanel();
		JPanel panelSubject = new JPanel();
		JPanel panelGrade = new JPanel();
		JPanel panelButton = new JPanel();//��ư ��Ƶ� JPanel

		panelName.setLayout(new FlowLayout());
		panelButton.setLayout(new FlowLayout());

		
		
		name = new JLabel("�̸� : ");
		/*
		subject = new JLabel("���� : ");//����� 1�� 1��
		grade = new JLabel("���� : ");//����� 1�� 2����
		*/
		
		tv_name = new JTextField(10);
		/*
		tv_subject = new JTextField(10);
		tv_grade = new JTextField(10);
		*/
//���� �����ϰ� �ִ� �κ�(btn ������)
//subject, grade�� �Է°����� �޴� 2��¥�� table data�����
		// Column names
        String[] columnNames = {"subject", "grade"};
        // Create a DefaultTableModel with sample data
        Object [][] data = new String[0][0];//â ó�� ��������� ���� ������ �������� �ǵ���
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        // Create a JTable with the DefaultTableModel
        table = new JTable(model);
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
        JButton addRow = new JButton("���� �߰�");//�� �߰� ��ư
        addRow.addActionListener(e -> {//Add Row ��ư ������, �� �� ����(�̺�Ʈ ������ ���� ��)
            model.addRow(new Object[table.getColumnCount()]);
        });
		
		btnOk = new JButton("Ȯ��");//OK��ư�� ������ ���� ���� ����ϵ��� �ٲٱ�(���� ���� �������� ���̺�� OK��ư �ٲٸ� ��!)
		btnClose = new JButton("���");

		btnOk.addActionListener(this);
		btnClose.addActionListener(this);

		panelName.add(name);
		panelName.add(tv_name);
/*
		panelSubject.add(subject);//�� �װ��� panel�� ���ε��� �־�������, ����, subject, grade�� ���� ��ķ� �� ��!
		panelSubject.add(tv_subject);

		panelGrade.add(grade);
		panelGrade.add(tv_grade);
*/
		panelButton.add(btnOk);
		panelButton.add(btnClose);
		//���� �߰��� �κ�
		panelButton.add(addRow);
		
		this.add(panelName, BorderLayout.NORTH);
		/*����
		this.add(panelSubject);
		this.add(panelGrade);
		*/
		//���� ����(�� ���ٸ�)
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.add(panelButton, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	// implements ActionListener �����Ƿ� �ݵ�� �ʿ��� �Լ�
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {//grade, subject�� ���� �迭 ���� ������, �̷κ��� Student ��ü ����
	        // Get the row count
			System.out.println(tv_name.getText().toString().trim());
	        int rowCount = table.getRowCount();
	        // Create an array to store column values
	        String[] subject_column = new String[rowCount];
	        String[] grade_column = new String[rowCount];
	        // Store column values in the array
	        for (int i = 0; i < rowCount; i++) {
	            subject_column[i] = (String) table.getValueAt(i, 0);}//tabel.getValueAt�� object type�̱⿡, String���� Ÿ�� ��ȯ. 0���� subject���̰�.
	        for (int i = 0; i < rowCount; i++) {
	            grade_column[i] = (String) table.getValueAt(i, 1);}

			Student student = new Student(tv_name.getText().toString().trim(), subject_column, grade_column);//tv_subject�� tv_grade�� 2���� ��ķμ� ����Ͽ� Student ��ü�� tv_grade�� �״�� ���� �ϰ�, ������� �߰�

			StuManager.list.add(student);//student ��ü�� list�� ����
			/* �ʱ�ȭ �κ�
			tv_name.setText(null);
			tv_subject.setText(null);
			tv_grade.setText(null);
		*/
			//â �ݱ�
			dispose();
			
		}
		if (e.getSource() == btnClose) {
			dispose();
		}

	}

}

class SearchMutualSubject extends JFrame implements ActionListener {
	public SearchMutualSubject() {
		super("��ȣ ���� ���� ��ȸ");

		this.setBounds(200, 200, 250, 300);
		this.setLayout(new FlowLayout());

		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}

// ���� ���� ���� ��ȸ �Լ�
class SearchCourseInformation extends JFrame implements ActionListener {
	public SearchCourseInformation() {
		super("���� ���� ���� ��ȸ");

		this.setBounds(200, 200, 250, 300);
		this.setLayout(new FlowLayout());

		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}

// ������ Ŭ����
class Student {
	String name;
	String [] subject;
	String [] grade;
	String average_grade;
	//������� ���� �߰�
	public Student(String name, String [] subject, String [] grade) {
		this.name = name;
		this.subject = subject;
		this.grade = grade;
//������� ���� �߰�
		float t=0;
		for(int i=0 ; i<grade.length ; i++) {//������ �� ������ ������ �迭�� ��������� length�� �ص� ��.
			t+=Float.parseFloat(grade[i]);
		}
		t=t/grade.length;
		average_grade=Float.toString(t);
	}
}

// �ش� Ŭ������ �̿��� �л��� ������ ������
class StuManager {
	public static ArrayList<Student> list = new ArrayList<Student>();
}
