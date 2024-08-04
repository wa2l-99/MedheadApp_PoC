import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivateAccountComponent } from './activate-account.component';
import { provideHttpClient } from '@angular/common/http';
import { CodeInputModule } from 'angular-code-input';

describe('ActivateAccountComponent', () => {
  let component: ActivateAccountComponent;
  let fixture: ComponentFixture<ActivateAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ActivateAccountComponent],
      providers: [provideHttpClient()],
      imports: [CodeInputModule],
    }).compileComponents();

    fixture = TestBed.createComponent(ActivateAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
