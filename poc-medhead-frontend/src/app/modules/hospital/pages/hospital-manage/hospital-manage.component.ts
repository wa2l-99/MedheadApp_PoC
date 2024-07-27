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
  size = 10;

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


}
