/* eslint-disable */
import { MedicalSpeciality } from '../models/medical-speciality';
export interface Hospital {
  adresse?: string;
  codePostal?: string;
  createdDate?: string;
  id?: number;
  latitude?: number;
  litsDisponible?: number;
  longitude?: number;
  nomOrganisation?: string;
  specialitesMedicales?: Array<MedicalSpeciality>;
}
