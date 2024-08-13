import { Injectable } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { CredentialsService } from '../credentials/credentials.service';
import { Observable } from 'rxjs';
import { MoneyService } from '../common/money.service';

@Injectable({
  providedIn: 'root',
})
export class MarketService {
  constructor(
    private appApiService: AppapiService,
    private leagueListService: LeagueListServiceService,
    private credentialsService: CredentialsService,
    private moneyService: MoneyService
  ) {}

  private marketPlayers: any[] = [];

  private offers: any[] = [];

  public loadMarket() {
    this.marketPlayers = [];

    this.loadOffers().subscribe((offers) => {
      this.offers = offers;
      console.log(offers);
    });

    this.appApiService
      .getMarketPlayers(
        this.leagueListService.getSelectedLeague().leagueUUID,
        this.credentialsService.getUserUUID()
      )
      .subscribe((players) => {
        //console.log(players);
        players.forEach((player) => {
          this.appApiService.getPlayerIcon(player.uuid).subscribe((icon) => {
            player.icon = icon;
          });

          this.appApiService
            .getPlayerTeamIcon(
              player.uuid,
              this.leagueListService.getSelectedLeague().rLeagueUUID
            )
            .subscribe((icon) => {
              player.teamicon = icon;
            });

          this.marketPlayers.push(player);
        });
      });
  }

  public getMarketPlayers() {
    return this.marketPlayers;
  }

  public bidUp(playerUUID: string, value: number) {
    return this.appApiService.bidPlayer(
      playerUUID,
      this.leagueListService.getSelectedLeagueUUID(),
      this.credentialsService.getUserUUID(),
      value
    );
  }

  public cancelBid(playerUUID: string) {
    this.appApiService
      .cancelBid(
        playerUUID,
        this.leagueListService.getSelectedLeagueUUID(),
        this.credentialsService.getUserUUID()
      )
      .subscribe(() => {
        this.loadMarket();
        this.moneyService.updateMoney(
          this.leagueListService.getSelectedLeagueUUID()
        );
      });
  }

  public sellPlayer(playerUUID: string, value: number) {
    return this.appApiService
      .sellPlayer(
        playerUUID,
        this.leagueListService.getSelectedLeagueUUID(),
        this.credentialsService.getUserUUID(),
        value
      )
      .subscribe((res) => {
        this.loadMarket();
      });
  }

  public cancelSell(playerUUID: string) {
    this.appApiService
      .cancelSell(
        playerUUID,
        this.leagueListService.getSelectedLeagueUUID(),
        this.credentialsService.getUserUUID()
      )
      .subscribe((res) => {
        this.loadMarket();
      });
  }

  public loadOffers() {
    return this.appApiService.getOffers(
      this.leagueListService.getSelectedLeagueUUID(),
      this.credentialsService.getUserUUID()
    );
  }

  public getOffers() {
    return this.offers;
  }

  public acceptOffer(offer: any) {
    this.appApiService.acceptOffer(offer).subscribe((res) => {
      this.loadMarket();
    });
  }

  public rejectOffer(offer: any) {
    this.appApiService.rejectOffer(offer).subscribe((res) => {
      this.loadMarket();
    });
  }
}
