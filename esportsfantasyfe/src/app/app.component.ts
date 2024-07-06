import { Component, OnInit } from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { CredentialsService } from './credentials/credentials.service';
import { AppapiService } from './common/API/appapi.service';
import { LeagueListServiceService } from './league-list-service.service';
import { MarketComponent } from './market/market.component';
import { MarketService } from './market/market.service';
import { MoneyService } from './common/money.service';
import { TeamService } from './team/team.service';

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
    private moneyService: MoneyService
  ) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.credentialsService.updateLoginCredentials();
      }
    });
  }
  ngOnInit(): void {
    if (!this.credentialsService.getLoggedIn()) {
      localStorage.removeItem('token');
      this.appapiService.sendToErrorPage(null);
    }

    this.leagueListService.updateLeagueList();
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
    this.marketService.loadMarket();
    this.teamService.loadTeam();
  }

  public getMoneyWithFormat() {
    return this.moneyService.getMoneyWithFormat();
  }
}
