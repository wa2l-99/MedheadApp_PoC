import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PageResponseHospitalResponse } from '../../../../services/hospital_service/models';
import { HospitalControllerService } from '../../../../services/hospital_service/services';

@Component({
  selector: 'app-hospital-manage',
  templateUrl: './hospital-manage.component.html',
  styleUrl: './hospital-manage.component.scss',
})
export class HospitalManageComponent implements OnInit {
  addHospital() {
    throw new Error('Method not implemented.');
  }
  hospitalResponse: PageResponseHospitalResponse = {};
  page = 0;
  size = 5;

  constructor(
    private router: Router,
    private hospitalService: HospitalControllerService,
    private toaster: ToastrService
  ) {}

  ngOnInit(): void {
    this.findAllHospitals();
  }

  findAllHospitals() {
    this.hospitalService
      .findAllHospitals({
        page: this.page,
        size: this.size,
      })
      .subscribe({
        next: (hospitals) => {
          this.hospitalResponse = hospitals;
        },
        error: (err) => {
          this.toaster.error(err, 'Error fetching hospitals');
        },
      });
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllHospitals();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllHospitals();
  }

  goToPage(page: number) {
    this.page = page;
    this.findAllHospitals();
  }

  goToNextPage() {
    this.page++;
    this.findAllHospitals();
  }
  goToLastPage() {
    this.page = (this.hospitalResponse.totalPages as number) - 1;
    this.findAllHospitals();
  }

  get isLastPage(): boolean {
    return this.page == (this.hospitalResponse.totalPages as number) - 1;
  }
}
