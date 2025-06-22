import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OwnerService } from '../service/owner.service';
import { Hotel } from '../models/hotel';
import { Room } from '../models/room';
import { Review } from '../models/review';
import { Booking } from '../models/booking';


@Component({
  selector: 'app-owner',
  templateUrl: './owner.component.html',
  styleUrls: ['./owner.component.css']
})
export class OwnerComponent implements OnInit {
  ownerId!: number;
  hotelId!: number;
  hotels: Hotel[] = [];
  rooms: Room[] = [];
  reviews: Review[] = [];
  bookings: Booking[] = [];

  activeRoomHotelId: number | null = null;
  showReviewSidebar = false;
  showBookingsSidebar = false;
  showAddRoomModal = false;
  showEditRoomModal = false;

  currentHotelIdForRoom: number | null = null;

  newRoom: Partial<Room> = {
    roomName: '',
    roomSize: '',
    bedType: '',
    maxOccupancy: 1,
    baseFare: 0,
    ac: true,
    roomStatus: 'Available'
  };

  editRoom: Partial<Room> = {};

  constructor(
    private route: ActivatedRoute,
    private ownerService: OwnerService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.ownerId = +params['ownerId'];
      if (this.ownerId) {
        this.loadHotels();
      }
    });
  }

  loadHotels(): void {
    this.ownerService.getHotels(this.ownerId).subscribe({
      next: (data: Hotel[]) => this.hotels = data,
      error: (err) => console.error('Failed to fetch hotels:', err)
    });
  }

  viewRooms(hotelId: number): void {
    this.loadRooms(hotelId);
  }

  loadRooms(hotelId: number): void {
    this.activeRoomHotelId = hotelId;
    this.ownerService.getRooms(hotelId).subscribe({
      next: (data: Room[]) => this.rooms = data,
      error: (err) => console.error('Failed to fetch rooms:', err)
    });
  }

  openAddRoomModal(hotelId: number): void {
    this.showAddRoomModal = true;
    this.currentHotelIdForRoom = hotelId;
  }

  closeAddRoomModal(): void {
    this.showAddRoomModal = false;
    this.newRoom = {
      roomName: '',
      roomSize: '',
      bedType: '',
      maxOccupancy: 1,
      baseFare: 0,
      ac: true,
      roomStatus: 'Available'
    };
  }

  addRoom(): void {
    if (this.currentHotelIdForRoom === null) return;

    const payload: Room = {
      ...this.newRoom,
      hotelID: this.currentHotelIdForRoom
    } as Room;

    this.ownerService.addRoom(this.currentHotelIdForRoom, payload).subscribe({
      next: () => {
        this.loadRooms(this.currentHotelIdForRoom!);
        this.closeAddRoomModal();
      },
      error: (err) => console.error('Failed to add room:', err)
    });
  }

  openEditRoomModal(room: Room): void {
    this.editRoom = { ...room };
    this.showEditRoomModal = true;
  }

  closeEditRoomModal(): void {
    this.showEditRoomModal = false;
    this.editRoom = {};
  }

  updateRoom(): void {
    if (!this.editRoom || !this.editRoom.roomID) return;

    this.ownerService.updateRoom(this.editRoom.roomID, this.editRoom).subscribe({
      next: () => {
        this.loadRooms(this.activeRoomHotelId!);
        this.closeEditRoomModal();
      },
      error: (err) => console.error('Failed to update room:', err)
    });
  }

  deleteRoom(roomId: number): void {
    this.ownerService.deleteRoom(roomId).subscribe({
      next: () => {
        if (this.activeRoomHotelId !== null) {
          this.loadRooms(this.activeRoomHotelId);
        }
      },
      error: (err) => {
        console.error('Failed to delete room:', err);
        alert("The room is booked by the guest, so it can't be deleted");
      }
    });
  }

toggleReviews(hotelId?: number): void {
    if (hotelId !== undefined) {
    this.activeRoomHotelId = hotelId;
  }
  this.showReviewSidebar = !this.showReviewSidebar;
  console.log('Fetching reviews for hotel:', this.activeRoomHotelId);

  if (this.showReviewSidebar && this.activeRoomHotelId !== null) {
    this.ownerService.getReviews(this.activeRoomHotelId).subscribe({
      next: (data: Review[]) => this.reviews = data,
      error: (err) => console.error('Failed to fetch reviews:', err)
    });
  }
}


  toggleBookings(): void {
    this.showBookingsSidebar = !this.showBookingsSidebar;
    if (this.showBookingsSidebar) {
      this.ownerService.getBookings(this.ownerId).subscribe({
        next: (data: Booking[]) => this.bookings = data,
        error: (err) => console.error('Failed to fetch bookings:', err)
      });
    }
  }

  updateBookingStatus(booking: Booking, newStatus: string): void {
    const payload: Booking = { ...booking, bookingStatus: newStatus };
    this.ownerService.updateBookingStatus(booking.bookingID, payload).subscribe({
      next: () => this.toggleBookings(), // Refresh booking list
      error: (err) => console.error('Failed to update booking status:', err)
    });
  }
}
