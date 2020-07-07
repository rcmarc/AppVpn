import { Injectable } from '@angular/core';
import { AuthTokens } from './auth/auth.token.interface';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  tokens: AuthTokens;
  constructor() {
  }

  getJwt(): string {
    return this.tokens ? this.tokens.jwt : undefined;
  }

  getRefreshToken(): string {
    return this.tokens ? this.tokens.refresh : undefined;
  }

  setTokens(jwt: string, refresh: string):void {
    this.tokens ? (this.tokens.jwt = jwt, this.tokens.refresh = refresh)  : this.tokens = {jwt, refresh};
  }

  
}
