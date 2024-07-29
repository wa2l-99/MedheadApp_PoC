import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { StorageUserServiceService } from '../../../../services/authentication_service/storageUser/storage-user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  constructor(
    private router: Router,
    private storageUserService: StorageUserServiceService
  ) {}

  ManageHospitals() {
    this.router.navigate(['hospitals']);
  }

  searchHospital(): void {
    this.router.navigate(['searchHospital'], {
      queryParams: { active: 'searchHospital' },
    });
  }

  isAdmin(): boolean {
    return this.storageUserService.hasRole('Admin');
  }

  isPatient(): boolean {
    return this.storageUserService.hasRole('Patient');
  }
}
