package hotel;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class HotelServer {

    private static final String _bookingManagerName = "Hilton";

    public static void main(String[] args){// Add throws exceptions..
        // set security manager if non existent
        if(System.getSecurityManager() != null)
            System.setSecurityManager(null);

        //Create BookingManager
        IBookingManager bm = new BookingManager();

        // locate registry
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry();

        } catch(RemoteException e) {
           // logger.log(Level.SEVERE, "Could not locate RMI registry.");
            System.exit(-1);
        }

        // register BookingManager
        IBookingManager stub;
        try {
            stub = (IBookingManager) UnicastRemoteObject.exportObject(bm, 0);
            registry.rebind(_bookingManagerName, stub);

            //logger.log(Level.INFO, "<{0}> Car Rental Company {0} is registered.", _rentalCompanyName);
        } catch(RemoteException e) {
            //logger.log(Level.SEVERE, "<{0}> Could not get stub bound of Car Rental Company {0}.", _rentalCompanyName);
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
