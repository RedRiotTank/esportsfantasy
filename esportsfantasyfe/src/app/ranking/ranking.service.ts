import { Injectable } from '@angular/core';
import { TeamService } from '../team/team.service';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';

@Injectable({
  providedIn: 'root',
})
export class RankingService {
  public selectedJour: number = this.teamService.currentJour;

  public jourRanking: any;
  totalRanking: any;

  constructor(
    private teamService: TeamService,
    private appApiService: AppapiService,
    private leagueListService: LeagueListServiceService
  ) {}

  init() {
    this.teamService.loadCurrentJour().subscribe(() => {
      this.selectedJour = this.teamService.currentJour;
      this.loadRanking();
    });
  }

  changeJour(jour: number) {
    console.log(jour);
    this.selectedJour = jour;
    this.loadRanking();
  }

  loadRanking(): void {
    this.appApiService
      .getRanking(
        this.leagueListService.getSelectedLeagueUUID(),
        this.selectedJour
      )
      .subscribe(
        (response) => {
          this.jourRanking = response.jourRanking;
          this.totalRanking = response.totalRanking;
        },
        (error) => {
          console.log(error);
        }
      );
  }
}
