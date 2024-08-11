import { Component, OnInit } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { CredentialsService } from '../credentials/credentials.service';
import { Router } from '@angular/router';

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
    private router: Router
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
    this.editing = true;
  }

  cancelEdit() {
    this.editing = false;
    this.password = '';
    this.fileToUpload = null;
    this.ngOnInit();
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
}
