/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseReservationResponse } from '../../models/page-response-reservation-response';

export interface FindAllReservations$Params {
  page?: number;
  size?: number;
}

export function findAllReservations(http: HttpClient, rootUrl: string, params?: FindAllReservations$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseReservationResponse>> {
  const rb = new RequestBuilder(rootUrl, findAllReservations.PATH, 'get');
  if (params) {
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseReservationResponse>;
    })
  );
}

findAllReservations.PATH = '/api/reservations';
