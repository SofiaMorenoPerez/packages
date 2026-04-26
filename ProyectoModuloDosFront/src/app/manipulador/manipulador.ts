import { Component, inject, OnInit } from '@angular/core';
import { ManipuladorService } from '../services/manipulador.service';
import { ManipuladorModel } from '../models/manipulador.model';

declare var bootstrap: any;

@Component({
  selector: 'app-manipulador',
  standalone: false,
  templateUrl: './manipulador.html',
  styleUrl: './manipulador.css',
})
export class Manipulador implements OnInit {
  private manipuladorService = inject(ManipuladorService);

  manipuladoresLista: ManipuladorModel[] = [];

  nombre: string = '';
  edad: number = 0;
  fechaInicio: string = '';
  tipoDePaquete: string = '';

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
      const toastEl = document.getElementById('manipuladorToast');
      const toast = new bootstrap.Toast(toastEl);
      toast.show();
    }, 100);
  }

  ngOnInit(): void {
    this.cargarLista();
  }

  cargarLista(): void {
    this.manipuladorService.getAll().subscribe({
      next: (response) => {
        this.manipuladoresLista = response as any;
      },
      error: () => {
        this.mostrarToast('Error al cargar la lista', false);
      },
    });
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

  editar(m: ManipuladorModel): void {
    this.modoEdicion = true;
    this.idEditando = m.id;
    this.nombre = m.nombre;
    this.edad = m.edad;
    this.fechaInicio = m.fechaInicio;
    this.tipoDePaquete = m.tipoDePaquete;
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
    this.fechaInicio = '';
    this.tipoDePaquete = '';
    this.modoEdicion = false;
    this.idEditando = 0;
  }
}
