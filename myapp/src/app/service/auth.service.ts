import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<string> {
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  const body = { email, password };
  return this.http.post<string>(`${this.baseUrl}/login/authenticate`, body, {
    headers,
    responseType: 'text' as 'json' 
  });
}
getrolebymail(email: string,token:string): Observable<any> {
   
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'accept': '*/*'
    });
    return this.http.get(`http://localhost:8080/users/userId?fullName=${email}`, { headers, responseType: 'text' as 'json'  });
  }


  register(data: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    console.log(data);
    return this.http.post(`${this.baseUrl}/registration/new`, data, { headers });
  }

  storeToken(token: string): void {
    localStorage.setItem('auth_token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  clearToken(): void {
    localStorage.removeItem('auth_token');
  }

  storeUserId(userId: number): void {
  localStorage.setItem('user_id', userId.toString());
  }

  getStoredUserId(): number | null {
    const id = localStorage.getItem('user_id');
    return id ? parseInt(id, 10) : null;
  }

  clearUserId(): void {
    localStorage.removeItem('user_id');
  }

  decodeToken(token: string): any {
    try {
      const payload = token.split('.')[1];
      return JSON.parse(atob(payload));
    } catch (e) {
      console.error('Invalid JWT format:', e);
      return null;
    }
  }
 getGuestIdFromToken(): number | null {
    const token = this.getToken();
    if (token) {
      const decoded: any = jwtDecode(token);
      return decoded.guestId || decoded.userId || null;
    }
    return null;
  }

}
