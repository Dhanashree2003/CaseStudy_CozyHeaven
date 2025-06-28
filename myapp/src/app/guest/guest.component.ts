import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { HotelService } from '../service/hotel.service';

@Component({
  selector: 'app-guest',
  templateUrl: './guest.component.html',
  styleUrls: ['./guest.component.css']
})

export class GuestComponent implements OnInit {
  username: string = '';
  hotels: any[] = [];
  errorMsg: string = '';

  reviewCounts: { [hotelId: number]: number } = {};
  ratingsMap: { [hotelId: number]: number } = {};
  selectedReviews: any[] = [];
  isSidebarOpen: boolean = false;
  bookingsSidebarOpen: boolean = false;
  bookings: any[] = [];

location: string = '';
checkInDate?: string;
checkOutDate?: string;
ac?: boolean = false;
today: string = new Date().toISOString().split('T')[0];
checkoutMinDate: string = this.today;
   

  constructor(private hotelService: HotelService, private router: Router,private authService : AuthService) {}

  ngOnInit(): void {
    const user = localStorage.getItem('username');
    this.username = user ?? 'Guest';
    this.updateCheckoutMinDate();
  }



searchHotels(): void {
  this.hotels = [];
  this.errorMsg = '';
  this.reviewCounts = {};
  this.ratingsMap = {};

  const filterPayload: any = {
    location: this.location || null,
    checkInDate: this.checkInDate || null,
    checkOutDate: this.checkOutDate || null,
    ac: this.ac
  };
  localStorage.setItem('filterData', JSON.stringify(filterPayload));
  console.log('Filter payload:', filterPayload);

  // Call the updated filter API
  this.hotelService.filterHotels(filterPayload).subscribe({
    next: (data) => {
      this.hotels = data;
      this.hotels.forEach(hotel => {
        this.hotelService.getHotelReviews(hotel.hotelID).subscribe(reviews => {
          this.reviewCounts[hotel.hotelID] = reviews.length;
          const avgRating = reviews.reduce((sum: number, r: any) => sum + r.rating, 0) / (reviews.length || 1);
          this.ratingsMap[hotel.hotelID] = Math.round(avgRating);
        });
      });
    },
    error: () => this.errorMsg = 'No hotels found or server error.'
  });
}

updateCheckoutMinDate(): void {
  if (this.checkInDate) {
    const nextDay = new Date(this.checkInDate);
    nextDay.setDate(nextDay.getDate() + 1);
    this.checkoutMinDate = nextDay.toISOString().split('T')[0];

    // Reset checkOutDate if it becomes invalid
    if (this.checkOutDate && this.checkOutDate < this.checkoutMinDate) {
      this.checkOutDate = '';
    }
  } else {
    this.checkoutMinDate = this.today;
  }
}

  getStars(hotelId: number): number[] {
    return Array(this.ratingsMap[hotelId] || 0).fill(0);
  }

  getEmptyStars(hotelId: number): number[] {
    return Array(5 - (this.ratingsMap[hotelId] || 0)).fill(0);
  }

  openReviewSidebar(hotelId: number): void {
    this.hotelService.getHotelReviews(hotelId).subscribe(reviews => {
      this.selectedReviews = reviews;
      this.isSidebarOpen = true;
    });
  }

  

  closeSidebar(): void {
    this.isSidebarOpen = false;
  }

  toggleBookingsSidebar(): void {
    this.bookingsSidebarOpen = !this.bookingsSidebarOpen;
    if (this.bookingsSidebarOpen) {
      const guestId =  this.authService.getStoredUserId(); 
        if (guestId) {
          this.hotelService.getBookingsByGuestId(guestId).subscribe({
            next: (data) => this.bookings = data,
            error: (err) => console.error('Error fetching bookings:', err)
          });
        } else {
          console.warn('Guest ID not found.');
        }

    }
  }

  cancelBooking(bookingId: number): void {
  this.hotelService.cancelBooking(bookingId).subscribe({
    next: () => {
      // After successful cancellation, fetch updated bookings
      const guestId = this.authService.getStoredUserId(); 
      if (guestId) {
        this.hotelService.getBookingsByGuestId(guestId).subscribe({
          next: (data) => {
            // Filter bookings by booking ID if necessary, or update full list
            this.bookings = data;
          },
          error: (err) => {
            console.error('Error fetching updated bookings:', err);
          }
        });
      } else {
        console.warn('Guest ID not found.');
      }
    },
    error: (err) => {
      console.error('Cancel booking failed:', err);
    }
  });
}



  goToRoomBooking(hotelId: number): void {

    this.router.navigate(['/roombooking', hotelId], {
  queryParams: {
    checkInDate: this.checkInDate,
    checkOutDate: this.checkOutDate,
    location: this.location,
    ac: this.ac
  }
});
  }

  submitReview(booking: any): void {
  const userID = this.authService.getStoredUserId(); 
console.log(booking);
  if (!booking.reviewRating || !booking.reviewComment) {
    alert('Please provide both rating and comment.');
    return;
  }

  const review = {
    reviewID: 0,
    userID: userID,
    hotelID: booking.hotelID, 
    rating: booking.reviewRating,
    comment: booking.reviewComment,
    reviewDate: new Date().toISOString().split('T')[0]
  };

  this.hotelService.submitReview(review).subscribe({
    next: () => {
      alert('Review submitted successfully!');
      booking.reviewRating = null;
      booking.reviewComment = '';
    },
    error: err => {
      console.error('Review submission failed:', err);
      alert('Failed to submit review.');
    }
  });
}

}

