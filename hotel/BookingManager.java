package hotel;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.*;

public class BookingManager implements IBookingManager {

	private Room[] rooms;

	public BookingManager() {
		this.rooms = initializeRooms();
	}

	private Room getRoom(Integer roomNumber){
		for(Room r: rooms){
			if(roomNumber.equals(r.getRoomNumber())){
				return r;
			}
		}
		return null;
	}

	public Set<Integer> getAllRooms() throws RemoteException {
		Set<Integer> allRooms = new HashSet<Integer>();
		Iterable<Room> roomIterator = Arrays.asList(rooms);
		for (Room room : roomIterator) {
			allRooms.add(room.getRoomNumber());
		}
		return allRooms;
	}

    // roomNumber doesnt exist => throw exception
	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) throws RemoteException {
		for(Room r: rooms){
			if(r.getRoomNumber().equals(roomNumber)){
				ArrayList<BookingDetail> bookings = (ArrayList<BookingDetail>) r.getBookings();
				for(BookingDetail b: bookings){
					if(b.getDate().isEqual(date)){
						return false;
					}
				}
				return true;
			}
		}
		return false;
		//throw new IllegalArgumentException("<" + roomNumber.toString() + "> No room with number " + roomNumber);
	}


	public void addBooking(BookingDetail bookingDetail) throws RemoteException {
		Integer roomNumber = bookingDetail.getRoomNumber();
		LocalDate date = bookingDetail.getDate();
		Room room = getRoom(roomNumber);

		room.getLock().lock();
		try{
			// critical section
			if(!this.isRoomAvailable(roomNumber, date)){
			/*throw new BookingException("<" + roomNumber.toString() + "> Room " +
			    roomNumber.toString() + " not available on " + date.toString());*/
				System.out.println("Booking Failed.");
				return;
			}
			List<BookingDetail> bookings = room.getBookings();
			bookings.add(bookingDetail);
			room.setBookings(bookings);
			return;
		}
		finally{
			//unlock in finally block to ensure the lock is opened
			//even if an exception is thrown in the CS
			room.getLock().unlock();
		}
	}

	public Set<Integer> getAvailableRooms(LocalDate date) throws RemoteException {
		Set<Integer> returnable = new HashSet<Integer>();
		for(Room r : rooms){
			List<BookingDetail> details = r.getBookings();
			boolean add = true;
			for (BookingDetail detail: details){
				if(detail.getDate().isEqual(date)){
					add = false;
				}
			}
			if(add) {
				returnable.add(r.getRoomNumber());
			}
		}
		return returnable;
	}

	private static Room[] initializeRooms() {
		Room[] rooms = new Room[4];
		rooms[0] = new Room(101);
		rooms[1] = new Room(102);
		rooms[2] = new Room(201);
		rooms[3] = new Room(203);
		return rooms;
	}
}
