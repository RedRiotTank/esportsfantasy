import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EsfHeaderComponent } from './common/esf-header/esf-header.component';
import { MdbModalModule } from 'mdb-angular-ui-kit/modal';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { EsfHomeComponent } from './esf-app/esf-app-home/esf-home/esf-home.component';
import { EsfErrorComponent } from './common/error/esf-error/esf-error.component';
import { GoogleLoginProvider, GoogleSigninButtonModule, SocialAuthServiceConfig, SocialLoginModule } from '@abacritt/angularx-social-login';
import { WelcomeComponent } from './welcome/welcome.component';
import { LoginComponent } from './credentials/login/login.component';
import { RegisterComponent } from './credentials/register/register.component';
import { RegistersuccessComponent } from './credentials/register/registersuccess/registersuccess.component';
@NgModule({
  declarations: [
    AppComponent,
    EsfHeaderComponent,
    EsfHomeComponent,
    EsfErrorComponent,
    WelcomeComponent,
    LoginComponent,
    RegisterComponent,
    RegistersuccessComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    MdbModalModule,
    HttpClientModule,
    SocialLoginModule,
    GoogleSigninButtonModule
  ],
  providers: [
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '860986754360-fqq5h0sfu5abhccvp7u1djv0h23rr80c.apps.googleusercontent.com'
            )
          }
        ],
        onError: (err) => {
          console.error(err);
        }
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
