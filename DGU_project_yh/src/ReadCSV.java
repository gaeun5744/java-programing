import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ReadCSV {
	public void ReadCSV () { 

	}
	
	
	static void ReadCSV(ArrayList<Student> sl, String fname) throws IOException {//file을 읽어서 Arraylist에 저장하는 메서드
					
		// Open student list file
		BufferedReader br = new BufferedReader(new FileReader(fname)); // 파일읽기
		// arraylist 생성
		ArrayList<String> name = new ArrayList<String>(); 
		ArrayList<String> subject = new ArrayList<String>(); 
		ArrayList<String> grade = new ArrayList<String>();
		
		int i=0;
		while(true) {
			
	        String line = br.readLine();

	        if (line == null) {
	        	sl.add(new Student(name.get(0),subject,grade));//마지막 학생 저장
	        	break;  
	        }
	       
	        String[] result = line.split(",");  
	        
	        name.add(result[0]);
	        subject.add(result[1]);
	        grade.add(result[2]);

	        if (i>=1) {
	        	if (!name.get(i).equals(name.get(i-1))) {
	        		sl.add(new Student(name.get(i-1),new ArrayList<>(subject.subList(0, i)),new ArrayList<>(grade.subList(0, i))));

	        		for (int j = 0; j <= i-1; j++) {
	        		    name.remove(0); 
	        		}
	        		for (int j = 0; j <= i-1; j++) {
	        		    subject.remove(0); 
	        		}
	        		for (int j = 0; j <= i-1; j++) {
	        		    grade.remove(0); 
	        		}
	        		i=0;
	        	}
	        }
	        i++;
	        
	    
		}
	    br.close();

	    
	}

}