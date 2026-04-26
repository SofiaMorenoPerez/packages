import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Inicio } from './inicio/inicio';
<<<<<<< HEAD
import {Usuario} from './usuario/usuario';

const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: Inicio },
  { path: 'usuario', component: Usuario }
=======
import { Crearenvio } from './paquete/crearenvio/crearenvio';

const routes: Routes = [
  { path: '', component: Inicio },
  { path: 'crear-envio', component: Crearenvio }
>>>>>>> 6f12b96539e15ee33219a92f3963cbc164c34b7d
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
