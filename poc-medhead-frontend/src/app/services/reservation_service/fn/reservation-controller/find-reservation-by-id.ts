/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ReservationResponse } from '../../models/reservation-response';

export interface FindReservationById$Params {
  'reservation-id': number;
}

export function findReservationById(http: HttpClient, rootUrl: string, params: FindReservationById$Params, context?: HttpContext): Observable<StrictHttpResponse<ReservationResponse>> {
  const rb = new RequestBuilder(rootUrl, findReservationById.PATH, 'get');
  if (params) {
    rb.path('reservation-id', params['reservation-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ReservationResponse>;
    })
  );
}

findReservationById.PATH = '/api/reservations/{reservation-id}';
