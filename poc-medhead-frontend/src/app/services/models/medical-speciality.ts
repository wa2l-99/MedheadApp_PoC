/* tslint:disable */
/* eslint-disable */
import { Hospital } from '../models/hospital';
export interface MedicalSpeciality {
  hospitals?: Array<Hospital>;
  id?: number;
  nom?: string;
}
