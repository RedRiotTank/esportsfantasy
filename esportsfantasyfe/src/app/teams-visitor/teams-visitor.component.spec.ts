import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamsVisitorComponent } from './teams-visitor.component';

describe('TeamsVisitorComponent', () => {
  let component: TeamsVisitorComponent;
  let fixture: ComponentFixture<TeamsVisitorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TeamsVisitorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TeamsVisitorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
