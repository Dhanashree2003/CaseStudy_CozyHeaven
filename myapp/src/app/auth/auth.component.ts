import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent {
  showLogin = true;

  constructor(private authService: AuthService, private router: Router) {}

loginUser(form: NgForm) {
  if (form.valid) {
    console.log(form.value);
    const { email, password } = form.value;

    this.authService.login(email, password).subscribe({
      next: (token: string) => {
        // alert(token); // Shows raw JWT
        console.log(token);

        this.authService.storeToken(token);
        alert('Login successful!');
        this.authService.getrolebymail(email, token).subscribe({
        next: (response: string) => {
        const decoded = JSON.parse(response);
        console.log(decoded);
        console.log(decoded.role);
      
      if (decoded.role) {
     this.authService.storeUserId(decoded?.userID); // Correct function call
        if (decoded?.role === 'Owner' && decoded?.userID) {
  this.router.navigate(['/owner'], {
    queryParams: { ownerId: decoded.userID } // this line is correct
  });
}else{
        this.handleRoleRedirect(decoded.role);
    }  } else {
        alert('Role not found in token');
      
      }
    },
    error: (err) => {
      console.error('Error getting role:', err);
      alert('Failed to get role');
    }
  });
       
      },
      error: (error) => {
        alert('Login failed!');
        console.error(error);
      }
    });
  }
}


  registerUser(form: NgForm) {
    if (form.valid) {
      this.authService.register(form.value).subscribe({
        next: () => {
           alert('Registration successful!');
          this.showLogin = true;
      this.router.navigate(['/']);
        },
        error: (e) =>{  alert('Registration successful!');
          this.showLogin = true;
      this.router.navigate(['/']);}
      });
    }
  }

  private handleRoleRedirect(role: string): void {
    if (role === 'Admin') {
      this.router.navigate(['/admin']);
    } else if (role === 'Guest') {
      this.router.navigate(['/guest']);
    } else if (role === 'Owner') {
      this.router.navigate(['/owner']);
    } else {
      alert('Unknown role');
    }
  }
}
