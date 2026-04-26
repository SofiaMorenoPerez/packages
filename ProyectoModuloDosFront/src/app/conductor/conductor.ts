import { Component, OnInit, inject } from '@angular/core';
import { ConductorService } from '../services/conductor.service';
import { ConductorModel } from '../models/conductor.model';

@Component({
  selector: 'app-conductor',
  standalone: false,
  templateUrl: './conductor.html',
  styleUrl: './conductor.css',
})
export class Conductor implements OnInit {
  conductorService = inject(ConductorService);
  conductores: ConductorModel[] = [];
  statuscode: number = 0;

  nombre: string = '';
  edad: number = 0;
  fechaInicio: string = '';
  tipoVehiculo: string = '';

  idEditar: number = 0;
  nombreEditar: string = '';
  edadEditar: number = 0;
  fechaInicioEditar: string = '';
  tipoVehiculoEditar: string = '';

  toastMensaje: string = '';
  toastTipo: string = '';

  constructor() {}

  ngOnInit(): void {
    this.loadConductores();
  }

  mostrarToast(mensaje: string, tipo: string): void {
    this.toastMensaje = mensaje;
    this.toastTipo = tipo;
    const toastEl = document.getElementById('conductorToast');
    if (toastEl) {
      const toast = (window as any).bootstrap.Toast.getOrCreateInstance(toastEl);
      toast.show();
    }
  }

  loadConductores(): void {
    this.conductorService.getAll().subscribe({
      next: (response: any) => {
        console.log('Response status:', response.status);
        console.log('Response body:', response.body);
        this.statuscode = response.status;
        const body = response.body;
        if (body && body.data) {
          this.conductores = body.data;
        } else if (Array.isArray(body)) {
          this.conductores = body;
        } else {
          this.conductores = [];
        }
        this.conductores = [...this.conductores];
      },
      error: (error: any) => {
        this.statuscode = error.status;
        this.conductores = [];
        this.mostrarToast('Error al cargar los conductores', 'danger');
      },
    });
  }

  crear(): void {
    this.conductorService.create(this.nombre, this.edad, this.fechaInicio, this.tipoVehiculo).subscribe({
      next: () => {
        this.mostrarToast('Conductor creado correctamente', 'success');
        this.loadConductores();
      },
      error: () => {
        this.mostrarToast('Error al crear el conductor', 'danger');
      },
    });
  }

  actualizar(): void {
    this.conductorService.update(this.idEditar, this.nombreEditar, this.edadEditar, this.fechaInicioEditar, this.tipoVehiculoEditar).subscribe({
      next: () => {
        this.mostrarToast('Conductor actualizado correctamente', 'success');
        this.loadConductores();
      },
      error: () => {
        this.mostrarToast('Error al actualizar el conductor', 'danger');
      },
    });
  }

  eliminar(id: number): void {
    this.conductorService.delete(id).subscribe({
      next: () => {
        this.mostrarToast('Conductor eliminado correctamente', 'success');
        this.loadConductores();
      },
      error: () => {
        this.mostrarToast('Error al eliminar el conductor', 'danger');
      },
    });
  }

  seleccionarEditar(conductor: ConductorModel): void {
    this.idEditar = conductor.id!;
    this.nombreEditar = conductor.nombre;
    this.edadEditar = conductor.edad;
    this.fechaInicioEditar = conductor.fechaInicio;
    this.tipoVehiculoEditar = conductor.tipoVehiculo;
  }
}
