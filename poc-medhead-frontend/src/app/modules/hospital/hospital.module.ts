import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HospitalRoutingModule } from './hospital-routing.module';
import { MainComponent } from './pages/main/main.component';
import { MenuComponent } from './components/menu/menu.component';
import { HomeComponent } from './components/home/home.component';
import { SearchComponent } from './components/search/search.component';
import { FormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { BlobToJsonInterceptor } from '../../interceptors/blob-to-json.interceptor';
import { ReservationComponent } from './components/reservation/reservation.component';

@NgModule({
  declarations: [MainComponent, MenuComponent, HomeComponent, SearchComponent, ReservationComponent],
  imports: [CommonModule, FormsModule, HospitalRoutingModule, NgSelectModule],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: BlobToJsonInterceptor,
      multi: true,
    },
    HttpClient,
  ],
})
export class HospitalModule {}
