import { Component, OnInit } from '@angular/core';
import { AppapiService } from '../common/API/appapi.service';
import { CredentialsService } from '../credentials/credentials.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-join-league',
  templateUrl: './join-league.component.html',
  styleUrl: './join-league.component.scss'
})
export class JoinLeagueComponent implements OnInit{



  // DISPLAY-NAVIGATION
  activeIndex: number|number[] = 0;
  errorMessage :string = null;

  // DISPLAY-OPTIONS
  games: any[] = [];
  leagues: any[] = [];

  //FORM-DATA
  leagueType: number = null;  // 1 = create, 2 = join public, 3 = join private.
  gameType: string = null;
  competition :string = null; //uuid

    //configuration
    leagueName: string = null;
    clauseActive: number = 0;
    startType: number = 0;

  code: string = null;
  
  constructor(
    private credentialsService: CredentialsService,
    private appapi: AppapiService,
    private router: Router
  ) { }


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


  // DISPLAY-NAVIGATION

  getSelectGameDisabled() : boolean {
    
    if(this.leagueType == 1 || this.leagueType == 2) return false;

    return true;
  }

  getSelectCompetitionDisabled() : boolean {
    if(this.gameType == null){
      return true;
    }

    return false;
  }

  getSelectConfigurationDisabled() : boolean {
    if(this.competition == null || this.leagueType == 2 || this.leagueType == 3){
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
 

  // SELECTORS

  selectLeagueType(leagueType: number) {
    this.leagueType = leagueType;
    
    if(leagueType == 1 || leagueType == 2){
      this.activeIndex = 1;
      this.code = null;
      
      
     
    } else if(leagueType == 3){
      this.activeIndex = 4;
      this.gameType = null;
      this.competition = null;
    }

   // console.log(this.activeIndex);
    

  }

  selectGame(gameType: string) {
    this.gameType = gameType;
    this.activeIndex = 2;
    this.leagues = [];

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

    if(this.leagueType == 1)
      this.activeIndex = 3;
  }

  // CREATION

  checkFields() {
    
      
    if(this.leagueType == null){
      this.errorMessage = "Please select a league type.";
      return false;
    }

    if(this.leagueType == 3 && this.code == null){
      this.errorMessage = "Please introduce your friend's code.";
      return false;
    }

    if((this.leagueType == 1 || this.leagueType == 2) && this.gameType == null){
      this.errorMessage = "Please select a game.";
      return false;
    }


    if((this.leagueType == 1 || this.leagueType == 2) && this.competition == null){
      this.errorMessage = "Please select a competition.";
      return false;
    }

    if(this.leagueType == 1  && this.leagueName == null){
      this.errorMessage = "Please name your league.";
      return false;
    }

    return true;
    
  }

  createLeague(){
    this.errorMessage = null;

    const canContinue = this.checkFields();

    var clauseActiveBoolean;

    if(this.clauseActive == 1){
      clauseActiveBoolean = true;
    } else {
      clauseActiveBoolean = false;
    }

    const leagueData = {
      leagueType: this.leagueType,
      gameType: this.gameType,
      competition: this.competition,
      leagueName: this.leagueName,
      clauseActive: clauseActiveBoolean,
      startType: this.startType,
      code: this.code,
      userMail: this.credentialsService.getDecodedToken().sub
    };


    console.log("leaguedata", leagueData);
    
    
    if(canContinue){
      this.appapi.joinLeague(leagueData).subscribe(response => {
        console.log("res: ", response);
        if(response.status == 201){
          this.router.navigate(['/home']);
        } else {
          this.router.navigate(['/error']);
        }
      }, error => {
        console.log("error");
      });

    }
    
  }

    

}
