import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppapiService } from '../API/appapi.service';
import { HeaderService } from './header.service';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { LeagueListServiceService } from '../../league-list-service.service';

/**
 * This component is responsible for managing the header of the application.
 */
@Component({
  selector: 'app-esf-header',
  templateUrl: './esf-header.component.html',
  styleUrl: './esf-header.component.scss',
})
export class EsfHeaderComponent implements OnInit {
  openHam: boolean = false;

  constructor(
    private headerService: HeaderService,
    private router: Router,
    private leagueListService: LeagueListServiceService
  ) {}

  ngOnInit(): void {}

  getPfpUrl() {
    return this.headerService.getPfpUrl();
  }

  getLoggedIn() {
    return this.headerService.getLoggedIn();
  }

  /**
   * This method opens and closes the hamburger navigation menu.
   */
  toggleHam() {
    this.openHam = !this.openHam;
  }

  getLeagues() {
    return this.leagueListService.getLeagues();
  }

  goToHome() {
    if (this.getLoggedIn()) this.router.navigate(['/home']);
    else this.router.navigate(['/welcome']);
  }

  goToMarket() {
    if (this.getLoggedIn() && this.leagueListService.getLeagues().length > 0)
      this.router.navigate(['/market']);
    else this.router.navigate(['/welcome']);
  }

  goToRanking() {
    if (this.getLoggedIn() && this.leagueListService.getLeagues().length > 0)
      this.router.navigate(['/ranking']);
    else this.router.navigate(['/welcome']);
  }

  goToMatchs() {
    if (this.getLoggedIn() && this.leagueListService.getLeagues().length > 0)
      this.router.navigate(['/matchs']);
    else this.router.navigate(['/welcome']);
  }

  goToTeam() {
    if (this.getLoggedIn() && this.leagueListService.getLeagues().length > 0)
      this.router.navigate(['/team']);
    else this.router.navigate(['/welcome']);
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }

  goToUserPage() {
    this.router.navigate(['/userpage']);
  }

  goToPlayersPage() {
    if (this.getLoggedIn() && this.leagueListService.getLeagues().length > 0)
      this.router.navigate(['/players']);
    else this.router.navigate(['/welcome']);
  }

  goToTeamsPrevisualizer() {
    this.router.navigate(['/visitorprevisualizer'], {
      queryParams: { type: 'team' },
    });
  }

  goToPlayersPrevisualizer() {
    this.router.navigate(['/visitorprevisualizer'], {
      queryParams: { type: 'player' },
    });
  }

  goToContact() {
    this.router.navigate(['/contact']);
  }
}
