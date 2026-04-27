import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Manipulador } from './manipulador';

describe('Manipulador', () => {
  let component: Manipulador;
  let fixture: ComponentFixture<Manipulador>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Manipulador],
    }).compileComponents();

    fixture = TestBed.createComponent(Manipulador);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
