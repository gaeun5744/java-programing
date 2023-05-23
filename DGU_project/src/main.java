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
class MainView extends JFrame implements ActionListener { // 메인뷰 정의
	JButton btn1 = null;
	JButton btn2 = null;
	JButton btn3 = null;
	JTable table = null;

	public MainView() {
		super("융합소프트웨어 연계전공 FAQ시스템");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(200, 200, 800, 500);
		this.setLayout(new FlowLayout());

		JPanel panelTable = new JPanel();//메인 페이지에서 table과 btn1, btn2, btn3를 묶는 컨테이너
		JPanel panelNormal = new JPanel();

		panelTable.setLayout(new FlowLayout());
		panelNormal.setLayout(new FlowLayout());

		// panelTable
		String title[] = new String[5];
		title[0] = "학생 이름";
		title[1] = "학생 과목";
		title[2] = "평균 학점";
		title[3] = "학점 상호 인정";
		title[4] = "개설 강좌 정보";
		String data[][] = new String[0][0];

		// 테이블 생성
		table = new JTable(data, title);
		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(700, 200));

		panelTable.add(sp);

		// panelNormal
		// 버튼 생성
		btn1 = new JButton("과목 및 학점 입력");
		btn1.addActionListener(this);
		panelNormal.add(btn1);

		btn2 = new JButton("정보 삭제");
		btn2.addActionListener(this);
		panelNormal.add(btn2);

		btn3 = new JButton("정보 조회");
		btn3.addActionListener(this);//btn3누르면, 리스너 실행
		panelNormal.add(btn3);

		this.add(panelTable);
		this.add(panelNormal);

		this.setVisible(true);
	}

	// 학생 정보 삭제 후, 다시 테이블 재생성할 때 사용하는 함수
	public void refreshTable() { 

		String titleTemp[] = new String[5];
		titleTemp[0] = "학생 이름";
		titleTemp[1] = "학생 과목";
		titleTemp[2] = "평균 학점";
		titleTemp[3] = "학점 상호 인정";
		titleTemp[4] = "개설 강좌 정보";

		int size = StuManager.list.size();//맨 밑에 StuManager class는 Student 객체를 저장하고 있는 list를 가지고 있음. 이는 list안에 들어간 원소(Student 객체) 수임.
//		String[][] dataStudentArray = new String[size][5];
		//datastudentarray는 J테이블에 나타낼 테이블임. 
		Object [][] dataStudentArray = new Object[size][5];
		for (int i = 0; i < size; i++) {//student 객체 내 subject 모두 각각 한 행씩 차지하도록(즉, student 객체 당 행 한개가 되도록)
			Student dataStudent = StuManager.list.get(i);
			dataStudentArray[i][0] = dataStudent.name;
			String subject = "";//String 객체 당 모든 subject를 하나로 이어 붙일 것
			//여기를 한 줄로 입력할 수 있도록 바꾸기
			for(int j=0; j<dataStudent.subject.length;j++) {
				subject=subject.concat(dataStudent.subject[j]);
				subject=subject.concat(", ");
			}
			dataStudentArray[i][1]=subject;
			dataStudentArray[i][2]=dataStudent.average_grade;
		}

		// 표에 버튼 넣는 코드
		table.setModel(new DefaultTableModel(dataStudentArray, titleTemp) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 3 || column == 4; // 특정 열(버튼 열)만 편집 가능하도록 설정
			}
		});

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(3).setCellRenderer(new TableCell("학점 상호 인정"));
		columnModel.getColumn(3).setCellEditor(new TableCell("학점 상호 인정"));
		columnModel.getColumn(4).setCellRenderer(new TableCell("개설 강좌 정보"));
		columnModel.getColumn(4).setCellEditor(new TableCell("개설 강좌 정보"));
	}

	// 표 안에 버튼을 넣기 위해 사용하는 함수
	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

		JButton jb;

		public TableCell(String text) {
			// TODO Auto-generated constructor stub
			jb = new JButton(text);

			if (text.equals("학점 상호 인정")) {
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
	
	// Action 관련 함수
	// ActionListener 를 implement 했으므로 반드시 필요한 함수 
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btn1) {
			new InputSubject();//InputSubject class는 JFrame을 구현한 것으로, 이로부터 새로운 컨테이너(새 창)를 만든다
		}

		if (e.getSource() == btn2) {//btn2가 눌러졌을 때
			if (table.getSelectedRow() == -1) {
			} else {
				//선택된 행의 index 삭제 부분(해당 학생 정보 전부 삭제)
				int deleteIndex = table.getSelectedRow();
				int size = StuManager.list.size();
				int row_num = 0;//테이블 행 개수 구하기
				for (int i=0;i<size;i++) {
					row_num+=StuManager.list.get(i).grade.length;//각 student 객체 내 배열(grade, 과목의 수 동일하니) 원소 수 덧셈
					if(row_num>deleteIndex) {//deleteIndex 행이 속한 student 객체(i) 찾으면, list에서 student 삭제(deleteIndex가 rownum보다 작거나 같으면, 아직 해당 student에 접근 x 의미)
						StuManager.list.remove(i);
						break;
					}
				}
//				StuManager.list.remove(deleteIndex);
				this.refreshTable();
			}
		}

		if (e.getSource() == btn3) {//this는 mainView
			String titleTemp[] = new String[5];
			titleTemp[0] = "학생 이름";
			titleTemp[1] = "학생 과목";
			titleTemp[2] = "평균 학점";
			titleTemp[3] = "학점 상호 인정";
			titleTemp[4] = "개설 강좌 정보";

			int size = StuManager.list.size();//맨 밑에 StuManager class는 Student 객체를 저장하고 있는 list를 가지고 있음. 이는 list안에 들어간 원소(Student 객체) 수임.
//			String[][] dataStudentArray = new String[size][5];
			//datastudentarray는 J테이블에 나타낼 테이블임. 
			Object [][] dataStudentArray = new Object[size][5];
			for (int i = 0; i < size; i++) {//student 객체 내 subject 모두 각각 한 행씩 차지하도록(즉, student 객체 당 행 한개가 되도록)
				Student dataStudent = StuManager.list.get(i);
				dataStudentArray[i][0] = dataStudent.name;
				String subject = "";//String 객체 당 모든 subject를 하나로 이어 붙일 것
				//여기를 한 줄로 입력할 수 있도록 바꾸기
				for(int j=0; j<dataStudent.subject.length;j++) {
					subject=subject.concat(dataStudent.subject[j]);
					subject=subject.concat(", ");
				}
				dataStudentArray[i][1]=subject;
				dataStudentArray[i][2]=dataStudent.average_grade;
			}


			table.setModel(new DefaultTableModel(dataStudentArray, titleTemp) {//table 재생성
				@Override
				public boolean isCellEditable(int row, int column) {
					return column == 3 || column == 4; // 특정 열(버튼 열)만 편집 가능하도록 설정
				}
			});

			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(3).setCellRenderer(new TableCell("학점 상호 인정"));
			columnModel.getColumn(3).setCellEditor(new TableCell("학점 상호 인정"));
			columnModel.getColumn(4).setCellRenderer(new TableCell("개설 강좌 정보"));
			columnModel.getColumn(4).setCellEditor(new TableCell("개설 강좌 정보"));
		}

	}

}

// 학생 정보 입력 클래스
class InputSubject extends JFrame implements ActionListener {

	JButton btnOk, btnClose;
	JLabel name, subject, grade;
	JTextField tv_name, tv_subject, tv_grade;
	JTable table;
	
	public InputSubject() {//
		super("과목 및 학점 입력");
		this.setBounds(200, 200, 250, 300);
		this.setLayout(new BorderLayout());

		JPanel panelName = new JPanel();
		JPanel panelSubject = new JPanel();
		JPanel panelGrade = new JPanel();
		JPanel panelButton = new JPanel();//버튼 모아둘 JPanel

		panelName.setLayout(new FlowLayout());
		panelButton.setLayout(new FlowLayout());

		
		
		name = new JLabel("이름 : ");
		/*
		subject = new JLabel("과목 : ");//행렬의 1행 1열
		grade = new JLabel("학점 : ");//행렬의 1행 2열에
		*/
		
		tv_name = new JTextField(10);
		/*
		tv_subject = new JTextField(10);
		tv_grade = new JTextField(10);
		*/
//내가 변경하고 있는 부분(btn 전까지)
//subject, grade를 입력값으로 받는 2열짜리 table data만들기
		// Column names
        String[] columnNames = {"subject", "grade"};
        // Create a DefaultTableModel with sample data
        Object [][] data = new String[0][0];//창 처음 만들어졌을 때는 데이터 공집합이 되도록
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
        JButton addRow = new JButton("과목 추가");//행 추가 버튼
        addRow.addActionListener(e -> {//Add Row 버튼 누르면, 빈 행 생성(이벤트 리스너 붙인 것)
            model.addRow(new Object[table.getColumnCount()]);
        });
		
		btnOk = new JButton("확인");//OK버튼을 누르면 남은 과목 출력하도록 바꾸기(기존 메인 페이지의 테이블과 OK버튼 바꾸면 됨!)
		btnClose = new JButton("취소");

		btnOk.addActionListener(this);
		btnClose.addActionListener(this);

		panelName.add(name);
		panelName.add(tv_name);
/*
		panelSubject.add(subject);//각 네개의 panel에 따로따로 넣었었지만, 이젠, subject, grade는 같은 행렬로 할 것!
		panelSubject.add(tv_subject);

		panelGrade.add(grade);
		panelGrade.add(tv_grade);
*/
		panelButton.add(btnOk);
		panelButton.add(btnClose);
		//내가 추가한 부분
		panelButton.add(addRow);
		
		this.add(panelName, BorderLayout.NORTH);
		/*기존
		this.add(panelSubject);
		this.add(panelGrade);
		*/
		//내가 수정(이 한줄만)
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.add(panelButton, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	// implements ActionListener 했으므로 반드시 필요한 함수
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {//grade, subject에 대한 배열 각각 생성후, 이로부터 Student 객체 생성
	        // Get the row count
			System.out.println(tv_name.getText().toString().trim());
	        int rowCount = table.getRowCount();
	        // Create an array to store column values
	        String[] subject_column = new String[rowCount];
	        String[] grade_column = new String[rowCount];
	        // Store column values in the array
	        for (int i = 0; i < rowCount; i++) {
	            subject_column[i] = (String) table.getValueAt(i, 0);}//tabel.getValueAt은 object type이기에, String으로 타입 변환. 0열은 subject열이고.
	        for (int i = 0; i < rowCount; i++) {
	            grade_column[i] = (String) table.getValueAt(i, 1);}

			Student student = new Student(tv_name.getText().toString().trim(), subject_column, grade_column);//tv_subject와 tv_grade는 2열의 행렬로서 사용하여 Student 객체에 tv_grade는 그대로 들어가게 하고, 평균학점 추가

			StuManager.list.add(student);//student 객체를 list에 저장
			/* 초기화 부분
			tv_name.setText(null);
			tv_subject.setText(null);
			tv_grade.setText(null);
		*/
			//창 닫기
			dispose();
			
		}
		if (e.getSource() == btnClose) {
			dispose();
		}

	}

}

class SearchMutualSubject extends JFrame implements ActionListener {
	public SearchMutualSubject() {
		super("상호 인정 과목 조회");

		this.setBounds(200, 200, 250, 300);
		this.setLayout(new FlowLayout());

		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}

// 개설 강좌 정보 조회 함수
class SearchCourseInformation extends JFrame implements ActionListener {
	public SearchCourseInformation() {
		super("개설 강좌 정보 조회");

		this.setBounds(200, 200, 250, 300);
		this.setLayout(new FlowLayout());

		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}

// 데이터 클래스
class Student {
	String name;
	String [] subject;
	String [] grade;
	String average_grade;
	//평균학점 변수 추가
	public Student(String name, String [] subject, String [] grade) {
		this.name = name;
		this.subject = subject;
		this.grade = grade;
//평균학점 변수 추가
		float t=0;
		for(int i=0 ; i<grade.length ; i++) {//무조건 빈 공간이 없도록 배열을 만들었으니 length로 해도 됨.
			t+=Float.parseFloat(grade[i]);
		}
		t=t/grade.length;
		average_grade=Float.toString(t);
	}
}

// 해당 클래스를 이용해 학생들 정보를 연동함
class StuManager {
	public static ArrayList<Student> list = new ArrayList<Student>();
}
