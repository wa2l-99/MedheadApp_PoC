/* eslint-disable */
import { HospitalResponse } from '../models/hospital-response';
export interface PageResponseHospitalResponse {
  content?: Array<HospitalResponse>;
  first?: boolean;
  last?: boolean;
  number?: number;
  size?: number;
  totalElements?: number;
  totalPages?: number;
}
