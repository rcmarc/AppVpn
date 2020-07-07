import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';

// material
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDividerModule } from '@angular/material/divider';

import { AuthFormComponent } from './components/auth-form/auth-form.component';
import { LoginComponent } from './containers/login/login.component';
import { SignupComponent } from './containers/signup/signup.component';
import { ErrorModule } from '../error/error.module';
import { SpinnersModule } from '../spinners/spinners.module';
import { RegisteredComponent } from './components/registered/registered.component';

const routes: Routes = [
  {
    path: 'auth',
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
      { path: 'signup', component: SignupComponent},
      { path: 'registered', component: RegisteredComponent}
    ],
  },
];

@NgModule({
  declarations: [AuthFormComponent, LoginComponent, SignupComponent, RegisteredComponent],
  imports: [
    CommonModule,
    MatCardModule,
    RouterModule.forChild(routes),
    MatButtonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDividerModule,
    ErrorModule,
    SpinnersModule
  ],
  exports: [SignupComponent]
})
export class AuthModule {}
