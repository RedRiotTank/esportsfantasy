import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppapiService } from '../common/API/appapi.service';
import { CredentialsService } from '../credentials/credentials.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{

  leagues: any[] = [];


  constructor(
    private router: Router,
    private appapiService: AppapiService,
    private credentialsService: CredentialsService) { }
  
  ngOnInit(): void {
    this.appapiService.getUserLeagues(this.credentialsService.getDecodedToken().sub).subscribe( leagues => {
      leagues.forEach(league => {
        this.appapiService.getLeagueIcon(league.leagueUUID).subscribe(icon => {
          league.icon = icon;
        });
        this.leagues.push(league);
      });
    });
    
  }

  goToCreateLeague(){
    this.router.navigate(['/joinLeague']);
  }

  

}
