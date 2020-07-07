import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  {path: '', redirectTo: 'app', pathMatch: 'full'},
  {
    path: 'app',
    canActivate: [AuthGuard],
    loadChildren: () => import('./vpn/vpn.module').then(m => m.VpnModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
