import { TestBed } from '@angular/core/testing';

import { StorageUserServiceService } from './storage-user.service';

describe('StorageUserServiceService', () => {
  let service: StorageUserServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StorageUserServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
