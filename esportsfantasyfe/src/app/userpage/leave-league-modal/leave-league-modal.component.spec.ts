import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaveLeagueModalComponent } from './leave-league-modal.component';

describe('LeaveLeagueModalComponent', () => {
  let component: LeaveLeagueModalComponent;
  let fixture: ComponentFixture<LeaveLeagueModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LeaveLeagueModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LeaveLeagueModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
