import { TestBed } from '@angular/core/testing';

import { EmpleadoAdmin } from './empleado-admin';

describe('EmpleadoAdmin', () => {
  let service: EmpleadoAdmin;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpleadoAdmin);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
