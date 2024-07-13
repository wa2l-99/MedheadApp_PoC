/* tslint:disable */
/* eslint-disable */
export interface HospitalRequest {
  adresse: string;
  codePostal: string;
  createdDate?: string;
  id?: number;
  latitude: number;
  litsDisponible: number;
  longitude: number;
  nomOrganisation: string;
  specialiteIds: Array<number>;
}
