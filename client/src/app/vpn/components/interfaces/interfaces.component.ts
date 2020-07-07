import { Component, OnInit } from '@angular/core';
import { SpringService } from 'src/app/spring.service';
import { Device } from 'src/app/models/device.model';
import { HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { AuthTokens } from 'src/app/auth/auth.token.interface';
import { TokenService } from 'src/app/token.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-interfaces',
  templateUrl: './interfaces.component.html',
  styleUrls: ['./interfaces.component.css'],
})
export class InterfacesComponent implements OnInit {
  devices: Device[];
  authenticationError: boolean = false;
  constructor(
    private spring: SpringService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.spring.IsAuthenticated()) this.getDevices();
    else this.authenticationError = true;
  }

  getDevices() {
    this.spring
      .Devices()
      .pipe(catchError((err) => this.handleError(err)))
      .subscribe(
        (data) => (this.devices = data),
        () =>
          this.spring
            .RefreshToken()
            .subscribe((tokens) => this.handleTokens(tokens))
      );
  }

  handleError(err: Error) {
    if (err instanceof HttpErrorResponse) {
      if (err.status === 403) {
        if (this.spring.IsAuthenticated()) {
          return throwError(err);
        } else {
          this.authenticationError = true;
        }
      }
    }
  }
  handleTokens(tokens: AuthTokens): void {
    this.tokenService.setTokens(tokens.jwt, tokens.refresh);
    this.getDevices();
  }

  onSave(value: Device) {
    this.spring.SelectDevice(value.id).subscribe((d  => {
      this.router.navigateByUrl('app/main')
    }));
  }
}
