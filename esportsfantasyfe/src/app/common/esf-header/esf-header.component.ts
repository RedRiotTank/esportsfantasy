import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppapiService } from '../API/appapi.service';
import { HeaderService } from './header.service';
import { CarouselModule } from 'ngx-owl-carousel-o';

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

  constructor(private headerService: HeaderService, private router: Router) {}

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

  goToHome() {
    if (this.getLoggedIn()) this.router.navigate(['/home']);
    else this.router.navigate(['/welcome']);
  }

  goToMarket() {
    if (this.getLoggedIn()) this.router.navigate(['/market']);
    else this.router.navigate(['/welcome']);
  }

  goToRanking() {
    if (this.getLoggedIn()) this.router.navigate(['/ranking']);
    else this.router.navigate(['/welcome']);
  }

  goToMatchs() {
    this.router.navigate(['/matchs']);
  }

  goToTeam() {
    if (this.getLoggedIn()) this.router.navigate(['/team']);
    else this.router.navigate(['/welcome']);
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }

  goToUserPage() {
    this.router.navigate(['/userpage']);
  }

  goToPlayersPage() {
    this.router.navigate(['/players']);
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
