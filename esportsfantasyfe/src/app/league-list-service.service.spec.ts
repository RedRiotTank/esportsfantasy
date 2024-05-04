import { TestBed } from '@angular/core/testing';

import { LeagueListServiceService } from './league-list-service.service';

describe('LeagueListServiceService', () => {
  let service: LeagueListServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LeagueListServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
