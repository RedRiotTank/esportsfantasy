import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EsfErrorComponent } from './common/error/esf-error/esf-error.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { LoginComponent } from './credentials/login/login.component';
import { RegisterComponent } from './credentials/register/register.component';
import { RegistersuccessComponent } from './credentials/register/registersuccess/registersuccess.component';
import { HomeComponent } from './home/home.component';
import { authGuard, welcomeGuard } from './credentials/auth.guard';
import { JoinLeagueComponent } from './join-league/join-league.component';
import { MarketComponent } from './market/market.component';
import { TeamComponent } from './team/team.component';
import { MatchsComponent } from './matchs/matchs.component';
import { PlayerinfoComponent } from './playerinfo/playerinfo.component';
import { TeaminfoComponent } from './teaminfo/teaminfo.component';
import { RankingComponent } from './ranking/ranking.component';
import { UserpageComponent } from './userpage/userpage.component';
import { PlayersComponent } from './players/players.component';

const routes: Routes = [
  { path: '', redirectTo: '/welcome', pathMatch: 'full' },
  { path: 'matchs', component: MatchsComponent },
  { path: 'players', component: PlayersComponent },
  { path: 'welcome', component: WelcomeComponent, canActivate: [welcomeGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'register-success', component: RegistersuccessComponent },
  {
    path: 'joinLeague',
    component: JoinLeagueComponent,
    canActivate: [authGuard],
  },
  { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  { path: 'userpage', component: UserpageComponent, canActivate: [authGuard] },
  { path: 'market', component: MarketComponent, canActivate: [authGuard] },
  { path: 'ranking', component: RankingComponent, canActivate: [authGuard] },
  { path: 'team', component: TeamComponent, canActivate: [authGuard] },
  { path: 'playerinfo/:id', component: PlayerinfoComponent },
  { path: 'teaminfo/:id', component: TeaminfoComponent },
  { path: 'error', component: EsfErrorComponent },
];

/**
 * This module is responsible for managing the application routes.
 * @author Alberto Plaza Montes
 */
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
