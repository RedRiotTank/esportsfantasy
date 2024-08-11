import { Injectable } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { TeamService } from '../team/team.service';
import { LeagueListServiceService } from '../league-list-service.service';

@Injectable({
  providedIn: 'root',
})
export class HomeService {
  public transferPosts: any[] = [];

  constructor(
    private appapiService: AppapiService,
    private teamService: TeamService,
    private leagueListService: LeagueListServiceService
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
            this.transferPosts = response.transferPosts;
          });
      });
    }
  }

  getTransferPosts() {
    return this.transferPosts;
  }
}
