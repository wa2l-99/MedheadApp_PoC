import { Component, Input } from '@angular/core';
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

  onModifyHospital() {
    throw new Error('Method not implemented.');
  }
  onModifyNbLits() {
    throw new Error('Method not implemented.');
  }
  onAddSpeciality() {
    throw new Error('Method not implemented.');
  }
  onDeleteHospital() {
    throw new Error('Method not implemented.');
  }
}
