import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { FormControl } from '@angular/forms';
import { retry, catchError } from 'rxjs/operators';

import { Vpn } from '../../../models/vpn.model';
import { SpringService } from '../../../spring.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthTokens } from 'src/app/auth/auth.token.interface';
import { throwError } from 'rxjs';
import { TokenService } from 'src/app/token.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent implements OnInit {
  displayed = ['id', 'createdDate', 'ipSrc', 'ipDst'];
  dataSource: MatTableDataSource<Vpn>;
  startDate = new FormControl(new Date());
  endDate = new FormControl(new Date());
  authenticationError = false;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  constructor(
    private spring: SpringService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this.getEvents();
    if (this.dataSource) this.dataSource.paginator = this.paginator;
  }

  getEvents() {
    this.spring
      .Events(this.startDate.value, this.endDate.value)
      .pipe(catchError((err) => this.handleError(err)))
      .subscribe(
        (vpns) => this.handleSuccess(vpns),
        () =>
          this.spring
            .RefreshToken()
            .subscribe((tokens) => this.handleTokens(tokens))
      );
    if (this.spring.IsAuthenticated()) {
      this.spring.Events(this.startDate.value, this.endDate.value).subscribe(
        (vpn) => this.handleSuccess(vpn),
        (err) => this.handleError(err)
      );
    } else this.authenticationError = true;
  }

  handleSuccess(vpns: Vpn[]) {
    this.dataSource = new MatTableDataSource(vpns);
    this.dataSource.paginator = this.paginator;
  }

  handleTokens(tokens: AuthTokens): void {
    this.tokenService.setTokens(tokens.jwt, tokens.refresh);
    this.getEvents();
  }

  handleError(err: Error) {
    if (err instanceof HttpErrorResponse) {
      if (err.status === 403) {
        if (this.spring.IsAuthenticated()) {
          return throwError(err);
        } else {
          this.authenticationError = true;
        }
      }
    }
  }
}
