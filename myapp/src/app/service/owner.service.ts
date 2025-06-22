// src/app/services/owner.service.ts
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';


@Injectable({
  providedIn: 'root',
})
export class OwnerService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
      Accept: '*/*'
    });
  }

  getHotels(ownerId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/owner/hotels?ownerId=${ownerId}`, {
      headers: this.getAuthHeaders()
    });
  }

  getRooms(hotelId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/owner/rooms/${hotelId}`, {
      headers: this.getAuthHeaders()
    });
  }

  addRoom(hotelId: number, room: any) {
    return this.http.post(`${this.baseUrl}/owner/hotels/${hotelId}/rooms`, room, {
      headers: this.getAuthHeaders()
    });
  }

  deleteRoom(roomId: number) {
    return this.http.delete(`${this.baseUrl}/owner/rooms/${roomId}`, {
      headers: this.getAuthHeaders()
    });
  }

  getReviews(hotelId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/owner/reviews?hotelId=${hotelId}`, {
      headers: this.getAuthHeaders()
    });
  }

  getBookings(ownerId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/owner/bookings?ownerId=${ownerId}`, {
      headers: this.getAuthHeaders()
    });
  }

  updateBookingStatus(bookingId: number, payload: any) {
    return this.http.put(`${this.baseUrl}/owner/bookings/${bookingId}/status`, payload, {
      headers: this.getAuthHeaders()
    });
  }

  updateRoom(roomId: number, roomData: any) {
    return this.http.put(`${this.baseUrl}/owner/${roomId}`, roomData, {
      headers: this.getAuthHeaders()
    });
  }
}
