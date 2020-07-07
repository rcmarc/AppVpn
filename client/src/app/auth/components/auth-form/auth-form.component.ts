import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl,
} from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth-form',
  templateUrl: './auth-form.component.html',
  styleUrls: ['./auth-form.component.css'],
})
export class AuthFormComponent implements OnInit {
  @Input() newUser: boolean;
  @Input() serverError: string;
  @Output() Submit = new EventEmitter<FormGroup>();
  constructor(private formBuilder: FormBuilder, private router: Router) {}
  authForm: FormGroup;
  formName: string;

  ngOnInit(): void {
    if (this.newUser) {
      this.formName = 'Sign Up';
      this.authForm = this.formBuilder.group({
        username: ['', Validators.required],
        email: ['', [Validators.email, Validators.required]],
        password: ['', [Validators.minLength(5), Validators.required]],
      });
    } else {
      this.formName = 'Sign In';
      this.authForm = this.formBuilder.group({
        username: ['', [Validators.required]],
        password: ['', [Validators.minLength(5), Validators.required]],
      });
    }
  }

  onSave(): void {
    if (this.authForm.valid) this.Submit.emit(this.authForm);
  }

  click() {
    if (this.newUser) {
      this.router.navigateByUrl('auth/login');
    } else {
      this.router.navigateByUrl('auth/signup');
    }
  }

  getUsernameError(): string {
    const fc = this.authForm.get('username');
    if (fc.hasError('required')) return 'El nombre de usuario es requerido';
  }

  getEmailError(): string {
    const fc = this.authForm.get('email');
    if (fc.hasError('required')) return 'El correo es requerido';
    else if(fc.hasError('email')) return 'Entre una dirección válida';
  }

  getPasswordError():string{
    const fc = this.authForm.get('password');
    if (fc.hasError('required')) return 'La contraseña es requerida';
    else if(fc.hasError('minlength')) return 'La contraseña debe tener más de 5 caracteres';
  }
}
