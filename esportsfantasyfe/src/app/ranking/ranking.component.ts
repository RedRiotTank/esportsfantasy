import { Component, OnInit } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { TeamService } from '../team/team.service';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.scss'],
})
export class RankingComponent implements OnInit {
  public selectedJour: number = this.teamService.currentJour;

  jourRanking: any;
  totalRanking: any;

  constructor(
    private appApiService: AppapiService,
    private leagueListService: LeagueListServiceService,
    private teamService: TeamService
  ) {}

  ngOnInit(): void {
    this.loadRanking();
  }

  onSelectionChange(event: any) {
    this.selectedJour = event.value;
    this.loadRanking();
  }

  getLeagueCurrentJourArray() {
    return Array.from(
      { length: this.teamService.currentJour },
      (_, i) => this.teamService.currentJour - i
    );
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
