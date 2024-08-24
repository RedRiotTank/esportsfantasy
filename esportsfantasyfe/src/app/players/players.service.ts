import { Injectable } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { tap } from 'rxjs';

interface Filter {
  icon: string;
  selected: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class PlayersService {
  public playerList: any[] = [];
  public teamicons: { [key: string]: string } = {};

  public posFilter: { [key: string]: Filter } = {};
  public teamFilter: { [key: string]: Filter } = {};

  itemsPerPage = 10;
  totalItems = 0;
  isLoading = true;
  currentPage = 1;

  paginatedPlayers: any[] = [];
  filteredPlayers: any[] = [];
  // Variables para los filtros
  selectedPositions: string[] = [];
  selectedTeams: string[] = [];

  // Orden descendente por defecto
  sortOrder: string = 'desc';

  constructor(
    public appApiService: AppapiService,
    public leagueListService: LeagueListServiceService
  ) {}

  loadPlayers() {
    return this.appApiService
      .getAllPlayers(this.leagueListService.getSelectedLeagueUUID())
      .pipe(
        tap((data) => {
          this.playerList = data.players;

          for (const player of this.playerList) {
            player.teamList.forEach((team: any) => {
              if (!(team in this.teamicons)) {
                this.teamicons[team] = 'working';
                this.appApiService.getTeamIcon(team).subscribe((i: any) => {
                  this.teamFilter[team] = {
                    icon: i,
                    selected: false,
                  };

                  this.teamicons[team] = i;
                });
              }

              if (!(player.role in this.posFilter)) {
                this.posFilter[player.role] = {
                  icon: player.role,
                  selected: false,
                };
              }
            });
          }
        })
      );
  }

  public load() {
    this.loadPlayers().subscribe(() => {
      this.totalItems = this.playerList.length;
      this.applyFilters();
      this.isLoading = false;
    });
  }

  applyFilters(): void {
    this.currentPage = 1;
    this.filteredPlayers = this.playerList.filter((player) => {
      const matchesPosition =
        this.selectedPositions.length === 0 ||
        this.selectedPositions.includes(player.role);
      const matchesTeam =
        this.selectedTeams.length === 0 ||
        player.teamList.some((team: any) => this.selectedTeams.includes(team));

      return matchesPosition && matchesTeam;
    });

    this.totalItems = this.filteredPlayers.length;

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

  updatePaginatedPlayers(): void {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.paginatedPlayers = this.filteredPlayers.slice(startIndex, endIndex);
  }

  get totalPages(): number {
    return Math.ceil(this.totalItems / this.itemsPerPage);
  }
}
