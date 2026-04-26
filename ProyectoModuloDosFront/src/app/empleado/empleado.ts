import { Component, OnInit } from '@angular/core';
import { EmpleadoAdminService } from '../services/admin.service';
import { EmpleadoAdminModel } from '../models/admin.model';

@Component({
  selector: 'app-empleado',
  standalone: false,
  templateUrl: './empleado.html',
  styleUrl: './empleado.css',
})
export class Empleado implements OnInit {
  empleadoAdminService = new EmpleadoAdminService();
  empleados: EmpleadoAdminModel[] = [];
  statuscode: number = 0;

  nombre: string = '';
  edad: number = 0;
  fechaInicio: string = '';
  zonaAsignada: string = '';

  idEditar: number = 0;
  nombreEditar: string = '';
  edadEditar: number = 0;
  fechaInicioEditar: string = '';
  zonaAsignadaEditar: string = '';

  toastMensaje: string = '';
  toastTipo: string = '';

  constructor() {}

  ngOnInit(): void {
    this.loadEmpleados();
  }

  mostrarToast(mensaje: string, tipo: string): void {
    this.toastMensaje = mensaje;
    this.toastTipo = tipo;
    const toastEl = document.getElementById('empleadoToast');
    if (toastEl) {
      const toast = (window as any).bootstrap.Toast.getOrCreateInstance(toastEl);
      toast.show();
    }
  }

  loadEmpleados(): void {
    this.empleadoAdminService.getAll().subscribe({
      next: (response) => {
        console.log('Response status:', response.status);
        console.log('Response body:', response.body);
        this.statuscode = response.status;
        const body = response.body;
        if (body && (body as any).data) {
          this.empleados = (body as any).data;
        } else if (Array.isArray(body)) {
          this.empleados = body;
        } else {
          this.empleados = [];
        }
        this.empleados = [...this.empleados];
      },
      error: (error) => {
        this.statuscode = error.status;
        this.empleados = [];
        this.mostrarToast('Error al cargar los administrativos', 'danger');
      },
    });
  }

  crear(): void {
    this.empleadoAdminService.create(this.nombre, this.edad, this.fechaInicio, this.zonaAsignada).subscribe({
      next: () => {
        this.mostrarToast('Administrativo creado correctamente', 'success');
        this.loadEmpleados();
      },
      error: (err) => {
        this.mostrarToast('Error al crear el administrativo', 'danger');
      },
    });
  }

  actualizar(): void {
    this.empleadoAdminService.update(this.idEditar, this.nombreEditar, this.edadEditar, this.fechaInicioEditar, this.zonaAsignadaEditar).subscribe({
      next: () => {
        this.mostrarToast('Administrativo actualizado correctamente', 'success');
        this.loadEmpleados();
      },
      error: (err) => {
        this.mostrarToast('Error al actualizar el administrativo', 'danger');
      },
    });
  }

  eliminar(id: number): void {
    this.empleadoAdminService.delete(id).subscribe({
      next: () => {
        this.mostrarToast('Administrativo eliminado correctamente', 'success');
        this.loadEmpleados();
      },
      error: (err) => {
        this.mostrarToast('Error al eliminar el administrativo', 'danger');
      },
    });
  }

  seleccionarEditar(emp: EmpleadoAdminModel): void {
    this.idEditar = emp.id!;
    this.nombreEditar = emp.nombre;
    this.edadEditar = emp.edad;
    this.fechaInicioEditar = emp.fechaInicio;
    this.zonaAsignadaEditar = emp.zonaAsignada;
  }
}
