export interface Booking {
  bookingID: number;
  userID: number;
  roomID: number;
  hotelID: number;
  checkInDate: string;
  checkOutDate: string;
  noOfAdults: number;
  noOfChildren: number;
  totalFare: number;
  bookingStatus: string;
  
}
