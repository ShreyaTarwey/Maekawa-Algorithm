import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;

public class check {
	public static int initialFileCheck()
	{

		BufferedReader br = null;
 
		try {

			String[][] quorums = new String[100][100];
			String[] server = new String[100];
			String[] port = new String[100];
  			int count = 0;
  			int quorumlength = 0;
  			int flag = 0;
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("testconfig.txt"));
 
			while ((sCurrentLine = br.readLine()) != null ) {

				if(sCurrentLine.startsWith("dc")||sCurrentLine.startsWith("DC")||sCurrentLine.startsWith("Dc"))
				{
					String[] parts = sCurrentLine.split("\t");
					server[count]=parts[0];
					port[count]=parts[1];
					String[] quorum = parts[2].split(" ");
					int len = quorum.length;
					if(flag == 0)
					{
						quorumlength = len;
						for(int i=0;i<quorum.length;i++)
						{
							quorums[count][i] = quorum[i];
						}
						count += 1;
						flag =1;
					}
					else if(quorumlength == len && flag == 1)
					{
					
						for(int i=0;i<quorum.length;i++)
						{
							quorums[count][i] = quorum[i];
						}
						count += 1;
					}
					else
					{
						System.out.println("Error:003 with quorum formations - The number of quorums in which each process is should be the same");
						return 1;
					}
				}
			}
			int same = 0;
			int different = 0;
			for(int i=0;i<count-1;i++)
			{
				for(int r=i+1;r<count;r++)
				{
					same = 0;
					different = 0;
					for(int j=0;j<quorumlength;j++)
					{
							for(int k=0; k<quorumlength; k++)
							{
								if(quorums[i][j].equals(quorums[r][k]))
								{
									same = 1;
									different += 1;
								}
								else
								{

								}
							}

					}
					if(different==quorumlength)
					{
						System.out.println("Error:002 with quorum formations - Quorum property of subset violated");
						return 1;
					}
					if(same!=1)
					{
						System.out.println("Error:001 with quorum formations - Quorum property of intersection violated");
						return 1;
					}
				}
			}
 
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error 008:Error reading config file");
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return 0;

	}
	public static int writeToCheckFile(int prsid,int pos)
	{

		try{

			String data = "" ;
			if(pos ==1)
			{
    			data = ""+prsid+",";
    		}
    		else if(pos ==2)
    		{
    			data = ""+prsid+"\n";
    		}
    		else
    		{
    			System.out.println("Error 005:Error writing to the file, position not correct");
    			return 1;
    		}
 			System.out.println("----------------------------File Created ------------------");
    		File file =new File("checkFile.txt");
    		System.out.println(file.getAbsolutePath());

    		if(!file.exists()){
    			file.createNewFile();
    		}
 
    		FileWriter fileWritter = new FileWriter(file.getName(),true);
    	    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	    bufferWritter.write(data);
    	    bufferWritter.close();
 
	        System.out.println("Done");
 
    	}catch(IOException e){
    		System.out.println("Error 004:Error writing to the file");
    		return 1;
    	}
		return 0;
	}

	public static int isMultipleCS()
	{
		BufferedReader br = null;
 
		try {
			String sCurrentLine;
 			String var[] = new String[100];
			br = new BufferedReader(new FileReader("checkFile.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				var = sCurrentLine.split(",");
				if(var.length!=2)
				{
					System.out.println("Error 007:James Bond error, Multiple access to Critical Section");
					return 1;
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Error 006:Error reading file");
			return 1;
		}
		return 0;
	}
 


	public static void main(String[] args) {
 
		initialFileCheck();
 
	}
}