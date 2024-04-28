import { Component, OnInit } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';


@Component({
  selector: 'app-join-league',
  templateUrl: './join-league.component.html',
  styleUrl: './join-league.component.scss'
})
export class JoinLeagueComponent implements OnInit{

  activeIndex: number = 0;
  leagueType: number = null;  // 1 = create, 2 = join public, 3 = join private.
  code: string = null;
  gameType: string = null;
  games: any[] = [];
  leagues: any[] = [];




  // DISPLAY




  //FORM-DATA
  competition :string = null; //uuid

  leagueName: string = '';
  clauseActive: string = 'yes';
  startType: string = 'money';



  constructor(private appapi: AppapiService) { }


  ngOnInit(): void {

    this.appapi.getGamesFromAPI().subscribe(
      games => {
        games.forEach(game => {
          this.appapi.getGameIcon(game).subscribe(icon => {
            this.games.push({game: game, icon: icon});
          });

        });  


      }
    );
    

  }






  getSelectGameDisabled() : boolean {
    //console.log(this.leagueType);
    
    if(this.leagueType == 1 || this.leagueType == 2){
      return false;
    }

    return true;
  }

  getSelectCompetitionDisabled() : boolean {
    if(this.gameType == null){
      return true;
    }

    return false;
  }

  getSelectConfigurationDisabled() : boolean {
    if(this.competition == null){
      return true;
    }

    return false;
  }

  getConfigurationDisabled() : boolean {
    return true;
  }

  getCodeDisabled() : boolean {
    if(this.leagueType == 3){
      return false;
    }

    return true;
  }
 

  selectLeagueType(leagueType: number) {
    this.leagueType = leagueType;
    
    if(leagueType == 1 || leagueType == 2){
      this.activeIndex = 1;
      
      
     
    } else if(leagueType == 3){
      this.activeIndex = 4;
    }

   // console.log(this.activeIndex);
    

  }

  jj(){
    console.log(this.leagues);
    
  }

  selectGame(gameType: string) {
    this.gameType = gameType;
    this.activeIndex = 2;

    this.appapi.getCompetitions(this.gameType).subscribe(response =>{
      response.leagues.forEach(competition => {
        this.appapi.getLeagueIcon(competition.Uuid).subscribe(icon =>{
          this.leagues.push({competition: competition, icon: icon});
        })
      });
    });

  }

  selectCompetition(competition : string){
    this.competition = competition;
    this.activeIndex = 3;

    console.log("comp: ", this.competition);
    
      
  }

    

}
