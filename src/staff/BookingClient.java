package staff;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.Set;

import hotel.BookingDetail;
import hotel.IBookingManager;

public class BookingClient extends AbstractScriptedSimpleTest {

    public static final String _defaultBookingManagerName = "Hilton";

	private IBookingManager bm = null;

	public static void main(String[] args) throws Exception {
		// set security manager
		if (System.getSecurityManager() != null)
			System.setSecurityManager(null);

		BookingClient client = new BookingClient();
		client.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/
	public BookingClient() {
		try {
			//Look up the registered remote instance
			Registry registry = LocateRegistry.getRegistry();
            bm = (IBookingManager) registry.lookup(_defaultBookingManagerName);

			//bm = new BookingManager();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) throws RemoteException {
        return bm.isRoomAvailable(roomNumber, date);
	}

	@Override
	public void addBooking(BookingDetail bookingDetail) throws RemoteException{
		bm.addBooking(bookingDetail);
	}

	@Override
	public Set<Integer> getAvailableRooms(LocalDate date) throws RemoteException {
        return bm.getAvailableRooms(date);
	}

	@Override
	public Set<Integer> getAllRooms()  throws RemoteException{
        return bm.getAllRooms();
	}
}
