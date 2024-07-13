/* tslint:disable */
/* eslint-disable */
import { SpecialityResponse } from '../models/speciality-response';
export interface PageResponseSpecialityResponse {
  content?: Array<SpecialityResponse>;
  first?: boolean;
  last?: boolean;
  number?: number;
  size?: number;
  totalElements?: number;
  totalPages?: number;
}
