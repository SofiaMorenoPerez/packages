import { Component, inject, OnInit } from '@angular/core';
import { ManipuladorService } from '../services/manipulador.service';
import { PaqueteService } from '../services/paquete.service';
import { ManipuladorModel } from '../models/manipulador.model';
import { PaqueteModel } from '../models/paquete.model';

declare var bootstrap: any;

@Component({
  selector: 'app-manipulador',
  standalone: false,
  templateUrl: './manipulador.html',
  styleUrl: './manipulador.css',
})
export class Manipulador implements OnInit {
  private manipuladorService = inject(ManipuladorService);
  private paqueteService = inject(PaqueteService);

  manipuladoresLista: ManipuladorModel[] = [];
  paquetesLista: PaqueteModel[] = [];
  paquetesManipulador: PaqueteModel[] = [];

  vista: string = 'registrar';
  idBuscar: number = 0;
  manipuladorEncontrado: ManipuladorModel | null = null;

  nombre: string = '';
  edad: number = 0;
  fechaInicio: string = new Date().toISOString().slice(0, 16);
  tipoDePaquete: string = '';

  modoEdicion: boolean = false;
  idEditando: number = 0;

  toastMensaje: string = '';
  toastTitulo: string = '';
  toastColor: string = '';

  mostrarToast(mensaje: string, exito: boolean): void {
    this.toastMensaje = mensaje;
    this.toastTitulo = exito ? '¡Éxito! ✅' : '¡Error! ❌';
    this.toastColor = exito ? '#3fcd76' : '#c40505';
    setTimeout(() => {
      const toastEl = document.getElementById('manipuladorToast');
      const toast = new bootstrap.Toast(toastEl);
      toast.show();
    }, 100);
  }

  ngOnInit(): void {
    this.cargarLista();
    this.cargarPaquetes();
  }

  cargarLista(): void {
    this.manipuladorService.getAll().subscribe({
      next: (response) => { this.manipuladoresLista = response; },
      error: () => { this.mostrarToast('Error al cargar manipuladores', false); },
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
    this.manipuladorEncontrado = null;
    this.paquetesManipulador = [];
    this.limpiarFormulario();
  }

  buscarPorId(): void {
    const encontrado = this.manipuladoresLista.find(m => m.id === Number(this.idBuscar));
    if (encontrado) {
      this.modoEdicion = true;
      this.idEditando = encontrado.id!;
      this.nombre = encontrado.nombre;
      this.edad = encontrado.edad;
      this.fechaInicio = encontrado.fechaInicio;
      this.tipoDePaquete = encontrado.tipoDePaquete;
      this.manipuladorEncontrado = encontrado;
    } else {
      this.mostrarToast(`No se encontró manipulador con ID ${this.idBuscar}`, false);
    }
  }

  buscarPaquetesPorId(): void {
    const encontrado = this.manipuladoresLista.find(m => m.id === Number(this.idBuscar));
    if (encontrado) {
      this.manipuladorEncontrado = encontrado;
      this.paquetesManipulador = this.paquetesLista.filter(p => p.idManipulador === Number(this.idBuscar));
    } else {
      this.manipuladorEncontrado = null;
      this.paquetesManipulador = [];
      this.mostrarToast(`No se encontró manipulador con ID ${this.idBuscar}`, false);
    }
  }


  buscarPorIdYEliminar(): void {
    const encontrado = this.manipuladoresLista.find(u => u.id === Number(this.idBuscar));
    if (encontrado) {
      this.eliminar(encontrado.id);
    } else {
      this.mostrarToast(`No se encontró manipulador con ID ${this.idBuscar}`, false);
    }
  }


  guardar(): void {
    if (this.modoEdicion) {
      this.manipuladorService.update(this.idEditando, this.nombre, this.edad, this.fechaInicio, this.tipoDePaquete).subscribe({
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
      this.manipuladorService.create(this.nombre, this.edad, this.fechaInicio, this.tipoDePaquete).subscribe({
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


  eliminar(id: number): void {
    this.manipuladorService.delete(id).subscribe({
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
    this.tipoDePaquete = '';
    this.modoEdicion = false;
    this.idEditando = 0;
    this.manipuladorEncontrado = null;
  }
}
