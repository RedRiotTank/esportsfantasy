import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppapiService } from '../common/API/appapi.service';
import { CredentialsService } from '../credentials/credentials.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { HomeService } from './home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit {
  constructor(
    private router: Router,
    private leagueListService: LeagueListServiceService,
    private homeService: HomeService
  ) {}

  ngOnInit(): void {
    this.homeService.loadHome();
  }

  goToCreateLeague() {
    this.router.navigate(['/joinLeague']);
  }

  getTransferPosts() {
    return this.homeService.getTransferPosts();
  }

  getLeagues() {
    return this.leagueListService.getLeagues();
  }
}
