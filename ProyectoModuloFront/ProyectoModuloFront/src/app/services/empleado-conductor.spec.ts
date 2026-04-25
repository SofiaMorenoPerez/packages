import { TestBed } from '@angular/core/testing';

import { EmpleadoConductor } from './empleado-conductor';

describe('EmpleadoConductor', () => {
  let service: EmpleadoConductor;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpleadoConductor);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
