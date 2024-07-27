import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  private _manage = false;

  constructor(private router: Router) {}

  ManageHospitals() {
    this.router.navigate(['hospitals'])
  }

  searchHospital(): void {
    this.router.navigate(['searchHospital'], { queryParams: { active: 'searchHospital' } });
  }

  get manage(): boolean {
    return this._manage;
  }

  set manage(value: boolean) {
    this.manage = value;
  }
}
