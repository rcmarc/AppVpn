import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bad-credentials-error',
  templateUrl: './bad-credentials-error.component.html',
})
export class BadCredentialsErrorComponent implements OnInit {
  title = 'Credenciales incorrectas';
  message = 'El nombre de usuario o la contraseña son incorrectos'
  constructor() { }

  ngOnInit(): void {
  }

}
