import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

export interface User {
  userID: number;
  fullName: string;
  email: string;
  password: string;
  role: string;
  gender: string;
  contactNumber: string;
  address: string;
}

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
      Accept: '*/*'
    });
  }
  
  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/getbyid/${userId}`, {
      headers: this.getAuthHeaders()
    });
  }

  updateUser(user: User): Observable<any> {
    return this.http.put(`${this.baseUrl}/update`, user, {
      headers: this.getAuthHeaders()
    });
  }
}
