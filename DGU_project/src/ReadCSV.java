import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ReadCSV {
	public void ReadCSV () { 

	}
	
	
	static void ReadCSV(ArrayList<Student> sl, String fname) throws IOException {
					
		// Open student list file
		BufferedReader br = new BufferedReader(new FileReader(fname));
		ArrayList<String> name =new ArrayList<String>(); ArrayList<String> subject=new ArrayList<String>(); ArrayList<String> grade=new ArrayList<String>();
		int i=0;
		while(true) {
				

	        String line = br.readLine();
	        //������ �л� ��ü ����
	        if (line == null) {
	        	sl.add(new Student(name.get(0),subject,grade));//마지막 학생 저장
	        	break;  
	        }

	        String[] result = line.split(",");  //3���̴�, result�� 1�� 3��(name, subject, grade)
	        
	        name.add(result[0]);subject.add(result[1]);grade.add(result[2]);
	        
	        //�л� ��ü ����
	        if (i>=1) {//2�� �̻��� ������ ����Ǿ� ���� ��,
	        	if (!name.get(i).equals(name.get(i-1))) {//i�� ���� �ֱ� index
	        		sl.add(new Student(name.get(i-1),new ArrayList<>(subject.subList(0, i)),new ArrayList<>(grade.subList(0, i))));//index i�����ϰ�� �� name ����
//i 이하는 다 같은 학생의 것이기에 학생 추가 위에서 했으면,i만 남기고 다 삭제 -> i=0으로
	        		for (int j = 0; j <= i-1; j++) {
	        		    name.remove(0); // 인덱스 0의 요소를 삭제
	        		}
	        		for (int j = 0; j <= i-1; j++) {
	        		    subject.remove(0); // 인덱스 0의 요소를 삭제
	        		}
	        		for (int j = 0; j <= i-1; j++) {
	        		    grade.remove(0); // 인덱스 0의 요소를 삭제
	        		}
	        		i=0;
	        	}
	        }
	        i++;
	        
	    
		}
	    br.close();

	    
	}

}