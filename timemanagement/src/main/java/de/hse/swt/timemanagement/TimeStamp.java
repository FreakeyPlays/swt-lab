import java.sql.Timestamp;
import java.util.Date;

public class DateToTimestampExample1 {
	
	Date date;
	Timestamp ts;
	
	public static void main(String args[]){    
		initComponents();
	}
	
	
	public Timestamp getTimestamp() {
        ts=new Timestamp(date.getTime());  
        System.out.println(ts);//Test Printout of the Timestamp
        return ts;//Returntype based on the sql Timestamp!!
	}
	
	private void initComponents() {
		date = new Date();
	}
	
}    