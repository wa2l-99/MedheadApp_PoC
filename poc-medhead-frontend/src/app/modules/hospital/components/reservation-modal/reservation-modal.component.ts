import { Component, Input } from '@angular/core';
import { ReservationResponse } from '../../../../services/reservation_service/models';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-reservation-modal',
  templateUrl: './reservation-modal.component.html',
  styleUrl: './reservation-modal.component.scss',
})
export class ReservationModalComponent {
  @Input()
  reservation!: ReservationResponse;

  constructor(public activeModal: NgbActiveModal) {}
}
