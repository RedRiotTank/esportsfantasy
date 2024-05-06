import { Injectable } from '@angular/core';
import { ApiService } from './API/api.service';
import { CredentialsService } from '../credentials/credentials.service';
import { LeagueListServiceService } from '../league-list-service.service';

@Injectable({
  providedIn: 'root'
})
export class MoneyService {

  constructor(
    private api: ApiService,
    private credentialsService: CredentialsService
  ) { }

  private money: number = 0;

  public setMoney(money: number){
    this.money = money;
  }

  public getMoney(){
    return this.money;
  }

  public updateMoney(leagueUuid: string){
    this.api.sendRequest('userxleague/getUserXLeagueMoney', {playeruuid: this.credentialsService.getUserUUID(), leagueuuid: leagueUuid}).subscribe(response => {
      this.money = response.money;
    });
  }

  public getMoneyWithFormat(){
    return this.money;
  }
}
