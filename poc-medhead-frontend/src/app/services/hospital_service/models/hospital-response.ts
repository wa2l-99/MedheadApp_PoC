/* eslint-disable */
import { MedicalSpeciality } from '../models/medical-speciality';
export interface HospitalResponse {
  adresse?: string;
  codePostal?: string;
  distance?: number;
  id?: number;
  latitude?: number;
  litsDisponible?: number;
  longitude?: number;
  nomOrganisation?: string;
  specialitesMedicales?: Array<MedicalSpeciality>;
}
