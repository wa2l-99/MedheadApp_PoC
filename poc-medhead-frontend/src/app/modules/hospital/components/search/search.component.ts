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
import {
  ReservationRequest,
  ReservationResponse,
} from '../../../../services/reservation_service/models';
import { StorageUserServiceService } from '../../../../services/authentication_service/storageUser/storage-user.service';
import { ReservationModalComponent } from '../reservation-modal/reservation-modal.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

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
  reservationRequest: ReservationRequest = {
    patientId: 0,
    hospitalId: 0,
  };
  errorMsg: [] = [];

  constructor(
    private router: Router,
    private hospitalService: HospitalControllerService,
    private toastr: ToastrService,
    private reservationService: ReservationControllerService,
    private storageUserService: StorageUserServiceService,
    private modalService: NgbModal // Injection de NgbModal
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
  reserver(hospitalId?: number) {
    const patientId = this.storageUserService.getSavedUser()?.id;
    if (patientId !== undefined && hospitalId !== undefined) {
      this.reservationService
        .createReservation({
          body: {
            patientId,
            hospitalId,
          },
        })
        .subscribe({
          next: (response: ReservationResponse) => {
            this.toastr.success('Réservation effectuée avec succès');
            this.openReservationModal(response); // Ouvrir le modal avec la réponse de réservation
          },
          error: (err) => {
            if (err) {
              this.toastr.error(err.error, 'Erreur');
            }
          },
        });
    } else {
      this.toastr.error('Patient ID manquant', 'Erreur');
    }
  }
  openReservationModal(reservation: ReservationResponse) {
    const modalRef = this.modalService.open(ReservationModalComponent);
    modalRef.componentInstance.reservation = reservation; // Passez la réponse de réservation au composant modal
  }
}
