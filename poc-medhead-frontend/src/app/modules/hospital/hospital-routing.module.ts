import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './pages/main/main.component';
import { SearchComponent } from './components/search/search.component';
import { HomeComponent } from './pages/home/home.component';
import { HospitalManageComponent } from './pages/hospital-manage/hospital-manage.component';
import { ReservationComponent } from './components/reservation/reservation.component';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      {
        path: '',
        component: HomeComponent,
      },
      {
        path: 'searchHospital',
        component: SearchComponent,
      },
      {
        path: 'hospitals',
        component: HospitalManageComponent,
      },
      {
        path: 'reservations',
        component: ReservationComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HospitalRoutingModule {}
