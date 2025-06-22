import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AdminService } from '../service/admin.service';

@Component({
  selector: 'app-guest-manage',
  templateUrl: './guestmanage.component.html',
  styleUrls: ['./guestmanage.component.css']
})
export class GuestManageComponent implements OnInit {
  users: any[] = [];
  searchUserId: number = 0;

  constructor(private adminService: AdminService,private location: Location) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.adminService.getUsers().subscribe(data => this.users = data);
  }

  getUserById() {
    this.adminService.getUserById(this.searchUserId).subscribe(data => this.users = [data]);
  }

  deleteUser(id: number) {
  this.adminService.deleteUser(id).subscribe({
    next: () => {
      this.loadUsers(); 
    },
    error: (err) => {
      console.error('Error deleting user:', err);
      alert('User delete prevented due to bookings made.');
    }
  });
}


  goBack() {
  this.location.back();
}
}
