import { Component } from '@angular/core';
import { TeamService } from '../../team/team.service';

@Component({
  selector: 'app-sellplayer-modal',
  templateUrl: './sellplayer-modal.component.html',
  styleUrl: './sellplayer-modal.component.scss',
})
export class SellplayerModalComponent {
  constructor(public teamService: TeamService) {}

  ngOnInit(): void {}

  getPlayers() {
    console.log(this.teamService.getTeamPlayers());
    return this.teamService.getTeamPlayers();
  }
}
