import { Component, OnInit } from '@angular/core';
import { MarketService } from './market.service';
import { MatDialog } from '@angular/material/dialog';
import { BidupModalComponent } from './bidup-modal/bidup-modal.component';
import { SellplayerModalComponent } from './sellplayer-modal/sellplayer-modal.component';
import { TeamService } from '../team/team.service';
import { CredentialsService } from '../credentials/credentials.service';
import { LeagueListServiceService } from '../league-list-service.service';

@Component({
  selector: 'app-market',
  templateUrl: './market.component.html',
  styleUrls: ['./market.component.scss'],
})
export class MarketComponent implements OnInit {
  private roleIconsCache: { [role: string]: boolean } = {};

  constructor(
    private marketService: MarketService,
    public dialog: MatDialog,
    public teamService: TeamService,
    public credentialsService: CredentialsService,
    public leagueListService: LeagueListServiceService
  ) {}

  ngOnInit(): void {
    this.marketService.loadMarket();
    this.teamService.loadTeam();
  }

  getMarketPlayers() {
    return this.marketService.getMarketPlayers();
  }

  openModal(player: any): void {
    const dialogRef = this.dialog.open(BidupModalComponent, {
      width: '400px',
      data: { name: player.name, uuid: player.uuid, price: player.price },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.marketService.bidUp(player.uuid, result.number).subscribe(() => {
          //console.log("Bid up success");
        });
      }
    });
  }

  sellPlayerOpenModal(): void {
    const dialogRef = this.dialog.open(SellplayerModalComponent, {
      width: '800px',
      height: 'auto',
      data: { name: 'Sell Player', uuid: '', price: 0 },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.teamService.getTeamPlayers().forEach((p) => {
        if (p.selected) p.selected = false;
      });
    });
  }

  checkImageExists(player: any): boolean {
    const role = player.role;

    if (this.roleIconsCache[role] !== undefined) {
      return this.roleIconsCache[role];
    }

    const imgPath = `assets/positions-icons/${role}.webp`;
    const http = new XMLHttpRequest();
    http.open('HEAD', imgPath, false);
    http.send();
    const exists = http.status !== 404;
    this.roleIconsCache[role] = exists;

    return exists;
  }

  cancelSell(player: any) {
    this.marketService.cancelSell(player.uuid);
  }

  getOffers() {
    console.log('getOffers', this.marketService.getOffers());
    return this.marketService.getOffers();
  }

  acceptOffer(offer: any) {
    let dOffer = {
      playeruuid: offer.playerUuid,
      leagueuuid: this.leagueListService.getSelectedLeagueUUID(),
      useruuid: this.credentialsService.getUserUUID(),
      biduseruuid: offer.userUuid,
      jour: this.teamService.currentJour,
    };

    this.marketService.acceptOffer(dOffer);
  }

  rejectOffer(offer: any) {
    let dOffer = {
      playeruuid: offer.playerUuid,
      leagueuuid: this.leagueListService.getSelectedLeagueUUID(),
      useruuid: this.credentialsService.getUserUUID(),
      biduseruuid: offer.userUuid,
      jour: this.teamService.currentJour,
    };

    this.marketService.rejectOffer(dOffer);
  }
}
