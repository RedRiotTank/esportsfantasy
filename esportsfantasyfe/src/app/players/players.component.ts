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
  currentPage = 1;
  itemsPerPage = 10;
  totalItems = 0;
  paginatedPlayers: any[] = [];
  filteredPlayers: any[] = [];
  private roleIconsCache: { [role: string]: boolean } = {};
  isLoading = true;

  // Variables para los filtros
  selectedPositions: string[] = [];
  selectedTeams: string[] = [];

  // Orden descendente por defecto
  sortOrder: string = 'desc';

  constructor(
    public playersService: PlayersService,
    public router: Router,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.playersService.loadPlayers().subscribe(() => {
      this.totalItems = this.playersService.playerList.length;
      this.applyFilters(); // Aplica filtros y actualiza la paginación
      this.isLoading = false;
    });
  }

  applyFilters(): void {
    this.currentPage = 1;
    this.filteredPlayers = this.playersService.playerList.filter((player) => {
      const matchesPosition =
        this.selectedPositions.length === 0 ||
        this.selectedPositions.includes(player.role);
      const matchesTeam =
        this.selectedTeams.length === 0 ||
        player.teamList.some((team: any) => this.selectedTeams.includes(team));

      return matchesPosition && matchesTeam;
    });

    this.totalItems = this.filteredPlayers.length;

    // Aplicar ordenamiento si se ha seleccionado alguno
    this.applySort();
  }

  applySort(): void {
    if (this.sortOrder === 'asc') {
      this.filteredPlayers.sort(
        (a, b) => this.getTotalPoints(a) - this.getTotalPoints(b)
      );
    } else if (this.sortOrder === 'desc') {
      this.filteredPlayers.sort(
        (a, b) => this.getTotalPoints(b) - this.getTotalPoints(a)
      );
    }
    this.updatePaginatedPlayers();
  }

  updatePaginatedPlayers(): void {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.paginatedPlayers = this.filteredPlayers.slice(startIndex, endIndex);
  }

  goToPage(pageNumber: number): void {
    if (pageNumber >= 1 && pageNumber <= this.totalPages) {
      this.currentPage = pageNumber;
      this.updatePaginatedPlayers();
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

  get totalPages(): number {
    return Math.ceil(this.totalItems / this.itemsPerPage);
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

  getLast5Points(player: any): (number | string)[] {
    return player.playerPoints
      .slice(0, 5)
      .map((p: any) => (p.points !== undefined ? p.points : '-'));
  }

  getTotalPoints(player: any): number {
    return player.playerPoints.reduce((total: number, p: any) => {
      return total + (typeof p.points === 'number' ? p.points : 0);
    }, 0);
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

    this.selectedPositions.forEach((position) => {
      if (currentSelectedPositions[position]) {
        currentSelectedPositions[position].selected = true;
      }
    });

    this.selectedTeams.forEach((team) => {
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
        this.selectedPositions = Object.keys(result.selectedPositions).filter(
          (key) => result.selectedPositions[key]
        );

        this.selectedTeams = Object.keys(result.selectedTeams).filter(
          (key) => result.selectedTeams[key]
        );

        this.applyFilters();
      }
    });
  }
}