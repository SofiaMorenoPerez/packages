import { TestBed } from '@angular/core/testing';

import { EmpleadoManipulador } from './empleado-manipulador';

describe('EmpleadoManipulador', () => {
  let service: EmpleadoManipulador;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpleadoManipulador);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
