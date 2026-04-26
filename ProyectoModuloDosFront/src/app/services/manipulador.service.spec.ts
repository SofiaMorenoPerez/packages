import { TestBed } from '@angular/core/testing';

import { ManipuladorService } from './manipulador.service';

describe('ManipuladorService', () => {
  let service: ManipuladorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ManipuladorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
