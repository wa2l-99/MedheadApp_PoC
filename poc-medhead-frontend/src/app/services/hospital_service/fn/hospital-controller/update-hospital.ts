/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { HospitalRequest } from '../../models/hospital-request';
import { HospitalResponse } from '../../models/hospital-response';

export interface UpdateHospital$Params {
  'hospital-id': number;
      body: HospitalRequest
}

export function updateHospital(http: HttpClient, rootUrl: string, params: UpdateHospital$Params, context?: HttpContext): Observable<StrictHttpResponse<HospitalResponse>> {
  const rb = new RequestBuilder(rootUrl, updateHospital.PATH, 'put');
  if (params) {
    rb.path('hospital-id', params['hospital-id'], {});
    rb.body(params.body, 'application/json');
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

updateHospital.PATH = '/api/hospital/{hospital-id}';
