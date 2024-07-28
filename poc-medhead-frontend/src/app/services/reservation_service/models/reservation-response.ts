/* eslint-disable */
import { HospitalResponse } from '../models/hospital-response';
import { PatientResponse } from '../models/patient-response';
export interface ReservationResponse {
  hospital?: HospitalResponse;
  id?: number;
  patient?: PatientResponse;
  reference?: string;
}
