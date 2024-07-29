import { Component, EventEmitter, Input, Output } from '@angular/core';
import { HospitalResponse } from '../../../../services/hospital_service/models';

@Component({
  selector: 'app-hospital-card',
  templateUrl: './hospital-card.component.html',
  styleUrl: './hospital-card.component.scss',
})
export class HospitalCardComponent {
  private _hospital: HospitalResponse = {};

  @Input()
  get hospital(): HospitalResponse {
    return this._hospital;
  }

  set hospital(value: HospitalResponse) {
    this._hospital = value;
  }

  @Output() private updateHospital: EventEmitter<HospitalResponse> =
    new EventEmitter<HospitalResponse>();
  @Output() private updateBeds: EventEmitter<HospitalResponse> =
    new EventEmitter<HospitalResponse>();
  @Output() private addSpecialityToHospital: EventEmitter<HospitalResponse> =
    new EventEmitter<HospitalResponse>();
  @Output() private deleteHospital: EventEmitter<HospitalResponse> =
    new EventEmitter<HospitalResponse>();

  onModifyHospital() {
    this.updateHospital.emit(this._hospital);
  }
  onModifyNbLits() {
    this.updateBeds.emit(this._hospital);
  }
  onAddSpeciality() {
    this.addSpecialityToHospital.emit(this._hospital);
  }
  onDeleteHospital() {
    this.deleteHospital.emit(this._hospital);
  }
}
