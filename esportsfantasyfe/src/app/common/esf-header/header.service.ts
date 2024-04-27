import { Injectable } from '@angular/core';
import { AppapiService } from '../API/appapi.service';
import { CredentialsService } from '../../credentials/credentials.service';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  private loggedIn: boolean = false;
  private pfpUrl: string;

  constructor(
  ) { }


  public getPfpUrl(){
    return this.pfpUrl;
  }

  public setPfpUrl(pfpUrl: string){
    this.pfpUrl = pfpUrl;
  }


  public getLoggedIn(){
    return this.loggedIn;
  }

  public setLoggedIn(loggedIn: boolean){
    this.loggedIn = loggedIn;
  }


}
