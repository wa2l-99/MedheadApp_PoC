import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalManageComponent } from './hospital-manage.component';
import { HospitalControllerService } from '../../../../services/hospital_service/services';
import { provideHttpClient } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';

describe('HospitalManageComponent', () => {
  let component: HospitalManageComponent;
  let fixture: ComponentFixture<HospitalManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HospitalManageComponent],
      providers: [provideHttpClient(), HospitalControllerService],
      imports: [ToastrModule.forRoot(), FormsModule],
    }).compileComponents();

    fixture = TestBed.createComponent(HospitalManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
