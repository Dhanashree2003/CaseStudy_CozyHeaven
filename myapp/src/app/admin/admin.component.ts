import { Component, OnInit } from '@angular/core';
import { Hotel } from '../models/hotel';
import { AdminService } from '../service/admin.service';


declare var window: any;

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  hotels: Hotel[] = [];
  searchHotelId: number = 0;

  newHotel: Hotel = this.getEmptyHotel();
  editHotel: Hotel = this.getEmptyHotel();

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.loadHotels();
  }

  private getEmptyHotel(): Hotel {
    return {
  hotelName: '',
  location: '',
  amenities: '',
  imgUrl: '',
  ownerID: 0,
  hotelID: 0
};
  }

  loadHotels() {
    this.adminService.getHotels().subscribe({
      next: (data) => {
        this.hotels = data;
        console.log('Loaded hotels:', data);
      },
      error: (err) => {
        console.error('Error loading hotels:', err);
      }
    });
  }

  getHotelById() {
    this.adminService.getHotelById(this.searchHotelId).subscribe({
      next: (hotel) => {
        this.hotels = [hotel];
      },
      error: (err) => {
        console.error('Hotel not found:', err);
      }
    });
  }

  deleteHotel(id: number) {
    this.adminService.deleteHotel(id).subscribe({
      next: () => this.loadHotels(),
      error: () => {
        alert("Cannot delete: Hotel is referenced in bookings.");
      }
    });
  }

  openAddHotelForm() {
    this.newHotel = this.getEmptyHotel();
    const modal = new window.bootstrap.Modal(document.getElementById('addHotelModal'));
    modal.show();
  }

  submitAddHotel() {
    this.adminService.addHotel(this.newHotel).subscribe(() => {
      this.loadHotels();
      const modal = window.bootstrap.Modal.getInstance(document.getElementById('addHotelModal'));
      modal.hide();
    });
  }

  openEditHotelForm(hotel: Hotel) {
    this.editHotel = { ...hotel };
    const modal = new window.bootstrap.Modal(document.getElementById('editHotelModal'));
    modal.show();
  }

  submitEditHotel() {
    if (!this.editHotel.hotelID) return;

    this.adminService.updateHotel(this.editHotel.hotelID, this.editHotel).subscribe(() => {
      this.loadHotels();
      const modal = window.bootstrap.Modal.getInstance(document.getElementById('editHotelModal'));
      modal.hide();
    });
  }
}
