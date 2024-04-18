import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EsfHomeComponent } from './esf-app/esf-app-home/esf-home/esf-home.component';
import { EsfErrorComponent } from './common/error/esf-error/esf-error.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { LoginComponent } from './credentials/login/login.component';
import { RegisterComponent } from './credentials/register/register.component';
import { RegistersuccessComponent } from './credentials/register/registersuccess/registersuccess.component';

const routes: Routes = [
  { path: '', redirectTo: '/welcome', pathMatch: 'full' },
  { path: 'welcome', component: WelcomeComponent},
  { path: 'login', component: LoginComponent } ,
  { path: 'register', component: RegisterComponent } ,
  { path: 'register-success', component: RegistersuccessComponent } ,
  { path: 'home', component: EsfHomeComponent},
  { path: 'home', component: EsfHomeComponent },
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
