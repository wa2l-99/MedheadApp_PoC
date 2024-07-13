/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AddSpecialityToHospitalRequest } from '../../models/add-speciality-to-hospital-request';
import { HospitalResponse } from '../../models/hospital-response';

export interface AddSpecialityToHospital$Params {
      body: AddSpecialityToHospitalRequest
}

export function addSpecialityToHospital(http: HttpClient, rootUrl: string, params: AddSpecialityToHospital$Params, context?: HttpContext): Observable<StrictHttpResponse<HospitalResponse>> {
  const rb = new RequestBuilder(rootUrl, addSpecialityToHospital.PATH, 'post');
  if (params) {
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

addSpecialityToHospital.PATH = '/api/hospital/addSpecialityToHospital';
