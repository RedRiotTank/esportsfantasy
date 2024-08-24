import { Component } from '@angular/core';
import { MatchsService } from './matchs.service';
import { UtilsService } from '../common/utils.service';

@Component({
  selector: 'app-matchs',
  templateUrl: './matchs.component.html',
  styleUrl: './matchs.component.scss',
})
export class MatchsComponent {
  constructor(
    private matchsService: MatchsService,
    public utilsService: UtilsService
  ) {}

  ngOnInit(): void {
    this.matchsService.loadMatchs();
  }

  getMatchs() {
    return this.matchsService.matchs;
  }

  getJours() {
    return this.matchsService.jours;
  }
}
