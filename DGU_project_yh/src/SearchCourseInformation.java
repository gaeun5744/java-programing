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

// 개설 강좌 정보 조회 함수
class SearchCourseInformation extends JFrame implements ActionListener {
	//field
	private Student student;
	
	//constructor: student를 객체로 받음
    public SearchCourseInformation(Student student) {
    	this.student = student;
    	
    	// 개설 강좌 정보를 표시할 테이블 생성
    	
        this.setBounds(1000, 500, 1000, 500);
        this.setLayout(new FlowLayout());

        String[] columnNames = {"강의명", "교수", "강의실", "시간", "상호인정 과목", "언어"};
        Object[][] data = getClassDataFromCSV("C:/Users/gajig/Desktop/학점 관리/classinfo.csv");

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 400));

        add(scrollPane);

        this.setVisible(true);
    }
    
    //classinfo data 리턴 메서드: 1.CSV파일 읽기, 2.기존 수강과목 데이터를 불러와서 겹치는 과목 필터링, 3.테이블에 표시할 행열로 변형, 4.filtered data return
    private Object[][] getClassDataFromCSV(String filePath) {
        ArrayList<Class> classList = new ArrayList<Class>();
        
        //1. csv파일 읽는 코드
        String line = "";
        String csvSeparator = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // 첫 번째 라인은 헤더로 처리
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(csvSeparator);

                // CSV 파일의 각 열에 해당하는 값을 추출
                String classname = values[0];
                String professor = values[1];
                String classroom = values[2];
                String time = values[3];
                String tool = values[4];
                String mutualsub = values[5];

                // Class 객체를 생성하고 리스트에 추가
                Class course = new Class(classname, professor, classroom, time, tool, mutualsub);

                classList.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //2.기수강 데이터 데이터 추출: completed: 학생 한명이 수강한 과목의 arraylist
        ArrayList<String> completed = student.subject;
        
        //3. filteredClasses = classList - completed
        ArrayList<Class> filteredClasses = new ArrayList<>();
        for (Class course : classList) {
            if (!completed.contains(course.classname)) {
                filteredClasses.add(course);
            }
        }
        
     
        
        //4.필터링된 개설 강좌 정보를 2차원 배열로 변환

        Object[][] filteredData = new Object[filteredClasses.size()][6];


        for (int i = 0; i < filteredClasses.size(); i++) {
            Class course = filteredClasses.get(i);
            filteredData[i][0] = course.classname;
            filteredData[i][1] = course.professor;
            filteredData[i][2] = course.classroom;
            filteredData[i][3] = course.time;
            filteredData[i][4] = course.tool;
            filteredData[i][5] = course.mutualsub;

        }
        
        
        return filteredData;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // 필요한 경우 동작 이벤트 처리
    }
}