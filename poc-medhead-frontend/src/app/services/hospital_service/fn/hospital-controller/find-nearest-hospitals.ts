/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { HospitalResponse } from '../../models/hospital-response';

export interface FindNearestHospitals$Params {
  address: string;
  specialty: string;
}

export function findNearestHospitals(http: HttpClient, rootUrl: string, params: FindNearestHospitals$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<HospitalResponse>>> {
  const rb = new RequestBuilder(rootUrl, findNearestHospitals.PATH, 'get');
  if (params) {
    rb.query('address', params.address, {});
    rb.query('specialty', params.specialty, {});
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<HospitalResponse>>;
    })
  );
}

findNearestHospitals.PATH = '/api/hospital/nearest';
