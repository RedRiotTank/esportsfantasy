import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EsfErrorComponent } from './esf-error.component';

describe('EsfErrorComponent', () => {
  let component: EsfErrorComponent;
  let fixture: ComponentFixture<EsfErrorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EsfErrorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EsfErrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
