import { TestBed } from '@angular/core/testing';

import { MatchsService } from './matchs.service';

describe('MatchsService', () => {
  let service: MatchsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MatchsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
