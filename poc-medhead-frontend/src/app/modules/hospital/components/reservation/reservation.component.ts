import { Component } from '@angular/core';
import { PageResponseReservationResponse } from '../../../../services/reservation_service/models';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReservationControllerService } from '../../../../services/reservation_service/services';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrl: './reservation.component.scss',
})
export class ReservationComponent {
  reservationResponse: PageResponseReservationResponse = {};
  page = 0;
  size = 5;

  constructor(
    private router: Router,
    private ReservationService: ReservationControllerService,
    private toaster: ToastrService
  ) {}

  ngOnInit(): void {
    this.findAllReservations();
  }

  findAllReservations() {
    this.ReservationService.findAllReservations({
      page: this.page,
      size: this.size,
    }).subscribe({
      next: (reservations) => {
        this.reservationResponse = reservations;
      },
      error: (err) => {
        this.toaster.error(err, 'Error fetching reservations');
      },
    });
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllReservations();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllReservations();
  }

  goToPage(page: number) {
    this.page = page;
    this.findAllReservations();
  }

  goToNextPage() {
    this.page++;
    this.findAllReservations();
  }
  goToLastPage() {
    this.page = (this.reservationResponse.totalPages as number) - 1;
    this.findAllReservations();
  }

  get isLastPage(): boolean {
    return this.page == (this.reservationResponse.totalPages as number) - 1;
  }
}
