import { Component, OnInit } from '@angular/core';
import { PlayersService } from './players.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { FilterModalComponent } from './filter-modal/filter-modal.component';

@Component({
  selector: 'app-players',
  templateUrl: './players.component.html',
  styleUrls: ['./players.component.scss'],
})
export class PlayersComponent implements OnInit {
  private roleIconsCache: { [role: string]: boolean } = {};

  constructor(
    public playersService: PlayersService,
    public router: Router,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.playersService.load();
  }

  goToPage(pageNumber: number): void {
    if (pageNumber >= 1 && pageNumber <= this.playersService.totalPages) {
      this.playersService.currentPage = pageNumber;
      this.playersService.updatePaginatedPlayers();
    }
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

  getPointsClass(points: number | string): string {
    if (typeof points === 'string' && points === '-') {
      return 'points-gray';
    } else if (typeof points === 'number') {
      if (points > 50) {
        return 'points-green';
      } else if (points > 30) {
        return 'points-orange';
      } else if (points > 0) {
        return 'points-red';
      } else {
        return 'points-gray';
      }
    }
    return 'points-gray';
  }

  gotoPlayerInfo(uuid: string) {
    this.router.navigate(['/playerinfo', uuid]);
  }

  gotoTeamInfo(uuid: string) {
    this.router.navigate(['/teaminfo', uuid]);
  }

  openFilterModal(): void {
    const currentSelectedPositions = { ...this.playersService.posFilter };
    const currentSelectedTeams = { ...this.playersService.teamFilter };

    this.playersService.selectedPositions.forEach((position) => {
      if (currentSelectedPositions[position]) {
        currentSelectedPositions[position].selected = true;
      }
    });

    this.playersService.selectedTeams.forEach((team) => {
      if (currentSelectedTeams[team]) {
        currentSelectedTeams[team].selected = true;
      }
    });

    const dialogRef = this.dialog.open(FilterModalComponent, {
      width: '400px',
      data: {
        positions: currentSelectedPositions,
        teams: currentSelectedTeams,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.playersService.selectedPositions = Object.keys(
          result.selectedPositions
        ).filter((key) => result.selectedPositions[key]);

        this.playersService.selectedTeams = Object.keys(
          result.selectedTeams
        ).filter((key) => result.selectedTeams[key]);

        this.playersService.applyFilters();
      }
    });
  }
}
