/* eslint-disable */
import { ReservationResponse } from '../models/reservation-response';
export interface PageResponseReservationResponse {
  content?: Array<ReservationResponse>;
  first?: boolean;
  last?: boolean;
  number?: number;
  size?: number;
  totalElements?: number;
  totalPages?: number;
}
