import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NotAuthenticatedErrorComponent } from './not-authenticated-error/not-authenticated-error.component';
import { ErrorComponent } from './error/error.component';
import { BadCredentialsErrorComponent } from './bad-credentials-error/bad-credentials-error.component';

//MATERIAL
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [
    NotAuthenticatedErrorComponent,
    ErrorComponent,
    BadCredentialsErrorComponent,
  ],
  imports: [
    CommonModule,
    //MATERIAL
    MatCardModule,
  ],
  exports: [NotAuthenticatedErrorComponent, BadCredentialsErrorComponent],
})
export class ErrorModule {}
