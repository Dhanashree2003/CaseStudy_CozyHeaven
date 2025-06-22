import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
  
})
export class AppComponent {
  title = 'myapp';
  showHeader = true;

  constructor(private router: Router) {
    // Listen to route changes
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // Hide header on login/register pages
        this.showHeader = !(event.url.includes('login') );
        this.showHeader = event.urlAfterRedirects !== '/';
      }
    });
  }
}
