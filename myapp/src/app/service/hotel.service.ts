import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { Room } from '../models/room';

@Injectable({
  providedIn: 'root'
})
export class HotelService {
  private baseUrl = 'http://localhost:8080/guest/';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
      Accept: '*/*'
    });
  }

  filterHotels(filter: any): Observable<any[]> {
    return this.http.post<any[]>(`${this.baseUrl}filter`, filter, {
      headers: this.getAuthHeaders()
    });
  }

  getRoomsByHotelId(hotelId: number): Observable<Room[]> {
    return this.http.get<Room[]>(`${this.baseUrl}hotels/${hotelId}/rooms`, {
      headers: this.getAuthHeaders()
    });
  }

  filterRooms(filter: any): Observable<Room[]> {
    return this.http.post<Room[]>(`${this.baseUrl}roomFilter`, filter, {
      headers: this.getAuthHeaders()
    });
  }

  bookRoom(bookingData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}booking`, bookingData, {
      headers: this.getAuthHeaders()
    });
  }

  makePayment(paymentData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}pay`, paymentData, {
      headers: this.getAuthHeaders()
    });
  }

  getHotelReviews(hotelId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}review/${hotelId}`, {
      headers: this.getAuthHeaders()
    });
  }

  getBookingsByGuestId(guestId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}bookings/${guestId}`, {
      headers: this.getAuthHeaders()
    });
  }

  cancelBooking(bookingId: number): Observable<any> {
    return this.http.put(`${this.baseUrl}booking/cancel/${bookingId}`, {}, {
      headers: this.getAuthHeaders()
    });
  }

  submitReview(review: any): Observable<any> {
    return this.http.post(`${this.baseUrl}addReview`, review, {
      headers: this.getAuthHeaders()
    });
  }
}
