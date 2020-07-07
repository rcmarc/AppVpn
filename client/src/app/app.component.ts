import { Component } from '@angular/core';
import { SpringService } from './spring.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'AppVpn';

  constructor(public spring: SpringService, private router: Router) {}

  Logout() {
    this.spring
      .LogOut()
      .subscribe(() => this.router.navigateByUrl('/auth/login'));
  }

  IsAuthenticated(): boolean {
    return this.spring.IsAuthenticated();
  }
}
