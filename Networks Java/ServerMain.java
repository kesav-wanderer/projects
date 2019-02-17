/**
 * server program for single client by
 * Dhirish Parthasarathy and Kesavraj kannan**/
package SC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class ServerMain {
	private static String dir = "C:\\Users\\god\\Downloads\\project-1-skeleton.tar\\project-1-skeleton\\project-1-skeleton\\message-comm";
	public static void main(String[] args) throws Exception {
		ServerSocket clisocket= new ServerSocket(5002);
		Socket servsocket = clisocket.accept();   								//accept data from client 
		Scanner optionScanner = new Scanner(servsocket.getInputStream());		
		String option = optionScanner.nextLine().toUpperCase();
		switch(option) {
		
		case "DOWNLOAD":{
			Download(servsocket);
			break;
		}
		
		case "UPLOAD":{			
			Upload(servsocket);	
			break;
		}
		
		case "RENAME":{
			Rename(servsocket);
			break;
		  }
		case "DELETE":{
			Download(servsocket);
			break;
		}
		
		}
			optionScanner.close();
		

	}
	/**
	 * method to download the file from server
	 * arguments : servsocket 
	 **/
	public static void Download(Socket servsocket) throws Exception {
		PrintWriter pt = new PrintWriter(new OutputStreamWriter(servsocket.getOutputStream()));	
		PrintStream ps = new PrintStream(servsocket.getOutputStream());
		
		boolean flag= false;
		File F1 = new File(dir);		
		File[] list=F1.listFiles();		
		System.out.println("Available Files");
		for(int i = 0 ; i< list.length ; i++) {
			System.out.println(i+"."+list[i].getName());				
		}
		System.out.println("Enter the name of the file to download");
		Scanner nameScanner = new Scanner(System.in);
		String fileName = nameScanner.nextLine();
		for(int i = 0 ; i< list.length ; i++) {				
			if((list[i].getName()).equals(fileName)){
				flag = true;
			}
		}
		
		if(flag) {						//if desired file is found send the data
			String File = dir;
			ps.println(flag);
			ps.flush();
			StringBuffer buff = new StringBuffer(File);
			buff.append("\\");
			buff.append(fileName);
			File = buff.toString();
			F1 = new File(File);
			try {
				pt.println(fileName);
				pt.flush();
				
				File ff = new File(F1.getAbsolutePath());
				FileReader fr = new FileReader(ff);
				BufferedReader bfr = new BufferedReader(fr);
				String s= null;
				while((s=bfr.readLine())!=null)			//packet sending loop
				{
					
					pt.println("\n");
					pt.println(s);						//send data to user
					pt.flush();
				}
				bfr.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				
				servsocket.close();
				pt.flush();
				pt.close();
			}
		}else {
			ps.println(flag);
			ps.flush();
			servsocket.close();
			pt.flush();
			pt.close();
		}
	}
	/**
	 * method to upload the file from server
	 * arguments : servsocket 
	 **/
	
	public static void Upload(Socket servsocket) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(servsocket.getInputStream()));
		String filename = br.readLine();
		File file = new File(dir+"\\"+filename);
		BufferedWriter pt1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		try {
			
			String uploadData = null;				
			StringBuffer buffer = new StringBuffer("");
			while((uploadData= br.readLine())!= null) {
				
				buffer.append(uploadData);
				buffer.append("\n");
				pt1.write(uploadData);     //create a local file in the server directory
				
			}
			
		}catch(Exception e) {
			e.getMessage();
			
		}finally {
			pt1.flush();
			pt1.close();
			servsocket.close();
			System.out.println("Packet Finished");
		}
	}
	/**
	 * method to rename a file from server
	 * arguments : servsocket 
	 **/
	
	public static void Rename(Socket servsocket) throws Exception {
		PrintWriter pt = new PrintWriter(new OutputStreamWriter(servsocket.getOutputStream()));
		BufferedReader br = new BufferedReader(new InputStreamReader(servsocket.getInputStream()));
		String Directory =dir;
		String file= Directory;
		File F1 = new File(file);
		File[] list=F1.listFiles();
		boolean flag = false;
		String name = br.readLine();
		
		for(int i = 0 ; i< list.length ; i++) {     //loop to check the existence of file in server
			PrintStream ps = new PrintStream(servsocket.getOutputStream());
			if((list[i].getName()).equals(name)){
				flag= true;					
				ps.println(flag);
				ps.flush();
				break;
			}
		}
		if(flag){          
			String rename = br.readLine();			
			try {
				StringBuffer buff = new StringBuffer(Directory);
				buff.append("\\");
				buff.append(rename);
				Directory= buff.toString();
				File toRename = new File(Directory);
				StringBuffer old = new StringBuffer(file);
				old.append("\\");
				old.append(name);
				String file1= old.toString();
				File oldName = new File(file1);
				boolean namingFlag = oldName.renameTo(toRename);   //renaming the file in server
				pt.println(namingFlag);
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}finally{
				pt.close();
				br.close();
				servsocket.close();
			}
		}
	}
	/**
	 * method to delete a file from server
	 * arguments : servsocket 
	 **/
	
	public static void Delete(Socket servsocket) throws Exception {
		PrintWriter pt = new PrintWriter(new OutputStreamWriter(servsocket.getOutputStream()));
		BufferedReader br = new BufferedReader(new InputStreamReader(servsocket.getInputStream()));
		try {
			
			File F1 = new File(dir);
			File[] list=F1.listFiles();
			boolean flag = false;
			String name = br.readLine();
			for(int i = 0 ; i< list.length ; i++) {    //find the file and then delete
				
				if((list[i].getName()).equals(name)){
					flag= true;							
					list[i].delete();
					pt.println(flag);
					break;
				}else{
					pt.println(flag);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			pt.flush();
			pt.close();
			br.close();
			servsocket.close();
		}
	}

}
