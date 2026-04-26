

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Inicio } from './inicio/inicio';
import { Usuario } from './usuario/usuario';
import { Paquete } from './paquete/paquete';

const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: Inicio },
  { path: 'usuario', component: Usuario },
  { path: 'paquete', component: Paquete }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
