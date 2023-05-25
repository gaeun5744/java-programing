import java.util.ArrayList;

class Student {
	String name;
	ArrayList<String> subject;
	ArrayList<String> grade;
	String average_grade;
	//평균학점 변수 추가
	public Student(String name, ArrayList<String> subject, ArrayList<String> grade) {
		this.name = name;
		this.subject = subject;
		this.grade = grade;
//평균학점 변수 추가
		float t=0;
		for(int i=0 ; i<grade.size() ; i++) {//무조건 빈 공간이 없도록 배열을 만들었으니 length로 해도 됨.
			t+=Float.parseFloat(grade.get(i));
		}
		t=t/grade.size();
		average_grade=Float.toString(t);
	}
}
