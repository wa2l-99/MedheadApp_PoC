import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HospitalControllerService } from '../../../../services/hospital_service/services';
import {
  HospitalResponse,
  PageResponseSpecialityResponse,
  SpecialityResponse,
} from '../../../../services/hospital_service/models';
import { ToastrService } from 'ngx-toastr';
import { ReservationControllerService } from '../../../../services/reservation_service/services';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.scss',
})
export class SearchComponent implements OnInit {
  specialityResponse: PageResponseSpecialityResponse = {};
  filteredSpecialities: SpecialityResponse[] = [];
  selectedSpeciality!: SpecialityResponse;
  hospitalResponse!: HospitalResponse;
  address = '';
  hospitalResponses: HospitalResponse[] = [];
  searchPerformed = false;

  errorMsg: [] = [];

  constructor(
    private router: Router,
    private hospitalService: HospitalControllerService,
    private toastr: ToastrService,
    private reservationService: ReservationControllerService
  ) {}

  ngOnInit(): void {
    this.findAllSpeciality();
  }

  private findAllSpeciality() {
    this.hospitalService.findAllSpecialities().subscribe({
      next: (specialities) => {
        this.specialityResponse = specialities;
        this.filteredSpecialities = this.specialityResponse.content || [];
      },
      error: (err) => {
        this.toastr.error(err, 'Error fetching specialities');
      },
    });
  }

  searchNearest() {
    this.searchPerformed = true;
    if (
      this.selectedSpeciality &&
      this.selectedSpeciality.nom &&
      this.address
    ) {
      this.hospitalResponses = [];
      this.hospitalService
        .findNearestHospitals({
          address: this.address,
          specialty: this.selectedSpeciality.nom,
        })
        .subscribe({
          next: (nearestHospitals) => {
            this.hospitalResponses = nearestHospitals;
          },
          error: (err) => {
            if (err) {
              this.toastr.error(err.error, 'Erreur');
            }
          },
        });
    }
  }

  onSearch(event: { term: string; items: SpecialityResponse[] }): void {
    const term = event.term;
    if (this.specialityResponse.content) {
      if (term) {
        this.filteredSpecialities = this.specialityResponse.content.filter(
          (speciality) =>
            speciality.nom?.toLowerCase().includes(term.toLowerCase())
        );
      } else {
        this.filteredSpecialities = this.specialityResponse.content;
      }
    }
  }
  reserver() {
    throw new Error('Method not implemented.');
  }
}
