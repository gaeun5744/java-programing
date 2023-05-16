import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.ButtonGroup;
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

class main {

 public static void main(String[] args) {
  new Test();
 }

}

class Test extends JFrame implements ActionListener {
 JButton btn1 = null;
 JButton btn2 = null;
 JButton btn3 = null;
 JTable table = null;


 public Test() {
  super("문제");
  this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  this.setBounds(200, 200, 500, 500);
  this.setLayout(new FlowLayout());

  JPanel panelTable = new JPanel();
  JPanel panelNormal = new JPanel();

  panelTable.setLayout(new FlowLayout());
  panelNormal.setLayout(new FlowLayout());

  // panelTable
  String title[] = new String[3];
  title[0] = "학생 이름";
  title[1] = "학생 과목";
  title[2] = "학생 학점";
  String data[][] = new String[0][0];
  // data[0][0] ="1";
  // data[0][1] ="남동길";
  // data[0][2] ="27";
  //
  // data[1][0] ="2";
  // data[1][1] ="이효림";
  // data[1][2] ="23";

  table = new JTable(data, title);
  JScrollPane sp = new JScrollPane(table);
  sp.setPreferredSize(new Dimension(300, 200));

  panelTable.add(sp);

  // panelNormal
  btn1 = new JButton("과목 및 학점 입력");
  btn1.addActionListener(this);
  panelNormal.add(btn1);


  btn2 = new JButton("학점 상호 인정 조회");
  btn2.addActionListener(this);
  panelNormal.add(btn2);
  
  btn3 = new JButton("개설 강좌 정보 조회");
  btn3.addActionListener(this);
  panelNormal.add(btn3);


  this.add(panelTable);
  this.add(panelNormal);

  this.setVisible(true);
 }



 @Override
 public void actionPerformed(ActionEvent e) {

  if (e.getSource() == btn1) {
	  new InputSubject();
  }

  if (e.getSource() == btn2) {
	  new SearchMutualSubject();
  }
  if (e.getSource() == btn3) {
	  new SearchCourseInformation();
  }

 }

}


class InputSubject extends JFrame implements ActionListener {


 public InputSubject() {
  super("과목 및 학점 입력");
  this.setBounds(200, 200, 250, 300);
  this.setLayout(new FlowLayout());




  this.setVisible(true);
 }

 @Override
 public void actionPerformed(ActionEvent e) {
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

