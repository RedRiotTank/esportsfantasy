import { Component, OnInit } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { MarketService } from './market.service';

@Component({
  selector: 'app-market',
  templateUrl: './market.component.html',
  styleUrl: './market.component.scss'
})
export class MarketComponent implements OnInit{

  

  constructor(
    private marketService: MarketService
  ) { }

  ngOnInit(): void {
    this.marketService.loadMarket();
  }

  getMarketPlayers(){
    return this.marketService.getMarketPlayers();
  }

}
