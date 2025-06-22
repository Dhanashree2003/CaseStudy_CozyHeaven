import { Component, OnInit } from '@angular/core';
import { AdminService } from '../service/admin.service';

@Component({
  selector: 'app-ownermanage',
  templateUrl: './ownermanage.component.html',
  styleUrls: ['./ownermanage.component.css']
})
export class OwnerManageComponent implements OnInit {
  owners: any[] = [];
  searchOwnerId: number = 0;

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.loadOwners();
  }

  loadOwners() {
    this.adminService.getOwners().subscribe(data => this.owners = data);
  }

  getOwnerById() {
    this.adminService.getOwnerById(this.searchOwnerId).subscribe(data => this.owners = [data]);
  }

  deleteOwner(id: number) {
  if (confirm('Are you sure you want to delete this owner?')) {
    this.adminService.deleteOwner(id).subscribe({
      next: () => this.loadOwners(),
      error: (err) => {
        console.error('Error deleting owner:', err);
        alert('Owner delete prevented due to associated hotels or bookings.');
      }
    });
  }
}

}
