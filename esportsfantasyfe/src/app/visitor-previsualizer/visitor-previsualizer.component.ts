import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppapiService } from '../common/API/appapi.service';

@Component({
  selector: 'app-visitor-previsualizer',
  templateUrl: './visitor-previsualizer.component.html',
  styleUrl: './visitor-previsualizer.component.scss',
})
export class VisitorPrevisualizerComponent implements OnInit {
  type: string;

  // DISPLAY-NAVIGATION
  activeIndex: number | number[] = 0;
  errorMessage: string = null;

  games: any[] = [];
  leagues: any[] = [];
  teams: any[] = [];
  players: any[] = [];

  competition: string = null; //uuid
  team: string = null; //uuid
  player: string = null; //uuid

  gameType: string = null;

  constructor(private route: ActivatedRoute, private appapi: AppapiService) {}

  ngOnInit(): void {
    this.type = this.route.snapshot.queryParamMap.get('type');

    this.getGames();
  }

  getGames() {
    this.appapi.getGamesFromAPI().subscribe((games) => {
      games.forEach((game) => {
        this.appapi.getGameIcon(game).subscribe((icon) => {
          this.games.push({ game: game, icon: icon });
        });
      });
    });
  }

  selectGame(gameType: string) {
    this.competition = null;
    this.team = null;
    this.player = null;

    this.gameType = gameType;
    this.activeIndex = 1;

    this.appapi.getGameRLeagues(this.gameType).subscribe((leagues) => {
      this.leagues = [];

      leagues.forEach((league) => {
        this.appapi.getRLeagueIcon(league.Uuid).subscribe((icon) => {
          this.leagues.push({ competition: league, icon: icon });
        });
      });
    });
  }

  getSelectCompetitionDisabled(): boolean {
    if (this.gameType == null) {
      return true;
    }

    return false;
  }

  selectCompetition(competition: string) {
    this.team = null;
    this.player = null;

    this.competition = competition;

    this.activeIndex = 2;

    this.appapi.getRLeagueTeams(this.competition).subscribe((response) => {
      this.teams = [];
      this.teams = response.teams;
    });
  }

  getSelectTeamDisabled(): boolean {
    if (this.competition == null) {
      return true;
    }

    return false;
  }

  selectTeam(team: string) {
    this.player = null;

    if (this.type == 'team') {
      this.team = null;

      setTimeout(() => {
        this.team = team;
      }, 0);
    } else this.team = team;

    if (this.type == 'player') this.activeIndex = 3;

    this.appapi.getTeamInfo(this.team).subscribe((response) => {
      this.players = response.players;
    });
  }

  getSelectPlayerDisabled(): boolean {
    if (this.team == null) {
      return true;
    }

    return false;
  }

  selectPlayer(player: string) {
    this.player = null;
    setTimeout(() => {
      this.player = player;
    }, 0);
  }
}
