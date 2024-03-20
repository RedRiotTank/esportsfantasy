import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EsfInitComponent } from './esf-init.component';

describe('EsfInitComponent', () => {
  let component: EsfInitComponent;
  let fixture: ComponentFixture<EsfInitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EsfInitComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EsfInitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
