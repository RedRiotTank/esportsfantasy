import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistersuccessComponent } from './registersuccess.component';

describe('RegistersuccessComponent', () => {
  let component: RegistersuccessComponent;
  let fixture: ComponentFixture<RegistersuccessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegistersuccessComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegistersuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
