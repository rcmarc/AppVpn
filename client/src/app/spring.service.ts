import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AuthTokens } from './auth/auth.token.interface';
import { Device } from './models/device.model';
import { Vpn } from './models/vpn.model';
import { TokenService } from './token.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class SpringService {
  user: string;
  constructor(
    private http: HttpClient,
    private tokenService: TokenService,
    private router: Router
  ) {}

  Login(username: string, password: string): Observable<AuthTokens> {
    return this.http
      .post<AuthTokens>('auth/login', { username, password })
      .pipe(finalize(() => (this.user = username)));
  }

  Register(username: string, email: string, password: string): Observable<any> {
    return this.http.post('auth/register', { username, email, password }, {headers: this.getBearerHeader()});
  }

  RefreshToken(): Observable<AuthTokens> {
    console.log('refreshed');
    return this.http.post<AuthTokens>('/auth/token', {
      username: this.user,
      refresh: this.tokenService.getRefreshToken(),
    });
  }

  SelectDevice(id: number): Observable<Device> {
    return this.http.get<Device>(`/devices/${id}`, {
      headers: this.getBearerHeader()
    });
  }

  Devices(): Observable<Device[]> {
    return this.http.get<Device[]>('/devices', {
      headers: this.getBearerHeader(),
    });
  }

  Events(start: Date, end: Date): Observable<Vpn[]> {
    const body = { start, end };
    return this.http.post<Vpn[]>('/events', body, {
      headers: this.getBearerHeader(),
    });
  }


  LogOut(): Observable<any> {
    return this.http
      .post(
        'auth/logout',
        { jwt: this.tokenService.getJwt() },
        { headers: this.getBearerHeader() }
      )
      .pipe(finalize(() => this.tokenService.setTokens(undefined, undefined)));
  }

  IsAuthenticated(): boolean {
    return this.tokenService.getJwt() !== undefined;
  }

  private getBearerHeader(): HttpHeaders {
    return this.tokenService.getJwt()
      ? new HttpHeaders({
          Authorization: 'Bearer ' + this.tokenService.getJwt(),
        })
      : new HttpHeaders();
  }
}
