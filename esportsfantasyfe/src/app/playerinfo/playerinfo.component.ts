import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';

@Component({
  selector: 'app-playerinfo',
  templateUrl: './playerinfo.component.html',
  styleUrls: ['./playerinfo.component.scss'],
})
export class PlayerinfoComponent implements OnInit {
  playerId: string | null = null;

  playerInfo: any = {};

  private roleIconsCache: { [role: string]: boolean } = {};

  constructor(
    private route: ActivatedRoute,
    private appApiService: AppapiService,
    private leaguelistService: LeagueListServiceService
  ) {}

  ngOnInit(): void {
    this.playerId = this.route.snapshot.paramMap.get('id');

    let leagueUuid = this.leaguelistService.getSelectedLeagueUUID();

    this.appApiService
      .getPlayerInfo(this.playerId, leagueUuid)
      .subscribe((response) => {
        this.playerInfo = {
          uuid: response.uuid,
          username: response.name,
          icon: response.icon,
          fullName: response.fullName,
          price: response.price,
          clause: response.clause,
          role: response.role,
          teamList: response.teamsList,
          playerPoints: response.playerPoints.map((point) => ({
            event: point.event.replace(/_/g, ' '),
            points: point.points,
          })),
          ownerUsername: response.ownerUsername,
          ownerIcon: response.ownerIcon,
        };
      });
  }

  checkImageExists(player: any): boolean {
    const role = player.role;

    if (this.roleIconsCache[role] !== undefined) {
      return this.roleIconsCache[role];
    }

    const imgPath = `assets/positions-icons/${role}.webp`;
    const http = new XMLHttpRequest();
    http.open('HEAD', imgPath, false);
    http.send();
    const exists = http.status !== 404;
    this.roleIconsCache[role] = exists;

    return exists;
  }
}
