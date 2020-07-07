import { Component, OnInit } from '@angular/core';
import { Error } from '../../error.interface';
import { FormGroup } from '@angular/forms';
import { SpringService } from 'src/app/spring.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
})
export class SignupComponent implements OnInit {
  error: string;
  constructor(private spring: SpringService, private router: Router) {}

  ngOnInit(): void {}

  SignUp(data: FormGroup) {
    this.spring
      .Register(
        data.get('username').value,
        data.get('email').value,
        data.get('password').value
      )
      .subscribe(() => this.router.navigateByUrl('auth/registered'));
  }
}
