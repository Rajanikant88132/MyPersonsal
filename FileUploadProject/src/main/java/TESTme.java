import java.text.SimpleDateFormat;
import java.util.Date;

public class TESTme {

	public static void main(String args[])
	{
		 String pattern = "dd.MM.YYYY hh:mm:ss";
	       SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	       String date = simpleDateFormat.format(new Date());
	       System.out.println(date);
	     
		
	}
}
