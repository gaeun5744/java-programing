import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

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
		super("융합전공소프트웨어FAQ시스템");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(200, 200, 800, 500);
		this.setLayout(new FlowLayout());

		JPanel panelTable = new JPanel();//���� ���������� table�� btn1, btn2, btn3�� ���� �����̳�
		JPanel panelNormal = new JPanel();

		panelTable.setLayout(new FlowLayout());
		panelNormal.setLayout(new FlowLayout());

		// panelTable
		String title[] = new String[5];
		title[0] = "학생 이름";
		title[1] = "기수강과목";
		title[2] = "평균 학점";
		title[3] = "학점 상호 인정";
		title[4] = "개설 강좌 정보";
		String data[][] = new String[0][0];

		// ���̺� ����
		table = new JTable(data, title);
		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(700, 200));

		panelTable.add(sp);

		// panelNormal
		// ��ư ����
		btn1 = new JButton("정보 불러오기");
		btn1.addActionListener(this);
		panelNormal.add(btn1);

		btn2 = new JButton("삭제");
		btn2.addActionListener(this);
		panelNormal.add(btn2);

		btn3 = new JButton("정보 조회");
		btn3.addActionListener(this);//btn3������, ������ ����
		panelNormal.add(btn3);

		this.add(panelTable);
		this.add(panelNormal);

		this.setVisible(true);
	}

	// �л� ���� ���� ��, �ٽ� ���̺� ������� �� ����ϴ� �Լ�
	public void refreshTable() { 

		String titleTemp[] = new String[5];
		titleTemp[0] = "학생 이름";
		titleTemp[1] = "기수강과목";
		titleTemp[2] = "평균 학점";
		titleTemp[3] = "학점 상호 인정";
		titleTemp[4] = "개설 강좌 정보";

		int size = StuManager.list.size();//�� �ؿ� StuManager class�� Student ��ü�� �����ϰ� �ִ� list�� ������ ����. �̴� list�ȿ� �� ����(Student ��ü) ����.
//		String[][] dataStudentArray = new String[size][5];
		//datastudentarray�� J���̺� ��Ÿ�� ���̺���. 
		Object [][] dataStudentArray = new Object[size][5];
		for (int i = 0; i < size; i++) {//student ��ü �� subject ��� ���� �� �྿ �����ϵ���(��, student ��ü �� �� �Ѱ��� �ǵ���)
			Student dataStudent = StuManager.list.get(i);
			dataStudentArray[i][0] = dataStudent.name;
			String subject = "";//String ��ü �� ��� subject�� �ϳ��� �̾� ���� ��
			//���⸦ �� �ٷ� �Է��� �� �ֵ��� �ٲٱ�
			for(int j=0; j<dataStudent.subject.size();j++) {
				subject=subject.concat(dataStudent.subject.get(i));
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
		columnModel.getColumn(3).setCellRenderer(new TableCell("학점상호인정"));
		columnModel.getColumn(3).setCellEditor(new TableCell("학점상호인정"));
		columnModel.getColumn(4).setCellRenderer(new TableCell("개설강좌정보"));
		columnModel.getColumn(4).setCellEditor(new TableCell("개설강좌정보"));
	}

	// ǥ �ȿ� ��ư�� �ֱ� ���� ����ϴ� �Լ�
	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

		JButton jb;

		public TableCell(String text) {
			// TODO Auto-generated constructor stub
			jb = new JButton(text);

			if (text.equals("학점상호인정")) {
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
			String fname = "C:/Users/USER/OneDrive/바탕 화면/융합 소프트웨어/융합프로그래밍2/팀과제/java-programing/student샘플.csv";
			
			
			ReadCSV rcsv = new ReadCSV();
			try {
				rcsv.ReadCSV(StuManager.list, fname);
			} catch (IOException f) {
				f.printStackTrace();
			}
		}
		

		if (e.getSource() == btn2) {//btn2�� �������� ��
			if (table.getSelectedRow() == -1) {
			} else {
				//���õ� ���� index ���� �κ�(�ش� �л� ���� ���� ����)
				int deleteIndex = table.getSelectedRow();
				StuManager.list.remove(deleteIndex);
				this.refreshTable();
			}
		}

		if (e.getSource() == btn3) {//this�� mainView
			String titleTemp[] = new String[5];
			titleTemp[0] = "학생 이름";
			titleTemp[1] = "기수강과목";
			titleTemp[2] = "평균 학점";
			titleTemp[3] = "학점 상호 인정";
			titleTemp[4] = "개설 강좌 정보";
			
			int size = StuManager.list.size();//�� �ؿ� StuManager class�� Student ��ü�� �����ϰ� �ִ� list�� ������ ����. �̴� list�ȿ� �� ����(Student ��ü) ����.
//			String[][] dataStudentArray = new String[size][5];
			//datastudentarray�� J���̺� ��Ÿ�� ���̺���. 
			Object [][] dataStudentArray = new Object[size][5];
			for (int i = 0; i < size; i++) {//student ��ü �� subject ��� ���� �� �྿ �����ϵ���(��, student ��ü �� �� �Ѱ��� �ǵ���)
				Student dataStudent = StuManager.list.get(i);
				dataStudentArray[i][0] = dataStudent.name;
				String subject = "";//String ��ü �� ��� subject�� �ϳ��� �̾� ���� ��
				//���⸦ �� �ٷ� �Է��� �� �ֵ��� �ٲٱ�
				for(int j=0; j<dataStudent.subject.size();j++) {
					subject=subject.concat(dataStudent.subject.get(j));
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
			columnModel.getColumn(3).setCellRenderer(new TableCell("학점상호인정"));
			columnModel.getColumn(3).setCellEditor(new TableCell("학점상호인정"));
			columnModel.getColumn(4).setCellRenderer(new TableCell("개설강좌정보"));
			columnModel.getColumn(4).setCellEditor(new TableCell("개설강좌정보"));
		}

	}

}


class SearchMutualSubject extends JFrame implements ActionListener {
	public SearchMutualSubject() {
		super("학점상호인정");

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
		super("개설강좌정보");

		this.setBounds(200, 200, 250, 300);
		this.setLayout(new FlowLayout());

		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}

// ������ Ŭ����

// �ش� Ŭ������ �̿��� �л��� ������ ������
class StuManager {
	public static ArrayList<Student> list = new ArrayList<Student>();
}
