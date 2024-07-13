/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { HospitalResponse } from '../../models/hospital-response';

export interface FindHospitalById$Params {
  'hospital-id': number;
}

export function findHospitalById(http: HttpClient, rootUrl: string, params: FindHospitalById$Params, context?: HttpContext): Observable<StrictHttpResponse<HospitalResponse>> {
  const rb = new RequestBuilder(rootUrl, findHospitalById.PATH, 'get');
  if (params) {
    rb.path('hospital-id', params['hospital-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<HospitalResponse>;
    })
  );
}

findHospitalById.PATH = '/api/hospital/{hospital-id}';
