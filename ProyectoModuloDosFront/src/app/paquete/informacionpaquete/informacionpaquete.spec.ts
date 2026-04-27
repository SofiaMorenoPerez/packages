import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Informacionpaquete } from './informacionpaquete';

describe('Informacionpaquete', () => {
  let component: Informacionpaquete;
  let fixture: ComponentFixture<Informacionpaquete>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Informacionpaquete],
    }).compileComponents();

    fixture = TestBed.createComponent(Informacionpaquete);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
