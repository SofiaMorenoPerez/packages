import { Component, OnInit, inject } from '@angular/core';
import { ManipuladorService } from '../services/manipulador.service';
import { ManipuladorModel } from '../models/manipulador.model';

@Component({
  selector: 'app-manipulador',
  standalone: false,
  templateUrl: './manipulador.html',
  styleUrl: './manipulador.css',
})
export class Manipulador implements OnInit {
  manipuladorService = inject(ManipuladorService);
  manipuladores: ManipuladorModel[] = [];
  statuscode: number = 0;

  nombre: string = '';
  edad: number = 0;
  fechaInicio: string = '';
  tipoDePaquete: string = '';

  idEditar: number = 0;
  nombreEditar: string = '';
  edadEditar: number = 0;
  fechaInicioEditar: string = '';
  tipoDePaqueteEditar: string = '';

  toastMensaje: string = '';
  toastTipo: string = '';

  constructor() {}

  ngOnInit(): void {
    this.loadManipuladores();
  }

  mostrarToast(mensaje: string, tipo: string): void {
    this.toastMensaje = mensaje;
    this.toastTipo = tipo;
    const toastEl = document.getElementById('manipuladorToast');
    if (toastEl) {
      const toast = (window as any).bootstrap.Toast.getOrCreateInstance(toastEl);
      toast.show();
    }
  }

  loadManipuladores(): void {
    this.manipuladorService.getAll().subscribe({
      next: (response: any) => {
        console.log('Response status:', response.status);
        console.log('Response body:', response.body);
        this.statuscode = response.status;
        const body = response.body;
        if (body && body.data) {
          this.manipuladores = body.data;
        } else if (Array.isArray(body)) {
          this.manipuladores = body;
        } else {
          this.manipuladores = [];
        }
        this.manipuladores = [...this.manipuladores];
      },
      error: (error: any) => {
        this.statuscode = error.status;
        this.manipuladores = [];
        this.mostrarToast('Error al cargar los manipuladores', 'danger');
      },
    });
  }

  crear(): void {
    this.manipuladorService.create(this.nombre, this.edad, this.fechaInicio, this.tipoDePaquete).subscribe({
      next: () => {
        this.mostrarToast('Manipulador creado correctamente', 'success');
        this.loadManipuladores();
      },
      error: () => {
        this.mostrarToast('Error al crear el manipulador', 'danger');
      },
    });
  }

  actualizar(): void {
    this.manipuladorService.update(this.idEditar, this.nombreEditar, this.edadEditar, this.fechaInicioEditar, this.tipoDePaqueteEditar).subscribe({
      next: () => {
        this.mostrarToast('Manipulador actualizado correctamente', 'success');
        this.loadManipuladores();
      },
      error: () => {
        this.mostrarToast('Error al actualizar el manipulador', 'danger');
      },
    });
  }

  eliminar(id: number): void {
    this.manipuladorService.delete(id).subscribe({
      next: () => {
        this.mostrarToast('Manipulador eliminado correctamente', 'success');
        this.loadManipuladores();
      },
      error: () => {
        this.mostrarToast('Error al eliminar el manipulador', 'danger');
      },
    });
  }

  seleccionarEditar(manipulador: ManipuladorModel): void {
    this.idEditar = manipulador.id!;
    this.nombreEditar = manipulador.nombre;
    this.edadEditar = manipulador.edad;
    this.fechaInicioEditar = manipulador.fechaInicio;
    this.tipoDePaqueteEditar = manipulador.tipoDePaquete;
  }
}
