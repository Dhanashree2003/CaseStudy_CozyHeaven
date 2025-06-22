export interface Payment {
  bookingID: number;
  paymentDate: string;
  amount: number;
  paymentMethod: string;
  paymentStatus: string;
  transactionID: string;
  mobileNumber: string;
  bankName: string;
}
