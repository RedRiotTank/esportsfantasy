import { Component, OnInit } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { CredentialsService } from '../credentials/credentials.service';
import { Router } from '@angular/router';
import { LeagueListServiceService } from '../league-list-service.service';
import { MatDialog } from '@angular/material/dialog';
import { LeaveLeagueModalComponent } from './leave-league-modal/leave-league-modal.component';

@Component({
  selector: 'app-userpage',
  templateUrl: './userpage.component.html',
  styleUrls: ['./userpage.component.scss'],
})
export class UserpageComponent implements OnInit {
  user: any = {};
  editing: boolean = false;
  password: string = '';
  fileToUpload: File | null = null;
  error: string = '';

  constructor(
    private appApiService: AppapiService,
    private credentialsService: CredentialsService,
    private router: Router,
    private leagueListService: LeagueListServiceService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.loaduser();
  }

  loaduser() {
    this.appApiService
      .getUserInfo(this.credentialsService.getUserUUID())
      .subscribe((data: any) => {
        this.user.uuid = data.uuid;
        this.user.mail = data.mail;
        this.user.username = data.username;
        this.user.icon = data.icon;
      });
  }

  editUser() {
    if (this.editing) {
      this.editing = false;
      this.password = '';
      this.fileToUpload = null;
      this.ngOnInit();
    } else this.editing = true;
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.fileToUpload = file;
      this.convertToBase64(file);
    }
  }

  convertToBase64(file: File) {
    const reader = new FileReader();
    reader.onload = () => {
      const base64String = (reader.result as string).split(',')[1];
      this.user.icon = base64String;
    };
    reader.readAsDataURL(file);
  }

  onSubmit() {
    if (this.password) {
      this.appApiService
        .updateUserInfo(
          this.user.uuid,
          this.user.mail,
          this.user.username,
          this.user.icon,
          this.password
        )
        .subscribe((response: any) => {
          if (response.success) {
            this.editing = false;
            this.password = '';
            this.fileToUpload = null;
          } else {
            this.error = response.message;
          }
        });
    } else {
      this.error = 'Please introduce your password.';
    }
  }

  logout() {
    this.credentialsService.logout();
    this.router.navigate(['/welcome']);
  }

  leaveLeagueOpenModal(league: any): void {
    const dialogRef = this.dialog.open(LeaveLeagueModalComponent, {
      width: '800px',
      height: 'auto',
      data: {
        league: league,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.leagueListService.clearLeagues();
      this.leagueListService.updateLeagueList();
    });
  }

  getLeagueList() {
    let leagues: any[] = this.leagueListService.getLeagues();

    return this.leagueListService.getLeagues();
  }
}
