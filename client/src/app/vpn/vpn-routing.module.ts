import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VpnComponent } from './vpn.component';
import { InterfacesComponent } from './components/interfaces/interfaces.component';
import { MainComponent } from './components/main/main.component';
import { UsersComponent } from './components/users/users.component';

const routes: Routes = [{ path: '', component: VpnComponent, children: [
    {path: 'ifs', component: InterfacesComponent },
    {path: 'main', component: MainComponent},
    {path: 'users', component: UsersComponent}
] }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class VpnRoutingModule {}
