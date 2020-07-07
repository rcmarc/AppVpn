import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs/operators';

import { SpringService } from 'src/app/spring.service';
import { TokenService } from 'src/app/token.service';
import { AuthTokens } from '../../auth.token.interface';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
  loading = false;
  error: string;
  constructor(
    private spring: SpringService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  onSubmit(data: FormGroup) {
    this.loading = true;
    this.spring
      .Login(data.get('username').value, data.get('password').value)
      .pipe(finalize(() => (this.loading = false)))
      .subscribe(
        (tokens) => this.handleSuccess(tokens),
        (err) => this.handleError(err)
      );
  }

  handleSuccess(tokens: AuthTokens) {
    this.tokenService.setTokens(tokens.jwt, tokens.refresh);
    this.router.navigateByUrl('app/main');
  }

  handleError(err: any) {
    if (err instanceof HttpErrorResponse) {
      if (err.status === 403) {
        this.error = 'El nombre de usuario o contraseña son incorrectos';
        return;
      }
    }
    this.error = 'error de red';
  }
}
