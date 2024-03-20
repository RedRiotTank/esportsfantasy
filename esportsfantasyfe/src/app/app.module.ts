import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EsfShowcaseComponent } from './esf-init/esf-showcase/esf-showcase.component';
import { EsfInitComponent } from './esf-init/esf-init.component';
import { EsfHeaderComponent } from './common/esf-header/esf-header.component';
import { MdbModalModule } from 'mdb-angular-ui-kit/modal';
import { EsfLofregModalComponent } from './common/esf-logreg/esf-lofreg-modal/esf-lofreg-modal.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { EsfHomeComponent } from './esf-app/esf-app-home/esf-home/esf-home.component';
import { EsfLogregRegisterSuccessComponent } from './common/esf-logreg/esf-logreg-registerSuccess/esf-logreg-register-success/esf-logreg-register-success.component';
import { EsfErrorComponent } from './common/error/esf-error/esf-error.component';
import { GoogleLoginProvider, GoogleSigninButtonModule, SocialAuthServiceConfig, SocialLoginModule } from '@abacritt/angularx-social-login';
@NgModule({
  declarations: [
    AppComponent,
    EsfHeaderComponent,
    EsfShowcaseComponent,
    EsfInitComponent,
    EsfLofregModalComponent,
    EsfHomeComponent,
    EsfLogregRegisterSuccessComponent,
    EsfErrorComponent
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
