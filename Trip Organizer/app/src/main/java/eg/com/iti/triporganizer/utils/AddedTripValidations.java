package eg.com.iti.triporganizer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddedTripValidations {
    public AddedTripValidations() {
    }

    public int compareDate(String currentDateString,String dateToCheckString){
        int indicator = 1;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-mm-yyyy");

        try {
            Date currentDate=simpleDateFormat.parse(currentDateString);
            Date dateToCheck=simpleDateFormat.parse(dateToCheckString);
            if(dateToCheck.before(currentDate)){
                indicator= -1;
            }
            else if(dateToCheck.equals(currentDate))
            {
                indicator= 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            return indicator;
        }
    }

    public boolean compareTime(String currentTimeString ,String timeToCheckString) {
        boolean check=true;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm");
        try {
            Date currentTime=simpleDateFormat.parse(currentTimeString);
            Date timeToCheck=simpleDateFormat.parse(currentTimeString);
            if(timeToCheck.before(currentTime))
            {
                check=false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            return check;
        }
    }
}
