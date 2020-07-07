import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

//* Material
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';

import { VpnComponent } from './vpn.component';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { InterfacesComponent } from './components/interfaces/interfaces.component';
import { VpnRoutingModule } from './vpn-routing.module';
import { MainComponent } from './components/main/main.component';
import { UsersComponent } from './components/users/users.component';
import { SpinnersModule } from '../spinners/spinners.module';
import { AuthModule } from '../auth/auth.module';
import { ErrorModule } from '../error/error.module';


@NgModule({
  declarations: [VpnComponent, SidenavComponent, InterfacesComponent, MainComponent, UsersComponent],
  imports: [
    //ANGULAR
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    //MATERIAL
    MatSidenavModule,
    MatButtonModule,
    SpinnersModule,
    MatDividerModule,
    MatListModule,
    MatTableModule,
    MatPaginatorModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatNativeDateModule,
    MatIconModule,
    //PROYECTO
    VpnRoutingModule,
    AuthModule,
    ErrorModule
  ],
  providers: [MatNativeDateModule]
})
export class VpnModule { }
