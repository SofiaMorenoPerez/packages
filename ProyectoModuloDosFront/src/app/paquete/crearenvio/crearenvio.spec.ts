import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Crearenvio } from './crearenvio';

describe('Crearenvio', () => {
  let component: Crearenvio;
  let fixture: ComponentFixture<Crearenvio>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Crearenvio],
    }).compileComponents();

    fixture = TestBed.createComponent(Crearenvio);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
