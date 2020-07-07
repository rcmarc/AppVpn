import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Device } from './models/device.model';
import { from } from 'rxjs';
import { Vpn } from './models/vpn.model';

@Injectable({
  providedIn: 'root'
})
export class FakeService {

  constructor() { }

  DummyDevices(): Observable<Device[]> {
    const devices: Device[]= [
      {id: 1, name: 'fake', description: 'description'},
      {id: 2, name: 'fake2', description: 'description2'}
    ];
    return from([devices]);
  }

  DummyVpn(): Observable<Vpn[]> {
    const vpns: Vpn[] = [
      {id: 1, createdDate: '2020-01-23', ipSrc: '213.43.12.4', ipDst: '321,4534.543'},
      {id: 1, createdDate: '2020-01-23', ipSrc: '213.43.12.4', ipDst: '321,4534.543'},
      {id: 1, createdDate: '2020-01-23', ipSrc: '213.43.12.4', ipDst: '321,4534.543'},
      {id: 1, createdDate: '2020-01-23', ipSrc: '213.43.12.4', ipDst: '321,4534.543'},
      {id: 1, createdDate: '2020-01-23', ipSrc: '213.43.12.4', ipDst: '321,4534.543'},
      {id: 1, createdDate: '2020-01-23', ipSrc: '213.43.12.4', ipDst: '321,4534.543'},
      {id: 1, createdDate: '2020-01-23', ipSrc: '213.43.12.4', ipDst: '321,4534.543'},
      {id: 1, createdDate: '2020-01-23', ipSrc: '213.43.12.4', ipDst: '321,4534.543'}
    ];
    return from([vpns]);
  }
}
