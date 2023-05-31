import java.util.ArrayList;

class Student {
	String name;
	ArrayList<String> subject;
	ArrayList<String> grade;
	String average_grade;

	public Student(String name, ArrayList<String> subject, ArrayList<String> grade) {
		this.name = name;
		this.subject = subject;
		this.grade = grade;

		float t=0;
		for(int i=0 ; i<grade.size() ; i++) {
			t+=Float.parseFloat(grade.get(i));
		}
		t=t/grade.size();
		average_grade=Float.toString(t);
	}
}
