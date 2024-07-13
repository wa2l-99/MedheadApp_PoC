/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { HospitalResponse } from '../../models/hospital-response';

export interface FindNearestHospital$Params {
  address: string;
  specialty: string;
}

export function findNearestHospital(http: HttpClient, rootUrl: string, params: FindNearestHospital$Params, context?: HttpContext): Observable<StrictHttpResponse<HospitalResponse>> {
  const rb = new RequestBuilder(rootUrl, findNearestHospital.PATH, 'get');
  if (params) {
    rb.query('address', params.address, {});
    rb.query('specialty', params.specialty, {});
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

findNearestHospital.PATH = '/api/hospital/nearest';
