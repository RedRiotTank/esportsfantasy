import { Component, OnInit } from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { CredentialsService } from './credentials/credentials.service';
import { AppapiService } from './common/API/appapi.service';
import { LeagueListServiceService } from './league-list-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit{
  title = 'esportsfantasyfe';

  constructor(private router: Router, 
    private credentialsService: CredentialsService, 
    private appapiService: AppapiService,
    private leagueListService: LeagueListServiceService) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.credentialsService.updateLoginCredentials();
      }
    });
  }
  ngOnInit(): void {
    this.leagueListService.updateLeagueList();
  }

  getLeagues(){
    return this.leagueListService.getLeagues();
  }

  goToCreateLeague(){
    this.router.navigate(['/joinLeague']);
  }

}
