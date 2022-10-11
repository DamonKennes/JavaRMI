package hotel;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.*;

public class BookingManager implements IBookingManager {

	private Room[] rooms;

	public BookingManager() {
		this.rooms = initializeRooms();
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
			System.out.println(r.getRoomNumber().toString());
			if(r.getRoomNumber().equals(roomNumber)){
				System.out.println("test");
				ArrayList<BookingDetail> bookings = (ArrayList<BookingDetail>) r.getBookings();
				for(BookingDetail b: bookings){
					if(b.getDate().isEqual(date)){
						System.out.println("testfalse");
						return false;
					}
				}
				System.out.println("testtrue");
				return true;
			}
		}
		return false;
		//throw new IllegalArgumentException("<" + roomNumber.toString() + "> No room with number " + roomNumber);
	}


	public void addBooking(BookingDetail bookingDetail) throws RemoteException {
		Integer roomNumber = bookingDetail.getRoomNumber();
		LocalDate date = bookingDetail.getDate();
		if(!this.isRoomAvailable(roomNumber, date)){
			throw new BookingException("<" + roomNumber.toString() + "> Room " +
			    roomNumber.toString() + " not available on " + date.toString());
		}
		for(Room r : rooms) {
			if(r.getRoomNumber() == roomNumber){
				List<BookingDetail> bookings = r.getBookings();
				bookings.add(bookingDetail);
				r.setBookings(bookings);
				return;
			}
		}
		//throw new IllegalArgumentException("<" + roomNumber.toString() + "> No room with number " + roomNumber);
	}

	public Set<Integer> getAvailableRooms(LocalDate date) throws RemoteException {
		Set<Integer> returnable = new HashSet<Integer>();
		Iterable<Room> roomIterator = Arrays.asList(rooms);
		for(Room r : roomIterator){
			List<BookingDetail> details = r.getBookings();
			for (BookingDetail detail: details){
				if(detail.getDate().isEqual(date) == true){
					returnable.add(r.getRoomNumber());
				}
			}
		}
		if(returnable.isEmpty() == false){
			return returnable;
		}
		else{
			throw new IllegalArgumentException("No rooms avaible for given date: " + date.toString());
		}
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
