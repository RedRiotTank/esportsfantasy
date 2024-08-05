import { NgModule, OnInit } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EsfHeaderComponent } from './common/esf-header/esf-header.component';
import { MdbModalModule } from 'mdb-angular-ui-kit/modal';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { EsfErrorComponent } from './common/error/esf-error/esf-error.component';
import {
  GoogleLoginProvider,
  GoogleSigninButtonModule,
  SocialAuthServiceConfig,
  SocialLoginModule,
} from '@abacritt/angularx-social-login';
import { WelcomeComponent } from './welcome/welcome.component';
import { LoginComponent } from './credentials/login/login.component';
import { RegisterComponent } from './credentials/register/register.component';
import { RegistersuccessComponent } from './credentials/register/registersuccess/registersuccess.component';
import { FooterComponent } from './common/footer/footer.component';
import { HomeComponent } from './home/home.component';
import { CredentialsService } from './credentials/credentials.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { AccordionModule } from 'primeng/accordion';
import { JoinLeagueComponent } from './join-league/join-league.component';
import { MarketComponent } from './market/market.component';
import { TeamComponent } from './team/team.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { BidupModalComponent } from './market/bidup-modal/bidup-modal.component';
import { MatDialogModule } from '@angular/material/dialog';
import { SliderModule } from 'primeng/slider';
import { SelectplayerModalComponent } from './team/selectplayer-modal/selectplayer-modal.component'; // Importa SliderModule de PrimeNG
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatchsComponent } from './matchs/matchs.component';
import { SellplayerModalComponent } from './market/sellplayer-modal/sellplayer-modal.component';
import { PlayerinfoComponent } from './playerinfo/playerinfo.component';
@NgModule({
  declarations: [
    AppComponent,
    EsfHeaderComponent,
    EsfErrorComponent,
    WelcomeComponent,
    LoginComponent,
    RegisterComponent,
    RegistersuccessComponent,
    FooterComponent,
    HomeComponent,
    JoinLeagueComponent,
    MarketComponent,
    TeamComponent,
    BidupModalComponent,
    SelectplayerModalComponent,
    MatchsComponent,
    SellplayerModalComponent,
    PlayerinfoComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    MdbModalModule,
    HttpClientModule,
    SocialLoginModule,
    GoogleSigninButtonModule,
    BrowserAnimationsModule,
    CarouselModule,
    AccordionModule,
    MatDialogModule,
    SliderModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    FormsModule,
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
            ),
          },
        ],
        onError: (err) => {
          console.error(err);
        },
      } as SocialAuthServiceConfig,
    },
    provideAnimationsAsync(),
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
