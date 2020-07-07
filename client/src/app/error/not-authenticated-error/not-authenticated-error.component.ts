import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-not-authenticated-error',
  templateUrl: './not-authenticated-error.component.html',
})
export class NotAuthenticatedErrorComponent implements OnInit {
  title = "Sin Autenticación";
  message = "Usted no se encuentra autenticado en el sistema"
  constructor() { }

  ngOnInit(): void {
  }

}
