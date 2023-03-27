package exceptions;


// ReadingNotFoundException thrown when the logbook does not contain a specified reading (by date and time)
public class ReadingNotFoundException extends Exception {
    public ReadingNotFoundException(String msg) {
        super(msg);
    }
}

