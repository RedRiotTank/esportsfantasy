import { Component, OnInit } from '@angular/core';
import { TeamService } from './team.service';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrl: './team.component.scss'
})
export class TeamComponent implements OnInit{

  constructor(private teamService: TeamService) { }

  ngOnInit(): void {
    this.teamService.loadTeam();
  }

  getTeamPlayers(){
    return this.teamService.getTeamPlayers();
  }

}
