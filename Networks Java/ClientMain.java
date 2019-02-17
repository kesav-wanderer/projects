package SC;
/**
 *Client program for single client by
 * Dhirish Parthasarathy and Kesavraj kannan**/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClientMain {
	private static String dir= "C:\\Users\\god\\Downloads\\project-1-skeleton.tar\\project-1-skeleton\\project-1-skeleton\\message-comm\\download";
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("Enter One of the four options: ");
		System.out.println("1.Download \n 2.Upload \n 3.Rename \n 4.Delete  ");
		Scanner optionScanner = new Scanner(System.in);
		String option = optionScanner.nextLine().toUpperCase();
		Socket clisocket= new Socket("127.0.0.1",5002);
		PrintStream optionStream = new PrintStream(clisocket.getOutputStream());
		
		
		
		switch(option) {
		case "DOWNLOAD":{			
			optionStream.println(option);
			Download(clisocket);
			break;
		}
		
		case "UPLOAD":{
			optionStream.println(option);
			Upload(clisocket);
			break;
		}
		
		case "RENAME":{
			optionStream.println(option);
			Rename(clisocket);
			break;
		}
		
		case "DELETE":{
			optionStream.println(option);
			Delete(clisocket);
			break;
		}
		
		default:{
			System.out.println("Invalid.option");
			break;
		}
			
		
		}		
		optionScanner.close();
		
	}
	
	public static void Download(Socket clisocket) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(clisocket.getInputStream()));
		Scanner flagScanner = new Scanner(clisocket.getInputStream());
		String flag= flagScanner.nextLine();
		if((" "!=flag) && (flag != null) && flag.equals("true")){
			String filename = flagScanner.nextLine();			
			File file = new File(dir+"\\"+filename);
			BufferedWriter pt1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			try {				
				String s = null;				
				StringBuffer buffer = new StringBuffer("\n");
				while((s= br.readLine())!= null) {
					
					buffer.append(s);
					pt1.write(s);	
					
				}				
			}catch(Exception e){
				System.out.println(e.getMessage());				
			}finally {
				pt1.flush();
				pt1.close();
				clisocket.close();
				System.out.println("Packet Finished");
			}
			
		}else {
			System.out.println("wrong file name");
		}	
			
	}
	
	public static void Upload(Socket clisocket) throws IOException {
		PrintWriter pt = new PrintWriter(new OutputStreamWriter(clisocket.getOutputStream()));
		BufferedReader br = new BufferedReader(new InputStreamReader(clisocket.getInputStream()));
		System.out.println("Enter the path of the file and file name: ");
		Scanner fileScanner = new Scanner(System.in);
		String path =  fileScanner.nextLine();
		String name = fileScanner.nextLine();
		StringBuffer buff = new StringBuffer(path);
		buff.append("\\");
		buff.append(name);
		pt.println(name);
		path= buff.toString();
		File F1 = new File(path);
		
		
		try{
			File ff = new File(F1.getAbsolutePath());
			FileReader fr = new FileReader(ff);
			BufferedReader bfr = new BufferedReader(fr);
			String s = null;
			while((s=bfr.readLine())!=null)
			{
				pt.println(s);
				pt.flush();
			}	
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			pt.flush();
			pt.close();	
			clisocket.close();
		}
	}

	@SuppressWarnings("resource")
	public static void Rename(Socket clisocket) throws IOException {
		PrintWriter pt = new PrintWriter(new OutputStreamWriter(clisocket.getOutputStream()));
		BufferedReader br = new BufferedReader(new InputStreamReader(clisocket.getInputStream()));
		System.out.println("Enter the file name: ");
		Scanner nameScanner = new Scanner(System.in);
		Scanner flagScanner = new Scanner(clisocket.getInputStream());
		String name= nameScanner.nextLine();
		pt.println(name);
		pt.flush();
		
		String flag = flagScanner.nextLine();
		if((" "!=flag) && (flag != null)){
			if(flag.equals("true")) {
				System.out.println("Enter the new name: ");
				String rename = nameScanner.nextLine();
				pt.println(rename);
				pt.flush();
				
			}else{
				System.out.println("Wrong file name");
				return ;
			}
		}
		
		String renameFlag = br.readLine();
		if(renameFlag.equals("true")) {
			System.out.println("Rename Succesful");
		}else {
			System.out.println("Rename unSuccessful");
		}
		pt.flush();
		pt.close();	
		br.close();
		nameScanner.close();
		flagScanner.close();
		clisocket.close();
	}
	
	public static void Delete(Socket clisocket) throws IOException {
		PrintWriter pt = new PrintWriter(new OutputStreamWriter(clisocket.getOutputStream()));
		BufferedReader br = new BufferedReader(new InputStreamReader(clisocket.getInputStream()));
		System.out.println("Enter the file name: ");
		Scanner deleteScanner = new Scanner(System.in);
		String name= deleteScanner.nextLine();
		pt.println(name);
		pt.flush();
		try {
			String s = br.readLine();
			if(s.equalsIgnoreCase("false")) {
				System.out.println("delete unsuccessful");
			}else {
				System.out.println("file deleted");
			}
			
		}catch(Exception e) {
			
		}finally {
			pt.flush();
			pt.close();
			br.close();
			deleteScanner.close();
		}
	}
}
