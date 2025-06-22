import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { HotelService } from '../service/hotel.service';
import { Room } from '../models/room';

@Component({
  selector: 'app-roombooking',
  templateUrl: './roombooking.component.html',
  styleUrls: ['./roombooking.component.css']
})
export class RoombookingComponent implements OnInit {
  hotelId!: number;
  rooms: Room[] = [];
  bookingForms: { [roomId: number]: any } = {};
  selectedRoomId: number | null = null;
  modalInstance: any; 

minOccupancy: number | null = null;
  maxPrice: number | null = null;
  roomSize: string = '';

  checkInDate: string = '';
  checkOutDate: string = '';
  location: string = '';
  ac: boolean | null = null;
  errorMsg: string = '';
  today: string = new Date().toISOString().split('T')[0];



  constructor(
    private authService: AuthService, 
    private route: ActivatedRoute,
    private hotelService: HotelService,
    private router: Router

  ) {}

ngOnInit(): void {
  this.hotelId = +this.route.snapshot.paramMap.get('hotelId')!;
  const queryParams = this.route.snapshot.queryParams;

  this.checkInDate = queryParams['checkInDate'] || '';
  this.checkOutDate = queryParams['checkOutDate'] || '';
  this.location = queryParams['location'] || '';
  this.ac = queryParams['ac'] === 'true';

  console.log('Restored from Query Params:', {
    checkInDate: this.checkInDate,
    checkOutDate: this.checkOutDate,
    location: this.location,
    ac: this.ac,
  });

  this.loadRooms();
}


    searchRooms(): void {
    this.rooms = [];
    this.errorMsg = '';

    const payload: any = {
      hotelId: this.hotelId,
      minOccupancy: this.minOccupancy || null,
      maxPrice: this.maxPrice || null,
      roomSize: this.roomSize?.trim() || null,
      checkInDate: this.checkInDate || null,
      checkOutDate: this.checkOutDate || null,
      location: this.location || null,
       ac: this.ac
    };
    console.log('Room Filter Payload:', payload);

    this.hotelService.filterRooms(payload).subscribe({
      next: (data) => {
        this.rooms = data;
      },
      error: () => {
        this.errorMsg = 'No rooms found or server error.';
      }
    });
  }

  loadRooms(): void {
    this.hotelService.getRoomsByHotelId(this.hotelId).subscribe({
      next: (data: Room[]) => {
        this.rooms = data;
        data.forEach((room: Room) => {
          this.bookingForms[room.roomID] = {
            userID: this.authService.getStoredUserId(), 
            roomID: room.roomID,
            hotelID:room.hotelID,
            checkInDate: '',
            checkOutDate: '',
            noOfAdults: 1,
            noOfChildren: 0,
            totalFare: room.baseFare,
            bookingStatus: 'Pending'
          };
        });
      },
      error: (err) => {
        console.error('Failed to fetch rooms:', err);
      }
    });
  }

  openBookingPopup(roomId: number): void {
    this.selectedRoomId = roomId;
    const modalElement = document.getElementById('bookingModal');
    if (modalElement) {
      const bootstrapModal = (window as any).bootstrap.Modal;
      this.modalInstance = new bootstrapModal(modalElement);
      this.modalInstance.show();
    }
  }

  closeModal(): void {
    if (this.modalInstance) {
      this.modalInstance.hide();
    }
    this.selectedRoomId = null;
  }

  updateTotalFare(roomId: number): void {
  const form = this.bookingForms[roomId];
  const checkIn = new Date(form.checkInDate);
  const checkOut = new Date(form.checkOutDate);
  const room = this.rooms.find(r => r.roomID === roomId);

  if (!form.checkInDate || !form.checkOutDate || !room) {
    form.totalFare = room?.baseFare || 0;
    return;
  }

  const diffTime = checkOut.getTime() - checkIn.getTime();
  const diffDays = Math.max(Math.ceil(diffTime / (1000 * 60 * 60 * 24)), 1);

  form.totalFare = diffDays * room.baseFare;
}

  submitBooking(roomId: number): void {
  const bookingData = this.bookingForms[roomId];
const room = this.rooms.find(r => r.roomID === roomId);
if (!room) {
  console.error('Room not found for roomId:', roomId);
  alert('Something went wrong. Room details missing.');
  return;
}

const checkIn = new Date(bookingData.checkInDate);
const checkOut = new Date(bookingData.checkOutDate);
const diffTime = Math.abs(checkOut.getTime() - checkIn.getTime());
const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) || 1;

bookingData.totalFare = diffDays * room.baseFare;


  this.hotelService.bookRoom(bookingData).subscribe({
    next: (res) => {
      const bookingID = 7; 
      this.processOTPAndPayment(bookingID, bookingData.totalFare);
    },
    error: (err) => {
      console.error('Booking failed:', err);
      alert('Booking failed. Please try again.');
    }
  });
}

currentStep: number = 1;
bookingSuccess: boolean = false;

otp: string = '';
paymentDetails = {
  paymentMethod: 'CreditCard',
  mobileNumber: '',
  bankName: '',
  cardNumber: '',
  transactionID: 'TXN' + Date.now() + Math.floor(Math.random() * 1000)

};

nextStep() {
  this.currentStep = 2;
}

previousStep() {
  this.currentStep = 1;
}


processOTPAndPayment(bookingID: number, amount: number): void {
  console.log(this.otp);

  // if (this.otp === '1111') {
    const paymentPayload = {
    
      bookingID: bookingID,
      paymentDate: new Date().toISOString().split('T')[0],
      amount: amount,
      paymentMethod: this.paymentDetails.paymentMethod,
      paymentStatus: 'Success',
      transactionID: this.paymentDetails.transactionID,
      mobileNumber: this.paymentDetails.mobileNumber,
      bankName: this.paymentDetails.bankName
    };
console.log(paymentPayload);
    this.hotelService.makePayment(paymentPayload).subscribe({
      next: () => {
       alert('Payment successful and booking confirmed!');
        this.closeModal();
        this.router.navigate(['/paymentsuccess']);
      },
      error: () => {
        alert('Payment failed.');
      }
    });
}

}
