package hotel;

import java.rmi.RemoteException;

public class BookingException extends RemoteException {

    public BookingException(String string){super(string);}
}
