import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalManageComponent } from './hospital-manage.component';

describe('HospitalManageComponent', () => {
  let component: HospitalManageComponent;
  let fixture: ComponentFixture<HospitalManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HospitalManageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HospitalManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
