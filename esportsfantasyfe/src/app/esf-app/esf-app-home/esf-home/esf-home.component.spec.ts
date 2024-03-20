import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EsfHomeComponent } from './esf-home.component';

describe('EsfHomeComponent', () => {
  let component: EsfHomeComponent;
  let fixture: ComponentFixture<EsfHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EsfHomeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EsfHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
