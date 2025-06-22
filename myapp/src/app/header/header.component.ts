import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { HeaderService, User } from '../service/header.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  user: User = {
    userID: 0,
    fullName: '',
    email: '',
    password: '',
    role: '',
    gender: '',
    contactNumber: '',
    address: '',
  };

  showProfilePanel = false;
  showPassword = false;
  editMode = false;

  constructor(private headerService: HeaderService, private router: Router,private authService:AuthService) {}

  ngOnInit(): void {
    const userId = parseInt(localStorage.getItem('user_id') || '0', 10);

    if (userId) {
      this.fetchUser(userId);
    }
  }

  fetchUser(userId: number) {
    this.headerService.getUserById(userId).subscribe({
      next: (res) => (this.user = res),
      error: (err) => console.error('Error fetching user', err),
    });
  }

 

  updateProfile() {
    this.headerService.updateUser(this.user).subscribe({
      next: () => {
        alert('Profile updated successfully!');
        this.editMode = false;
      },
      error: (err) => alert('Update failed: ' + err.message),
    });
  }

  toggleProfilePanel() {
    this.showProfilePanel = !this.showProfilePanel;
    this.editMode = false;
    this.showPassword = false;
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  goBack() {
    window.history.back();
  }
}
