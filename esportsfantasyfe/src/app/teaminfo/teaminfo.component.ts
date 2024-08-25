import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppapiService } from '../common/API/appapi.service';

@Component({
  selector: 'app-teaminfo',
  templateUrl: './teaminfo.component.html',
  styleUrl: './teaminfo.component.scss',
})
export class TeaminfoComponent {
  @Input() teamIdInput: string | null = null;
  teamId: string | null = null;

  teamInfo: any = {};
  private roleIconsCache: { [role: string]: boolean } = {};

  constructor(
    private route: ActivatedRoute,
    public router: Router,
    private appApiService: AppapiService
  ) {}

  ngOnInit(): void {
    this.teamId = this.route.snapshot.paramMap.get('id');

    if (this.teamIdInput) {
      this.teamId = this.teamIdInput;
    }

    this.appApiService.getTeamInfo(this.teamId).subscribe((response) => {
      this.teamInfo = {
        uuid: response.uuid,
        name: response.name,
        shortName: response.shortName,
        game: response.game,
        icon: response.icon,
        players: response.players,
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
  gotoPlayerInfo(uuid: string) {
    this.router.navigate(['/playerinfo', uuid]);
  }
}
