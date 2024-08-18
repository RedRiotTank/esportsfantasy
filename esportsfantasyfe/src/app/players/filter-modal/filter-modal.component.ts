import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

interface Filter {
  icon: string;
  selected: boolean;
}

@Component({
  selector: 'app-filter-modal',
  templateUrl: './filter-modal.component.html',
  styleUrls: ['./filter-modal.component.scss'],
})
export class FilterModalComponent {
  public selectedPositions: { [key: string]: boolean } = {};
  public selectedTeams: { [key: string]: boolean } = {};

  constructor(
    public dialogRef: MatDialogRef<FilterModalComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: {
      positions: { [key: string]: Filter };
      teams: { [key: string]: Filter };
    }
  ) {
    // Inicializar los valores seleccionados según los datos recibidos
    for (const key in data.positions) {
      this.selectedPositions[key] = false; // Inicializa como no seleccionados
    }

    for (const key in data.teams) {
      this.selectedTeams[key] = data.teams[key].selected;
    }
  }

  toggleSelection(type: 'position' | 'team', key: string): void {
    if (type === 'position') {
      this.selectedPositions[key] = !this.selectedPositions[key];
    } else if (type === 'team') {
      this.selectedTeams[key] = !this.selectedTeams[key];
    }
  }

  closeModal(): void {
    this.dialogRef.close({
      selectedPositions: this.selectedPositions,
      selectedTeams: this.selectedTeams,
    });
  }
}
