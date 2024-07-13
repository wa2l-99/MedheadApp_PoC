/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { HospitalResponse } from '../../models/hospital-response';

export interface UpdateNbBeds$Params {
  'hospital-id': number;
  beds: number;
}

export function updateNbBeds(http: HttpClient, rootUrl: string, params: UpdateNbBeds$Params, context?: HttpContext): Observable<StrictHttpResponse<HospitalResponse>> {
  const rb = new RequestBuilder(rootUrl, updateNbBeds.PATH, 'put');
  if (params) {
    rb.path('hospital-id', params['hospital-id'], {});
    rb.query('beds', params.beds, {});
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

updateNbBeds.PATH = '/api/hospital/updateBeds/{hospital-id}';
