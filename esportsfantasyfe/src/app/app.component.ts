import { Component, OnInit, ViewChild } from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { CredentialsService } from './credentials/credentials.service';
import { AppapiService } from './common/API/appapi.service';
import { LeagueListServiceService } from './league-list-service.service';
import { MarketComponent } from './market/market.component';
import { MarketService } from './market/market.service';
import { MoneyService } from './common/money.service';
import { TeamService } from './team/team.service';
import { MatchsService } from './matchs/matchs.service';
import { RankingComponent } from './ranking/ranking.component';
import { RankingService } from './ranking/ranking.service';
import { HomeService } from './home/home.service';
import { PlayersService } from './players/players.service';
import { PlayersComponent } from './players/players.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  title = 'esportsfantasyfe';

  constructor(
    private router: Router,
    private credentialsService: CredentialsService,
    private appapiService: AppapiService,
    private leagueListService: LeagueListServiceService,
    private marketService: MarketService,
    private teamService: TeamService,
    private moneyService: MoneyService,
    private matchsService: MatchsService,
    private rankingService: RankingService,
    private homeService: HomeService,
    private playersService: PlayersService
  ) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.credentialsService.updateLoginCredentials();
      }
    });
  }
  ngOnInit(): void {
    if (localStorage.getItem('token') != null) {
      this.credentialsService
        .loginWithToken(localStorage.getItem('token'))
        .subscribe(
          (response: any) => {
            if (response.error) {
              this.credentialsService.sendToErrorPage(response.error);
            } else {
              this.credentialsService.loggedIn = true;
              this.credentialsService.social_started = true;
              this.credentialsService.social_user = response.user;
              this.leagueListService.updateLeagueListObs().subscribe(() => {});
            }
          },
          (error: any) => {
            this.credentialsService.sendToErrorPage(error);
          }
        );
    }
  }

  getLeagues() {
    return this.leagueListService.getLeagues();
  }

  goToCreateLeague() {
    this.router.navigate(['/joinLeague']);
  }

  public getCredentialService() {
    return this.credentialsService;
  }

  public getSelectedLeague() {
    return this.leagueListService.getSelectedLeague();
  }

  public setSelectedLeague(index: number) {
    this.leagueListService.setSelectedLeague(index);

    this.teamService.loadCurrentJour().subscribe(() => {
      switch (this.router.url) {
        case '/home':
          this.homeService.loadHome();
          break;

        case '/ranking':
          this.rankingService.changeJour(this.teamService.currentJour);
          break;
        case '/market':
          this.marketService.loadMarket();
          break;
        case '/team':
          this.teamService.loadTeam();
          this.teamService.selectedJour = this.teamService.currentJour;
          break;
        case '/matchs':
          this.matchsService.loadMatchs();
          break;
        case '/players':
          console.log('load players');
          this.playersService.load();
          break;
      }
    });
  }

  public getMoneyWithFormat() {
    return this.moneyService.getMoneyWithFormat();
  }
}
