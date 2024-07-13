/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { createReservation } from '../fn/reservation-controller/create-reservation';
import { CreateReservation$Params } from '../fn/reservation-controller/create-reservation';
import { deleteGetReservation } from '../fn/reservation-controller/delete-get-reservation';
import { DeleteGetReservation$Params } from '../fn/reservation-controller/delete-get-reservation';
import { findAllReservations } from '../fn/reservation-controller/find-all-reservations';
import { FindAllReservations$Params } from '../fn/reservation-controller/find-all-reservations';
import { findReservationById } from '../fn/reservation-controller/find-reservation-by-id';
import { FindReservationById$Params } from '../fn/reservation-controller/find-reservation-by-id';
import { PageResponseReservationResponse } from '../models/page-response-reservation-response';
import { ReservationResponse } from '../models/reservation-response';

@Injectable({ providedIn: 'root' })
export class ReservationControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `createReservation()` */
  static readonly CreateReservationPath = '/api/reservations/addReservation';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createReservation()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createReservation$Response(params: CreateReservation$Params, context?: HttpContext): Observable<StrictHttpResponse<ReservationResponse>> {
    return createReservation(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createReservation$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createReservation(params: CreateReservation$Params, context?: HttpContext): Observable<ReservationResponse> {
    return this.createReservation$Response(params, context).pipe(
      map((r: StrictHttpResponse<ReservationResponse>): ReservationResponse => r.body)
    );
  }

  /** Path part for operation `findAllReservations()` */
  static readonly FindAllReservationsPath = '/api/reservations';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllReservations()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllReservations$Response(params?: FindAllReservations$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseReservationResponse>> {
    return findAllReservations(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllReservations$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllReservations(params?: FindAllReservations$Params, context?: HttpContext): Observable<PageResponseReservationResponse> {
    return this.findAllReservations$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseReservationResponse>): PageResponseReservationResponse => r.body)
    );
  }

  /** Path part for operation `findReservationById()` */
  static readonly FindReservationByIdPath = '/api/reservations/{reservation-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findReservationById()` instead.
   *
   * This method doesn't expect any request body.
   */
  findReservationById$Response(params: FindReservationById$Params, context?: HttpContext): Observable<StrictHttpResponse<ReservationResponse>> {
    return findReservationById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findReservationById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findReservationById(params: FindReservationById$Params, context?: HttpContext): Observable<ReservationResponse> {
    return this.findReservationById$Response(params, context).pipe(
      map((r: StrictHttpResponse<ReservationResponse>): ReservationResponse => r.body)
    );
  }

  /** Path part for operation `deleteGetReservation()` */
  static readonly DeleteGetReservationPath = '/api/reservations/{reservation-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteGetReservation()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteGetReservation$Response(params: DeleteGetReservation$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return deleteGetReservation(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteGetReservation$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteGetReservation(params: DeleteGetReservation$Params, context?: HttpContext): Observable<void> {
    return this.deleteGetReservation$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

}
