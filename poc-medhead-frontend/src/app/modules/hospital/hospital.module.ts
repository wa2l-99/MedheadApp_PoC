import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HospitalRoutingModule } from './hospital-routing.module';
import { MainComponent } from './pages/main/main.component';
import { MenuComponent } from './components/menu/menu.component';
import { SearchComponent } from './components/search/search.component';
import { FormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { ReservationComponent } from './components/reservation/reservation.component';
import { HomeComponent } from './pages/home/home.component';
import { HospitalManageComponent } from './pages/hospital-manage/hospital-manage.component';
import { HospitalCardComponent } from './components/hospital-card/hospital-card.component';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { ReservationModalComponent } from './components/reservation-modal/reservation-modal.component';

@NgModule({
  declarations: [
    MainComponent,
    MenuComponent,
    HomeComponent,
    SearchComponent,
    ReservationComponent,
    HospitalManageComponent,
    HospitalCardComponent,
    ReservationModalComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    HospitalRoutingModule,
    NgSelectModule,
    NgbDropdownModule,
  ],
})
export class HospitalModule {}
