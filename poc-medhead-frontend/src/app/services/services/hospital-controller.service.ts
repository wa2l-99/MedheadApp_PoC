/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { addSpecialityToHospital } from '../fn/hospital-controller/add-speciality-to-hospital';
import { AddSpecialityToHospital$Params } from '../fn/hospital-controller/add-speciality-to-hospital';
import { createHospital } from '../fn/hospital-controller/create-hospital';
import { CreateHospital$Params } from '../fn/hospital-controller/create-hospital';
import { createSpecialty } from '../fn/hospital-controller/create-specialty';
import { CreateSpecialty$Params } from '../fn/hospital-controller/create-specialty';
import { deleteHospital } from '../fn/hospital-controller/delete-hospital';
import { DeleteHospital$Params } from '../fn/hospital-controller/delete-hospital';
import { findAllHospitals } from '../fn/hospital-controller/find-all-hospitals';
import { FindAllHospitals$Params } from '../fn/hospital-controller/find-all-hospitals';
import { findAllHospitalsBySpecialty } from '../fn/hospital-controller/find-all-hospitals-by-specialty';
import { FindAllHospitalsBySpecialty$Params } from '../fn/hospital-controller/find-all-hospitals-by-specialty';
import { findAllSpecialities } from '../fn/hospital-controller/find-all-specialities';
import { FindAllSpecialities$Params } from '../fn/hospital-controller/find-all-specialities';
import { findHospitalById } from '../fn/hospital-controller/find-hospital-by-id';
import { FindHospitalById$Params } from '../fn/hospital-controller/find-hospital-by-id';
import { findNearestHospital } from '../fn/hospital-controller/find-nearest-hospital';
import { FindNearestHospital$Params } from '../fn/hospital-controller/find-nearest-hospital';
import { HospitalResponse } from '../models/hospital-response';
import { PageResponseHospitalResponse } from '../models/page-response-hospital-response';
import { PageResponseSpecialityResponse } from '../models/page-response-speciality-response';
import { updateHospital } from '../fn/hospital-controller/update-hospital';
import { UpdateHospital$Params } from '../fn/hospital-controller/update-hospital';
import { updateNbBeds } from '../fn/hospital-controller/update-nb-beds';
import { UpdateNbBeds$Params } from '../fn/hospital-controller/update-nb-beds';

@Injectable({ providedIn: 'root' })
export class HospitalControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `findHospitalById()` */
  static readonly FindHospitalByIdPath = '/api/hospital/{hospital-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findHospitalById()` instead.
   *
   * This method doesn't expect any request body.
   */
  findHospitalById$Response(params: FindHospitalById$Params, context?: HttpContext): Observable<StrictHttpResponse<HospitalResponse>> {
    return findHospitalById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findHospitalById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findHospitalById(params: FindHospitalById$Params, context?: HttpContext): Observable<HospitalResponse> {
    return this.findHospitalById$Response(params, context).pipe(
      map((r: StrictHttpResponse<HospitalResponse>): HospitalResponse => r.body)
    );
  }

  /** Path part for operation `updateHospital()` */
  static readonly UpdateHospitalPath = '/api/hospital/{hospital-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateHospital()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateHospital$Response(params: UpdateHospital$Params, context?: HttpContext): Observable<StrictHttpResponse<HospitalResponse>> {
    return updateHospital(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateHospital$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateHospital(params: UpdateHospital$Params, context?: HttpContext): Observable<HospitalResponse> {
    return this.updateHospital$Response(params, context).pipe(
      map((r: StrictHttpResponse<HospitalResponse>): HospitalResponse => r.body)
    );
  }

  /** Path part for operation `deleteHospital()` */
  static readonly DeleteHospitalPath = '/api/hospital/{hospital-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteHospital()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteHospital$Response(params: DeleteHospital$Params, context?: HttpContext): Observable<StrictHttpResponse<string>> {
    return deleteHospital(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteHospital$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteHospital(params: DeleteHospital$Params, context?: HttpContext): Observable<string> {
    return this.deleteHospital$Response(params, context).pipe(
      map((r: StrictHttpResponse<string>): string => r.body)
    );
  }

  /** Path part for operation `updateNbBeds()` */
  static readonly UpdateNbBedsPath = '/api/hospital/updateBeds/{hospital-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateNbBeds()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateNbBeds$Response(params: UpdateNbBeds$Params, context?: HttpContext): Observable<StrictHttpResponse<HospitalResponse>> {
    return updateNbBeds(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateNbBeds$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateNbBeds(params: UpdateNbBeds$Params, context?: HttpContext): Observable<HospitalResponse> {
    return this.updateNbBeds$Response(params, context).pipe(
      map((r: StrictHttpResponse<HospitalResponse>): HospitalResponse => r.body)
    );
  }

  /** Path part for operation `createSpecialty()` */
  static readonly CreateSpecialtyPath = '/api/hospital/speciality';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createSpecialty()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createSpecialty$Response(params: CreateSpecialty$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createSpecialty(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createSpecialty$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createSpecialty(params: CreateSpecialty$Params, context?: HttpContext): Observable<number> {
    return this.createSpecialty$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `addSpecialityToHospital()` */
  static readonly AddSpecialityToHospitalPath = '/api/hospital/addSpecialityToHospital';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addSpecialityToHospital()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addSpecialityToHospital$Response(params: AddSpecialityToHospital$Params, context?: HttpContext): Observable<StrictHttpResponse<HospitalResponse>> {
    return addSpecialityToHospital(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addSpecialityToHospital$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addSpecialityToHospital(params: AddSpecialityToHospital$Params, context?: HttpContext): Observable<HospitalResponse> {
    return this.addSpecialityToHospital$Response(params, context).pipe(
      map((r: StrictHttpResponse<HospitalResponse>): HospitalResponse => r.body)
    );
  }

  /** Path part for operation `createHospital()` */
  static readonly CreateHospitalPath = '/api/hospital/addHospital';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createHospital()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createHospital$Response(params: CreateHospital$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createHospital(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createHospital$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createHospital(params: CreateHospital$Params, context?: HttpContext): Observable<number> {
    return this.createHospital$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findAllHospitals()` */
  static readonly FindAllHospitalsPath = '/api/hospital';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllHospitals()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllHospitals$Response(params?: FindAllHospitals$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseHospitalResponse>> {
    return findAllHospitals(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllHospitals$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllHospitals(params?: FindAllHospitals$Params, context?: HttpContext): Observable<PageResponseHospitalResponse> {
    return this.findAllHospitals$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseHospitalResponse>): PageResponseHospitalResponse => r.body)
    );
  }

  /** Path part for operation `findAllHospitalsBySpecialty()` */
  static readonly FindAllHospitalsBySpecialtyPath = '/api/hospital/speciality/id={speciality-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllHospitalsBySpecialty()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllHospitalsBySpecialty$Response(params: FindAllHospitalsBySpecialty$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseHospitalResponse>> {
    return findAllHospitalsBySpecialty(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllHospitalsBySpecialty$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllHospitalsBySpecialty(params: FindAllHospitalsBySpecialty$Params, context?: HttpContext): Observable<PageResponseHospitalResponse> {
    return this.findAllHospitalsBySpecialty$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseHospitalResponse>): PageResponseHospitalResponse => r.body)
    );
  }

  /** Path part for operation `findAllSpecialities()` */
  static readonly FindAllSpecialitiesPath = '/api/hospital/specialities';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllSpecialities()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllSpecialities$Response(params?: FindAllSpecialities$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseSpecialityResponse>> {
    return findAllSpecialities(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllSpecialities$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllSpecialities(params?: FindAllSpecialities$Params, context?: HttpContext): Observable<PageResponseSpecialityResponse> {
    return this.findAllSpecialities$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseSpecialityResponse>): PageResponseSpecialityResponse => r.body)
    );
  }

  /** Path part for operation `findNearestHospital()` */
  static readonly FindNearestHospitalPath = '/api/hospital/nearest';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findNearestHospital()` instead.
   *
   * This method doesn't expect any request body.
   */
  findNearestHospital$Response(params: FindNearestHospital$Params, context?: HttpContext): Observable<StrictHttpResponse<HospitalResponse>> {
    return findNearestHospital(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findNearestHospital$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findNearestHospital(params: FindNearestHospital$Params, context?: HttpContext): Observable<HospitalResponse> {
    return this.findNearestHospital$Response(params, context).pipe(
      map((r: StrictHttpResponse<HospitalResponse>): HospitalResponse => r.body)
    );
  }

}
