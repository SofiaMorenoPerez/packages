import { Component, inject, OnInit } from '@angular/core';
import { ConductorService } from '../services/conductor.service';
import { ConductorModel } from '../models/conductor.model';

declare var bootstrap: any;

@Component({
  selector: 'app-conductor',
  standalone: false,
  templateUrl: './conductor.html',
  styleUrl: './conductor.css',
})
export class Conductor implements OnInit {
  private conductorService = inject(ConductorService);

  conductoresLista: ConductorModel[] = [];

  nombre: string = '';
  edad: number = 0;
  fechaInicio: string = '';
  tipoVehiculo: string = '';

  modoEdicion: boolean = false;
  idEditando: number = 0;

  toastMensaje: string = '';
  toastTitulo: string = '';
  toastColor: string = '';

  mostrarToast(mensaje: string, exito: boolean): void {
    this.toastMensaje = mensaje;
    this.toastTitulo = exito ? '¡Éxito! ✅' : '¡Error! ❌';
    this.toastColor = exito ? '#9fdfb8' : '#ee9fb7';
    setTimeout(() => {
      const toastEl = document.getElementById('conductorToast');
      const toast = new bootstrap.Toast(toastEl);
      toast.show();
    }, 100);
  }

  ngOnInit(): void {
    this.cargarLista();
  }

  cargarLista(): void {
    this.conductorService.getAll().subscribe({
      next: (response) => {
        this.conductoresLista = response;
      },
      error: () => {
        this.mostrarToast('Error al cargar la lista', false);
      },
    });
  }

  guardar(): void {
    if (this.modoEdicion) {
      this.conductorService.update(this.idEditando, this.nombre, this.edad, this.fechaInicio, this.tipoVehiculo).subscribe({
        next: (response) => {
          this.mostrarToast(response, true);
          this.limpiarFormulario();
          this.cargarLista();
        },
        error: (error) => {
          const msg = typeof error.error === 'string' ? error.error : JSON.stringify(error.error);
          this.mostrarToast(msg, false);
        },
      });
    } else {
      this.conductorService.create(this.nombre, this.edad, this.fechaInicio, this.tipoVehiculo).subscribe({
        next: (response) => {
          this.mostrarToast(response, true);
          this.limpiarFormulario();
          this.cargarLista();
        },
        error: (error) => {
          const msg = typeof error.error === 'string' ? error.error : JSON.stringify(error.error);
          this.mostrarToast(msg, false);
        },
      });
    }
  }

  editar(c: ConductorModel): void {
    this.modoEdicion = true;
    this.idEditando = c.id!;
    this.nombre = c.nombre;
    this.edad = c.edad;
    this.fechaInicio = c.fechaInicio;
    this.tipoVehiculo = c.tipoVehiculo;
  }

  eliminar(id: number): void {
    this.conductorService.delete(id).subscribe({
      next: (response) => {
        this.mostrarToast(response, true);
        this.cargarLista();
      },
      error: (error) => {
        const msg = typeof error.error === 'string' ? error.error : JSON.stringify(error.error);
        this.mostrarToast(msg, false);
      },
    });
  }

  limpiarFormulario(): void {
    this.nombre = '';
    this.edad = 0;
    this.fechaInicio = '';
    this.tipoVehiculo = '';
    this.modoEdicion = false;
    this.idEditando = 0;
  }
}
