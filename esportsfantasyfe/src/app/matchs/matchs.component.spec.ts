import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchsComponent } from './matchs.component';

describe('MatchsComponent', () => {
  let component: MatchsComponent;
  let fixture: ComponentFixture<MatchsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MatchsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MatchsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
