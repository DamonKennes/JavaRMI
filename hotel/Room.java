package hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Room {
	private Lock lock = new ReentrantLock(true);

	private Integer roomNumber;
	private List<BookingDetail> bookings;

	public Room(Integer roomNumber) {
		this.roomNumber = roomNumber;
		bookings = new ArrayList<BookingDetail>();
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public List<BookingDetail> getBookings() {
		return bookings;
	}

	public Lock getLock(){
		return lock;
	}

	public void setBookings(List<BookingDetail> bookings) {
		this.bookings = bookings;
	}
}