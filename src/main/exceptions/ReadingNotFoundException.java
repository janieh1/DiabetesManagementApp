package exceptions;


// ReadingNotFoundException thrown when the logbook does not contain a specified reading, i.e.,
// there is no reading in the logbook that has the specified date and time
public class ReadingNotFoundException extends Exception {
    public ReadingNotFoundException(String msg) {
        super(msg);
    }
}

