import { Component, OnInit } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { LeagueListServiceService } from '../league-list-service.service';
import { MarketService } from './market.service';
import { MatDialog } from '@angular/material/dialog';
import { BidupModalComponent } from './bidup-modal/bidup-modal.component';


@Component({
  selector: 'app-market',
  templateUrl: './market.component.html',
  styleUrls: ['./market.component.scss'] // Aquí está el cambio
})
export class MarketComponent implements OnInit{

  

  constructor(
    private marketService: MarketService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.marketService.loadMarket();
  }

  getMarketPlayers(){
    return this.marketService.getMarketPlayers();
  }

  openModal(player: any): void {
    console.log(player);
    
    const dialogRef = this.dialog.open(BidupModalComponent, {
      width: '400px',
      data: { name: player.name, uuid: player.uuid, price: player.price}
    });

    dialogRef.afterClosed().subscribe(result => {
      
      if(result){
        this.marketService.bidUp(player.uuid, result.number).subscribe( () => {
          console.log("Bid up success");
          
        });
        
      }

    });
  }

}
