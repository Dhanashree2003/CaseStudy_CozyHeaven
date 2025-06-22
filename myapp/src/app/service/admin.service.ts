import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Hotel } from '../models/hotel';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private baseUrl = 'http://localhost:8080/admin';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}


  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
      Accept: '*/*'
    });
  }

  
  getHotels(): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(`${this.baseUrl}/hotels`, {
      headers: this.getAuthHeaders()
    });
  }

  getHotelById(id: number): Observable<Hotel> {
    return this.http.get<Hotel>(`${this.baseUrl}/hotels/${id}`, {
      headers: this.getAuthHeaders()
    });
  }

  addHotel(hotel: Hotel): Observable<Hotel> {
    return this.http.post<Hotel>(`${this.baseUrl}/hotels`, hotel, {
      headers: this.getAuthHeaders()
    });
  }

  updateHotel(hotelId: number, hotel: Hotel): Observable<Hotel> {
    return this.http.put<Hotel>(`${this.baseUrl}/hotels/${hotelId}`, hotel, {
      headers: this.getAuthHeaders()
    });
  }

  deleteHotel(hotelId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/hotels/${hotelId}`, {
      headers: this.getAuthHeaders()
    });
  }

 
  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/users`, {
      headers: this.getAuthHeaders()
    });
  }

  getUserById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/users/${id}`, {
      headers: this.getAuthHeaders()
    });
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/users/${id}`, {
      headers: this.getAuthHeaders()
    });
  }


  getOwners(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/owners`, {
      headers: this.getAuthHeaders()
    });
  }

  getOwnerById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/owners/${id}`, {
      headers: this.getAuthHeaders()
    });
  }

  deleteOwner(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/owners/${id}`, {
      headers: this.getAuthHeaders()
    });
  }
}
