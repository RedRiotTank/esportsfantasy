import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TeamService } from '../team.service';

@Component({
  selector: 'app-selectplayer-modal',
  templateUrl: './selectplayer-modal.component.html',
  styleUrl: './selectplayer-modal.component.scss'
})
export class SelectplayerModalComponent {

  players: any = this.data.players;

  constructor(
    private teamService: TeamService,
    public dialogRef: MatDialogRef<SelectplayerModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ){}

  closeModal(): void {
    this.dialogRef.close(
      {number: 0}
    );
  }

  selectPlayer(player: any): void {
    
    this.teamService.setAlignment(player.uuid, this.data.pos);


    this.dialogRef.close(
      {number: player}
    );
  }

}
