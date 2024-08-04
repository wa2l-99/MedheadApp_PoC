import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationModalComponent } from './reservation-modal.component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

describe('ReservationModalComponent', () => {
  let component: ReservationModalComponent;
  let fixture: ComponentFixture<ReservationModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ReservationModalComponent],
      providers: [NgbActiveModal],
    }).compileComponents();

    fixture = TestBed.createComponent(ReservationModalComponent);
    component = fixture.componentInstance;
    component.reservation = { reference: '' };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
