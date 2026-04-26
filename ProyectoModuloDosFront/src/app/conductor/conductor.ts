import { Component, inject, OnInit } from '@angular/core';
import { ConductorService } from '../services/conductor.service';
import { PaqueteService } from '../services/paquete.service';
import { ConductorModel } from '../models/conductor.model';
import { PaqueteModel } from '../models/paquete.model';

declare var bootstrap: any;

@Component({
  selector: 'app-conductor',
  standalone: false,
  templateUrl: './conductor.html',
  styleUrl: './conductor.css',
})
export class Conductor implements OnInit {
  private conductorService = inject(ConductorService);
  private paqueteService = inject(PaqueteService);

  conductoresLista: ConductorModel[] = [];
  paquetesLista: PaqueteModel[] = [];
  paquetesConductor: PaqueteModel[] = [];

  vista: string = 'registrar';
  idBuscar: number = 0;
  conductorEncontrado: ConductorModel | null = null;

  nombre: string = '';
  edad: number = 0;
  fechaInicio: string = new Date().toISOString().slice(0, 16);
  tipoVehiculo: string = '';

  modoEdicion: boolean = false;
  idEditando: number = 0;

  toastMensaje: string = '';
  toastTitulo: string = '';
  toastColor: string = '';

  mostrarToast(mensaje: string, exito: boolean): void {
    this.toastMensaje = mensaje;
    this.toastTitulo = exito ? '¡Éxito! ✅' : '¡Error! ❌';
    this.toastColor = exito ? '#b5f1ca' : '#ee9fb7';
    setTimeout(() => {
      const toastEl = document.getElementById('conductorToast');
      const toast = new bootstrap.Toast(toastEl);
      toast.show();
    }, 100);
  }

  ngOnInit(): void {
    this.cargarLista();
    this.cargarPaquetes();
  }

  cargarLista(): void {
    this.conductorService.getAll().subscribe({
      next: (response) => { this.conductoresLista = response; },
      error: () => { this.mostrarToast('Error al cargar conductores', false); },
    });
  }

  cargarPaquetes(): void {
    this.paqueteService.getAll().subscribe({
      next: (response) => { this.paquetesLista = response; },
      error: () => { this.paquetesLista = []; },
    });
  }

  cambiarVista(v: string): void {
    this.vista = v;
    this.idBuscar = 0;
    this.conductorEncontrado = null;
    this.paquetesConductor = [];
    this.limpiarFormulario();
  }

  buscarPorId(): void {
    const encontrado = this.conductoresLista.find(c => c.id === Number(this.idBuscar));
    if (encontrado) {
      this.modoEdicion = true;
      this.idEditando = encontrado.id!;
      this.nombre = encontrado.nombre;
      this.edad = encontrado.edad;
      this.fechaInicio = encontrado.fechaInicio;
      this.tipoVehiculo = encontrado.tipoVehiculo;
      this.conductorEncontrado = encontrado;
    } else {
      this.mostrarToast(`No se encontró conductor con ID ${this.idBuscar}`, false);
    }
  }

  buscarPaquetesPorId(): void {
    const encontrado = this.conductoresLista.find(c => c.id === Number(this.idBuscar));
    if (encontrado) {
      this.conductorEncontrado = encontrado;
      this.paquetesConductor = this.paquetesLista.filter(p => p.idConductor === Number(this.idBuscar));
    } else {
      this.conductorEncontrado = null;
      this.paquetesConductor = [];
      this.mostrarToast(`No se encontró conductor con ID ${this.idBuscar}`, false);
    }
  }

  buscarPorIdYEliminar(): void {
    const encontrado = this.conductoresLista.find(u => u.id === Number(this.idBuscar));
    if (encontrado) {
      this.eliminar(encontrado.id);
    } else {
      this.mostrarToast(`No se encontró conductor con ID ${this.idBuscar}`, false);
    }
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
          this.conductorService.getAll().subscribe({
            next: (lista) => {
              const nuevo = lista[lista.length - 1];
              this.mostrarToast(`${response} — Su ID es: ${nuevo?.id}`, true);
              this.limpiarFormulario();
              this.cargarLista();
            },
            error: () => {
              this.mostrarToast(response, true);
              this.limpiarFormulario();
              this.cargarLista();
            }
          });
        },
        error: (error) => {
          const msg = typeof error.error === 'string' ? error.error : JSON.stringify(error.error);
          this.mostrarToast(msg, false);
        },
      });
    }
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
    this.fechaInicio = new Date().toISOString().slice(0, 16);
    this.tipoVehiculo = '';
    this.modoEdicion = false;
    this.idEditando = 0;
    this.conductorEncontrado = null;
  }
}
