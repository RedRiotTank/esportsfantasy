import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EsfLogregRegisterSuccessComponent } from './esf-logreg-register-success.component';

describe('EsfLogregRegisterSuccessComponent', () => {
  let component: EsfLogregRegisterSuccessComponent;
  let fixture: ComponentFixture<EsfLogregRegisterSuccessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EsfLogregRegisterSuccessComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EsfLogregRegisterSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
