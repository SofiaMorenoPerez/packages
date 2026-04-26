

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { Inicio } from './inicio/inicio';
import { Usuario } from './usuario/usuario';
import {Crearenvio} from './paquete/crearenvio/crearenvio';

const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: Inicio },
  { path: 'usuario', component: Usuario },
  { path: '', component: Inicio },
  { path: 'crear-envio', component: Crearenvio }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
