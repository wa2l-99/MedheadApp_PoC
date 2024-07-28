/* eslint-disable */
import { UserResponse } from '../models/user-response';
export interface AuthenticationResponse {
  token?: string;
  user?: UserResponse;
}
