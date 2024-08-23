import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { MatDialog } from '@angular/material/dialog';
import { ClausePlayerModalComponent } from './clause-player-modal/clause-player-modal.component';
import { CredentialsService } from '../credentials/credentials.service';

@Component({
  selector: 'app-playerinfo',
  templateUrl: './playerinfo.component.html',
  styleUrls: ['./playerinfo.component.scss'],
})
export class PlayerinfoComponent implements OnInit {
  @Input() playerIdInput: string | null = null;

  playerId: string | null = null;

  playerInfo: any = {};

  teamListInfo: any[] = [];
  private roleIconsCache: { [role: string]: boolean } = {};

  constructor(
    private route: ActivatedRoute,
    private appApiService: AppapiService,
    private leaguelistService: LeagueListServiceService,
    public router: Router,
    public dialog: MatDialog,
    public credentialsService: CredentialsService
  ) {}

  ngOnInit(): void {
    this.playerId = this.route.snapshot.paramMap.get('id');
    if (this.playerIdInput) {
      this.playerId = this.playerIdInput;
    }
    console.log('playerId is: ', this.playerId);

    let leagueUuid = this.leaguelistService.getSelectedLeagueUUID();

    console.log('leagueUuid is: ', leagueUuid);

    this.loadPlayerInfo(this.playerId, leagueUuid);
  }

  loadPlayerInfo(playerId, leagueUuid) {
    this.appApiService
      .getPlayerInfo(playerId, leagueUuid)
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
          ownerUUID: response.ownerUUID,
          ownerUsername: response.ownerUsername,
          ownerIcon: response.ownerIcon,
        };

        console.log(this.playerInfo);

        this.playerInfo.teamList.forEach((teamUuid: string) => {
          console.log('teamuuid is: ', teamUuid);
          this.appApiService.getTeamInfo(teamUuid).subscribe((response) => {
            this.teamListInfo.push({
              uuid: response.uuid,
              shortName: response.shortName,
              icon: response.icon,
            });
          });
        });
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

  navigateToTeamInfo(uuid: string) {
    this.router.navigate(['/teaminfo', uuid]);
  }

  clausePlayerOpenModal(update: boolean): void {
    const dialogRef = this.dialog.open(ClausePlayerModalComponent, {
      width: '800px',
      height: 'auto',
      data: {
        update: update,
        playerName: this.playerInfo.username,
        playerUUID: this.playerInfo.uuid,
        playerIcon: this.playerInfo.icon,
        clause: this.playerInfo.clause,
        ownerUUID: this.playerInfo.ownerUUID,
        ownerName: this.playerInfo.ownerUsername,
        ownerIcon: this.playerInfo.ownerIcon,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.teamListInfo = [];
      this.loadPlayerInfo(
        this.playerId,
        this.leaguelistService.getSelectedLeagueUUID()
      );
    });
  }
}
