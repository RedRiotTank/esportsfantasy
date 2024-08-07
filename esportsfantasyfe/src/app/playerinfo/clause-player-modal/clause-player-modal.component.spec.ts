import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClausePlayerModalComponent } from './clause-player-modal.component';

describe('ClausePlayerModalComponent', () => {
  let component: ClausePlayerModalComponent;
  let fixture: ComponentFixture<ClausePlayerModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ClausePlayerModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClausePlayerModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
