import { Component, OnInit } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { TeamService } from '../team/team.service';
import { RankingService } from './ranking.service';
import { UtilsService } from '../common/utils.service';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.scss'],
})
export class RankingComponent implements OnInit {
  constructor(
    private teamService: TeamService,
    private rankingService: RankingService,
    public utilsService: UtilsService
  ) {}

  ngOnInit(): void {
    this.rankingService.init();
  }

  getRankingService() {
    return this.rankingService;
  }

  getJourRanking() {
    return this.rankingService.jourRanking;
  }

  getTotalRanking() {
    return this.rankingService.totalRanking;
  }

  onSelectionChange(event: any) {
    this.rankingService.changeJour(event.value);
  }

  getTeamService() {
    return this.teamService;
  }

  getLeagueCurrentJourArray() {
    return Array.from(
      { length: this.teamService.currentJour },
      (_, i) => this.teamService.currentJour - i
    );
  }
}
