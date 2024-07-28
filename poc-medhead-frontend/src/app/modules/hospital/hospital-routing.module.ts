import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './pages/main/main.component';
import { SearchComponent } from './components/search/search.component';
import { HomeComponent } from './pages/home/home.component';
import { HospitalManageComponent } from './pages/hospital-manage/hospital-manage.component';
import { ReservationComponent } from './components/reservation/reservation.component';
import { authGuard } from '../../services/guard/auth.guard';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        component: HomeComponent,
        canActivate: [authGuard],
      },
      {
        path: 'searchHospital',
        component: SearchComponent,
        canActivate: [authGuard],
      },
      {
        path: 'hospitals',
        component: HospitalManageComponent,
        canActivate: [authGuard],
      },
      {
        path: 'reservations',
        component: ReservationComponent,
        canActivate: [authGuard],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HospitalRoutingModule {}
