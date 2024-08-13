import { Component } from '@angular/core';
import { TeamService } from '../../team/team.service';
import { MoneyService } from '../../common/money.service';
import { AppapiService } from '../../common/API/appapi.service';
import { MarketService } from '../market.service';
import { MatDialogRef } from '@angular/material/dialog';
import { LeagueListServiceService } from '../../league-list-service.service';

@Component({
  selector: 'app-sellplayer-modal',
  templateUrl: './sellplayer-modal.component.html',
  styleUrl: './sellplayer-modal.component.scss',
})
export class SellplayerModalComponent {
  constructor(
    public teamService: TeamService,
    public dialogRef: MatDialogRef<SellplayerModalComponent>,
    public moneyService: MoneyService,
    public appapiService: AppapiService,
    public marketService: MarketService,
    public leagueListService: LeagueListServiceService
  ) {}

  public value: number = 0;

  private players = this.teamService.getTeamPlayers();

  private selectedPlayer: any;

  public playerMinValue;

  lastJour: number;

  ngOnInit(): void {
    this.loadCurrentJour();
    if (this.players.length > 0) {
      this.clickSellPlayer(this.players[0]);
    }
  }

  getPlayers() {
    console.log(this.teamService.getTeamPlayers());
    console.log(this.lastJour);
    return this.teamService.getTeamPlayers();
  }

  clickSellPlayer(player: any) {
    this.selectedPlayer = player;

    this.playerMinValue = player.value;
    this.value = player.value;

    this.teamService.getTeamPlayers().forEach((p) => {
      p.selected = false;
    });

    player.selected = true;
  }

  sell() {
    this.marketService.sellPlayer(this.selectedPlayer.uuid, this.value);
    this.dialogRef.close();
  }

  loadCurrentJour() {
    this.appapiService
      .getRLeagueCurrentJour(this.leagueListService.getSelectedRLeagueUUID())
      .subscribe((response) => {
        this.lastJour = response.currentJour;
      });
  }
}
