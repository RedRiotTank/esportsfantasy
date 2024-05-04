import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppapiService } from '../common/API/appapi.service';
import { CredentialsService } from '../credentials/credentials.service';
import { LeagueListServiceService } from '../league-list-service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{

  constructor(
    private router: Router,
    private leagueListService: LeagueListServiceService) { }
  
  ngOnInit(): void {
    
  }

  goToCreateLeague(){
    this.router.navigate(['/joinLeague']);
  }
  
  getLeagues(){
    return this.leagueListService.getLeagues();
  }
  

}
