import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EsfHomeComponent } from './esf-app/esf-app-home/esf-home/esf-home.component';
import { EsfInitComponent } from './esf-init/esf-init.component';
import { EsfLogregRegisterSuccessComponent } from './common/esf-logreg/esf-logreg-registerSuccess/esf-logreg-register-success/esf-logreg-register-success.component';
import { EsfErrorComponent } from './common/error/esf-error/esf-error.component';

const routes: Routes = [
  { path: '', redirectTo: '/init', pathMatch: 'full' }, // Redirige a LoginComponent por defecto
  { path: 'init', component: EsfInitComponent},
  { path: 'home', component: EsfHomeComponent },
  { path: 'registerSuccess', component: EsfLogregRegisterSuccessComponent },
  { path: 'error', component: EsfErrorComponent }

];

/**
 * This module is responsible for managing the application routes.
 * @author Alberto Plaza Montes
 */
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
