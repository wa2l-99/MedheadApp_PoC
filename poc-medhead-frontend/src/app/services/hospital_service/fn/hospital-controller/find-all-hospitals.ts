/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseHospitalResponse } from '../../models/page-response-hospital-response';

export interface FindAllHospitals$Params {
  page?: number;
  size?: number;
}

export function findAllHospitals(http: HttpClient, rootUrl: string, params?: FindAllHospitals$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseHospitalResponse>> {
  const rb = new RequestBuilder(rootUrl, findAllHospitals.PATH, 'get');
  if (params) {
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseHospitalResponse>;
    })
  );
}

findAllHospitals.PATH = '/api/hospital';
