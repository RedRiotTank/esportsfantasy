import { Injectable } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { TeamService } from '../team/team.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { MatchsService } from '../matchs/matchs.service';

@Injectable({
  providedIn: 'root',
})
export class HomeService {
  public transferPosts: any[] = [];
  public closestEvent: any = {};

  constructor(
    private appapiService: AppapiService,
    private teamService: TeamService,
    private leagueListService: LeagueListServiceService,
    private matchsService: MatchsService
  ) {}

  loadHome() {
    if (this.leagueListService.getSelectedLeagueIndex() == 0) {
      this.leagueListService.updateLeagueListObs().subscribe(() => {
        this.teamService.loadCurrentJour().subscribe(() => {
          this.appapiService
            .getLeagueTransferPosts(
              this.leagueListService.getSelectedLeagueUUID()
            )
            .subscribe((response) => {
              this.getCloseEvent();
              this.transferPosts = response.transferPosts;
            });
        });
      });
    } else {
      this.teamService.loadCurrentJour().subscribe(() => {
        this.appapiService
          .getLeagueTransferPosts(
            this.leagueListService.getSelectedLeagueUUID()
          )
          .subscribe((response) => {
            this.getCloseEvent();
            this.transferPosts = response.transferPosts;
          });
      });
    }
  }

  public getCloseEvent() {
    return this.appapiService
      .getCloseEvent(this.leagueListService.getSelectedRLeagueUUID())
      .subscribe((response) => {
        this.closestEvent = response;
      });
  }

  getTransferPosts() {
    return this.transferPosts;
  }
}
